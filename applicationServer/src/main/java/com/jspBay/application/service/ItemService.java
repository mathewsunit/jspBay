package com.jspBay.application.service;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.DTO.UserDTO;
import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import com.jspBay.application.enums.ItemStatus;
import com.jspBay.application.exceptions.ItemNotFoundException;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class ItemService {

    protected Logger logger = Logger.getLogger(ItemService.class.getName());

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;

    @Autowired
    public ItemService(UserRepository userRepo, ItemRepository itemRepo, BidRepository bidRepository) {
        this.userRepository = userRepo;
        this.itemRepository = itemRepo;
        this.bidRepository = bidRepository;
    }

    public List<ItemDTO> bySeller(String partialName) {
        logger.info("items-service bySeller() invoked: " + itemRepository.getClass().getName() + " for " + partialName);

        User user = userRepository.findOneByUserName(partialName);
        if(null == user){
            throw new ItemNotFoundException(partialName);
        }

        List<ItemDTO> itemsDTO = new ArrayList<>();
        List<Item> items = itemRepository.findBySeller(user);
        logger.info("items-service bySeller() found: " + items);
        if (items == null || items.size() == 0)
            throw new ItemNotFoundException(partialName);
        else {
            for(Item item:items) {
                List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
                ItemDTO itemDTO = new ItemDTO(item, bids.size() == 1 ? new BidDTO(bids.get(0)) : null);
                itemsDTO.add(itemDTO);
            }
            return itemsDTO;
        }
    }

    public ItemDTO byNumber(String itemNumber) {

        logger.info("items-service byNumber() invoked: " + itemNumber);
        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
        logger.info("items-service byNumber() found: " + item);

        if (item == null) {
            throw new ItemNotFoundException(itemNumber);
        } else {
            List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
            return new ItemDTO(item, (bids.size() == 1) ? new BidDTO(bids.get(0), true) : null);
        }
    }

    public BidDTO bidOnItem(BidDTO bid) {

        logger.info("items-service bidOnItem() invoked: " + bid);
        User bidder = userRepository.findOneByUserName(bid.getBidder().getUserName());
        Item item = itemRepository.findOneById(bid.getItemId());
        Bid newBid = new Bid(bidder, item, bid.getBidStatus(), Calendar.getInstance().getTime(), bid.getBidAmount());
        newBid = bidRepository.save(newBid);
        if (newBid == null) {
            throw new ItemNotFoundException("Bid could not be created.");
        } else {
            return new BidDTO(newBid, false);
        }

    }

    public ItemDTO createItem(ItemDTO itemDTO) {
        User seller = userRepository.findOneByUserName(itemDTO.getSeller().getUserName());
        Item item = new Item(itemDTO.getItemName(), seller, itemDTO.getItemDesc(), itemDTO.getItemCostMin(), itemDTO.getExpiring(), Calendar.getInstance().getTime(), ItemStatus.ONSALE);
        try {
            item = itemRepository.save(item);
        } catch (DataAccessException e) {
            return new ItemDTO(e.getMessage());
        }
        /*
            create Thread and execute the end of item.
        */
        return new ItemDTO(item);
    }
}
