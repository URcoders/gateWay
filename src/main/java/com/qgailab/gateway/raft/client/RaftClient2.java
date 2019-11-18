package com.qgailab.gateway.raft.client;

import java.util.List;

import com.google.common.collect.Lists;


import com.qgailab.gateway.raft.internal.entity.LogEntry;
import com.qgailab.gateway.raft.internal.rpc.DefaultRpcClient;
import com.qgailab.gateway.raft.internal.rpc.Request;
import com.qgailab.gateway.raft.internal.rpc.Response;
import com.qgailab.gateway.raft.internal.rpc.RpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public class RaftClient2 {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaftClient.class);


    private final static RpcClient client = new DefaultRpcClient();

    static String addr = "localhost:8778";
    static List<String> list3 = Lists.newArrayList("localhost:8777", "localhost:8778", "localhost:8779");
    static List<String> list2 = Lists.newArrayList( "localhost:8777", "localhost:8779");
    static List<String> list1 = Lists.newArrayList( "localhost:8779");

    public static void main(String[] args) throws InterruptedException {
        for (int i = 3; ; i++) {

            try {
                Request<ClientKVReq> r = new Request<>();

                int size = list2.size();

                ClientKVReq obj = ClientKVReq.newBuilder().key("hello:" + i).type(ClientKVReq.GET).build();
                int index = (i) % size;
                addr = list2.get(index);
                r.setUrl(addr);
                r.setObj(obj);
                r.setCmd(Request.CLIENT_REQ);

                Response<LogEntry> response2 = client.send(r);

                LOGGER.info("request content : {}, url : {}, get response : {}", obj.key + "=" + obj.getValue(), r.getUrl(), response2.getResult());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Thread.sleep(1000);

            }

        }
    }

}
