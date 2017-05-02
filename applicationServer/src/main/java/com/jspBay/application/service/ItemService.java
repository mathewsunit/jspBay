package com.jspBay.application.service;

import com.jspBay.application.DTO.BidDTO;
import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.DTO.ResponseDTO;
import com.jspBay.application.domain.Bid;
import com.jspBay.application.domain.Item;
import com.jspBay.application.domain.User;
import com.jspBay.application.enums.BidStatus;
import com.jspBay.application.enums.ItemStatus;
import com.jspBay.application.exceptions.ItemNotFoundException;
import com.jspBay.application.repository.BidRepository;
import com.jspBay.application.repository.ItemRepository;
import com.jspBay.application.repository.UserRepository;
import com.jspBay.application.scheduler.ScheduleThread;
import com.jspBay.application.scheduler.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class ItemService {

    protected Logger logger = Logger.getLogger(ItemService.class.getName());

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;
    private final TransactionTemplate transactionTemplate;

    @Autowired
    public ItemService(UserRepository userRepo, ItemRepository itemRepo, BidRepository bidRepository, TransactionTemplate transactionTemplate) {
        this.userRepository = userRepo;
        this.itemRepository = itemRepo;
        this.bidRepository = bidRepository;
        this.transactionTemplate = transactionTemplate;
    }

    public List<ItemDTO> bySeller(String partialName) {
        logger.info("items-service bySeller() invoked: " + itemRepository.getClass().getName() + " for " + partialName);

        User user = userRepository.findOneByUserName(partialName);
        if(null == user){
            throw new ItemNotFoundException(partialName);
        }

        List<ItemDTO> itemsDTO = new ArrayList<>();
        List<Item> items = itemRepository.findBySeller(user);
        logger.info("items-service bySeller() found: " + items);
        if (items == null || items.size() == 0)
            throw new ItemNotFoundException(partialName);
        else {
            for(Item item:items) {
                List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
                ItemDTO itemDTO = new ItemDTO(item, bids.size() == 1 ? new BidDTO(bids.get(0)) : null);
                itemsDTO.add(itemDTO);
            }
            return itemsDTO;
        }
    }

    public List<ItemDTO> bySearchTerm(String partialName) {
        logger.info("items-service bySearchTerm() invoked: " + itemRepository.getClass().getName() + " for " + partialName);

        List<ItemDTO> itemsDTO = new ArrayList<>();
        List<Item> items = itemRepository.findBySearch(partialName);
        logger.info("items-service bySearchTerm() found: " + items);
        if (items == null || items.size() == 0)
            throw new ItemNotFoundException(partialName);
        else {
            for(Item item:items) {
                List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
                ItemDTO itemDTO = new ItemDTO(item, bids.size() == 1 ? new BidDTO(bids.get(0)) : null);
                itemsDTO.add(itemDTO);
            }
            return itemsDTO;
        }
    }

    public ItemDTO byNumber(String itemNumber) {

        logger.info("items-service byNumber() invoked: " + itemNumber);
        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
        logger.info("items-service byNumber() found: " + item);

        if (item == null) {
            throw new ItemNotFoundException(itemNumber);
        } else {
            List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
            return new ItemDTO(item, (bids.size() == 1) ? new BidDTO(bids.get(0), true) : null);
        }
    }

    public BidDTO bidOnItem(BidDTO bid) {

        logger.info("items-service bidOnItem() invoked: " + bid);
        User bidder = userRepository.findOneByUserName(bid.getBidder().getUserName());
        Item item = itemRepository.findOneById(bid.getItemId());
        Bid newBid = new Bid(bidder, item, bid.getBidStatus(), Calendar.getInstance().getTime(), bid.getBidAmount());
        newBid = bidRepository.save(newBid);
        if (newBid == null) {
            throw new ItemNotFoundException("Bid could not be created.");
        } else {
            return new BidDTO(newBid, false);
        }

    }

    @Transactional
    public ItemDTO finishAuction(Long itemId) {
        Item item = itemRepository.findOneById(itemId);
        if(item != null && item.getItemStatus() == ItemStatus.ONSALE) {
            List<Bid> bids = bidRepository.findFirstByItemAndBidStatusNotOrderByValueDesc(item, BidStatus.DELETED);
            Bid currentBid = null;
            if (bids.size() > 0)
                currentBid = bids.get(0);
            if (currentBid != null) {
                currentBid.setBidStatus(BidStatus.ACCEPTED);
                bidRepository.save(currentBid);
                item.setBuyer(currentBid.getBidder());
            }
            Item finalItem = item;
            this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    int bidsUpdated = bidRepository.updateAllStatusForStatusAndItem(BidStatus.REJECTED, BidStatus.LEADING, finalItem);
                    logger.info("bidsUpdated for Item " + itemId + ": " + bidsUpdated);
                }
            });
            item.setItemStatus(ItemStatus.SOLD);
            item = itemRepository.save(item);
            String  d_email = "jpabay12345@gmail.com",
                    d_uname = "jpabay12345@gmail.com",
                    d_password = "password strength",
                    d_host = "smtp.gmail.com",
                    d_port  = "465",
                    bid_subject = item.getName() + "'s Auction has ended and you won the bid!",
                    seller_subject = item.getName() + "'s Auction has ended.",
                    m_text = item.getName() + "\n" + item.getDescription();


            Properties props = new Properties();
            props.put("mail.smtp.user", d_email);
            props.put("mail.smtp.host", d_host);
            props.put("mail.smtp.port", d_port);
            props.put("mail.smtp.starttls.enable","true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.socketFactory.port", d_port);
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session session = Session.getInstance(props, null);
            session.setDebug(true);

            MimeMessage sendMsg = new MimeMessage(session);
            try {
                sendMsg.setSubject(seller_subject);
                sendMsg.setFrom(new InternetAddress(d_email));
                sendMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(item.getSeller().getEmail()));
                sendMsg.setText(m_text);

                Transport transport = session.getTransport("smtps");
                transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
                transport.sendMessage(sendMsg, sendMsg.getAllRecipients());
                transport.close();

            } catch (MessagingException e) {
                e.printStackTrace();
            }
            if(currentBid != null) {
                MimeMessage bidderMsg = new MimeMessage(session);
                try {
                    bidderMsg.setSubject(bid_subject);
                    bidderMsg.setFrom(new InternetAddress(d_email));
                    bidderMsg.addRecipient(Message.RecipientType.TO, new InternetAddress(currentBid.getBidder().getEmail()));
                    bidderMsg.setText(m_text);
                    Transport transport = session.getTransport("smtps");
                    transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
                    transport.sendMessage(bidderMsg, bidderMsg.getAllRecipients());
                    transport.close();

                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            return new ItemDTO(item);
        } else {
            logger.info("Item not found!");
            return null;
        }
    }

    public ItemDTO createItem(ItemDTO itemDTO, Scheduler scheduler) {
        User seller = userRepository.findOneByUserName(itemDTO.getSeller().getUserName());
        Item item = new Item(itemDTO.getItemName(), seller, itemDTO.getItemDesc(), itemDTO.getItemCostMin(), itemDTO.getExpiring(), Calendar.getInstance().getTime(), ItemStatus.ONSALE);
        try {
            item = itemRepository.save(item);
        } catch (DataAccessException e) {
            return new ItemDTO(e.getMessage());
        }
        scheduler.schedule(new ScheduleThread(itemDTO.getExpiring().getTime(), this, item.getId()), itemDTO.getExpiring());
        return new ItemDTO(item);
    }

	public ResponseDTO<ItemDTO> removeItem(ItemDTO object) {
        Item item = itemRepository.findOneById(object.getItemId());
        item.setItemStatus(ItemStatus.REMOVED);
        int bidCount = bidRepository.setFixedBidStatusForItem(BidStatus.DELETED, item);
        logger.info("Status changed to deleted for " + bidCount + " bids related to this item.");
        /*
            Remove threads.
        */
        try {
            item = itemRepository.save(item);
            return new ResponseDTO<>("SUCCESS", "Successfully removed the item from sale.", new ItemDTO(item));
        } catch (DataAccessException e) {
            return new ResponseDTO<>("ERROR", e.getMessage());
        }
	}
}
