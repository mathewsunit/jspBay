package com.jspBay.application;

import com.jspBay.application.configuration.AccountsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

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
        SpringApplication.run(ApplicationServer.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(UserRepository userRepository, ItemRepository itemRepository) {
//        return (args) -> {
//            //save a couple of players
//            User ala = new User("ala", "ala@ala.com", new BCryptPasswordEncoder().encode("ala"));
//            userRepository.save(ala);
//            User mary = new User("mary", "mary@mary.com",  new BCryptPasswordEncoder().encode("mary"));
//            userRepository.save(mary);
//            itemRepository.save(new Item(ala,mary,"blah",Long.MIN_VALUE));
//            itemRepository.save(new Item(ala,mary,"bling",Long.MIN_VALUE));
//        };
//    }
}
