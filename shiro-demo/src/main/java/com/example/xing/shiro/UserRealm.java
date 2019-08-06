package com.example.xing.shiro;

import com.example.xing.common.SysConstant;
import com.example.xing.dao.SysMenuDao;
import com.example.xing.dao.SysUserDao;
import com.example.xing.entity.SysMenu;
import com.example.xing.entity.SysUser;
import com.google.common.collect.Sets;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 认证
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList;

        // 系统管理员，拥有最高权限
        if (userId == SysConstant.SUPER_ROLE_ID) {
            List<SysMenu> menuList = sysMenuDao.findAll();
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        // 用户权限列表
        Set<String> permsSet = Sets.newHashSet();

        permsList.stream().filter(Strings::isNotBlank)
                .map(s -> StringUtils.split(s.trim(), ","))
                .map(Arrays::asList)
                .forEach(permsSet::addAll);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        // 查询用户信息
        SysUser user = new SysUser();
        user.setUsername(token.getUsername());
        Optional<SysUser> userOptional = sysUserDao.findOne(Example.of(user));

        // 账号不存在
        if (!userOptional.isPresent()) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        user = userOptional.get();

        // 账号锁定
        if (Integer.valueOf(0).equals(user.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user
                .getSalt()), getName());

        return info;
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

    public static void main(String[] args) {
        System.out.println(ShiroUtils.sha256("123456", "DEIHrLK2GjNoxgA"));
    }
}
