package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_GanggoalData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GoalLocked implements IReceiveMessage {
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

		String res="false";

		response.put("res", res);

		int needpoints=1000;

		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);

		Znx_GangData znx_gangdata=Bean.getZnx_gangmap().get(znx_playerdata.getGangid());

		if(znx_gangdata==null)
			return response;

		synchronized (new Object()) {
			if(Bean.getZnx_ganggoalmap().containsKey(znx_gangdata.getId()))
				return response;

			//职务不符合，锁定失败
			if(znx_playerdata.getGangduties()>2)
				return response;

			int points=znx_gangdata.getPoints();
			//积分不足，锁定失败
			if(points<needpoints)
				return response;

			if(znx_gangdata.getGoallockedtime()!=null){

				Date date=null;

				try {

					date=Bean.getDateFormat().parse(znx_gangdata.getGoallockedtime());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//时间校验
				Calendar lockedtime=Calendar.getInstance();

				Calendar nowtime=Calendar.getInstance();

				lockedtime.setTime(date);
				//三点刷新
				lockedtime.add(Calendar.HOUR_OF_DAY, -3);

				nowtime.add(Calendar.HOUR_OF_DAY, -3);
				//时间间隔未过3点，锁定失败
				if(lockedtime.get(Calendar.DAY_OF_YEAR)==nowtime.get(Calendar.DAY_OF_YEAR))
					return response;

			}

			/*
			 * 锁定成功逻辑
			 * 1、减积分并更新锁定时间
			 * 2、重置军团所有人进入次数
			 * 3、群发锁定消息并添加到数据库
			 * 4、线程开始计时
			 * 5、更新缓存
			 */

			String nowstr=Bean.getDateFormat().format(new Date());

			myJdbcTemplate.update("update player_gang set points=points-"+needpoints+",goallockedtime='"+nowstr+"' where id="+znx_playerdata.getGangid());

			znx_gangdata.setGoallockedtime(nowstr);

			myJdbcTemplate.update("update player_ganginfo set goalhurtnum=0 where gangid="+znx_playerdata.getGangid());

			List<String> onlineplayer=znx_gangdata.getOnlineplayerid();


			for(String onlineplayerid:onlineplayer){
				Znx_PlayerData z=Bean.getZnx_playermap().get(onlineplayerid);
				if(z!=null)
					z.setGoalhurtnum(0);
			}


			Znx_GanggoalData znx_ganggoaldata=new Znx_GanggoalData();

			znx_ganggoaldata.setId(znx_gangdata.getId());

			int goallv=znx_gangdata.getGoallv();
			long hp=0;
			if(goallv<=10){
				hp=10000000*goallv;
			}else{
				hp=50000000+5000000*goallv;
			}

			znx_ganggoaldata.addHp(hp);

			znx_ganggoaldata.setMaxhp(hp);

			Future<?> future=Bean.getExecutor().submit(new GanggoalThread(znx_gangdata.getId(),30*60*1000));

			znx_ganggoaldata.setFuture(future);

			Bean.getZnx_ganggoalmap().put(znx_gangdata.getId(), znx_ganggoaldata);

			SendMessageHelper.sendGangMessage(znx_gangdata, znx_playerdata, "1", "0",1);

			response.put("res", "true");
		}
		return response;

	}

}
