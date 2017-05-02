package com.jspBay.application.repository;


import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Cacheable(value = "defaultCache")
    Bid findOneById(Long bidId);

    @Cacheable(value = "defaultCache")
    Bid findOneByItemAndBidStatus(Item item, BidStatus bidStatus);

    @Cacheable(value = "defaultCache")
    List<Bid> findFirstByItemAndBidStatusNotOrderByValueDesc(Item item, BidStatus bidStatus);

    @Cacheable(value = "defaultCache")
    List<Bid> findByItem(Item item);

    @Cacheable(value = "defaultCache")
    int countByItem(Item item);

    @Cacheable(value = "defaultCache")
    @Query("SELECT b from Bid b JOIN FETCH b.item JOIN b.bidder u WHERE u = ?1")
    List<Bid> findByBidder(User user);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Bid b SET b.bidStatus = ?1 WHERE b.item = ?2")
    int setFixedBidStatusForItem(BidStatus bidStatus, Item item);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Bid b SET b.bidStatus = ?1 WHERE b.bidStatus = ?2 AND b.item = ?3")
    int updateAllStatusForStatusAndItem(BidStatus bidStatus1, BidStatus bidStatus2, Item item);

    @Cacheable(value = "defaultCache")
    @Query("SELECT count(*) from Bid")
    String countBids();
}
