package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class DeleteFriend implements IReceiveMessage{
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
		String friendid=(String)request.get("friendid");
		myJdbcTemplate.update("delete from player_friends where (playerid="+playerid+" and friendid="+friendid+") or (playerid="+friendid+" and friendid="+playerid+")");
		Session tosession=Bean.getPlayersession().get(friendid);
		if(tosession!=null)
		{
			HashMap<String,Object> mesresponse=new HashMap<String,Object>();
			mesresponse.put("wsurl","chatmessage");
			mesresponse.put("messagetype", "deleteyou");
			mesresponse.put("friendid", playerid);
			String jsonstr=JSON.toJSONString(mesresponse);
			String aesstr=EncryptUtil.aesEncrypt(jsonstr);
			tosession.getRemote().sendStringByFuture(aesstr);
		}
		response.put("res", "true");
		return response;
	}
}
