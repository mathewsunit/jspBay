    package com.jspBay.web.controller;

import com.jspBay.web.DTO.ItemDTO;
import com.jspBay.web.service.WebItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public class WebSearchController {

        private Logger logger = Logger.getLogger(WebSearchController.class.getName());

        @Autowired
        private WebItemsService itemsService;

        @InitBinder
        public void initBinder(WebDataBinder binder) {
            binder.setAllowedFields("itemNumber", "searchText");
        }

        @RequestMapping("/search/{text}")
        public ResponseEntity<List<ItemDTO>> itemSearch(@PathVariable("text") String searchText) {
            logger.info("WebItemsController itemSearch() invoked: " + searchText);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            List<ItemDTO> items = itemsService.searchItems(searchText,auth);
            logger.info("WebItemsController itemSearch() found: " + items);
            if(null != items){
                return new ResponseEntity<>(items, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }