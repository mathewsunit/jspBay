package com.jspBay.web.security;

import com.jspBay.web.DTO.UserDTO;
import com.jspBay.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.aspectj.util.LangUtil.isEmpty;

/**
 * Created by sunit on 3/20/17.
 */
//public class UserDetailsServiceImpl {
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
    private WebUserService userService;

    @Autowired
    public UserDetailsServiceImpl(WebUserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        checkNotNull(username);

        if(isEmpty(username)) {
            throw new UsernameNotFoundException("Username cannot be empty");
        }

        UserDTO user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " doesn't exist");
        }
        return new ContextUser(user);
    }
}
