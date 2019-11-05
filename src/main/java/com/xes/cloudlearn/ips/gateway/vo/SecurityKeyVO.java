package com.xes.cloudlearn.ips.gateway.vo;

/**
 * appid-appkey映射表实体
 *
 * @author lsy
 */
public class SecurityKeyVO {

    /**  */
    private int id;
    /** 调用方id */
    private String appId;
    /** 调用方秘钥 */
    private String appKey;
    /**  */
    private int isDelete;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "SecurityKeyVO{" +
                "id=" + id +
                ", appId='" + appId + '\'' +
                ", appKey='" + appKey + '\'' +
                ", isDelete=" + isDelete +
                '}';
    }
}
