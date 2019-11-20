package com.qgailab.gateway.service.impl;


import com.qgailab.gateway.dao.AdminDao;
import com.qgailab.gateway.enums.Status;
import com.qgailab.gateway.model.User;
import com.qgailab.gateway.service.AdminService;
import com.qgailab.gateway.util.TokenUtil;
import com.qgailab.gateway.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 管理员登录
 * @author guopei
 * @date 2019-11-18 21:06
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Override
    @CachePut(cacheNames = "admin_tokens", key ="#user.token")
    public String AdminLogin(User user) {
        if (VerifyUtil.isNull(user) || VerifyUtil.isEmpty(user.getUsername())
                || VerifyUtil.isEmpty(user.getPassword())) {
            //Todo 可能需要再做处理
            return null;
        }
        if (VerifyUtil.isNull(adminDao.login(user))) {
            return null;
        }
        //生成一个对应的Token
        String token = TokenUtil.createToken(user.getUsername());
        user.setToken(token);
        return token;
    }
}