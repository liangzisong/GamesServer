package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Future;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_GanggoalData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GoalHurt implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String type=(String)request.get("type");
		String res="false";
		response.put("res", res);
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		Znx_GangData znx_gangdata=Bean.getZnx_gangmap().get(znx_playerdata.getGangid());
		if(type.equals("start")){
			if(!Bean.getZnx_ganggoalmap().containsKey(znx_playerdata.getGangid())){
				synchronized (new Object()) {
					int i=0;
					if(!Bean.getZnx_ganggoalmap().containsKey(znx_playerdata.getGangid())){
						i=reloadGoal(znx_gangdata);
						if(i==0)
							return response;
					}
				}
			}

			int needstone=0;
			int stone=Integer.parseInt((String)request.get("stone"));	
			String goalhurttime=znx_playerdata.getGoalhurttime();
			String locktime=znx_gangdata.getGoallockedtime();

			if(goalhurttime!=null){
				Date lockdate=null;
				Date hurtdate=null;
				try {
					hurtdate=Bean.getDateFormat().parse(goalhurttime);
					lockdate=Bean.getDateFormat().parse(locktime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar hurtctime=Calendar.getInstance();
				Calendar now=Calendar.getInstance();
				hurtctime.setTime(hurtdate);

				if(hurtctime.get(Calendar.DAY_OF_YEAR)==now.get(Calendar.DAY_OF_YEAR)){
					//进入次数
					int hurtnum=znx_playerdata.getGoalhurtnum();
					if(hurtdate.getTime()<=lockdate.getTime())
						hurtnum=0;
					//已间隔时间
					int isspantime=(int) ((now.getTimeInMillis()-hurtctime.getTimeInMillis())/1000);
					//应间隔时间
					int spantime=0;
					if(hurtnum!=0)
						spantime=Bean.getGangbossmap().get(String.valueOf(hurtnum)).getSpan();
					//计入收费星钻
					needstone=(spantime-isspantime)/6;
					if(needstone>0){
						//星钻差
						int diffstone=needstone-stone;
						if(diffstone>2||diffstone<-2)
							return response;
						if(stone>0){
							String sql="update znx_player set stone=stone-"+stone+" where id="+playerid;
							LvupHelper.spendStone(playerid, stone, "goalhurt", sql, request, true);
						}
					}	
				}
			}

			String sql="update player_ganginfo set goalhurttime='"+znx_gangdata.getGoallockedtime()+"' where playerid="+playerid;
			myJdbcTemplate.update(sql);
			znx_playerdata.setGoalhurttime(znx_gangdata.getGoallockedtime());
			znx_playerdata.setGoalhurtnum(znx_playerdata.getGoalhurtnum()+1);
			znx_playerdata.setCanhurtgoal(true);
			res="true";
			response.put("res", res);
			return response;
		}else{
			if(!znx_playerdata.getCanhurtgoal())
				return response;
			String damage=(String)request.get("damage");
			String nowstr=Bean.getDateFormat().format(new Date());
			String sql="update player_ganginfo set goalhurtnum=goalhurtnum+1,goalhurttime='"+nowstr+"' where playerid="+playerid;
			myJdbcTemplate.update(sql);

			znx_playerdata.setGoalhurttime(nowstr);
			znx_playerdata.setCanhurtgoal(false);
			int hurtnum=znx_playerdata.getGoalhurtnum();
			if(Bean.getZnx_ganggoalmap().containsKey(znx_playerdata.getGangid())){
				boolean islived=Bean.getZnx_ganggoalmap().get(znx_playerdata.getGangid()).addHp(-Long.parseLong(damage));
				if(islived){	
					SendMessageHelper.sendGangMessage(znx_gangdata, znx_playerdata, "2", damage,hurtnum);
				}else{
					SendMessageHelper.sendGangMessage(znx_gangdata, znx_playerdata, "2", damage,hurtnum);
					SendMessageHelper.sendGangMessage(znx_gangdata, znx_playerdata, "3", damage,1);
					Bean.getZnx_ganggoalmap().get(znx_playerdata.getGangid()).getFuture().cancel(true);
					myJdbcTemplate.update("update player_gang set goallv=goallv+1 where id="+znx_playerdata.getGangid());
					znx_gangdata.setGoallv(znx_gangdata.getGoallv()+1);
				}
				res="true";
			}else{
				res="dead";
			}
			FinishTaskHelper.finishEverydayTask(playerid, "23", "1");
			response.put("res", res);
			return response;
		}
	}
	public int reloadGoal(Znx_GangData znx_gangdata){
		String locktime=znx_gangdata.getGoallockedtime();
		Date lockdate=null;
		try {
			lockdate=Bean.getDateFormat().parse(locktime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date now=new Date();
		int betweenmin=(int)((now.getTime()-lockdate.getTime())/(1000*60));
		if(betweenmin>29)
			return 0;
		long damage=myJdbcTemplate.queryForObject("select isnull(sum(mess),0)  from player_gang_message where gangid="+znx_gangdata.getId()+" and messtype=2 and updatetime>'"+locktime+"'",Long.class);
		Znx_GanggoalData znx_ganggoaldata=new Znx_GanggoalData();

		znx_ganggoaldata.setId(znx_gangdata.getId());

		int goallv=znx_gangdata.getGoallv();
		long hp=0;
		if(goallv<=10){
			hp=10000000*goallv;
		}else{
			hp=50000000+5000000*goallv;
		}
		znx_ganggoaldata.setMaxhp(hp);
		hp=hp-damage;
		znx_ganggoaldata.addHp(hp);
		Future<?> future=Bean.getExecutor().submit(new GanggoalThread(znx_gangdata.getId(),30*60*1000));

		znx_ganggoaldata.setFuture(future);

		Bean.getZnx_ganggoalmap().put(znx_gangdata.getId(), znx_ganggoaldata);
		return 1;

	}
}
