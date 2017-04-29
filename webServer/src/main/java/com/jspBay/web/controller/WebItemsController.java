package com.jspBay.web.controller;

import com.jspBay.web.DTO.ItemDTO;
import com.jspBay.web.service.WebItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class WebItemsController {

    private Logger logger = Logger.getLogger(WebItemsController.class.getName());

    @Autowired
    private WebItemsService itemsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("itemNumber", "searchText");
    }

    @RequestMapping("/items")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/items/{itemNumber}")
    public ResponseEntity<ItemDTO> byNumber(@PathVariable("itemNumber") String itemNumber) {
        logger.info("WebItemsController byNumber() invoked: " + itemNumber);
        ItemDTO item = itemsService.findByNumber(itemNumber);
        logger.info("WebItemsController byNumber() found: " + item);
        if(null != item) {
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping("/items/seller/{text}")
    public ResponseEntity<List<ItemDTO>> sellerSearch(@PathVariable("text") String name) {
        logger.info("WebItemsController bySeller() invoked: " + name);
        List<ItemDTO> items = itemsService.bySellerContains(name);
        logger.info("WebItemsController bySeller() found: " + items);
        if(null != items){
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @RequestMapping(value = "/items/search", method = RequestMethod.GET)
//    public String searchForm(Model model) {
//        model.addAttribute("searchCriteria", new SearchCriteria());
//        return "itemSearch";
//    }
//
//    @RequestMapping(value = "/items/dosearch")
//    public String doSearch(Model model, SearchCriteria criteria,
//                           BindingResult result) {
//        logger.info("web-service search() invoked: " + criteria);
//
//        criteria.validate(result);
//
//        if (result.hasErrors())
//            return "itemSearch";
//
//        String itemNumber = criteria.getItemNumber();
//        if (StringUtils.hasText(itemNumber)) {
//            return byNumber(model, itemNumber);
//        } else {
//            String searchText = criteria.getSearchText();
//            return sellerSearch(model, searchText);
//        }
//    }
}