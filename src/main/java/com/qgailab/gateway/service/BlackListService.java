package com.qgailab.gateway.service;

import com.qgailab.gateway.model.BlackList;
import com.qgailab.gateway.model.BlackListVo;
import com.qgailab.gateway.model.Paging;
import org.springframework.stereotype.Service;

@Service
public interface BlackListService {

    BlackListVo queryBlackListNumber(Paging paging);

    String addBlackList(BlackList blackList);

    String deleteBlackList(BlackList blackList);

}
