package com.jspBay.application.service;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.BidNotFoundException;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class BidService {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BidRepository bidRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    protected Logger logger = Logger.getLogger(BidService.class.getName());

    public BidDTO byNumber(@PathVariable("bidNumber") String bidNumber) {
        logger.info("bids-service byNumber() invoked: " + bidNumber);
        Bid bid = bidRepository.findOneById(Long.valueOf(bidNumber));
        logger.info("bids-service byNumber() found: " + bid);

        if (bid == null)
            throw new BidNotFoundException(bidNumber);
        else {
            BidDTO bidDTO = new BidDTO(bid.getBidId(),bid.getValue(),bid.getBidStatus());
            return bidDTO;
        }
    }

    public List<BidDTO> byBidder(@PathVariable("name") String partialName) {
        logger.info("bids-service byBidder() invoked for "
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
            List<BidDTO> bidDTOList = new ArrayList<>();
            for(Bid bid:bids){
                BidDTO bidDTO = new BidDTO(bid.getBidId(),bid.getValue(),bid.getBidStatus());
                bidDTOList.add(bidDTO);
            }
            return bidDTOList;
        }
    }
}
