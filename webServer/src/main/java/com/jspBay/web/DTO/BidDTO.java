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
    private Long id;
    @NotNull
    private Long bidAmount;
    @NotNull
    private UserDTO bidder;
    @NotNull
    private BidStatus bidStatus;

    private String errorMessage;

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

    public BidDTO(Long id, Long itemId, Long bidAmount, BidStatus bidStatus) {
        this.id = id;
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
    }

    public BidDTO(Long id, Long itemId, Long bidAmount, BidStatus bidStatus, ItemDTO item) {
        this.id = id;
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
        this.item = item;
    }

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus, ItemDTO item, String bidderUserName) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
        this.item = item;
        this.bidder = new UserDTO(bidderUserName);
    }

    public BidDTO(String errorMessage) {
        this.errorMessage = errorMessage;
        this.id = (long) -1;
    }

    public ItemDTO getItem() {
        return item;
    }

    public Long getId() {
        return id;
    }

    public BidDTO(){}

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return "BidDTO{" +
                "itemId=" + itemId +
                ", id=" + id +
                ", bidAmount=" + bidAmount +
                ", bidder=" + bidder +
                ", bidStatus=" + bidStatus +
                ", errorMessage='" + errorMessage + '\'' +
                ", item=" + item +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBidder(UserDTO bidder) {
        this.bidder = bidder;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
