package com.mangni.vegaplan.jobscheduling;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.datatable.CompetitionSeasonRoom;
import com.mangni.vegaplan.toolshelper.Bean;

public class CompetitionSensonRoomJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ConcurrentHashMap<String, CompetitionSeasonRoom> csmap=Bean.getCompetitionseasonroommap();
		Iterator<Entry<String, CompetitionSeasonRoom>> iterator=csmap.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<String, CompetitionSeasonRoom>  mapentry = iterator.next();
			CompetitionSeasonRoom csr=mapentry.getValue();
			long timelimit=csr.getTime();
			if(timelimit>95000){
				iterator.remove();
			}
		}
	}
}
