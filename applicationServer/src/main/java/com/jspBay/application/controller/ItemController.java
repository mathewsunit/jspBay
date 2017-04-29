package com.jspBay.application.controller;

import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import com.jspBay.application.service.ItemService;
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
public class ItemController {

    protected Logger logger = Logger.getLogger(ItemController.class.getName());
    protected ItemRepository itemRepository;
    protected UserRepository userRepository;

    @Autowired
    protected ItemService itemService;

    @Autowired
    public ItemController(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping("/items/{itemNumber}")
    public ItemDTO byNumber(@PathVariable("itemNumber") String itemNumber) {
        logger.info("ItemController byNumber() invoked for" + itemNumber);
        return itemService.byNumber(itemNumber);
    }

    @RequestMapping("/items/seller/{name}")
    public List<ItemDTO> bySeller(@PathVariable("name") String partialName) {
        logger.info("ItemController bySeller() invoked for " + partialName);
        return itemService.bySeller(partialName);
    }
}