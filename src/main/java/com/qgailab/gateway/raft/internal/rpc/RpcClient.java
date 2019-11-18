package com.qgailab.gateway.raft.internal.rpc;

/**
 *
 * @author linxu
 */
public interface RpcClient {

    Response send(Request request);

    Response send(Request request, int timeout);
}
