package com.jspBay.application.DTO;


import com.jspBay.application.enums.ItemStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by sunit on 3/19/17.
 */

public class ItemDTO {


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

    public ItemDTO(Long itemCostMin, String itemName, String itemDesc, Date expiring, ItemStatus itemStatus) {
        this.itemCostMin = itemCostMin;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.expiring = expiring;
        this.itemStatus = itemStatus;
    }
}