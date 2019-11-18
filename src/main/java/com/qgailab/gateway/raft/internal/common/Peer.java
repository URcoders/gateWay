package com.qgailab.gateway.raft.internal.common;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/**
 * 当前节点的 同伴.
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Getter
@Setter
public class Peer {

    /** ip:selfPort */
    private final String addr;


    public Peer(String addr) {
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Peer peer = (Peer) o;
        return Objects.equals(addr, peer.addr);
    }

    @Override
    public int hashCode() {

        return Objects.hash(addr);
    }

    @Override
    public String toString() {
        return "Peer{" +
            "addr='" + addr + '\'' +
            '}';
    }
}
