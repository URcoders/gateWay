package com.qgailab.gateway.raft.internal.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 节点配置
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Getter
@Setter
@ToString
public class NodeConfig {

    /**
     * 自身 selfPort  端口号
     */
    public int selfPort;

    /**
     * 所有节点地址.IP+PORT
     */
    public List<String> peerAddrs;

}
