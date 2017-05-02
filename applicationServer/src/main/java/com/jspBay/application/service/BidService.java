package com.jspBay.application.service;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.DTO.ResponseDTO;
import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import com.jspBay.application.exceptions.BidNotFoundException;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class BidService {

    private final ItemRepository itemRepo;
    private final UserRepository userRepository;
    private final BidRepository bidRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    protected Logger logger = Logger.getLogger(BidService.class.getName());


    @Autowired
    public BidService(ItemRepository itemRepo, UserRepository userRepo, BidRepository bidRepo) {
        this.itemRepo = itemRepo;
        this.userRepository = userRepo;
        this.bidRepository = bidRepo;
    }

    public BidDTO byNumber(@PathVariable("bidNumber") String bidNumber) {
        logger.info("bids-service byNumber() invoked: " + bidNumber);
        Bid bid = bidRepository.findOneById(Long.valueOf(bidNumber));
        logger.info("bids-service byNumber() found: " + bid);

        //Changed to new bid dto. check for conflicts.
        if (bid == null)
            throw new BidNotFoundException(bidNumber);
        else
            return new BidDTO(bid, true);
    }

    public List<BidDTO> byBidder(@PathVariable("name") String partialName) {
        logger.info("bids-service byBidder() invoked for " + partialName);
        User user = userRepository.findOneByUserName(partialName);
        if(user == null)
            throw new BidNotFoundException(partialName);

        List<Bid> bids = bidRepository.findByBidder(user);
        logger.info("bids-service byBidder() found: " + bids);

        if (bids == null || bids.size() == 0)
            throw new BidNotFoundException(partialName);
        else {
            List<BidDTO> bidDTOList = new ArrayList<>();
            for(Bid bid : bids)
                bidDTOList.add(new BidDTO(bid.getId(),bid.getValue(), bid.getBidStatus(), new ItemDTO(bid.getItem(), null), (long) -1));
            return bidDTOList;
        }
    }

    public ResponseDTO<BidDTO> removeItem(BidDTO object) {
        Bid bid = bidRepository.findOneById(object.getId());
        bid.setBidStatus(BidStatus.DELETED);
        try {
            bid = bidRepository.save(bid);
            return new ResponseDTO<>("SUCCESS", "Successfully removed the item from sale.", new BidDTO(bid));
        } catch (DataAccessException e) {
            return new ResponseDTO<>("ERROR", e.getMessage());
        }
    }
}
