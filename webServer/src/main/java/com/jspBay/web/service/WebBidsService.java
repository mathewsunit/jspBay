package com.jspBay.web.service;

import com.jspBay.web.DTO.BidDTO;
import com.jspBay.web.exceptions.BidNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@Service
public class WebBidsService {

    public static final String APPLICATION_SERVICE_URL = "http://APPLICATION-SERVER";

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebBidsService.class.getName());

    @Autowired
    public WebBidsService(@Value(APPLICATION_SERVICE_URL) String serviceUrl) {
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

    public BidDTO findByNumber(String bidNumber) {

        logger.info("findByNumber() invoked: for " + bidNumber);
        return restTemplate.getForObject(serviceUrl + "/bid/{number}",
                BidDTO.class, bidNumber);
    }

    public List<BidDTO> byBidderContains(String name) {
        logger.info("byBidderContains() invoked:  for " + name);
        BidDTO[] bid = null;

        try {
            bid = restTemplate.getForObject(serviceUrl
                    + "/bid/bidder/{name}", BidDTO[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (bid == null || bid.length == 0)
            return null;
        else
            return Arrays.asList(bid);
    }

    public BidDTO getByNumber(String bidNumber) {
        BidDTO account = restTemplate.getForObject(serviceUrl
                + "/bid/{number}", BidDTO.class, bidNumber);

        if (account == null)
            throw new BidNotFoundException(bidNumber);
        else
            return account;
    }
}