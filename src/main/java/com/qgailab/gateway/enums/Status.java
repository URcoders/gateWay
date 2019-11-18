package com.qgailab.gateway.enums;

/**
 * @author linxu
 * @date 2019/11/18
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public enum Status {
    LOGIN_FAIL(2),
    LOGIN_OK(1),
    OP_OK(3),
    OP_FAIL(4);
    private int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
