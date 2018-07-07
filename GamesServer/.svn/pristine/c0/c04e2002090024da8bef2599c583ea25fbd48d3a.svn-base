package com.mangni.vegaplan.servletsrc.selectdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

/**
 * 用于查询玩家信息，客户端发送playerid=1&tablename&cum1,cum2,cum3,服务器返回XX&XX|YY&YY
 */
public class GetPlayerchamtop implements IReceiveMessage{
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
		String playerid = (String) request.get("playerid");
		//double battletime=Double.parseDouble(SqlHelper.getOneRead("select battletime from player_champions where playerid="+playerid));
		List<Map<String,Object>> meg=null;
		/*
		if(battletime<40)
		{
			meg = SqlHelper.getOneRead("select * from player_champions where sumhurt>(select sumhurt from player_champions where playerid="+playerid+")");	
		}
		else
		{
			meg = SqlHelper.getOneRead("select count(id) from player_champions where battletime<(select battletime from player_champions where playerid="+playerid+")");
		}
		*/
		
		meg = myJdbcTemplate.queryForList("select * from player_champions where playerid="+playerid);
		response.put("res","true");
		response.put("data",meg);
		return response;
	}
}