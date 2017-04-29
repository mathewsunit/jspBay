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

    @Query("SELECT i FROM Item i JOIN i.seller JOIN fetch currentBid i.currentBid LEFT OUTER JOIN Bid b2 ON (b2.item = i AND (b1.date < b2.date OR b1.date = b2.date AND b1.id < b2.id)) WHERE b2.id IS NULL AND i.seller = ?1")
    List<Item> findBySeller(User seller);

    @Query("SELECT count(*) from Item")
    String countItems();
}
