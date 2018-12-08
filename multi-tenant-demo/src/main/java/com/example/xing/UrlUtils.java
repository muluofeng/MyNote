package com.example.xing;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL工具, 用于提取一二级域名
 */
public class UrlUtils {

    private static final Pattern PATTEN_IP = Pattern.compile("((\\d+\\.){3}(\\d+))");

    public static String getDomain(String url, int level) {
        Matcher matcher = PATTEN_IP.matcher(url);
        if (matcher.find()) {
            return matcher.group(4);
        }

        String[] domain = StringUtils.split(url, ".");

        switch (level) {
            case 1:
                return domain[domain.length - 2];
            case 2:
                return domain[domain.length - 3];
            case 3:
                return domain[domain.length - 4];
            default:
                return "";
        }
    }
}