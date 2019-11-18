package com.qgailab.gateway.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 仲裁结果
 */
@Getter
@Setter
public class ArbitrateResult implements Serializable {
    /**
     * 仲裁结果
     */
    private boolean accept;

    public boolean accept() {
        return accept;
    }
}
