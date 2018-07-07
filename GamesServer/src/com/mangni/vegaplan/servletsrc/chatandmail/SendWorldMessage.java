package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class SendWorldMessage implements IReceiveMessage {
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
		String playerid=(String)request.get("playerid");
		String message=(String)request.get("message");

		HashMap<String,Object> mesresponse=new HashMap<String,Object>();
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		mesresponse.put("wsurl","chatmessage");
		mesresponse.put("messagetype", "worldmessage");
		mesresponse.put("message", message);
		mesresponse.put("playerid", playerid);
		mesresponse.put("nickname", znx_playerdata.getNickname());
		mesresponse.put("viplv", znx_playerdata.getViplv());
		mesresponse.put("sex", znx_playerdata.getSex());
		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();

		for(Entry<String, Session> entry:sessionmap.entrySet())
		{
			if(entry.getValue().isOpen())
				entry.getValue().getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
		}
		
		Object[] para={"0",playerid,message,"1"};
		myJdbcTemplate.update("insert into player_chat_message(playerid,friendid,mess,isreaded) values(?,?,?,?)", para);
		
		response.put("res","true");
		return response;
	}
}
