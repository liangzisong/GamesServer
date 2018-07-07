package com.mangni.vegaplan.jobscheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GangBattleDayBalance implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MyJdbcTemplate myjdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String sql1="select gangid,sum(mess) as summess from player_gang_message where messtype=6 group by gangid,mess order by summess desc";
		List<Map<String,Object>> messinfo=myjdbcTemplate.queryForList(sql1);
		HashMap<String,List<String>> battleidteam=new HashMap<String,List<String>>();
		for(Map<String,Object> map:messinfo){
			String gangid=String.valueOf(map.get("gangid"));
			Znx_GangData gangdata=Bean.getZnx_gangmap().get(gangid);
			String battleid=String.valueOf(gangdata.getBattleid());
			List<String> battleidlist=battleidteam.get(battleid);
			int integral=0;
			int stone=0;
			if(battleidlist==null){//第一名
				integral=3;
				stone=300;
			}else{
				if(battleidlist.size()==1){//第二名
					integral=2;
					stone=200;
				}else{//第三名
					integral=1;
					stone=100;
				}
			}
			gangdata.addGangintegral(integral);
			myjdbcTemplate.update("update player_gang set gangintegral=gangintegral+"+integral+" where id="+gangid);
			List<Map<String,Object>> playerlist=myjdbcTemplate.queryForList("select playerid from player_ganginfo where gangid"+gangid+" and ischecked=1");
			for(Map<String,Object> playermap:playerlist){
				String playerid=String.valueOf(playermap.get("playerid"));
				String sql="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM) VALUES(?,?,?,?,?,?)";
				String[] sqlparam={playerid,"10","stone","0",String.valueOf(stone)};
				SendMessageHelper.sendMail(sql, sqlparam, playerid);
			}
		}
	}

}
