package com.jspBay.application.configuration;

import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by sunit on 4/28/17.
 */
@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private ItemRepository itemRepository;

    @Autowired
    public DataLoader(UserRepository userRepository,ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public void run(ApplicationArguments args) {
    User ala = new User("ala", "ala@ala.com", new BCryptPasswordEncoder().encode("ala"));
    userRepository.save(ala);
    User mary = new User("mary", "mary@mary.com",  new BCryptPasswordEncoder().encode("mary"));
    userRepository.save(mary);
    itemRepository.save(new Item(ala,mary,"blah",Long.MIN_VALUE));
    itemRepository.save(new Item(ala,mary,"bling",Long.MIN_VALUE));
    }
}
