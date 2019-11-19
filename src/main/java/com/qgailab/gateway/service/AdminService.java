package com.qgailab.gateway.service;

import com.qgailab.gateway.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AdminService {

    Map<String, String> AdminLogin(User user);

}
