package com.jspBay.application;

import com.jspBay.application.configuration.AccountsConfiguration;
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
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Calendar;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class})
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
        new SpringApplicationBuilder(ApplicationServer.class).web(true).run(args);
        /*
        Try initializing.
        */
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, ItemRepository itemRepository, BidRepository bidRepository) {
        return (args) -> {
            //save a couple of players
            User ala = new User("ala", "pruthvi.vooka@hotmail.com", new BCryptPasswordEncoder().encode("ala"));
            userRepository.save(ala);
            User mary = new User("mary", "mathewsunit@gmail.com",  new BCryptPasswordEncoder().encode("mary"));
            userRepository.save(mary);
            Item item1 = new Item(ala,mary,"blah",(long) 100);
            itemRepository.save(item1);
            itemRepository.save(new Item(ala,mary,"bling", (long) 100));
            Item item3 = new Item(mary,ala,"blingbling", (long) 10000);
            itemRepository.save(item3);
            bidRepository.save(new Bid(ala,item3, BidStatus.LEADING, Calendar.getInstance().getTime(), (long) 10000));
            bidRepository.save(new Bid(mary,item1, BidStatus.LEADING, Calendar.getInstance().getTime(), (long) 10000));
        };
    }
}
