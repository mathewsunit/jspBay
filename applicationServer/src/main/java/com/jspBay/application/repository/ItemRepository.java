package com.jspBay.application.repository;

import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Cacheable(value = "defaultCache")
    Item findOneById(Long id);

    @Cacheable(value = "defaultCache")
    List<Item> findBySeller(User seller);

    @Cacheable(value = "defaultCache")
    List<Item> findByNameContainingIgnoreCase(String partialName);

    @Cacheable(value = "defaultCache")
    @Query("SELECT i FROM Item i WHERE LOWER(i.description) LIKE LOWER( CONCAT('%',:searchTerm,'%') ) OR LOWER(i.name) LIKE LOWER( CONCAT('%',:searchTerm,'%') )")
    List<Item> findBySearch(@Param("searchTerm") String searchTerm);

    @Cacheable(value = "defaultCache")
    @Query("SELECT count(*) from Item")
    String countItems();
}
