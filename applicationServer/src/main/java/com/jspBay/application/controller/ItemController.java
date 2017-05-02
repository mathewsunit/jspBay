package com.jspBay.application.controller;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.DTO.ResponseDTO;
import com.jspBay.application.scheduler.ScheduleThread;
import com.jspBay.application.scheduler.Scheduler;
import com.jspBay.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class ItemController {

    protected Logger logger = Logger.getLogger(ItemController.class.getName());

    @Autowired
    private Scheduler scheduler;

    @Autowired
    protected ItemService itemService;

    @RequestMapping(value = "/items/{itemNumber}", method = RequestMethod.GET)
    public ItemDTO byNumber(@PathVariable("itemNumber") String itemNumber) {
        logger.info("ItemController byNumber() invoked for" + itemNumber);
        return itemService.byNumber(itemNumber);
    }

    /*
    @RequestMapping("/items/schedule/{time}")
    public String schedule(@PathVariable("time") String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(time));
        scheduler.schedule(new ScheduleThread(Long.parseLong(time), itemService, (long)1), cal.getTime());
        return "Done";
    }
    */

    @RequestMapping(value = "/items/bid", method = RequestMethod.POST)
    public BidDTO bid(@RequestBody BidDTO bidDTO) {
        logger.info("ItemController byNumber() invoked for" + bidDTO);
        return itemService.bidOnItem(bidDTO);
    }

    @RequestMapping("/items/seller/{name}")
    public List<ItemDTO> bySeller(@PathVariable("name") String partialName) {
        logger.info("ItemController bySeller() invoked for " + partialName);
        return itemService.bySeller(partialName);
    }

    @RequestMapping(value = "/items/create", method = RequestMethod.POST)
    public ItemDTO create(@RequestBody ItemDTO itemDTO) {
        logger.info("ItemController create() invoked for" + itemDTO);
        return itemService.createItem(itemDTO, scheduler);
    }

    @RequestMapping(value = "/items/remove", method = RequestMethod.POST)
    public ResponseDTO<ItemDTO> remove(@RequestBody ResponseDTO<ItemDTO> responseDTO) {
        logger.info("ItemController remove() invoked for" + responseDTO.getObject());
        return itemService.removeItem(responseDTO.getObject());
    }
}