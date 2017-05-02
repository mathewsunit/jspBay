package com.jspBay.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jspBay.web.DTO.BidDTO;
import com.jspBay.web.DTO.ResponseDTO;
import com.jspBay.web.service.WebBidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class WebBidsController {

    private Logger logger = Logger.getLogger(WebBidsController.class.getName());

    @Autowired
    private WebBidsService bidsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("bidNumber", "searchText");
    }

    @RequestMapping("/bids")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/bids/{bidNumber}")
    public ResponseEntity<BidDTO> byNumber(@PathVariable("bidNumber") String bidNumber) {
        logger.info("WebBidsController byNumber() invoked: " + bidNumber);
        BidDTO bid = bidsService.findByNumber(bidNumber);
        logger.info("WebBidsController byNumber() found: " + bid);
        if(bid != null){
            return new ResponseEntity<>(bid, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/bids/bidder/{text}")
    public ResponseEntity<List<BidDTO>> ownerSearch(@PathVariable("text") String name) {
        logger.info("WebBidsController byBidder() invoked: " + name);

        List<BidDTO> bids = bidsService.byBidderContains(name);
        logger.info("WebBidsController byBidder() found: " + bids);
        if (bids != null)
            return new ResponseEntity<>(bids, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/bids/remove", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> removeItem(@RequestBody String response) {
        logger.info("WebItemsController bySeller() invoked: " + response);
        try {
            HashMap result = new ObjectMapper().readValue(response, HashMap.class);
            String bidId = (String) result.get("bidId");
            BidDTO bidDTO = bidsService.getByNumber(bidId);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            ResponseDTO responseDTO = bidsService.removeItem(bidDTO, auth.getName());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}