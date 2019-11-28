package com.qgailab.gateway.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 服务的信息
 */
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object obj) {
        JSONObject jsonObject = (JSONObject) obj;
        ServiceInfo service = jsonObject.toJavaObject(ServiceInfo.class);
        if (this.serviceId.equals(service.serviceId) && this.addressAndPort.equals(service.addressAndPort) && this.serviceName.equals(service.serviceName)) {
            return true;
        }
        return false;
    }
}
