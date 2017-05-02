package com.jspBay.application.scheduler;

import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.domain.Item;
import com.jspBay.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by scy11a on 5/1/17.
 */
@Service
@Scope("prototype")
@Transactional
public class ScheduleThread extends Thread {

	protected Logger logger = Logger.getLogger(ScheduleThread.class.getName());
	private long timeScheduled = -1;
	private long itemId;
	private ItemService itemService;

	@Override
	public void run() {
		logger.info("threadPoolTaskScheduler----------------------------------------------------");
		ItemDTO item = itemService.finishAuction(itemId);
		logger.info("Runnable scheduled at " + timeScheduled + " and executed at " + new Date().getTime());
		logger.info(item.toString());
		logger.info("----------------------------------------------------threadPoolTaskScheduler");
	}

	public ScheduleThread(long timeScheduled, ItemService itemService, Long itemId) {
		this.timeScheduled = timeScheduled;
		this.itemService = itemService;
		this.itemId = itemId;
	}

}
