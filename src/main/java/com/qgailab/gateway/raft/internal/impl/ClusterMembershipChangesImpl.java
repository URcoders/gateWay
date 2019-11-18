package com.qgailab.gateway.raft.internal.impl;


import com.qgailab.gateway.raft.internal.common.NodeStatus;
import com.qgailab.gateway.raft.internal.common.Peer;
import com.qgailab.gateway.raft.internal.entity.LogEntry;
import com.qgailab.gateway.raft.internal.membership.changes.ClusterMembershipChanges;
import com.qgailab.gateway.raft.internal.membership.changes.Result;
import com.qgailab.gateway.raft.internal.rpc.Request;
import com.qgailab.gateway.raft.internal.rpc.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 集群配置变更接口默认实现.
 *
 * @author linxu
 */
public class ClusterMembershipChangesImpl implements ClusterMembershipChanges {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterMembershipChangesImpl.class);


    private final DefaultNode node;

    public ClusterMembershipChangesImpl(DefaultNode node) {
        this.node = node;
    }

    /**
     * 必须是同步的,一次只能添加一个节点
     *
     * @param newPeer
     */
    @Override
    public synchronized Result addPeer(Peer newPeer) {
        // 已经存在
        if (node.peerSet.getPeersWithOutSelf().contains(newPeer)) {
            return new Result();
        }

        node.peerSet.getPeersWithOutSelf().add(newPeer);

        if (node.status == NodeStatus.LEADER) {
            node.nextIndexs.put(newPeer, 0L);
            node.matchIndexs.put(newPeer, 0L);

            for (long i = 0; i < node.logModule.getLastIndex(); i++) {
                LogEntry e = node.logModule.read(i);
                if (e != null) {
                    node.replication(newPeer, e);
                }
            }

            for (Peer item : node.peerSet.getPeersWithOutSelf()) {
                // TODO 同步到其他节点.
                Request request = Request.newBuilder()
                        .cmd(Request.CHANGE_CONFIG_ADD)
                        .url(newPeer.getAddr())
                        .obj(newPeer)
                        .build();

                Response response = node.rpcClient.send(request);
                Result result = (Result) response.getResult();
                if (result != null && result.getStatus() == Result.Status.SUCCESS.getCode()) {
                    //添加成功
                    LOGGER.info("replication config success, peer : {}, newServer : {}", newPeer, newPeer);
                } else {
                    //添加失败
                    LOGGER.warn("replication config fail, peer : {}, newServer : {}", newPeer, newPeer);
                }
            }

        }

        return new Result();
    }


    /**
     * 必须是同步的,一次只能删除一个节点
     *
     * @param oldPeer
     */
    @Override
    public synchronized Result removePeer(Peer oldPeer) {
        node.peerSet.getPeersWithOutSelf().remove(oldPeer);
        node.nextIndexs.remove(oldPeer);
        node.matchIndexs.remove(oldPeer);

        return new Result();
    }
}
