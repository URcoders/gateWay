package com.qgailab.gateway.department;

/**
 * @author linxu
 * @date 2019/11/16
 * <tip>take care of yourself.everything is no in vain.</tip>
 * 健康检查者的接口，各种软硬件设备都可以通过实现这个检查者来实现检查的功能
 */
public interface HealthChecker {
    boolean check();

    boolean check(String... args);
}
