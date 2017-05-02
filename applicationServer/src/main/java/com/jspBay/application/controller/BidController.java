package com.jspBay.application.controller;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.DTO.ResponseDTO;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class BidController {

    protected Logger logger = Logger.getLogger(BidController.class.getName());

    @Autowired
    protected BidService bidService;

    @RequestMapping("/bids/{bidNumber}")
    public BidDTO byNumber(@PathVariable("bidNumber") String bidNumber) {
        logger.info("BidController byNumber() invoked: " + bidNumber);
        return bidService.byNumber(bidNumber);
    }

    @RequestMapping("/bids/bidder/{name}")
    public List<BidDTO> byBidder(@PathVariable("name") String partialName) {
        logger.info("BidController byBidder() invoked for " + partialName);
        return bidService.byBidder(partialName);
    }

    @RequestMapping(value = "/bids/remove", method = RequestMethod.POST)
    public ResponseDTO<BidDTO> remove(@RequestBody ResponseDTO<BidDTO> responseDTO) {
        logger.info("ItemController remove() invoked for" + responseDTO.getObject());
        return bidService.removeItem(responseDTO.getObject());
    }
}