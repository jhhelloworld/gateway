package com.xes.cloudlearn.ips.gateway.vo;


/**
 * 需要权限校验的模块实体
 *
 * @author lsy
 */
public class AuthModuleVO {
    /** 路由id */
    private String zuulId;
    /** 路由路径 */
    private String path;
    /** 白名单 */
    private String whitePaths;

    public String getZuulId() {
        return zuulId;
    }

    public void setZuulId(String zuulId) {
        this.zuulId = zuulId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getWhitePaths() {
        return whitePaths;
    }

    public void setWhitePaths(String whitePaths) {
        this.whitePaths = whitePaths;
    }

    @Override
    public String toString() {
        return "AuthModuleVO{" +
                "zuulId='" + zuulId + '\'' +
                ", path='" + path + '\'' +
                ", whitePaths=" + whitePaths +
                '}';
    }
}
