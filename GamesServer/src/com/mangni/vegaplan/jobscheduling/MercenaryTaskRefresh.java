package com.mangni.vegaplan.jobscheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mangni.vegaplan.servletsrc.mercenary.GetMercenaryTask;

public class MercenaryTaskRefresh implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		new GetMercenaryTask().getMercenary();
		
	}

}
