package com.qgailab.gateway.dto;


import com.qgailab.gateway.bizException.BizException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description 返回的数据
 * @author guopei
 * @date 2019-11-18 20:55
 */
@Data
@Builder
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

    //管理员登录成功状态码
    public static final String LOGIN_SUCCESS = "1";

    //管理员登录失败状态码
    public static final String LOGIN_FAILED = "2";

    // 操作成功的状态码
    public static final String SUCCESS = "3";

    // 操作失败的状态码
    public static final String FAILED = "4";


    private String status = ResultBean.SUCCESS;

    private T data;

    public ResultBean(String status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResultBean(String status) {
        super();
        this.status = status;
        this.data = null;
    }

}