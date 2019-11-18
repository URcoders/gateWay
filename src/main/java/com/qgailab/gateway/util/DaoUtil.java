package com.qgailab.gateway.util;

import com.qgailab.gateway.raft.client.ClientKVAck;
import com.qgailab.gateway.raft.client.ClientKVReq;
import com.qgailab.gateway.raft.internal.rpc.Request;
import com.qgailab.gateway.raft.internal.rpc.Response;
import com.qgailab.gateway.raft.internal.rpc.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author linxu
 * @date 2019/11/18
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 这个工具是用于对分布式ROCK数据进行查询、更新、删除
 */
@Slf4j
public class DaoUtil {
    private static final Random RANDOM = new Random();

    /**
     * @param client RPC客户端
     * @param key    KEY
     * @param urls   PEERS
     * @return 一个包含结果的序列化字符串
     * @throws NullPointerException 抛出异常
     */
    public static Response<ClientKVAck> get(RpcClient client, String key, List<String> urls) throws NullPointerException {
        //内部构造GET请求
        ClientKVReq req = ClientKVReq.newBuilder().key(key).type(ClientKVReq.GET).build();
        Request<ClientKVReq> clientKVReqRequest = new Request<>();
        clientKVReqRequest.setUrl(urls.get(randomChoose(urls.size())));
        clientKVReqRequest.setCmd(Request.CLIENT_REQ);
        clientKVReqRequest.setObj(req);
        Response<ClientKVAck> response;
        try {
            response = client.send(clientKVReqRequest);
            return response;
        } catch (Exception e) {
            //retry.
            response = client.send(clientKVReqRequest);
            return response;
        }
    }

    public static Response<String> delete() {
        return null;
    }

    /**
     * set如果key相同，允许覆盖
     *
     * @param client RPC客户端
     * @param key    键
     * @param value  序列化值
     * @param urls   PEERS
     * @return 设置结果
     */
    public static Response<ClientKVAck> set(RpcClient client, String key, String value, List<String> urls) throws NullPointerException {
        ClientKVReq req = ClientKVReq.newBuilder().key(key).value(value).type(ClientKVReq.PUT).build();
        Request<ClientKVReq> clientKVReqRequest = new Request<>();
        clientKVReqRequest.setUrl(urls.get(randomChoose(urls.size())));
        clientKVReqRequest.setCmd(Request.CLIENT_REQ);
        clientKVReqRequest.setObj(req);
        Response<ClientKVAck> response;
        try {
            response = client.send(clientKVReqRequest);
            return response;
        } catch (Exception e) {
            //retry.
            log.error("set occurred error! tips:{}", e.getMessage());
            response = client.send(clientKVReqRequest);
            return response;
        }
    }

    private static int randomChoose(int max) {
        return RANDOM.nextInt(max);
    }
}
