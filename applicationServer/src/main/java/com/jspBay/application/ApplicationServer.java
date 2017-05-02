package com.jspBay.application;

import com.jspBay.application.configuration.AccountsConfiguration;
import org.springframework.boot.SpringApplication;
import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import com.jspBay.application.scheduler.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
//        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
@EnableDiscoveryClient
@Import(AccountsConfiguration.class)
public class ApplicationServer {

    protected Logger logger = Logger.getLogger(ApplicationServer.class.getName());

    /**
     * Run the application using Spring Boot and an embedded servlet engine.
     *
     * @param args
     *            Program arguments - ignored.
     */

    public static void main(String[] args) {
        // Tell server to look for application-server.properties or
        // application-server.yml
        System.setProperty("spring.config.name", "application-server");
        SpringApplication.run(ApplicationServer.class, args);
    }
}
