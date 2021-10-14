package com.flexsolution.chyzhov.kmb.security;

import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    public CustomUserDetailsService(UserService service) {
        this.service = service;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = service.findByUsername(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}