package com.jingrui.jrap.security;

import java.util.ArrayList;
import java.util.Collection;

import com.jingrui.jrap.account.dto.User;
import com.jingrui.jrap.account.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author hailor
 * @date 2016/6/16.
 */
public class CustomAuthenticationUserDetailsService implements AuthenticationUserDetailsService {

    @Autowired
    private IUserService userService;

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(Authentication authentication) throws UsernameNotFoundException {
        logger.debug("===========================================================");
        logger.debug(authentication.getPrincipal().toString());

        User user = userService.selectByUserName(authentication.getPrincipal().toString());

        if (user == null) {
            throw new UsernameNotFoundException("User not found:" + authentication.getPrincipal().toString());
        }
        CheckUserUtil.checkUserException(user);

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        for (String role : user.getRoleCode()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        UserDetails userDetails = new CustomUserDetails(user.getUserId(), user.getUserName(),
                user.getPasswordEncrypted(), true, true, true, true, authorities, user.getEmployeeId(), user.getEmployeeCode());

        return userDetails;
    }
}
