package com.qgailab.gateway.raft.internal;


import com.qgailab.gateway.raft.internal.entity.LogEntry;

/**
 * @author linxu
 * 日志模块
 */
public interface LogModule {

    void write(LogEntry logEntry);

    LogEntry read(Long index);

    void removeOnStartIndex(Long startIndex);

    LogEntry getLast();

    Long getLastIndex();
}
