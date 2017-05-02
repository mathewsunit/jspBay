package com.jspBay.web.service;

import com.jspBay.web.DTO.LocationDTO;
import com.jspBay.web.DTO.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@Service
public class WebUserService {

    public static final String APPLICATION_SERVICE_URL = "http://APPLICATION-SERVER";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebUserService.class.getName());

    @Autowired
    public WebUserService(@Value(APPLICATION_SERVICE_URL) String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }

    public UserDTO findByUserName(String userName) {
        logger.info("findByUserName() invoked: for " + userName);
        try {
            UserDTO response = restTemplate.getForObject(serviceUrl + "/user/{userName}",
                    UserDTO.class, userName);
            return response;
        } catch (HttpClientErrorException ex)   {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                return null;
            }
        }
        return null;
    }

    public UserDTO findByEmail(String email) {
        logger.info("findByEmail() invoked: for " + email);
        try {
            UserDTO response = restTemplate.getForObject(serviceUrl + "/user/email/{email}/",
                    UserDTO.class, email);
            return response;
        } catch (HttpClientErrorException ex)   {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                return null;
            }
        }
        return null;
    }

    public ResponseEntity<UserDTO> createNewUser(UserDTO user) {
        logger.info("createNewUser() invoked: for " + user.getUserName());
        return restTemplate.postForEntity(serviceUrl + "/user/create",user,UserDTO.class);
    }

    public ResponseEntity<LocationDTO> updateLocation(LocationDTO locationDTO) {
        logger.info("updateLocation() invoked: for " + locationDTO.getUser());
        return restTemplate.postForEntity(serviceUrl + "/user/updateLocation",locationDTO,LocationDTO.class);
    }

    public ResponseEntity<LocationDTO> getLocation(LocationDTO locationDTO) {
        logger.info("getLocation() invoked: for " + locationDTO.getUser());
        return restTemplate.postForEntity(serviceUrl + "/user/getLocation",locationDTO,LocationDTO.class);
    }

    public ResponseEntity<UserDTO> modifyUser(UserDTO user) {
        logger.info("modifyUser() invoked: for " + user.getUserName());
        return restTemplate.postForEntity(serviceUrl + "/user/modify",user,UserDTO.class);
    }
}