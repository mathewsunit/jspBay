package com.jspBay.application.repository;

import com.jspBay.application.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
* Created by sunit on 3/19/17.
*/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUserName(String userName);

    @Query("SELECT count(*) from User")
    String countItems();


    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User findOneByEmail(@Param("email") String email);
}
