package com.qgailab.gateway.raft.client;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Getter
@Setter
@ToString
public class ClientKVAck implements Serializable {

    Object result;

    public ClientKVAck(Object result) {
        this.result = result;
    }

    private ClientKVAck(Builder builder) {
        setResult(builder.result);
    }

    public static ClientKVAck ok() {
        return new ClientKVAck("ok");
    }

    public static ClientKVAck fail() {
        return new ClientKVAck("fail");
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {

        private Object result;

        private Builder() {
        }

        public Builder result(Object val) {
            result = val;
            return this;
        }

        public ClientKVAck build() {
            return new ClientKVAck(this);
        }
    }
}
