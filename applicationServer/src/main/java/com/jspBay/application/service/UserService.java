package com.jspBay.application.service;

import com.jspBay.application.DTO.LocationDTO;
import com.jspBay.application.DTO.UserDTO;
import com.jspBay.application.domain.Location;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.UserNotFoundException;
import com.jspBay.application.repository.LocationRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class UserService {

    protected Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserRepository userRepo;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepo, LocationRepository locationRepository) {
        this.userRepo = userRepo;
        this.locationRepository = locationRepository;
    }

    public UserDTO createNewUser(UserDTO userDTO) {
        logger.info("user-service createNewUser() invoked: " + userDTO.getUserName());
        User user = new User(userDTO.getUserName(),userDTO.getEmail(),new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        try{userRepo.save(user);}
        catch (Exception ex){
            logger.info("user-service DataIntegrityViolationException: " + ex.getMessage());
            return null;
        }
        return userDTO;
    }

    public UserDTO modifyUser(UserDTO userDTO) {
        logger.info("user-service modifyUser() invoked: " + userDTO.getUserName());
        User user = userRepo.findOneByUserName(userDTO.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        try{userRepo.save(user);}
        catch (Exception ex){
            logger.info("user-service DataIntegrityViolationException: " + ex.getMessage());
            return null;
        }
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

    public UserDTO byEmail(String email) {

        logger.info("user-service byEmail() invoked: " + email);
        User user = userRepo.findOneByEmail(email);

        if (user == null)
            throw new UserNotFoundException(email);
        else {
            UserDTO userDTO = new UserDTO(user,true);
            logger.info("user-service byEmail() found: " + user);
            return userDTO;
        }
    }

    public LocationDTO updateLocation(LocationDTO locationDTO) {

        logger.info("user-service updateLocation() invoked: " + locationDTO.getUser());
        User user = userRepo.findOneByUserName(locationDTO.getUser());
        Location locationOld = locationRepository.findOneByUser(user);
        if (locationOld == null){
            Location location = new Location(user, locationDTO.getLattitude(), locationDTO.getLongitude(), locationDTO.getCity(), locationDTO.getRegion(), new Date() ,new Date());
            locationRepository.save(location);
            return locationDTO;
        }
        else {
            logger.info("user-service updateLocation() found: " + locationOld);
            Location location = new Location(locationOld.getId(),user, locationDTO.getLattitude(), locationDTO.getLongitude(), locationDTO.getCity(), locationDTO.getRegion(),locationOld.getDate(),new Date());
            locationRepository.save(location);
            locationDTO = new LocationDTO(locationOld.getLattitude(),locationOld.getLongitude(),locationOld.getCity(),locationOld.getRegion(),locationOld.getUser().getUserName(),locationOld.getLastDate());
            return locationDTO;
        }
    }

    public LocationDTO getLocation(LocationDTO locationDTO) {

        logger.info("user-service getLocation() invoked: " + locationDTO.getUser());
        User user = userRepo.findOneByUserName(locationDTO.getUser());
        Location locationOld = locationRepository.findOneByUser(user);
        if (locationOld == null){
            return locationDTO;
        }
        else {
            logger.info("user-service getLocation() found: " + locationOld);
            locationDTO = new LocationDTO(locationOld.getLattitude(),locationOld.getLongitude(),locationOld.getCity(),locationOld.getRegion(),locationOld.getUser().getUserName(),locationOld.getLastDate());
            return locationDTO;
        }
    }
}