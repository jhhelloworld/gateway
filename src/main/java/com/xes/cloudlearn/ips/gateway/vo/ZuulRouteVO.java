package com.xes.cloudlearn.ips.gateway.vo;

import java.io.Serializable;

/**
 * 路由节点实体
 *
 * @author lsy
 */
public class ZuulRouteVO implements Serializable {
    /** zuul id */
    private String id;
    /** service path */
    private String path;
    /** service id */
    private String serviceId;
    /** service url */
    private String url;
    /** 敏感请求头 */
    private String sensitiveHeaders;
    /** 是否包含前缀 */
    private boolean stripPrefix = true;
    /** 是否支持重试 (配置为true 需在application.yml中添加额外配置) */
    /**
     * #同一个Server重试的次数(除去首次)
     * ribbon.MaxAutoRetries=3
     * #切换相同Server的次数
     * ribbon.MaxAutoRetriesNextServer=0
     */
    private Boolean retryable = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public Boolean getRetryable() {
        return retryable;
    }

    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    public String getSensitiveHeaders() {
        return sensitiveHeaders;
    }

    public void setSensitiveHeaders(String sensitiveHeaders) {
        this.sensitiveHeaders = sensitiveHeaders;
    }

    @Override
    public String toString() {
        return "ZuulRouteVO{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", url='" + url + '\'' +
                ", stripPrefix=" + stripPrefix +
                ", retryable=" + retryable +
                ", sensitiveHeaders='" + sensitiveHeaders + '\'' +
                '}';
    }
}
