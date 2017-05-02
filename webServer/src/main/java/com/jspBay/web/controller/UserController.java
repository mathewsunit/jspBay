package com.jspBay.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jspBay.web.DTO.LocationDTO;
import com.jspBay.web.DTO.UserDTO;
import com.jspBay.web.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/21/17.
 */

@RestController
public class UserController {

//    @Autowired
//    UserService userService;

    private Logger logger = Logger.getLogger(UserController.class.getName());

    @Autowired
    private WebUserService webUserService;

    @RequestMapping("/user")
    public ResponseEntity<Principal> user(Principal user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/user/details")
    public ResponseEntity<UserDTO> userDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("UserController userDetails  () invoked: ");
        UserDTO userDTO = webUserService.findByUserName(auth.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @RequestMapping("/user/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody String newUser) throws IOException {
        HashMap result = new ObjectMapper().readValue(newUser, HashMap.class);
        logger.info("UserController createUser  () invoked: " + result);
        UserDTO userDTO = webUserService.findByUserName((String) result.get("username"));
        if(userDTO == null){
            userDTO = webUserService.findByEmail((String) result.get("email"));
            if(userDTO == null){
                userDTO = new UserDTO((String) result.get("username"),(String) result.get("password"),(String) result.get("email"));
                return webUserService.createNewUser(userDTO);
            }
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping("/user/modify")
    public ResponseEntity<UserDTO> modifyUser(@RequestBody String newUser) throws IOException {
        HashMap result = new ObjectMapper().readValue(newUser, HashMap.class);
        logger.info("UserController modifyUser  () invoked: " + result);
        UserDTO userDTO = new UserDTO((String) result.get("userName"),(String) result.get("newpassword"),(String) result.get("email"));
        return webUserService.modifyUser(userDTO);
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {
        System.out.println("resource");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }

    @RequestMapping("/user/lastlogin/update")
    public ResponseEntity<LocationDTO> updateLastUserLogin(@RequestBody String newUser) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HashMap result = new ObjectMapper().readValue(newUser, HashMap.class);
        logger.info("UserController updateLastUserLogin  () invoked: " + result);
        LocationDTO locationDTO = new LocationDTO((Double) result.get("lat"),(Double) result.get("lng"),(String) result.get("city"),(String) result.get("region"),auth.getName());
        return webUserService.updateLocation(locationDTO);
    }

    @RequestMapping("/user/lastlogin/retrieve")
    public ResponseEntity<LocationDTO> getLastUserLogin() throws IOException {
        logger.info("UserController getLastUserLogin  () invoked: ");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LocationDTO locationDTO = new LocationDTO(auth.getName());
        return webUserService.getLocation(locationDTO);
    }
}