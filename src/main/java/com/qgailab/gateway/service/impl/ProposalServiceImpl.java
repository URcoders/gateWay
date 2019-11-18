package com.qgailab.gateway.service.impl;

import com.qgailab.gateway.config.DefaultProposalConfig;
import com.qgailab.gateway.model.Proposal;
import com.qgailab.gateway.model.ProposalSolveResult;
import com.qgailab.gateway.model.ServiceInfo;
import com.qgailab.gateway.service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Service
public class ProposalServiceImpl implements ProposalService {
    @Autowired
    private DefaultProposalConfig config;

    @Override
    public ProposalSolveResult solveAccess(Proposal proposal) {
        ProposalSolveResult result = new ProposalSolveResult();
        //超时
        if (System.currentTimeMillis() - proposal.getProposeTime() > config.getTimeout()) {
            result.setResult(ProposalSolveResult.Result.TIMEOUT);
        }
        //TODO 查黑名单列表判断是否存在
        ServiceInfo serviceInfo = (ServiceInfo) proposal.getObj();
        //FOR TEST
        result.setResult(ProposalSolveResult.Result.OK);
        //if (success) result.setOK
        //else setFail.
        return result;
    }

    @Override
    public ProposalSolveResult solveRenew(Proposal proposal) {
        return null;
    }

    @Override
    public ProposalSolveResult solveGC(Proposal proposal) {
        return null;
    }

    @Override
    public ProposalSolveResult solveEviction(Proposal proposal) {
        return null;
    }
}
