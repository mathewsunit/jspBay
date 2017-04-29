package com.jspBay.application.repository;

import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Item findOneById(Long id);

    List<Item> findBySeller(User seller);

    @Query("SELECT count(*) from Item")
    String countItems();
}
