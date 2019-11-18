package com.qgailab.gateway.model;

import lombok.Data;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 对服务进行驱逐的原因
 */
@Data
public class EvictReason {
    private Reason reason;

    public enum Reason {
        /**
         * 失联
         */
        OUTOFCONTACT,
        /**
         * 不可信任
         */
        UNRELIABLE;
    }
}
