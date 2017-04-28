package com.jspBay.web.controller;

import com.jspBay.web.DTO.BidDTO;
import com.jspBay.web.service.WebBidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<BidDTO>> ownerSearch(Model model, @PathVariable("text") String name) {
        logger.info("WebBidsController byBidder() invoked: " + name);

        List<BidDTO> bids = bidsService.byBidderContains(name);
        logger.info("WebBidsController byBidder() found: " + bids);
        model.addAttribute("search", name);
        if (bids != null)
            return new ResponseEntity<>(bids, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}