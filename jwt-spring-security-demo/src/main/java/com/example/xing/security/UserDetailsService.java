package com.example.xing.security;

import com.example.xing.common.constant.RoleKeys;
import com.example.xing.model.User;
import com.example.xing.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xiexingxing
 * @Created by 2018-09-06 上午12:13.
 */
@Service
@Slf4j
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询用户
        User user = userService.findByName(username);
        if(user==null){
            log.debug("Query returned no results for user '" + username + "'");
            throw new UsernameNotFoundException("未找到用户"+username);
        }
        //2.查询用户权限
        Set<GrantedAuthority> authoritySet=new HashSet<>();
        authoritySet.addAll(loadUserAuthorities(user));
        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(authoritySet);

        return createUserDetails(user, dbAuths);
    }

    private JwtUser createUserDetails(User user, List<GrantedAuthority> dbAuths) {
        JwtUser createUser = new JwtUser(user.getUsername(), user.getPassword(), user.getLocked(), dbAuths,user.getLastPasswordResetDate());
        return createUser;
    }

    /**
     * 根据用户查找权限列表
     * @param user
     * @return
     */
    protected List<GrantedAuthority> loadUserAuthorities(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(RoleKeys.ROLE_PREFIX+RoleKeys.ADMIN);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }
}
