package com.qgailab.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.qgailab.gateway.model.ServiceInfo;
import lombok.Data;

import java.util.List;

/**
 * @author linxu
 * @date 2019/11/18
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData {
    private List<ServiceInfo> serviceList;
    private int status;
}
