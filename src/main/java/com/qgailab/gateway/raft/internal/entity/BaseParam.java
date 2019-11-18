package com.qgailab.gateway.raft.internal.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
@Getter
@Setter
@ToString
public class BaseParam implements Serializable {

    /** 候选人的任期号  */
    public long term;

    /** 被请求者 ID(ip:selfPort) */
    public String serverId;

}
