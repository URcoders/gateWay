package com.qgailab.gateway.service;

import com.qgailab.gateway.model.Proposal;
import com.qgailab.gateway.model.ProposalSolveResult;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public interface ProposalService {
    /**
     * 处理接入提案
     *
     * @param proposal 接入提案
     * @return 处理结果
     */
    ProposalSolveResult solveAccess(Proposal proposal);

    /**
     * 处理续约提案
     *
     * @param proposal 续约提案
     * @return 处理结果
     */
    ProposalSolveResult solveRenew(Proposal proposal);

    /**
     * 处理退出提案
     *
     * @param proposal 退出提案
     * @return 处理结果
     */
    ProposalSolveResult solveGC(Proposal proposal);

    /**
     * 处理驱逐提案
     *
     * @param proposal 驱逐提案
     * @return 处理结果
     */
    ProposalSolveResult solveEviction(Proposal proposal);
}
