package com.qgailab.gateway.raft.internal.membership.changes;


import com.qgailab.gateway.raft.internal.common.Peer;

/**
 *
 * 集群配置变更接口.
 *
 * @author linxu
 */
public interface ClusterMembershipChanges {

    /**
     * 添加节点.
     * @param newPeer
     * @return
     */
    Result addPeer(Peer newPeer);

    /**
     * 删除节点.
     * @param oldPeer
     * @return
     */
    Result removePeer(Peer oldPeer);
}

