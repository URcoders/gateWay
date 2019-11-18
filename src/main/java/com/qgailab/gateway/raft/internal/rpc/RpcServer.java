package com.qgailab.gateway.raft.internal.rpc;

/**
 * @author linxu
 */
public interface RpcServer {

    void start();

    void stop();

    Response handlerRequest(Request request);

}
