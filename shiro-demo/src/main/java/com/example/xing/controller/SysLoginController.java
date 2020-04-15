package com.example.xing.controller;

import com.example.xing.common.R;
import com.example.xing.entity.SysUser;
import com.example.xing.shiro.ShiroUtils;
import com.example.xing.shiro.UserRealm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

/**
 * 登录相关
 */
@Controller
@RequestMapping("admin")
public class SysLoginController {

    @Autowired
    private UserRealm userRealm;


    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login")
    public R login(String userName, String password, String captcha) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return R.error("验证码不正确");
//        }

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            subject.login(token);

            //返回登陆后用户
            SysUser user = ShiroUtils.getUserEntity();

            Cache<Object, AuthorizationInfo> authorizationCache = userRealm.getAuthorizationCache();
            AuthorizationInfo authorizationInfo = null != authorizationCache ? authorizationCache
                    .get(ShiroUtils.getSubject().getPrincipal()) : null;

            if (null == authorizationInfo) {
                authorizationInfo = userRealm.doGetAuthorizationInfo(ShiroUtils.getSubject().getPrincipals());
            }

            //获得用户权限
            Collection<String> permissions = authorizationInfo.getStringPermissions();

            return R.ok().put("user", user).put("access", permissions);

        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

    }

    /**
     * 退出
     */
    @ResponseBody
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public R logout() {
        ShiroUtils.logout();
        return R.ok("退出成功");
    }

}
