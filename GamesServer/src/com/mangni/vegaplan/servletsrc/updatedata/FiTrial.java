package com.mangni.vegaplan.servletsrc.updatedata;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.TrialData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FiTrial implements IReceiveMessage {
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

		String trialid=(String)request.get("trialid");

		String triallv=(String)request.get("triallv");

		float trialusetime=Float.parseFloat((String)request.get("trialtime"));

		int triallength=Integer.parseInt((String)request.get("triallength"));

		String fighter1id=(String)request.get("fighter1id");

		String fighter2id=(String)request.get("fighter2id");

		String fighter3id=(String)request.get("fighter3id");

		String res="false";

		response.put("res", res);

		Calendar calendar=Calendar.getInstance();

		int hour=calendar.get(Calendar.HOUR_OF_DAY);

		if(hour<10&&hour>=1)
			return response;

		Map<String,Object> trailinfo=myJdbcTemplate.queryForMap("select trialtime,trialusetime,triallength from znx_player where id="+playerid);

		if(trailinfo.get("trialtime")!=null){

			try {

				String trailtime=String.valueOf(trailinfo.get("trialtime"));

				Date date=Bean.getDateFormat().parse(trailtime);

				Calendar datecalendar=Calendar.getInstance();

				datecalendar.setTime(date);

				calendar.add(Calendar.HOUR_OF_DAY, -3);

				datecalendar.add(Calendar.HOUR_OF_DAY, -3);

				if(calendar.get(Calendar.YEAR)==datecalendar.get(Calendar.YEAR)){

					if(calendar.get(Calendar.DAY_OF_YEAR)<=datecalendar.get(Calendar.DAY_OF_YEAR)){			

						return response;

					}

				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);

		TrialData trialdata=Bean.gettrialmap().get(trialid);

		HashMap<String,String> rewardmap=new HashMap<String,String>();

		int playerlv=znx_playerdata.getPlayerlv();

		if(playerlv<36){

			rewardmap=trialdata.getGetmap1();

		}else if(playerlv>=36&&playerlv<56){

			rewardmap=trialdata.getGetmap2();

		}else if(playerlv>=56&&playerlv<75){

			rewardmap=trialdata.getGetmap3();

		}else if(playerlv>=75&&playerlv<90){

			rewardmap=trialdata.getGetmap4();

		}else if(playerlv>=90&&playerlv<=100){

			rewardmap=trialdata.getGetmap5();

		}

		int exp=Integer.parseInt(rewardmap.get("GetExp"));
		String gold=rewardmap.get("GetGold");
		String goodsid=rewardmap.get("ItemId");
		String goodsnum=rewardmap.get("Quantity");

		float oldtrialusetime=0;
		int oldtriallength=0;

		if(trailinfo.get("triallength")!=null){
			oldtrialusetime=Float.parseFloat(String.valueOf(trailinfo.get("trialusetime")));
			oldtriallength=Integer.parseInt(String.valueOf(trailinfo.get("triallength")));
		}

		if(oldtriallength>triallength){
			triallength=oldtriallength;
			if(oldtrialusetime<trialusetime)
				trialusetime=oldtrialusetime;
		}

		String [] fighterpara={fighter1id,fighter2id,fighter3id};
		LvupHelper.UpPlayerExp(playerid, exp);
		LvupHelper.UpFighterExp(fighterpara, exp);
		myJdbcTemplate.update("update znx_player set gold=gold+"+gold+",trialtime=getdate(),triallv="+triallv+",triallength="+triallength+",trialusetime="+trialusetime+" where id="+playerid);
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, goodsid, goodsnum);

		response.put("exp", exp);
		response.put("gold", gold);
		response.put("goodsid", goodsid);
		response.put("goodsnum", goodsnum);
		response.put("res", "true");
		FinishTaskHelper.finishEverydayTask(playerid, "24","1");
		FinishTaskHelper.finishHolidayTask(playerid, "26");
		return response;
	}

}
