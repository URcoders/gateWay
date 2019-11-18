package com.qgailab.gateway.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 提案：由仲裁会提出的提案
 */
@Getter
@Setter
public class Proposal<T> implements Serializable {
    /**
     * 提案的类型
     */
    Type type;
    /**
     * 提案时间
     */
    long proposeTime;
    /**
     * 提案内容
     */
    T obj;


    /**
     * @author linxu
     * 提案类型
     */
    public enum Type {
        /**
         * 接入审批
         */
        ACCESS_APPROVAL(1),
        /**
         * 续约审批
         */
        RENEW_APPROVAL(2),
        /**
         * 回收审批
         */
        GC_APPROVAL(3),
        /**
         * 认为宕机
         */
        EVICT_APPROVAL(4);

        private int type;

        Type(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
