    package com.jspBay.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jspBay.web.DTO.BidDTO;
import com.jspBay.web.DTO.ItemDTO;
import com.jspBay.web.DTO.ResponseDTO;
import com.jspBay.web.service.WebItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class WebItemsController {

    private Logger logger = Logger.getLogger(WebItemsController.class.getName());

    @Autowired
    private WebItemsService itemsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("itemNumber", "searchText");
    }

    @RequestMapping("/items")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/items/{itemNumber}")
    public ResponseEntity<ItemDTO> byNumber(@PathVariable("itemNumber") String itemNumber) {
        logger.info("WebItemsController byNumber() invoked: " + itemNumber);
        ItemDTO item = itemsService.findByNumber(itemNumber);
        logger.info("WebItemsController byNumber() found: " + item);
        if(null != item) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/items/seller/{text}")
    public ResponseEntity<List<ItemDTO>> sellerSearch(@PathVariable("text") String name) {
        logger.info("WebItemsController bySeller() invoked: " + name);
        List<ItemDTO> items = itemsService.bySellerContains(name);
        logger.info("WebItemsController bySeller() found: " + items);
        if(null != items){
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/items/bid")
    public ResponseEntity<BidDTO> bidItem(@RequestBody String response) {
        logger.info("WebItemsController bySeller() invoked: " + response);
        try {
            HashMap result = new ObjectMapper().readValue(response, HashMap.class);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ItemDTO item = itemsService.findByNumber((String) result.get("itemNumber"));
            String message = item.getCanUserBid(auth.getName(), Calendar.getInstance().getTime(), result.get("bidAmount").toString());
            if(message == null) {
                BidDTO bid = itemsService.bidItem(item, result.get("bidAmount").toString(), auth.getName());
                logger.info("WebItemsController bySeller() found: " + bid);
                if(bid != null)
                    return new ResponseEntity<>(bid, HttpStatus.OK);
            } else {
                BidDTO bid = new BidDTO(message);
                logger.info("WebItemsController bySeller() returning: " + bid.getErrorMessage());
                return new ResponseEntity<>(bid, HttpStatus.OK);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/items/create")
    public ResponseEntity<ItemDTO> createItem(@RequestBody String response) {
        logger.info("WebItemsController bySeller() invoked: " + response);
        try {
            HashMap result = new ObjectMapper().readValue(response, HashMap.class);
            ItemDTO itemDTO = itemsService.createItem(result.get("itemName").toString(), result.get("itemDeadline").toString(), result.get("itemCost").toString(), result.get("itemDesc").toString());
            return new ResponseEntity<>(itemDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/items/remove", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> removeItem(@RequestBody String response) {
        logger.info("WebItemsController bySeller() invoked: " + response);
        try {
            HashMap result = new ObjectMapper().readValue(response, HashMap.class);
            String itemId = (String) result.get("itemId");
            ItemDTO itemDTO = itemsService.findByNumber(String.valueOf(itemId));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ResponseDTO responseDTO = itemsService.removeItem(itemDTO, auth.getName());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @RequestMapping(value = "/items/search", method = RequestMethod.GET)
//    public String searchForm(Model model) {
//        model.addAttribute("searchCriteria", new SearchCriteria());
//        return "itemSearch";
//    }
//
//    @RequestMapping(value = "/items/dosearch")
//    public String doSearch(Model model, SearchCriteria criteria,
//                           BindingResult result) {
//        logger.info("web-service search() invoked: " + criteria);
//
//        criteria.validate(result);
//
//        if (result.hasErrors())
//            return "itemSearch";
//
//        String itemNumber = criteria.getItemNumber();
//        if (StringUtils.hasText(itemNumber)) {
//            return byNumber(model, itemNumber);
//        } else {
//            String searchText = criteria.getSearchText();
//            return sellerSearch(model, searchText);
//        }
//    }
}