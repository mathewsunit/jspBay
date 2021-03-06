package com.jspBay.application.configuration;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.MailException;
import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 * Some trial shit; don't read
 */
@Configuration
@ComponentScan
@EntityScan("com.jspBay.application.domain")
@EnableJpaRepositories("com.jspBay.application.repository")
@EnableScheduling
@PropertySource("classpath:db-config.properties")
public class AccountsConfiguration {

    protected Logger logger;

    public AccountsConfiguration() {
        logger = Logger.getLogger(getClass().getName());
    }

    /**
     * Creates an in-memory "rewards" database populated with test data for fast
     * testing
     */

    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

//    @Bean
//    public DataSource dataSource() {
//        logger.info("dataSource() invoked");
//
//        // Create an in-memory H2 relational database containing some demo
//        // accounts.
//        DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
//                .addScript("classpath:testdb/data.sql").build();
//
//        logger.info("dataSource = " + dataSource);
//
//        // Sanity check
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        List<Map<String, Object>> accounts = jdbcTemplate.queryForList("SELECT number FROM T_ACCOUNT");
//        logger.info("System has " + accounts.size() + " accounts");
//
//        // Populate with random balances
//        Random rand = new Random();
//
//        for (Map<String, Object> item : accounts) {
//            String number = (String) item.get("number");
//            BigDecimal balance = new BigDecimal(rand.nextInt(10000000) / 100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
//            jdbcTemplate.update("UPDATE T_ACCOUNT SET balance = ? WHERE number = ?", balance, number);
//        }
//
//        return dataSource;
//    }
}