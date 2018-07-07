package com.mangni.vegaplan.jobscheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;


public class MilitaryRankTopMail implements Job {
	public void sendMilitaryRankMail(){
		List<Map<String,Object>> militaryrank=new ArrayList<Map<String,Object>>();
		militaryrank.addAll(GetRank.getMilitaryrank());
		//List<String[]> sqlparalist=new ArrayList<String[]>();
		String insertsql="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM,MAILCONTENT) VALUES(?,?,?,?,?,?,?)";
		for(Map<String,Object> rankmap:militaryrank){
			int rank=Integer.parseInt(String.valueOf(rankmap.get("num")));
			String stone="0";
			if(rank==1){
				stone="100";
			}else if(rank>=2&&rank<=4){
				stone="80";
			}else if(rank>=5&&rank<=20){
				stone="50";
			}
			
			String[] insertsqlpara={String.valueOf(rankmap.get("playerid")),"5","竞技排行奖励","stone","0",stone,"恭喜你在竞技排行榜中获得第"+rank+"名！"};
			SendMessageHelper.sendMail(insertsql, insertsqlpara, insertsqlpara[0]);
			//sqlparalist.add(insertsqlpara);
		}
		
		//SqlHelper.execTransaction(insertsql, sqlparalist);

		
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		sendMilitaryRankMail();
	}
}
