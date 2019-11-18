package com.qgailab.gateway.model;

import lombok.Data;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 提案的处理结果
 */
@Data
public class ProposalSolveResult {
    private Result result;

    public enum Result {
        TIMEOUT,
        FAIL,
        OK
    }

}
