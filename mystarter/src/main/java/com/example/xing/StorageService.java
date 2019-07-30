package com.example.xing;

import java.util.HashMap;

/**
 * @author xiexingxing
 * @Created by 2019-07-17 16:25.
 */
public class StorageService {
    private String url;
    private String username;
    private String password;
    private HashMap<String, Object> storage = new HashMap<String, Object>();

    public StorageService(StorageServiceProperties properties) {
        super();
        this.url = properties.getUrl();
        this.username = properties.getUsername();
        this.password = properties.getPassword();
    }

    public StorageService() {
    }

    public void put(String key, Object val) {
        storage.put(key, val);
    }

    public Object get(String key) {
        return storage.get(key);
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public HashMap<String, Object> getStorage() {
        return storage;
    }

    public void setStorage(HashMap<String, Object> storage) {
        this.storage = storage;
    }
}
