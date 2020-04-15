package com.example.xing.config;

import com.example.xing.DBConstant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeaderTenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    /**
     * 返回当前租户标识
     *
     * @return
     */
    @Override
    public String resolveCurrentTenantIdentifier() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            //优先使用  sessionTenantId 作为 数据源的租户id
            if (request != null) {
                String tenantId = (String) requestAttributes.getAttribute(DBConstant.TENANT_KEY, RequestAttributes.SCOPE_REQUEST);
                if (tenantId != null) {
                    return tenantId;
                }
            }

        }
        return DBConstant.DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}