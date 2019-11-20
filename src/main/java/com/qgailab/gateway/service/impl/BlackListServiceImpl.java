package com.qgailab.gateway.service.impl;

import com.qgailab.gateway.dao.BlackListDao;
import com.qgailab.gateway.enums.Status;
import com.qgailab.gateway.model.BlackList;
import com.qgailab.gateway.model.Paging;
import com.qgailab.gateway.service.BlackListService;
import com.qgailab.gateway.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author guopei
 * @date 2019-11-18 22:22
 */
@Service
public class BlackListServiceImpl implements BlackListService {

    @Autowired
    private BlackListDao blackListDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取Redis中的token
     * @param token
     * @return
     */
    @Cacheable(cacheNames = "admin_tokens",key = "#token")
    public String getToken(String token){
        return null;
    }

    @Override
    public Map<String, Object> queryBlackListNumber(Paging paging) {


        Map<String, Object> resultMap = new HashMap<>();
        if (VerifyUtil.isNull(paging) || VerifyUtil.isEmpty(paging.getOffset())
                || VerifyUtil.isEmpty(paging.getNumberPerPage())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        //校验token
        String token = getToken(paging.getToken());
        System.out.println("token值为：" + token);
        if (!token.equals(paging.getToken())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        int count = paging.getNumberPerPage() * paging.getOffset();
        if (blackListDao.countBlackList() < count) {
            //要查询的数量比数据库的总量还多
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        } else {
            //获取起始位置的索引
            paging.setStartIndex((paging.getOffset()-1) * paging.getNumberPerPage());
            List<BlackList> list = blackListDao.selectByLimit(paging);
            resultMap.put("blackList", list);
            resultMap.put("allSize", list.size());
            resultMap.put("pageSize", paging.getOffset());
            resultMap.put("status", Status.OP_OK);
            return resultMap;
        }
    }

    @Override
    public Map<String, Object> addBlackList(BlackList blackList) {

        Map<String, Object> resultMap = new HashMap<>();
        //Todo 可能还需确认校验
        if (VerifyUtil.isNull(blackList) || VerifyUtil.isEmpty(blackList.getIp())
                || VerifyUtil.isEmpty(blackList.getMac()) || VerifyUtil.isEmpty(blackList.getToken())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        //校验token
        if (!redisTemplate.opsForValue().get("admin_tokens").equals(blackList.getToken())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        if (blackListDao.addBlackList(blackList) != 1) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        resultMap.put("status", Status.OP_OK);
        return resultMap;
    }

    @Override
    public Map<String, Object> deleteBlackList(BlackList blackList) {

        Map<String, Object> resultMap = new HashMap<>();
        if (VerifyUtil.isNull(blackList) || VerifyUtil.isEmpty(blackList.getId())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        //校验token
        if (!redisTemplate.opsForValue().get("admin_tokens").equals(blackList.getToken())) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        if (blackListDao.deleteBlackList(blackList) != 1) {
            resultMap.put("status", Status.OP_FAIL);
            return resultMap;
        }
        resultMap.put("status", Status.OP_OK);
        return resultMap;
    }
}