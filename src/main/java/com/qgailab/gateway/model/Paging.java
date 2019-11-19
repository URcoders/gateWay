package com.qgailab.gateway.model;


import lombok.Data;

/**
 * @description 用于前端传输的分页对象
 * @author guopei
 * @date 2019-11-18 22:27
 */
@Data
public class Paging {

    private String token;

    private Integer offset;

    private Integer numberPerPage;

    private Integer startIndex;
}