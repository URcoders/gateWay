package com.qgailab.gateway.raft.internal.current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public class RaftThread extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaftThread.class);
    private static final UncaughtExceptionHandler uncaughtExceptionHandler = (t, e)
        -> LOGGER.warn("Exception occurred from thread {}", t.getName(), e);

    public RaftThread(String threadName,  Runnable r) {
        super(r, threadName);
        setUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

}
