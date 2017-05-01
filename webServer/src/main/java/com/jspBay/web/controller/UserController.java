package com.jspBay.web.controller;

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
        System.out.println("user");
        System.out.println(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/users/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody String newUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("WebItemsController createUser  () invoked: " + newUser);
//        JSONObject json = JSONObject.fromObject(jsonMessage);
//
//        UserDTO userDTO = webUserService.findByUserName()
//        String message = item.getCanUserBid(auth.getName(), Calendar.getInstance().getTime(), bidAmount);
//        if(message != null) {
//            BidDTO bid = itemsService.bidItem(item, bidAmount);
//            logger.info("WebItemsController bySeller() found: " + bid);
//            if(bid != null)
//                return new ResponseEntity<>(bid, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(new BidDTO(message), HttpStatus.OK);
//        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {
        System.out.println("resource");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("id", UUID.randomUUID().toString());
        model.put("content", "Hello World");
        return model;
    }
}