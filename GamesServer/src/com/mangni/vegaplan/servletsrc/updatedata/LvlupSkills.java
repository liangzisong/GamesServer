package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class LvlupSkills implements IReceiveMessage {
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
		String fighterid=(String) request.get("fighterid");
		String skill=(String) request.get("skill");//fighterskill1lv
		String uplv=(String) request.get("uplv");
		List<String> fighterlist=SqlHelper.getMyData("SELECT fighterlv, "+skill+" FROM player_fighter WHERE id="+fighterid);
		int fighterlv=Integer.parseInt(fighterlist.get(0));
		int skilllv=Integer.parseInt(fighterlist.get(1));
		int sumlv=skilllv+Integer.parseInt(uplv);
		String res="false";
		if(fighterlv>=sumlv)
		{
			int needgold=0;
			for(int i=skilllv;i<sumlv;i++)
			{
				needgold+=Bean.getLvlupmap().get(String.valueOf(i)).getSkilllevelupgold();
			}
			if(needgold!=0)
			{
				String[] paras={playerid,fighterid,skill,uplv,String.valueOf(needgold)};
				res=SqlHelper.DbExecute("lvlup_player_fighter_skills(?,?,?,?,?,?)", paras, true);
				FinishTaskHelper.finishEverydayTask(playerid, "14", uplv);
				FinishTaskHelper.finishHolidayTask(playerid, "22",uplv);
			}
		}
		response.put("res", res);
		return response;
	}
}
