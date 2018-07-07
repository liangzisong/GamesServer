package com.mangni.vegaplan.jobscheduling;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;

public class UpdateOnlinecount implements Job {
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SendHttpRequest.sendrequest("updateonlinecount", "serverid="+Bean.getServerid()+"&onlinecount="+Bean.getPlayersession().size());
	}
}
