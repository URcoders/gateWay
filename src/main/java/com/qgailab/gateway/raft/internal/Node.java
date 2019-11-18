package com.qgailab.gateway.raft.internal;


import com.qgailab.gateway.raft.client.ClientKVAck;
import com.qgailab.gateway.raft.client.ClientKVReq;
import com.qgailab.gateway.raft.internal.common.NodeConfig;
import com.qgailab.gateway.raft.internal.entity.AentryParam;
import com.qgailab.gateway.raft.internal.entity.AentryResult;
import com.qgailab.gateway.raft.internal.entity.RvoteParam;
import com.qgailab.gateway.raft.internal.entity.RvoteResult;


/**
 *
 * @author linxu
 */
public interface Node<T> extends LifeCycle{

    /**
     * 设置配置文件.
     *
     * @param config
     */
    void setConfig(NodeConfig config);

    /**
     * 处理请求投票 RPC.
     *
     * @param param
     * @return
     */
    RvoteResult handlerRequestVote(RvoteParam param);

    /**
     * 处理附加日志请求.
     *
     * @param param
     * @return
     */
    AentryResult handlerAppendEntries(AentryParam param);

    /**
     * 处理客户端请求.
     *
     * @param request
     * @return
     */
    ClientKVAck handlerClientRequest(ClientKVReq request);

    /**
     * 转发给 leader 节点.
     * @param request
     * @return
     */
    ClientKVAck redirect(ClientKVReq request);

}
