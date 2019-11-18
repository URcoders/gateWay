package com.qgailab.gateway.raft.internal.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 节点集合. 去重.
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public class PeerSet implements Serializable {

    private List<Peer> list = new ArrayList<>();

    private volatile Peer leader;

    /** final */
    private volatile Peer self;

    private PeerSet() {
    }

    public static PeerSet getInstance() {
        return PeerSetLazyHolder.INSTANCE;
    }

    private static class PeerSetLazyHolder {

        private static final PeerSet INSTANCE = new PeerSet();
    }

    public void setSelf(Peer peer) {
        self = peer;
    }

    public Peer getSelf() {
        return self;
    }

    public void addPeer(Peer peer) {
        list.add(peer);
    }

    public void removePeer(Peer peer) {
        list.remove(peer);
    }

    public List<Peer> getPeers() {
        return list;
    }

    /**
     * 获取其他节点列表
     * @return
     */
    public List<Peer> getPeersWithOutSelf() {
        List<Peer> list2 = new ArrayList<>(list);
        list2.remove(self);
        return list2;
    }


    public Peer getLeader() {
        return leader;
    }

    public void setLeader(Peer peer) {
        leader = peer;
    }

    @Override
    public String toString() {
        return "PeerSet{" +
            "list=" + list +
            ", leader=" + leader +
            ", self=" + self +
            '}';
    }
}
