package com.example.xing.imfiletest;

/**
 * Author: JetslyLi
 * Date: 2015/8/26
 * Description:
 */
public class BaseRequestModel implements Cloneable{
    /**
     * App的名字
     */
    private String appName;
    /**
     * App的Token
     */
    private String appToken;
    /**
     * 请求的用户名
     */
    private String userName;
    /**
     * 用户的token
     */
    private String userToken;

    /**
     * 请求时间
     */
    private Long requestStartTime;

    
    @SuppressWarnings("unchecked")
	public <T extends BaseRequestModel>T cloneInstance(T t) {
		T obj = null;
		try {
			obj = (T)t.clone();
		} catch (CloneNotSupportedException e) {}
		return obj;
	}
    
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Long requestStartTime) {
        this.requestStartTime = requestStartTime;
    }
}
