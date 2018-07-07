package com.mangni.vegaplan.jobscheduling;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;

public class ResetZnx_Player implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Calendar c=Calendar.getInstance();
		String curDir=System.getProperty("user.dir");
		Bean.setLogfilepath(curDir+"\\log\\"+c.get(Calendar.YEAR)+(c.get(Calendar.MONTH)+1)+c.get(Calendar.DAY_OF_MONTH)+"log.txt");
		HashMap<String, Znx_PlayerData> znx_playermap=Bean.getZnx_playermap();
		synchronized(znx_playermap)
		{
			Iterator<Entry<String, Znx_PlayerData>> it=znx_playermap.entrySet().iterator();
			while(it.hasNext())
			{
				Entry<String, Znx_PlayerData> entry=it.next();
				Znx_PlayerData znx_playerdata=entry.getValue();
				znx_playerdata.setBuyvitnum(0);
				znx_playerdata.setBuyenergynum(0);
				znx_playerdata.setBuygoldnum(0);
				znx_playerdata.setUsedfightersp(0);
				znx_playerdata.setGangturnid("0");
				znx_playerdata.setGangisturned(0);
				znx_playerdata.setGangturnpaynum(0);
				znx_playerdata.setGangturnrefreshnum(0);
			}			
		}

	}

}