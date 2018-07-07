package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ChipUpLv implements IReceiveMessage{
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
		String chipid=(String) request.get("chipid");
		int uplv=Integer.parseInt((String) request.get("uplv"));
		int playerlv=Integer.parseInt(SqlHelper.getOneRead("SELECT playerlv FROM znx_player WHERE id="+playerid));
		int chiplv=Integer.parseInt(SqlHelper.getOneRead("select lv from player_chip where id="+chipid));
		int sumuplv=chiplv+uplv;

		String res="false";
		if(playerlv>=sumuplv)
		{
			int needgold=0;
			for(int i=chiplv;i<sumuplv;i++)
			{
				needgold+=Bean.getLvlupmap().get(String.valueOf(i)).getChiplvlupgold();
			}
			if(needgold!=0)
			{
				String[] sqlpara={playerid,chipid,String.valueOf(uplv),String.valueOf(needgold)};
				res=SqlHelper.DbExecute("lvlup_player_chip(?,?,?,?,?)", sqlpara, true);
				FinishTaskHelper.finishEverydayTask(playerid, "33",String.valueOf(uplv));
			}
		}
		response.put("res",res);
		return response;
	}
}