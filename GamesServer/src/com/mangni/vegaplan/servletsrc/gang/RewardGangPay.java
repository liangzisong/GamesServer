package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.GangpostData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class RewardGangPay implements IReceiveMessage {
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

		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);

		String rewardpaytime=znx_playerdata.getRewardpaytime();

		List<String> sqllist=new ArrayList<String>();

		GangpostData armypostdata=Bean.getGangpostmap().get(String.valueOf(znx_playerdata.getGangmilitarylv()));

		int stone=armypostdata.getArmypay();

		if(rewardpaytime==null){

			sqllist.add("update player_ganginfo set rewardpaytime=getdate() where playerid="+playerid);
			sqllist.add("update znx_player set stone=stone+"+stone+" where id="+playerid);

		}else{

			try {

				Calendar calendar=Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, -3);

				Date date=Bean.getDateFormat().parse(rewardpaytime);

				Calendar paycalendar=Calendar.getInstance();

				paycalendar.setTime(date);

				paycalendar.add(Calendar.HOUR_OF_DAY, -3);

				int diffyear=calendar.get(Calendar.YEAR)-paycalendar.get(Calendar.YEAR);

				if(diffyear==0){

					int diffday=calendar.get(Calendar.DAY_OF_YEAR)-paycalendar.get(Calendar.DAY_OF_YEAR);

					if(diffday>0){

						sqllist.add("update player_ganginfo set rewardpaytime=getdate() where playerid="+playerid);
						sqllist.add("update znx_player set stone=stone+"+stone+" where id="+playerid);

					}

				}else{

					sqllist.add("update player_ganginfo set rewardpaytime=getdate() where playerid="+playerid);
					sqllist.add("update znx_player set stone=stone+"+stone+" where id="+playerid);

				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if(!sqllist.isEmpty()){

			for(int i=0;i<sqllist.size();i++){
				myJdbcTemplate.update(sqllist.get(i));
			}

			Date date=new Date();

			String rewardpaytimestr=Bean.getDateFormat().format(date);

			znx_playerdata.setRewardpaytime(rewardpaytimestr);

			FinishTaskHelper.finishEverydayTask(playerid, "28","1");
			res="true";
		}

		response.put("res", res);

		return response;

	}

}
