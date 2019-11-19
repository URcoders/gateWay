package com.qgailab.gateway.model;

import lombok.Data;

import java.util.List;

/**
 * @description
 * @author guopei
 * @date 2019-11-18 22:14
 */
@Data
public class BlackListVo {

    private Integer allSize;

    private Integer pageSize;

    private List<BlackList> blackList;

}
