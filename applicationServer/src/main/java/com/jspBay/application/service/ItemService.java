package com.jspBay.application.service;

import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class ItemService {

    private final UserRepository userRepo;
    private final ItemRepository itemRepo;

    @Autowired
    public ItemService(UserRepository userRepo, ItemRepository itemRepo) {
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
    }
}
