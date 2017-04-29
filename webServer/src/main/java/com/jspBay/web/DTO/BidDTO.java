package com.jspBay.web.DTO;

import com.jspBay.web.enums.BidStatus;

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
    private UserDTO bidder;
    @NotNull
    private BidStatus bidStatus;

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

    public UserDTO getBidder() {
        return bidder;
    }

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
    }

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus, ItemDTO item) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
        this.item = item;
    }

    public ItemDTO getItem() {
        return item;
    }

    public BidDTO(){}
}
