package com.liulu123.xyz.wchart.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wechart")
public class WechatAccountConfig {

    private String appID;

    private String appsecret;

    public WechatAccountConfig() {
    }

    public WechatAccountConfig(String appID, String appsecret) {
        this.appID = appID;
        this.appsecret = appsecret;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

}
