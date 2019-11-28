package com.qgailab.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.qgailab.gateway.config.ServiceManager;
import com.qgailab.gateway.model.ArbitrateResult;
import com.qgailab.gateway.model.Proposal;
import com.qgailab.gateway.model.ProposalSolveResult;
import com.qgailab.gateway.model.ServiceInfo;
import com.qgailab.gateway.raft.client.ClientKVAck;
import com.qgailab.gateway.raft.client.ClientKVReq;
import com.qgailab.gateway.raft.internal.RaftNodeBootStrap;
import com.qgailab.gateway.raft.internal.common.Peer;
import com.qgailab.gateway.raft.internal.common.PeerSet;
import com.qgailab.gateway.raft.internal.entity.Command;
import com.qgailab.gateway.raft.internal.rpc.DefaultRpcClient;
import com.qgailab.gateway.raft.internal.rpc.Request;
import com.qgailab.gateway.raft.internal.rpc.Response;
import com.qgailab.gateway.raft.internal.rpc.RpcClient;
import com.qgailab.gateway.service.Arbitration;
import com.qgailab.gateway.service.ProposalService;
import com.qgailab.gateway.util.DaoUtil;
import com.qgailab.gateway.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 仲裁会的默认实现
 */
@Slf4j
@Service
public class DefaultArbitrationImpl implements Arbitration {
    /**
     * 信鸽
     */
    private final RpcClient PIGEON = new DefaultRpcClient();

    @Autowired
    private ProposalService proposalService;
    @Autowired
    private RaftNodeBootStrap raftNodeBootStrap;

    @Override
    public boolean propose(Proposal proposal, boolean isRedirect) {
        if (proposal == null) {
            throw new NullPointerException();
        }
        //如果是我是主席，向我提出的直接处理
        if (PeerSet.getInstance().getSelf().equals(PeerSet.getInstance().getLeader())/* && !isRedirect*/) {
            //TODO
            return false;
        } else {
            //将提案发送给仲裁会主席
            return redirectProposal(proposal);
        }
    }

    @Override
    public boolean accept() {
        return true;
    }

    @Override
    public boolean reject() {
        return false;
    }

    private boolean redirectProposal(Proposal proposal) {
        Peer peer = PeerSet.getInstance().getLeader();
        if (peer != null) {
            ClientKVReq obj = ClientKVReq.newBuilder()
                    .key("proposal")
                    .value(JSON.toJSONString(proposal))
                    .type(ClientKVReq.PROPOSE)
                    .build();
            Request<ClientKVReq> clientKVReqRequest = new Request<>();
            clientKVReqRequest.setObj(obj);
            clientKVReqRequest.setCmd(Request.CLIENT_REQ);
            clientKVReqRequest.setUrl(peer.getAddr());
            Response<String> response;
            try {
                response = PIGEON.send(clientKVReqRequest);
                return Response.ok().equals(response.getResult());
            } catch (Exception e) {
                //retry.
                response = PIGEON.send(clientKVReqRequest);
                return Response.ok().equals(response.getResult());
            }
        }
        //打印转发失败
        log.error("follower redirect the proposal fail!");
        return false;
    }

    @Override
    public ArbitrateResult arbitrate(Proposal proposal) {
        ArbitrateResult arbitrateResult = new ArbitrateResult();
        //处理接入提案
        if (Proposal.Type.ACCESS_APPROVAL.equals(proposal.getType())) {
            //验证是否可以接入
            ProposalSolveResult result = proposalService.solveAccess(proposal);
            //提案处理成功
            if (result.getResult().equals(ProposalSolveResult.Result.OK)) {
                List<String> addrs = raftNodeBootStrap.getAddressList();
                Response<ClientKVAck> response = DaoUtil.get(PIGEON, ServiceManager.SERVICES_NAMESPACE, addrs);
                List serviceInfoList = ParseUtil.parseToListServiceInfo(response);
                if (serviceInfoList == null) {
                    //这种情况应当是属于系统没有任何接入服务
                    serviceInfoList = new LinkedList<>();
                    serviceInfoList.add((ServiceInfo) proposal.getObj());
                    DaoUtil.set(PIGEON, ServiceManager.SERVICES_NAMESPACE, JSON.toJSONString(serviceInfoList), addrs);
                    arbitrateResult.setAccept(true);
                    //TODO 修改为常量
                    arbitrateResult.setMessage("access success!");
                } else {
                    //已经有了  那个更新列表,但是要先判断一下服务是否存在
                    for (Object s : serviceInfoList) {
                        if (((ServiceInfo) proposal.getObj()).equals(s)) {
                            arbitrateResult.setAccept(true);
                            //TODO 修改为常量
                            arbitrateResult.setMessage("access renew!");
                            //由于已经存在这个服务，因此更新一下服务的时间即可;
                            serviceInfoList.remove(s);
                            serviceInfoList.add(proposal.getObj());
                            DaoUtil.set(PIGEON, ServiceManager.SERVICES_NAMESPACE, JSON.toJSONString(serviceInfoList), addrs);
                            return arbitrateResult;
                        }
                    }
                    //服务不存在的情况下
                    serviceInfoList.add((ServiceInfo) proposal.getObj());
                    DaoUtil.set(PIGEON, ServiceManager.SERVICES_NAMESPACE, JSON.toJSONString(serviceInfoList), addrs);
                    //TODO here 暂时忽略设置结果
                }
                //提案递交超时
            } else if (result.getResult().equals(ProposalSolveResult.Result.TIMEOUT)) {
                //处理结果
                arbitrateResult.setMessage("proposal timeout!");
                arbitrateResult.setAccept(false);
            }

        } else if (Proposal.Type.EVICT_APPROVAL.equals(proposal.getType())) {

        } else if (Proposal.Type.RENEW_APPROVAL.equals(proposal.getType())) {

        } else {

        }
        return arbitrateResult;
    }
}
