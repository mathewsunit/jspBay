package com.jspBay.application.controller;

import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.exceptions.ItemNotFoundException;
import com.jspBay.application.repository.ItemRepository;
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
public class ItemController {

    protected Logger logger = Logger.getLogger(ItemController.class.getName());
    protected ItemRepository itemRepository;
    protected UserRepository userRepository;

    /**
     * Create an instance plugging in the respository of Items.
     *
     * @param itemRepository
     *            An item repository implementation.
     */
    @Autowired
    public ItemController(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;

        logger.info("ItemRepository says system has "
                + itemRepository.countItems() + " items");
        logger.info("UserRepository says system has "
                + userRepository.countItems() + " items");

    }

    /**
     * Fetch an item with the specified item number.
     *
     * @param itemNumber
     *            A numeric, 9 digit item number.
     * @return The item if found.
     * @throws ItemNotFoundException
     *             If the number is not recognised.
     */
    @RequestMapping("/items/{itemNumber}")
    public Item byNumber(@PathVariable("itemNumber") String itemNumber) {

        logger.info("items-service byNumber() invoked: " + itemNumber);
        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
        logger.info("items-service byNumber() found: " + item);

        if (item == null)
            throw new ItemNotFoundException(itemNumber);
        else {
            return item;
        }
    }

//    /**
//     * Fetch an item with the specified item number.
//     *
//     * @param itemNumber
//     *            A numeric, 9 digit item number.
//     * @return The item if found.
//     * @throws ItemNotFoundException
//     *             If the number is not recognised.
//     */
//    @RequestMapping(value = "/items/createItem", method = RequestMethod.PUT)
//    public Item createItem(Model model) {
//
//        Item item = model.
//        logger.info("items-service createItem() invoked: " + );
////        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
//        logger.info("items-service createItem() found: " + item);
//
//        if (item == null)
//            throw new ItemNotFoundException(itemNumber);
//        else {
//            return item;
//        }
//    }


    /**
     * Fetch items with the specified name. A partial case-insensitive match
     * is supported. So <code>http://.../items/seller/a</code> will find any
     * items with upper or lower case 'a' in their name.
     *
     * @param partialName
     * @return A non-null, non-empty set of items.
     * @throws ItemNotFoundException
     *             If there are no matches at all.
     */
    @RequestMapping("/items/seller/{name}")
    public List<Item> bySeller(@PathVariable("name") String partialName) {
        logger.info("items-service bySeller() invoked: "
                + itemRepository.getClass().getName() + " for "
                + partialName);

        User user = userRepository.findOneByUserName(partialName);
        if(null == user){
            throw new ItemNotFoundException(partialName);
        }

        List<Item> items = itemRepository.findBySeller(user);
        logger.info("items-service bySeller() found: " + items);

        if (items == null || items.size() == 0)
            throw new ItemNotFoundException(partialName);
        else {
            return items;
        }
    }
}