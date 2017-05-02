package com.jspBay.application.scheduler;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by scy11a on 5/1/17.
 */

@Component
public class ScheduleThread extends Thread {

	protected Logger logger = Logger.getLogger(ScheduleThread.class.getName());
	private long timeScheduled = -1;

	@Override
	public void run() {
		logger.info("threadPoolTaskScheduler----------------------------------------------------");
		logger.info("Runnable scheduled at " + timeScheduled + " and executed at " + new Date().getTime());
		logger.info("----------------------------------------------------threadPoolTaskScheduler");
	}

	public ScheduleThread(long timeScheduled) {
		this.timeScheduled = timeScheduled;
	}

}
