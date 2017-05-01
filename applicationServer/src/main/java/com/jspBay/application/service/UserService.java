package com.jspBay.application.service;

import com.jspBay.application.DTO.UserDTO;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.UserNotFoundException;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class UserService {

    protected Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        logger.info("user-service createNewUser() invoked: " + userDTO.getUserName());
        User user = new User(userDTO.getUserName(),userDTO.getEmail(),new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        userRepo.save(user);
        return userDTO;
    }

    public UserDTO byUserName(String userName) {

        logger.info("user-service byUserName() invoked: " + userName);
        User user = userRepo.findOneByUserName(userName);

        if (user == null)
            throw new UserNotFoundException(userName);
        else {
            UserDTO userDTO = new UserDTO(user,true);
            logger.info("user-service byUserName() found: " + user);
            return userDTO;
        }
    }
}