package com.jspBay.application.service;

import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.ItemNotFoundException;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    public ItemService(UserRepository userRepo, ItemRepository itemRepo) {
        this.userRepository = userRepo;
        this.itemRepository = itemRepo;
    }

    public List<ItemDTO> bySeller(String partialName) {
        logger.info("items-service bySeller() invoked: "
                + itemRepository.getClass().getName() + " for "
                + partialName);

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
            for(Item item:items){
                ItemDTO itemDTO = new ItemDTO(item.getCost(),item.getName(),item.getDescription(),item.getExpiring(),item.getItemStatus());
                itemsDTO.add(itemDTO);
            };
            return itemsDTO;
        }
    }

    public ItemDTO byNumber(String itemNumber) {

        logger.info("items-service byNumber() invoked: " + itemNumber);
        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
        logger.info("items-service byNumber() found: " + item);

        if (item == null)
            throw new ItemNotFoundException(itemNumber);
        else {
            ItemDTO itemDTO = new ItemDTO(item.getCost(),item.getName(),item.getDescription(),item.getExpiring(),item.getItemStatus());
            return itemDTO;
        }
    }
}
