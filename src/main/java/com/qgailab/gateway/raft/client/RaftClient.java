package com.qgailab.gateway.raft.client;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.alipay.remoting.exception.RemotingException;
import com.google.common.collect.Lists;


import com.qgailab.gateway.raft.internal.current.SleepHelper;
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
public class RaftClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaftClient.class);


    private final static RpcClient client = new DefaultRpcClient();

    static String addr = "localhost:11111";
    static List<String> list = Lists.newArrayList("localhost:11112", "localhost:11113");

    public static void main(String[] args) throws RemotingException, InterruptedException {

        AtomicLong count = new AtomicLong(3);

        for (int i = 3; ; i++) {
            try {
                int index = (int) (count.incrementAndGet() % list.size());
                addr = list.get(index);

                ClientKVReq obj = ClientKVReq.newBuilder().key("hello:" + i).value("world:" + i).type(ClientKVReq.PUT).build();

                Request<ClientKVReq> r = new Request<>();
                r.setObj(obj);
                r.setUrl(addr);
                r.setCmd(Request.CLIENT_REQ);
                Response<String> response;
                try {
                  //  response = client.send(r);
                } catch (Exception e) {
                    r.setUrl(list.get((int) ((count.incrementAndGet()) % list.size())));
                 //   response = client.send(r);
                }

               // LOGGER.info("request content : {}, url : {}, put response : {}", obj.key + "=" + obj.getValue(), r.getUrl(), response.getResult());

                SleepHelper.sleep(1000);

                obj = ClientKVReq.newBuilder().key("hello:" + i).type(ClientKVReq.GET).build();

                addr = list.get(index);
                addr = list.get(index);
                r.setUrl(addr);
                r.setObj(obj);

                Response<LogEntry> response2;
                try {
                    response2 = client.send(r);
                } catch (Exception e) {
                    r.setUrl(list.get((int) ((count.incrementAndGet()) % list.size())));
                    response2 = client.send(r);
                }

                LOGGER.info("request content : {}, url : {}, get response : {}", obj.key + "=" + obj.getValue(), r.getUrl(), response2.getResult());
            } catch (Exception e) {
                e.printStackTrace();
                i = i - 1;
            }

            SleepHelper.sleep(5000);
        }


    }

}
