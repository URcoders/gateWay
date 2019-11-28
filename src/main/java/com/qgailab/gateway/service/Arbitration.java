package com.qgailab.gateway.service;

import com.qgailab.gateway.model.ArbitrateResult;
import com.qgailab.gateway.model.Proposal;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * this is the arbitration.
 */
public interface Arbitration {
    /**
     * 提出提案
     *
     * @param proposal 提案
     * @param isRedirect 是否是转发的请求
     * @return 返回提出的结果
     */
    //todo 这里应该是返回一个提案
    boolean propose(Proposal proposal,boolean isRedirect);

    ArbitrateResult arbitrate(Proposal proposal);

    /**
     * 同意提案
     *
     * @return 同意
     */
    boolean accept();

    /**
     * 拒绝提案
     *
     * @return 拒绝
     */
    boolean reject();
}
