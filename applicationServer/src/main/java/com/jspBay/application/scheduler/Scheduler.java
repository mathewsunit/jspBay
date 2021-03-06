package com.jspBay.application.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by scy11a on 5/1/17.
 */

@Service
public class Scheduler {

	protected Logger logger = Logger.getLogger(Scheduler.class.getName());
	private  ThreadPoolTaskScheduler threadPoolTaskScheduler;

	@Autowired
	public Scheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
		this.threadPoolTaskScheduler = threadPoolTaskScheduler;
	}

	public void schedule(Thread thread, Date startTime) {
		logger.info("Schedule thread byNumber() invoked for " + thread.getName() + " after  " + startTime.toString() + " milli sec");
		logger.info("threadPoolTaskScheduler pool size before scheduling: " + threadPoolTaskScheduler.getPoolSize());
		logger.info("threadPoolTaskScheduler: " + (threadPoolTaskScheduler == null));
		threadPoolTaskScheduler.schedule(thread, startTime);
		logger.info("threadPoolTaskScheduler pool size after scheduling: " + threadPoolTaskScheduler.getPoolSize());
	}
}
