package com.jspBay.application.domain;

import com.jspBay.application.enums.ItemStatus;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sunit on 3/19/17.
 */

@Entity
@Check(constraints = "item_status = 'REMOVED' or game_status = 'ONSALE' or game_status = 'SOLD'")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
    private Bid currentBid;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "expiring", nullable = false)
    private Date expiring;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "name", nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Date getExpiring() {
        return expiring;
    }

    public void setExpiring(Date expiring) {
        this.expiring = expiring;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public Bid getCurrentBid() {
        return currentBid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item(User seller, User buyer, String description, Long cost){
        this.seller = seller;
        this.buyer = buyer;
        this.description = description;
        this.cost = cost;
        Date now = new Date();
        this.created = now;
        this.expiring = now;
        this.itemStatus = ItemStatus.ONSALE;
        this.name = "Lolzz"+String.valueOf(now);
    }

    public Item(){
    }
}
