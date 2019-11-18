package com.qgailab.gateway.config;

import com.qgailab.gateway.model.ServiceInfo;
import lombok.Data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linxu
 * @date 2019/11/17
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Component
@Data
public class ServiceManager {
    /**
     * 服务命令空间,避免与使用分布式数据库冲突
     */
    public static final String SERVICES_NAMESPACE="SERVICES_N_SPACE";
}
