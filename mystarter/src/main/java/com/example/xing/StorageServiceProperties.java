package com.example.xing;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiexingxing
 * @Created by 2019-07-17 16:25.
 */
@ConfigurationProperties("storage.service")
public class StorageServiceProperties {
    private String username;
    private String password;
    private String url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
