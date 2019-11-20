package com.qgailab.gateway.model;

import lombok.Data;

/**
 * @description
 * @author guopei
 * @date 2019-11-18 20:52
 */
@Data
public class User {

    private String username;

    private String password;

    private String prio;

    private String token;

}
