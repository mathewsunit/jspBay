package com.jspBay.application.scheduler;

import com.jspBay.application.DTO.ItemDTO;
import com.jspBay.application.domain.Item;
import com.jspBay.application.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by scy11a on 5/1/17.
 */
@Component
@Scope("prototype")
public class ScheduleThread extends Thread {

	protected Logger logger = Logger.getLogger(ScheduleThread.class.getName());
	private long timeScheduled = -1;
	private ItemService itemService;

	@Override
	public void run() {
		ItemDTO item = itemService.finishAuction("1");
		logger.info("threadPoolTaskScheduler----------------------------------------------------");
		logger.info("Runnable scheduled at " + timeScheduled + " and executed at " + new Date().getTime());
		logger.info(item.toString());
		logger.info("----------------------------------------------------threadPoolTaskScheduler");0
	}

	public ScheduleThread(long timeScheduled, ItemService itemService) {
		this.timeScheduled = timeScheduled;
		this.itemService = itemService;
	}

}
