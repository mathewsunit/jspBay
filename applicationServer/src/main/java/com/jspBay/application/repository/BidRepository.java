package com.jspBay.application.repository;


import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    Bid findOneById(Long bidId);
    Bid findOneByItemAndBidStatus(Item item, BidStatus bidStatus);

    List<Bid> findFirstByItemAndBidStatusNotOrderByValueDesc(Item item, BidStatus bidStatus);

    List<Bid> findByItem(Item item);
    int countByItem(Item item);

    @Query("SELECT b from Bid b JOIN FETCH b.item JOIN b.bidder u WHERE u = ?1")
    List<Bid> findByBidder(User user);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Bid b SET b.bidStatus = ?1 WHERE b.item = ?2")
    int setFixedBidStatusForItem(BidStatus bidStatus, Item item);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Bid b SET b.bidStatus = ?1 WHERE b.bidStatus = ?2 AND b.item = ?3")
    int updateAllStatusForStatusAndItem(BidStatus bidStatus1, BidStatus bidStatus2, Item item);

    @Query("SELECT count(*) from Bid")
    String countBids();
}
