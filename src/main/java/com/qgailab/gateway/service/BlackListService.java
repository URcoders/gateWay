package com.qgailab.gateway.service;

import com.qgailab.gateway.model.BlackList;
import com.qgailab.gateway.model.BlackListVo;
import com.qgailab.gateway.model.Paging;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface BlackListService {

    Map<String, Object> queryBlackListNumber(Paging paging);

    Map<String, Object> addBlackList(BlackList blackList);

    Map<String, Object> deleteBlackList(BlackList blackList);

}
