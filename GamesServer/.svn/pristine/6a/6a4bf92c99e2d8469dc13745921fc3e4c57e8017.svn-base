package com.mangni.vegaplan.jobscheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class PowerRankTopMail implements Job {
	public void sendPowerRankMail(){
		List<Map<String,Object>> powerrank=new ArrayList<Map<String,Object>>();
		powerrank.addAll(GetRank.getFightpowerrank());
		//List<String[]> sqlparalist=new ArrayList<String[]>();
		String insertsql="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM,MAILCONTENT) VALUES(?,?,?,?,?,?,?)";
		for(Map<String,Object> rankmap:powerrank){
			int rank=Integer.parseInt(String.valueOf(rankmap.get("num")));
			String vit="0";
			if(rank==1){
				vit="60";
			}else if(rank>=2&&rank<=4){
				vit="40";
			}else if(rank>=5&&rank<=20){
				vit="20";
			}else if(rank>=21&&rank<=50){
				vit="10";
			}
			
			String[] insertsqlpara={String.valueOf(rankmap.get("playerid")),"5","战力排行奖励","vit","0",vit,"恭喜你在战力排行榜中获得第"+rank+"名！"};
			SendMessageHelper.sendMail(insertsql, insertsqlpara, insertsqlpara[0]);
			//sqlparalist.add(insertsqlpara);
		}
		
		//SqlHelper.execTransaction(insertsql, sqlparalist);
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		sendPowerRankMail();
	}
}
