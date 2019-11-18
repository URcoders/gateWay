package com.qgailab.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.qgailab.gateway.config.ServiceManager;
import com.qgailab.gateway.dto.ResponseData;
import com.qgailab.gateway.enums.Status;
import com.qgailab.gateway.model.Proposal;
import com.qgailab.gateway.model.ServiceInfo;
import com.qgailab.gateway.raft.internal.RaftNodeBootStrap;
import com.qgailab.gateway.raft.internal.entity.Command;
import com.qgailab.gateway.raft.internal.rpc.DefaultRpcClient;
import com.qgailab.gateway.service.Arbitration;
import com.qgailab.gateway.util.DaoUtil;
import com.qgailab.gateway.util.ParseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 这是仲裁会的控制器，处理仲裁会事务。
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class ArbitrationController {
    @Autowired
    private Arbitration arbitration;

    @Autowired
    private RaftNodeBootStrap bootStrap;

    /**
     * 提供给服务接入的
     * @param serviceInfo
     * @return
     */
    @PostMapping("/access")
    public String goAccess(@RequestBody ServiceInfo serviceInfo) {
        //TODO  linxu
        serviceInfo.setExpire(System.currentTimeMillis() + 20000);
        Proposal<ServiceInfo> proposal = new Proposal<ServiceInfo>();
        proposal.setProposeTime(System.currentTimeMillis());
        proposal.setObj(serviceInfo);
        proposal.setType(Proposal.Type.ACCESS_APPROVAL);
        return arbitration.arbitrate(proposal) ? "OK" : "ERROR";
    }

    @GetMapping("/queryService")
    public ResponseData queryServices() {
        ResponseData responseData = new ResponseData();
        responseData.setServiceList(ParseUtil.parseToListServiceInfo(
                DaoUtil.get(new DefaultRpcClient(), ServiceManager.SERVICES_NAMESPACE, bootStrap.getAddressList())
        ));
        //所有东西暂时取消TOKEN认证，后续再补充
        responseData.setStatus(Status.OP_OK.getValue());
        return responseData;
    }


    //TODO here 钰朝  郭沛
}
