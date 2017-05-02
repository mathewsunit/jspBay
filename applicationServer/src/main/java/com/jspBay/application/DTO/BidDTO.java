package com.jspBay.application.DTO;

import com.jspBay.application.domain.Bid;
import com.jspBay.application.enums.BidStatus;

import javax.validation.constraints.NotNull;


/**
 * Created by sunit on 3/19/17.
 */
public class BidDTO {

    @NotNull
    private Long itemId;
    @NotNull
    private Long bidAmount;
    @NotNull
    private BidStatus bidStatus;
    @NotNull
    private UserDTO bidder;

    private Long bidderId;

    private Long id;

    private ItemDTO item;

    public BidStatus getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(BidStatus bidStatus) {
        this.bidStatus = bidStatus;
    }

    public Long getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Long bidAmount) {
        this.bidAmount = bidAmount;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public ItemDTO getItem() {
        return item;
    }

    public UserDTO getBidder() {
        return bidder;
    }

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus) {
        this(itemId, bidAmount, bidStatus, null, (long) -1);
    }

    public BidDTO(Bid bid) {
        this(bid, false);
    }

    public Long getBidderId() {
        return bidderId;
    }

    public BidDTO(Bid bid, boolean shouldGetBidder) {
        this(bid.getItem().getId(), bid.getValue(), bid.getBidStatus(), null, bid.getBidder().getId());
        this.id = bid.getId();
        if(shouldGetBidder)
            this.bidder = new UserDTO(bid.getBidder());
    }

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus, ItemDTO item, Long bidderId) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
        this.item = item;
        this.bidderId = bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBidder(UserDTO bidder) {
        this.bidder = bidder;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public BidDTO() {
    }

    @Override
    public String toString() {
        return "BidDTO{" +
                "itemId=" + itemId +
                ", bidAmount=" + bidAmount +
                ", bidStatus=" + bidStatus +
                ", bidder=" + bidder +
                ", bidderId=" + bidderId +
                ", item=" + item +
                '}';
    }
}
