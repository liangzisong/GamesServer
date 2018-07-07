package com.mangni.vegaplan.servletsrc.beforeplay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

/**
 * 用于查询玩家信息，客户端发送playerid=1&tablename&cum1,cum2,cum3,服务器返回XX&XX|YY&YY
 */

public class GetPlayerData implements IReceiveMessage{
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
 		String tablename = (String) request.get("tablename");
		String cum = (String) request.get("cum");
		Object obj = request.get("gettype");
		if(cum!=null)
		{
			if(obj==null)
			{
				List<Map<String,Object>> meg = myJdbcTemplate.queryForList("select "+cum+ " from "+tablename+" where "+playerid);
				response.put("data",meg);
			}
			else
			{
				List<Map<String,Object>> meg = myJdbcTemplate.queryForList("select "+cum+ " from "+tablename);
				//List<Map<String,Object>> meg = myJdbcTemplate.queryForList("select "+cum+ " from "+tablename);
				response.put("data",meg);
			}
		}
		response.put("sendmap", request);
		return response;
	}
	
}