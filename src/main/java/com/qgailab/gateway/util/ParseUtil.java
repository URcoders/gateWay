package com.qgailab.gateway.util;

import com.alibaba.fastjson.JSON;
import com.qgailab.gateway.config.ServiceManager;
import com.qgailab.gateway.model.ServiceInfo;
import com.qgailab.gateway.raft.client.ClientKVAck;
import com.qgailab.gateway.raft.internal.entity.Command;
import com.qgailab.gateway.raft.internal.rpc.DefaultRpcClient;
import com.qgailab.gateway.raft.internal.rpc.Response;

import java.util.List;

/**
 * @author linxu
 * @date 2019/11/18
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public class ParseUtil {
    public static List<ServiceInfo> parseToListServiceInfo(Response<ClientKVAck> response) {
        return (List<ServiceInfo>) JSON.parse(((Command) (response.getResult().getResult())).getValue());
    }
}
