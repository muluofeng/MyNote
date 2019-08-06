package com.example.xing.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.xing.common.R;

import org.apache.shiro.web.filter.AccessControlFilter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: town
 * @description: 跨域授权拦截
 * @author: chenxing
 * @create: 2019-04-01 15:18
 **/
public class CORSAuthenticationFilter extends AccessControlFilter {

    private String domain;

    public CORSAuthenticationFilter(String domain) {
        super();
        this.domain = domain;
    }

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //Always return true if the request's method is OPTIONSif (request instanceof HttpServletRequest) {
        if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        } else if (isLoginRequest(request, response)) {
            return true;
        }

        return null != getSubject(request, response).getPrincipal();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;

        res.setHeader("Access-Control-Allow-Origin", domain);  // 不能使用 * 号 否则前端复发携带ookies
        res.setHeader("Access-Control-Allow-Credentials", "true");  //让ajax 携带Cookies
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,Content-Type, Accept");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token"); //当判定为预检请求后，设定允许请求的头部类型
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, DELETE");//当判定为预检请求后，设定允许请求的方法
        res.setHeader("Access-Control-Max-Age", "13600");

        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");

        PrintWriter writer = res.getWriter();

        R r = R.ok();
        r.setCode(HttpServletResponse.SC_UNAUTHORIZED);

        writer.write(JSONUtils.toJSONString(r));
        writer.close();
        return false;
    }
}
