package com.jspBay.web.security;

import com.google.common.collect.ImmutableSet;
import com.jspBay.web.DTO.UserDTO;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Created by sunit on 3/20/17.
 */
public class ContextUser extends org.springframework.security.core.userdetails.User {

    private final UserDTO user;

    public ContextUser(UserDTO user) {
        super(user.getUserName(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                ImmutableSet.of(new SimpleGrantedAuthority("create")));

        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }
}
