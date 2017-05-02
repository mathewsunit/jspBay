package com.jspBay.application.DTO;


import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.enums.ItemStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by sunit on 3/19/17.
 */

public class ItemDTO {


    @NotNull
    private Long itemId;
    @NotNull
    private Long itemCostMin;
    @NotNull
    private String itemName;
    @NotNull
    private String itemDesc;
    @NotNull
    private Date expiring;
    @NotNull
    private ItemStatus itemStatus;

    private UserDTO seller;

    private BidDTO currentBid;

    private String errorMsg = null;

	public ItemDTO(Item item) {
	    this.itemId = item.getId();
	    this.itemCostMin = item.getCost();
	    this.itemName = item.getName();
	    this.itemDesc = item.getDescription();
	    this.expiring = item.getExpiring();
	    this.itemStatus = item.getItemStatus();
	    this.seller = null;
	}

	public BidDTO getCurrentBid() {
        return currentBid;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    public Long getItemCostMin() {
        return itemCostMin;
    }

    public void setItemCostMin(Long itemCostMin) {
        this.itemCostMin = itemCostMin;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getExpiring() {
        return expiring;
    }

    public void setExpiring(Date expiring) {
        this.expiring = expiring;
    }

    public Long getItemId() {
        return itemId;
    }

    public UserDTO getSeller() {
        return seller;
    }

    private ItemDTO(Long itemId, Long itemCostMin, String itemName, String itemDesc, Date expiring, ItemStatus itemStatus, BidDTO currentBid, UserDTO seller) {
        this.itemId = itemId;
        this.itemCostMin = itemCostMin;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.expiring = expiring;
        this.itemStatus = itemStatus;
        this.currentBid = currentBid;
        this.seller = seller;
    }

    public ItemDTO(Item item, BidDTO currentBid) {
        this(item.getId(), item.getCost(),item.getName(),item.getDescription(),item.getExpiring(),item.getItemStatus(), currentBid, new UserDTO(item.getSeller()));
    }

    public ItemDTO() {
    }

    public ItemDTO(String errorMsg) {
	    this.itemId = (long) -1;
        this.errorMsg = errorMsg;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public void setSeller(UserDTO seller) {
        this.seller = seller;
    }

    public void setCurrentBid(BidDTO currentBid) {
        this.currentBid = currentBid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "itemId=" + itemId +
                ", itemCostMin=" + itemCostMin +
                ", itemName='" + itemName + '\'' +
                ", itemDesc='" + itemDesc + '\'' +
                ", expiring=" + expiring +
                ", itemStatus=" + itemStatus +
                ", seller=" + seller +
                ", currentBid=" + currentBid +
                '}';
    }
}