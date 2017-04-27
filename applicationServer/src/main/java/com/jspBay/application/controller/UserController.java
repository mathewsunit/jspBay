package com.jspBay.application.controller;


import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.UserNotFoundException;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class UserController {

    protected Logger logger = Logger.getLogger(UserController.class.getName());
    protected UserRepository userRepository;

    /**
     * Create an instance plugging in the respository of Items.
     *
     * @param userRepository
     *            An user Repository implementation.
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

        logger.info("ItemRepository says system has "
                + userRepository.countItems() + " items");
    }

    /**
     * Fetch an item with the specified item number.
     *
     * @param userName
     *            A userName
     * @return The user if found.
     * @throws UserNotFoundException
     *             If the userName is not recognised.
     */
    @RequestMapping("/user/{userName}")
    public User byUserName(@PathVariable("userName") String userName) {

        logger.info("user-service byUserName() invoked: " + userName);
        User user = userRepository.findOneByUserName(userName);
        logger.info("items-service byNumber() found: " + user);

        if (user == null)
            throw new UserNotFoundException(userName);
        else {
            return user;
        }
    }

    /**
     * Fetch an item with the specified item number.
     *
     * test
     *
     */
    @RequestMapping("/test")
    public Map<String, Object> home() {
        System.out.println("resource");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
}