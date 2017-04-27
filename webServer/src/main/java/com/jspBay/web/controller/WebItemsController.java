package com.jspBay.web.controller;

import com.jspBay.web.DTO.ItemDTO;
import com.jspBay.web.service.WebItemsService;
import com.jspBay.web.service.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    public String byNumber(Model model,
                           @PathVariable("itemNumber") String itemNumber) {

        logger.info("web-service byNumber() invoked: " + itemNumber);

        ItemDTO item = itemsService.findByNumber(itemNumber);
        logger.info("web-service byNumber() found: " + item);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping("/items/seller/{text}")
    public String sellerSearch(Model model, @PathVariable("text") String name) {
        logger.info("web-service byOwner() invoked: " + name);

        List<ItemDTO> items = itemsService.bySellerContains(name);
        logger.info("web-service byOwner() found: " + items);
        model.addAttribute("search", name);
        if (items != null)
            model.addAttribute("items", items);
        return "items";
    }

    @RequestMapping(value = "/items/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "itemSearch";
    }

    @RequestMapping(value = "/items/dosearch")
    public String doSearch(Model model, SearchCriteria criteria,
                           BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);

        criteria.validate(result);

        if (result.hasErrors())
            return "itemSearch";

        String itemNumber = criteria.getItemNumber();
        if (StringUtils.hasText(itemNumber)) {
            return byNumber(model, itemNumber);
        } else {
            String searchText = criteria.getSearchText();
            return sellerSearch(model, searchText);
        }
    }
}