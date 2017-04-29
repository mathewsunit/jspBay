package com.jspBay.application.domain;

import com.jspBay.application.enums.BidStatus;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sunit on 3/19/17.
 */

@Entity
@Check(constraints = "bid_status = 'ACCEPTED' or bid_status = 'REJECTED' or bid_status = 'LEADING'")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bidder_id", nullable = false)
    private User bidder;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Enumerated(EnumType.STRING)
    private BidStatus bidStatus;

    @Column(name = "value", nullable = false)
    private Long value;

    @Column(name = "created", nullable = false)
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBidder() {
        return bidder;
    }

    public void setBidder(User bidder) {
        this.bidder = bidder;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BidStatus getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(BidStatus bidStatus) {
        this.bidStatus = bidStatus;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Bid(User bidder, Item item, BidStatus bidStatus, Date created, Long value){
        this.bidder = bidder;
        this.item = item;
        this.created = created;
        this.value = value;
        this.bidStatus = bidStatus;
    }

    public Bid(){
    }
}
