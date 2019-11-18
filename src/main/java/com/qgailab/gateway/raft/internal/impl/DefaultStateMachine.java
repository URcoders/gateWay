package com.qgailab.gateway.raft.internal.impl;

import java.io.File;

import com.alibaba.fastjson.JSON;


import com.qgailab.gateway.raft.internal.StateMachine;

import com.qgailab.gateway.raft.internal.entity.Command;
import com.qgailab.gateway.raft.internal.entity.LogEntry;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认的状态机实现.
 * <p>
 *     每个节点都有一个对应的状态机；
 *     每个状态机实际是基于RocksDB进行数据持久化的处理。
 *     get、put、del、apply
 *     其中apply实对命令的追加
 * </p>
 *
 * @author linxu
 */
public class DefaultStateMachine implements StateMachine {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultStateMachine.class);

    /**
     * public just for test
     */
    public static String dbDir;
    public static String stateMachineDir;

    public static RocksDB machineDb;

    static {
        dbDir = "./rocksDB-raft/" + System.getProperty("serverPort");
        if (stateMachineDir == null) {
            stateMachineDir = dbDir + "/stateMachine";
        }
        RocksDB.loadLibrary();
    }


    private DefaultStateMachine() {
        synchronized (this) {
            try {
                File file = new File(stateMachineDir);
                boolean success = false;
                if (!file.exists()) {
                    success = file.mkdirs();
                }
                if (success) {
                    LOGGER.warn("make a new dir : " + stateMachineDir);
                }
                Options options = new Options();
                options.setCreateIfMissing(true);
                machineDb = RocksDB.open(options, stateMachineDir);

            } catch (RocksDBException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    public static DefaultStateMachine getInstance() {
        return DefaultStateMachineLazyHolder.INSTANCE;
    }

    private static class DefaultStateMachineLazyHolder {

        private static final DefaultStateMachine INSTANCE = new DefaultStateMachine();
    }


    @Override
    public LogEntry get(String key) {
        try {
            byte[] result = machineDb.get(key.getBytes());
            if (result == null) {
                return null;
            }
            return JSON.parseObject(result, LogEntry.class);
        } catch (RocksDBException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    @Override
    public String getString(String key) {
        try {
            byte[] bytes = machineDb.get(key.getBytes());
            if (bytes != null) {
                return new String(bytes);
            }
        } catch (RocksDBException e) {
            LOGGER.info(e.getMessage());
        }
        return "";
    }

    @Override
    public void setString(String key, String value) {
        try {
            machineDb.put(key.getBytes(), value.getBytes());
        } catch (RocksDBException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void delString(String... key) {
        try {
            for (String s : key) {
                machineDb.delete(s.getBytes());
            }
        } catch (RocksDBException e) {
            LOGGER.info(e.getMessage());
        }
    }

    @Override
    public synchronized void apply(LogEntry logEntry) {

        try {
            Command command = logEntry.getCommand();

            if (command == null) {
                throw new IllegalArgumentException("command can not be null, logEntry : " + logEntry.toString());
            }
            String key = command.getKey();
            machineDb.put(key.getBytes(), JSON.toJSONBytes(logEntry));
        } catch (RocksDBException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
