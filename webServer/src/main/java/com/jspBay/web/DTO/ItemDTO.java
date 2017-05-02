package com.jspBay.web.DTO;


import com.jspBay.web.enums.ItemStatus;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
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

    private boolean canUserBid;

    private String errorMsg;

	public ItemDTO(String userName, String itemName, String itemDesc, String itemDeadline, String itemCost) {
        Calendar calendar = Calendar.getInstance();
	    if(Long.parseLong(itemDeadline) < calendar.getTimeInMillis()) {
	        this.itemId = (long) -1;
            this.errorMsg = "Deadline cannot be less than the current time.";
        } else {
            calendar.setTimeInMillis(Long.parseLong(itemDeadline));
            this.seller = new UserDTO(userName);
            this.itemName = itemName;
            this.itemDesc = itemDesc;
            this.expiring = calendar.getTime();
            this.itemCostMin = Long.parseLong(itemCost);
        }
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

    public BidDTO getCurrentBid() {
        return currentBid;
    }

    public boolean isCanUserBid() {
        return canUserBid;
    }

    public Long getItemId() {
        return itemId;
    }

    public UserDTO getSeller() {
        return seller;
    }

    public void setCanUserBid(String currentUserName, Date currentDate) {
        this.canUserBid = getCanUserBid(currentUserName, currentDate, null) == null;
    }

    public String getCanUserBid(String currentUserName, Date currentDate, String bidAmount) {
        if(this.getItemStatus() != ItemStatus.ONSALE)
            return "You cannot bid on an item which is not on sale.";
        if(currentUserName.equals(this.seller.getUserName()))
            return "You cannot bid on your own item.";
        else if(this.getExpiring().getTime() < currentDate.getTime())
            return "The deadline has passed.";
        else if(bidAmount != null && ((this.getCurrentBid() != null && Long.parseLong(bidAmount) <= this.getCurrentBid().getBidAmount()) || Long.parseLong(bidAmount) < this.getItemCostMin()))
            return "Bid amount should be more than than the current bid";
        else
            return null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
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

    public void setCanUserBid(boolean canUserBid) {
        this.canUserBid = canUserBid;
    }

    public ItemDTO(Long itemId, Long itemCostMin, String itemName, String itemDesc, Date expiring, ItemStatus itemStatus, BidDTO currentBid, UserDTO seller) {
        this.itemId = itemId;
        this.itemCostMin = itemCostMin;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.expiring = expiring;
        this.itemStatus = itemStatus;
        this.currentBid = currentBid;
        this.seller = seller;
    }

    public ItemDTO(){}

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
                ", canUserBid=" + canUserBid +
                '}';
    }
}