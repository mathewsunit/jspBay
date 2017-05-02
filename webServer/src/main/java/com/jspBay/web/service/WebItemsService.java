package com.jspBay.web.service;

import com.jspBay.web.DTO.BidDTO;
import com.jspBay.web.DTO.ItemDTO;
import com.jspBay.web.enums.BidStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@Service
public class WebItemsService {

    public static final String APPLICATION_SERVICE_URL = "http://APPLICATION-SERVER";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebItemsService.class.getName());

    @Autowired
    public WebItemsService(@Value(APPLICATION_SERVICE_URL) String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is "
                + restTemplate.getRequestFactory().getClass());
    }

    public ItemDTO findByNumber(String itemNumber) {
        logger.info("findByNumber() invoked: for " + itemNumber);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ItemDTO item = restTemplate.getForObject(serviceUrl + "/items/{number}", ItemDTO.class, itemNumber);
        item.setCanUserBid(auth.getName(), Calendar.getInstance().getTime());
        return item;
    }

    public BidDTO bidItem(ItemDTO itemDTO, String bidAmount, String bidderUserName) {
        logger.info("bidItem() invoked:  for " + itemDTO.getItemId());
        return restTemplate.postForObject(serviceUrl + "/items/bid/", new BidDTO(itemDTO.getItemId(), Long.parseLong(bidAmount), BidStatus.LEADING, itemDTO, bidderUserName), BidDTO.class);
    }

    public List<ItemDTO> bySellerContains(String name) {
        logger.info("bySellerContains() invoked:  for " + name);
        ItemDTO[] item = null;
        try {
            item = restTemplate.getForObject(serviceUrl + "/items/seller/{name}", ItemDTO[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (item == null || item.length == 0)
            return null;
        else
            return Arrays.asList(item);
    }

    public List<ItemDTO> searchItems(String name, Authentication auth) {
        logger.info("searchItems() invoked:  for " + name);
        ItemDTO[] item = null;
        try {
            item = restTemplate.getForObject(serviceUrl + "/search/{name}", ItemDTO[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }
        List<ItemDTO> itemReturns = new ArrayList<>();
        if (item == null || item.length == 0)
            return null;
        else
            for(ItemDTO itemDTO:item){
                String message = itemDTO.getCanUserBid(auth.getName(), Calendar.getInstance().getTime(),String.valueOf(Long.MAX_VALUE));
                if(message == null) {
                    itemReturns.add(itemDTO);
                }
            }
            return itemReturns;
    }

    /*
    public ItemDTO getByNumber(String itemNumber) {
        ItemDTO account = restTemplate.getForObject(serviceUrl + "/items/{number}", ItemDTO.class, itemNumber);
        if (account == null)
            throw new ItemNotFoundException(itemNumber);
        else
            return account;
    }

    */
}