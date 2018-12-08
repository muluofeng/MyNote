package com.example.xing.config;

import com.example.xing.DBConstant;
import com.example.xing.UrlUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 租户过滤器
 *
 * @author: chenxing
 * @create: 2018-11-21 18:30
 **/
public class MultiTenantFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        String path = req.getServletPath();
        //获得域名
        String serverName = request.getServerName();

        //获得二级域名
        String domain = UrlUtils.getDomain(serverName, 2);

        if (StringUtils.isNotEmpty(domain)) {

            //请求中存储当前企业信息
//            request.setAttribute(DBConstant.CURRENT_ENTERPRISE_KEY, enterprise);

            //设置请求当前租户
            request.setAttribute(DBConstant.TENANT_KEY, DBConstant.DB_PREFIX + domain);

        } else {

            //TODO: 如果出错的话, 应该跳转到错误页面去
            request.setAttribute(DBConstant.TENANT_KEY, DBConstant.DEFAULT_TENANT);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
