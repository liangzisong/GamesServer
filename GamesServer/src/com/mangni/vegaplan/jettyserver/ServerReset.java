package com.mangni.vegaplan.jettyserver;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import com.mangni.vegaplan.jobscheduling.ChampionsTopMail;
import com.mangni.vegaplan.jobscheduling.RefreshDailytask;
import com.mangni.vegaplan.servletsrc.mercenary.GetMercenaryTask;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ServerReset 
{
	//private MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
	
	public ServerReset()
	{
		System.out.println("ServerReset.ServerReset()");
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		//排行榜挑战
		myJdbcTemplate.update("update player_tobattle set ischallenge=0");
		//领取体力
		//SqlHelper.Updatedb("UPDATE znx_player SET isrewardvit='00'");
		//SqlHelper.Updatedb("update player_tobattle set challengenum=8");

		//佣兵任务
		new GetMercenaryTask().getMercenary();
		//每日任务更新
		new RefreshDailytask().refresh();

		//猎杀邮件发放
		Calendar nowcalendar=Calendar.getInstance();
		int nowhour=nowcalendar.get(Calendar.HOUR_OF_DAY);
		if(nowhour>=3){
			String championsstr=myJdbcTemplate.queryForObject("SELECT MAX(UPDATETIME) FROM PLAYER_MAIL WHERE MAILTYPE=2",String.class);
			if(championsstr!=null){
				Date championsdate=new Date();
				try {
					championsdate=Bean.getDateFormat().parse(championsstr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nowcalendar.set(Calendar.HOUR_OF_DAY, 3);
				nowcalendar.set(Calendar.MINUTE, 0);
				nowcalendar.set(Calendar.SECOND, 0);
				nowcalendar.set(Calendar.MILLISECOND, 0);
				if(championsdate.getTime()<nowcalendar.getTime().getTime()){
					new ChampionsTopMail().sendChampionsMail();
				}
			}
		}
	}
}
