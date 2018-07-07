package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.XxddData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveXxdd implements IReceiveMessage {
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
		int receiveid=Integer.parseInt((String)request.get("receiveid"));
		String res="false";
		String receive=myJdbcTemplate.queryForObject("SELECT XXDDRECEIVE FROM ZNX_PLAYER WHERE ID="+playerid,String.class);
		if(receive.charAt(receiveid-1)=='1')
		{
			response.put("res", res);
			return response;
		}

		StringBuilder newreceive=new StringBuilder();
		for (int i=0;i<receive.length();i++) 
		{
			if(i!=receiveid-1)
			{
				newreceive.append(receive.charAt(i));
			}
			else
			{
				newreceive.append('1');
			}
		}
		XxddData xxdd=Bean.getXxddmap().get(String.valueOf(receiveid));
		int needstar=xxdd.getStarnum();
		int havestar=myJdbcTemplate.queryForObject("SELECT SUM(FINISHSTAR) FROM PLAYER_BARRIER WHERE PLAYERID="+playerid,Integer.class);
		if(needstar>havestar){
			response.put("res", res);
			return response;
		}
		LvupHelper.UpPlayerExp(playerid, xxdd.getExp());
		String sql="UPDATE ZNX_PLAYER SET GOLD=GOLD+"+xxdd.getGold()+",STONE=STONE+"+xxdd.getStone()+",XXDDRECEIVE='"+newreceive+"' WHERE ID="+playerid;
		myJdbcTemplate.update(sql);
		res="true";
		response.put("res", res);
		return response;
	}

}
