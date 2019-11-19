package com.qgailab.gateway.model;

import lombok.Data;
import java.util.List;

/**
 * @description 黑名单
 * @author guopei
 * @date 2019-11-18 22:11
 */
@Data
public class BlackList {

    private String token;

    private Integer id;

    private String ip;

    private String mac;

    private String serviceDescription;

    private String expire;

}
