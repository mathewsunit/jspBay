package com.jspBay.application.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jspBay.application.enums.ItemStatus;
import org.hibernate.annotations.Check;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sunit on 3/19/17.
 */

@Entity
@Table(name = "item")
@Check(constraints = "item_status = 'REMOVED' or game_status = 'ONSALE' or game_status = 'SOLD'")
public class Item extends ResourceSupport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    @JsonProperty("id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

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

    public Long getItemId() {
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

    public void setName(String name) {
        this.name = name;
    }

    public Item(User seller, User buyer, String description, Long cost){
        this.seller = seller;
        this.buyer = buyer;
        this.description = description;
        this.cost = cost;
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        this.created = c.getTime();
        this.expiring = c.getTime();
        this.itemStatus = ItemStatus.ONSALE;
        this.name = "Lolzz"+String.valueOf(c.getTime());
    }

    public Item(String name, User seller, String description, Long cost, Date expiring, Date created, ItemStatus itemStatus) {
        this.name = name;
        this.seller = seller;
        this.description = description;
        this.cost = cost;
        this.expiring = expiring;
        this.created = created;
        this.itemStatus = itemStatus;
    }

    public Item(){
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", buyer='" + buyer.getUserId() + '\'' +
                ", seller='" + seller.getUserId() + '\'' +
                ", description='" + description + '\'' +
                ", expiring='" + expiring + '\'' +
                ", itemStatus='" + itemStatus + '\'' +
                ", cost=" + cost +
                '}';
    }
}
