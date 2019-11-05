package com.xes.cloudlearn.ips.gateway.vo;

/**
 * appid-module映射表实体
 *
 * @author lsy
 */
public class SecurityModuleVO {

    /** 调用方id */
    private String appId;
    /** 可调用的微服务模块 */
    private String zuulId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getZuulId() {
        return zuulId;
    }

    public void setZuulId(String zuulId) {
        this.zuulId = zuulId;
    }

    @Override
    public String toString() {
        return "SecurityModuleVO{" +
                "appId='" + appId + '\'' +
                ", zuulId='" + zuulId + '\'' +
                '}';
    }
}
