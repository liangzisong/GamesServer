package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.DailytaskData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class DailyTask implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String) request.get("playerid");
		String znxdailyid=(String) request.get("znxdailyid");
		DailytaskData dutydata=Bean.getDailytaskmap().get(znxdailyid);
		int dutydot=dutydata.getDot();
		String res="false";
		response.put("res",res);
		if(dutydot>0)
		{
			int fcount=dutydata.getTaskcount();
			if(fcount==0)
			{
				String vipday=myJdbcTemplate.queryForObject("SELECT DATEDIFF(day,uptime,GETDATE()) FROM player_dailytask WHERE playerid="+playerid+" AND dutyid="+znxdailyid,String.class);
				if(vipday==null)
					return response;

				int limitday=0;
				switch(dutydata.getId()){
				case "18":
					limitday=7;
					break;
				case "19":
					limitday=31;
					break;
				case "20":
					limitday=365;
					break;
				default:
					return response;
				}

				if(Integer.parseInt(vipday)>=limitday)
					return response;

				res=finishduty(playerid,dutydata,response);
				if(res.equals("true")){
					if(dutydata.getId().equals("18"))
						FinishTaskHelper.finishHolidayTask(playerid, "36");
					if(dutydata.getId().equals("19"))
						FinishTaskHelper.finishHolidayTask(playerid, "37");
					if(dutydata.getId().equals("20"))
						FinishTaskHelper.finishHolidayTask(playerid, "38");
				}
			}
			else
			{
				res=finishduty(playerid,dutydata,response);
			}
		}
		else
		{
			res=rewardgift(playerid,dutydata);
		}
		response.put("res", res);

		return response;
	}

	private void receiveraward(String playerid,int rewardstone,int rewardgold,int playerexp,int fighterexp)
	{
		if(rewardstone!=0||rewardgold!=0)
		{
			String sqlstr="UPDATE znx_player SET stone=stone+"+rewardstone+",gold=gold+"+rewardgold+" WHERE id="+playerid;
			myJdbcTemplate.update(sqlstr);
		}
		if(playerexp!=0)
		{
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.PLAYEREXP, "0", String.valueOf(playerexp));
		}
		if(fighterexp!=0)
		{
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.EXPPOOL, "0", String.valueOf(fighterexp));
		}
	}

	private String finishduty(String playerid,DailytaskData dutydata,HashMap<String,Object> response)
	{
		String res="false";
		Map<String,Object> dutyinfo=myJdbcTemplate.queryForMap("SELECT finishcount, isreceived FROM player_dailytask WHERE playerid="+playerid+" AND dutyid="+dutydata.getId());
		int fcount=Integer.parseInt(String.valueOf(dutyinfo.get("finishcount")));
		String isreceived=String.valueOf(dutyinfo.get("isreceived"));
		if("0".equals(isreceived))
		{
			int needcount=dutydata.getTaskcount();
			if(fcount>=needcount)
			{
				int playerlv=Bean.getZnx_playermap().get(playerid).getPlayerlv();
				int playerexp=Bean.getLvtablemap().get(String.valueOf(playerlv)).getPlayerexpbase();
				int playerexppro=dutydata.getRewardexp();
				int fighterexp=Bean.getLvtablemap().get(String.valueOf(playerlv)).getFighterexpbase();
				int fighterexppro=dutydata.getFighterexp();
				int rewardgold=Bean.getLvtablemap().get(String.valueOf(playerlv)).getGoldbase();
				int rewardgoldpro=dutydata.getRewardgold();
				int rewardstone=dutydata.getRewardstone();

				receiveraward(playerid, rewardstone, rewardgold*rewardgoldpro, playerexp*playerexppro, fighterexp*fighterexppro);
				myJdbcTemplate.update("UPDATE player_dailytask SET isreceived=1 WHERE playerid="+playerid+" AND dutyid="+dutydata.getId());
				res="true";
			}else{
				response.put("errorinfo", "not enough");
				response.put("fcount",fcount);
			}
		}else{
			response.put("errorinfo", "isreceived");
		}
		return res;
	}

	private String rewardgift(String playerid,DailytaskData dutydata)
	{
		String res="false";
		int ndot=dutydata.getTaskcount();
		List<Map<String, Object>> dutyidlist=myJdbcTemplate.queryForList("SELECT dutyid,finishcount from player_dailytask WHERE playerid="+playerid);
		int fdot=0;
		for(Map<String, Object> hm:dutyidlist)
		{
			String fdutyid=String.valueOf(hm.get("dutyid"));
			int finishcount=Integer.parseInt(String.valueOf(hm.get("finishcount")));
			DailytaskData dailydata=Bean.getDailytaskmap().get(fdutyid);
			if(dailydata==null)
				continue;

			if(dailydata.getTaskcount()<=finishcount)
				fdot+=dailydata.getDot();
		}

		if(fdot>=ndot)
		{
			int rewardstone=dutydata.getRewardstone();
			receiveraward(playerid, rewardstone, 0, 0, 0);
			FinishTaskHelper.finishEverydayTask(playerid, dutydata.getId(), "1");
			res="true";
		}

		return res;
	}
}

