package com.example.xing.common;

/**
 * Redis所有Keys
 */
public class RedisKeys {

	public static String getSysConfigKey(String key) {
		return "sys:config:" + key;
	}

	public static String getShiroSessionKey(String key) {
		return "sessionid:" + key;
	}
}
