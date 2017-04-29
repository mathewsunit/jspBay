package com.jspBay.application.service;

import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
}