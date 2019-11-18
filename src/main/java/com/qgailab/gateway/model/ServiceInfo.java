package com.qgailab.gateway.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 服务的信息
 */
@Getter
@Setter
public class ServiceInfo implements Serializable {
    private String addressAndPort;
    /*   private String */
    private String serviceName;
    private String serviceId;
    private String serviceDescription;
    private String secretKey;
    /**
     * 超时时间
     */
    private long expire;

}
