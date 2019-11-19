package com.qgailab.gateway.service.impl;

import com.qgailab.gateway.dao.BlackListDao;
import com.qgailab.gateway.dto.ResultBean;
import com.qgailab.gateway.model.BlackList;
import com.qgailab.gateway.model.BlackListVo;
import com.qgailab.gateway.model.Paging;
import com.qgailab.gateway.service.BlackListService;
import com.qgailab.gateway.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public BlackListVo queryBlackListNumber(Paging paging) {

        if (VerifyUtil.isNull(paging) || VerifyUtil.isEmpty(paging.getOffset())
                || VerifyUtil.isEmpty(paging.getNumberPerPage())) {
            return null;
        }
        //校验token
        if (!redisTemplate.opsForValue().get("admin_tokens").equals(paging.getToken())) {
            return null;
        }
        int count = paging.getNumberPerPage() * paging.getOffset();
        if (blackListDao.countBlackList() < count) {
            //要查询的数量比数据库的总量还多
            return null;
        } else {
            //获取起始位置的索引
            paging.setStartIndex((paging.getOffset()-1) * paging.getNumberPerPage());
            List<BlackList> list = blackListDao.selectByLimit(paging);
            BlackListVo blackListVo = new BlackListVo();
            blackListVo.setBlackList(list);
            blackListVo.setAllSize(list.size());
            blackListVo.setPageSize(paging.getOffset());
            return blackListVo;
        }
    }

    @Override
    public String addBlackList(BlackList blackList) {

        //Todo 可能还需确认校验
        if (VerifyUtil.isNull(blackList) || VerifyUtil.isEmpty(blackList.getIp())
                || VerifyUtil.isEmpty(blackList.getMac()) || VerifyUtil.isEmpty(blackList.getToken())) {
            return ResultBean.FAILED;
        }
        //校验token
        if (!redisTemplate.opsForValue().get("admin_tokens").equals(blackList.getToken())) {
            return ResultBean.FAILED;
        }
        if (blackListDao.addBlackList(blackList) != 1) {
            return ResultBean.FAILED;
        }
        return ResultBean.SUCCESS;
    }

    @Override
    public String deleteBlackList(BlackList blackList) {

        if (VerifyUtil.isNull(blackList) || VerifyUtil.isEmpty(blackList.getId())) {
            return ResultBean.FAILED;
        }
        //校验token
        if (!redisTemplate.opsForValue().get("admin_tokens").equals(blackList.getToken())) {
            return ResultBean.FAILED;
        }
        if (blackListDao.deleteBlackList(blackList) != 1) {
            return ResultBean.FAILED;
        }
        return ResultBean.SUCCESS;
    }
}