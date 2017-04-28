package com.jspBay.application.DTO;

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

    public BidDTO(Long itemId, Long bidAmount, BidStatus bidStatus) {
        this.itemId = itemId;
        this.bidAmount = bidAmount;
        this.bidStatus = bidStatus;
    }
}
