package com.qgailab.gateway.raft.internal.common;

import lombok.Getter;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public interface NodeStatus {

    int FOLLOWER = 0;
    int CANDIDATE = 1;
    int LEADER = 2;

    @Getter
    enum Enum {
        FOLLOWER(0), CANDIDATE(1), LEADER(2);

        Enum(int code) {
            this.code = code;
        }

        int code;

        public static Enum value(int i) {
            for (Enum value : Enum.values()) {
                if (value.code == i) {
                    return value;
                }
            }
            return null;
        }

    }

}
