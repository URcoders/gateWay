package com.qgailab.gateway.raft.internal.current;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 */
public class SleepHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(SleepHelper.class);


    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage());
        }

    }

    public static void sleep2(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            LOGGER.warn(e.getMessage());
        }

    }


}
