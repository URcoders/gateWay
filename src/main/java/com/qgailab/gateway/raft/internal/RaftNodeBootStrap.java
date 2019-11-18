package com.qgailab.gateway.raft.internal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.qgailab.gateway.raft.internal.common.NodeConfig;
import com.qgailab.gateway.raft.internal.common.PeerSet;
import com.qgailab.gateway.raft.internal.current.SleepHelper;
import com.qgailab.gateway.raft.internal.impl.DefaultNode;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("B1")
@Slf4j
@PropertySource("classpath:server-config.properties")
@Data
public class RaftNodeBootStrap {
    static Node backUpNode;
    @Value("${qurom}")
    private String peers;

    @Value("${node.port}")
    private int port;

    private List<String> addressList = new LinkedList<>();

    @PostConstruct
    public void main0() throws Throwable {
        String[] peerAddr = peers.split(",");
        //提供一个全局列表维护
        addressList.addAll(Arrays.asList(peerAddr));
        NodeConfig config = new NodeConfig();

        // 自身节点
        config.setSelfPort(port);

        // 其他节点地址
        config.setPeerAddrs(Arrays.asList(peerAddr));
        Node node = DefaultNode.getInstance();
        node.setConfig(config);
        node.init();
        //获取回溯节点
        backUpNode = node;
       /* while (true) {
            SleepHelper.sleep2(3);
            System.err.println(PeerSet.getInstance().getLeader());
        }*/
    }

    @PreDestroy
    public void bye() {
        if (backUpNode != null) {
            try {
                backUpNode.destroy();
            } catch (Throwable throwable) {
                log.error("DESTROY BACKUP NODE FAIL.");
            }
        }
    }

}
