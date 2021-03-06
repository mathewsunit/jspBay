package com.jspBay.application.controller;


import com.jspBay.application.DTO.LocationDTO;
import com.jspBay.application.DTO.UserDTO;
import com.jspBay.application.exceptions.UserNotFoundException;
import com.jspBay.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    protected UserService userService;

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
    public UserDTO byUserName(@PathVariable("userName") String userName) {

        logger.info("user-controller byUserName() invoked: " + userName);
        return userService.byUserName(userName);
    }

    /**
     * Fetch an item with the specified item number.
     *
     * @param email
     *            A userName
     * @return The user if found.
     * @throws UserNotFoundException
     *             If the userName is not recognised.
     */
    @RequestMapping("/user/email/{email}")
    public UserDTO byEmail(@PathVariable("email") String email) {

        logger.info("user-controller byEmail() invoked: " + email);
        return userService.byEmail(email);
    }

    /**
     * Fetch an item with the specified item number.
     *
     *
     * @return The user if found.
     * @throws UserNotFoundException
     *             If the userName is not recognised.
     */
    @RequestMapping("/user/create")
    public UserDTO createNewUser(@RequestBody UserDTO userDTO) {

        logger.info("user-controller createNewUser() invoked: " + userDTO.getUserName());
        return userService.createNewUser(userDTO);
    }

    /**
     * Fetch an item with the specified item number.
     *
     *
     * @return The user if found.
     * @throws UserNotFoundException
     *             If the userName is not recognised.
     */
    @RequestMapping("/user/modify")
    public UserDTO modifyUser(@RequestBody UserDTO userDTO) {

        logger.info("user-controller modifyUser() invoked: " + userDTO.getUserName());
        return userService.modifyUser(userDTO);
    }

    /**
     * Fetch an item with the specified item number.
     *
     *
     * @return The user if found.
     * @throws UserNotFoundException
     *             If the userName is not recognised.
     */
    @RequestMapping("/user/updateLocation")
    public LocationDTO updateLocation(@RequestBody LocationDTO locationDTO) {

        logger.info("user-controller updateLocation() invoked: " + locationDTO.getUser());
        return userService.updateLocation(locationDTO);
    }

    @RequestMapping("/user/getLocation")
    public LocationDTO getLocation(@RequestBody LocationDTO locationDTO) {

        logger.info("user-controller updateLocation() invoked: " + locationDTO.getUser());
        return userService.getLocation(locationDTO);
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