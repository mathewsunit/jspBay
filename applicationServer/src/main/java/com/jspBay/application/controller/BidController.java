package com.jspBay.application.controller;

import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.BidNotFoundException;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class BidController {

    protected Logger logger = Logger.getLogger(BidController.class.getName());
    protected BidRepository bidRepository;
    protected UserRepository userRepository;

    /**
     * Create an instance plugging in the respository of Bids.
     *
     * @param bidRepository
     *            An bid repository implementation.
     */
    @Autowired
    public BidController(BidRepository bidRepository) {
        this.bidRepository = bidRepository;

        logger.info("BidRepository says system has "
                + bidRepository.countBids() + " bids");
    }

    /**
     * Fetch an bid with the specified bid number.
     *
     * @param bidNumber
     *            A numeric, 9 digit bid number.
     * @return The bid if found.
     * @throws BidNotFoundException
     *             If the number is not recognised.
     */
    @RequestMapping("/bids/{bidNumber}")
    public Bid byNumber(@PathVariable("bidNumber") String bidNumber) {

        logger.info("bids-service byNumber() invoked: " + bidNumber);
        Bid bid = bidRepository.findOneById(Long.valueOf(bidNumber));
        logger.info("bids-service byNumber() found: " + bid);

        if (bid == null)
            throw new BidNotFoundException(bidNumber);
        else {
            return bid;
        }
    }

    /**
     * Fetch bids with the specified name. A partial case-insensitive match
     * is supported. So <code>http://.../bids/bidder/a</code> will find any
     * bids with upper or lower case 'a' in their name.
     *
     * @param partialName
     * @return A non-null, non-empty set of bids.
     * @throws BidNotFoundException
     *             If there are no matches at all.
     */
    @RequestMapping("/bids/bidder/{name}")
    public List<Bid> byBidder(@PathVariable("name") String partialName) {
        logger.info("bids-service byBidder() invoked: "
                + bidRepository.getClass().getName() + " for "
                + partialName);

        User user = userRepository.findOneByUserName(partialName);
        if(null == user){
            throw new BidNotFoundException(partialName);
        }

        List<Bid> bids = bidRepository.findByBidder(user);
        logger.info("bids-service byBidder() found: " + bids);

        if (bids == null || bids.size() == 0)
            throw new BidNotFoundException(partialName);
        else {
            return bids;
        }
    }
}