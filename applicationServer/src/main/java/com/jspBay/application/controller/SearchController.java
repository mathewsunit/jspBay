package com.jspBay.application.controller;

import com.jspBay.application.DTO.ItemDTO;
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
public class SearchController {

    protected Logger logger = Logger.getLogger(SearchController.class.getName());

    @Autowired
    protected ItemService itemService;

    @RequestMapping("/search/{searchTerm}")
    public List<ItemDTO> bySearchTerm(@PathVariable("searchTerm") String searchTerm) {
        logger.info("SearchController bySearchTerm() invoked for" + searchTerm);
        return itemService.bySearchTerm(searchTerm);
    }
}