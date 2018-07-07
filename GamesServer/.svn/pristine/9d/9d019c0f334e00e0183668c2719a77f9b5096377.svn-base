package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;


public class SendPlayerMessage implements IReceiveMessage {
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
		String toplayerid=(String)request.get("toplayerid");
		String message=(String)request.get("message");
		String type=(String)request.get("type");//切磋发3
		Znx_PlayerData znxplayermap=Bean.getZnx_playermap().get(playerid);
		String nickname=znxplayermap.getNickname();
		String sex=znxplayermap.getSex();
		Session tosession=Bean.getPlayersession().get(toplayerid);
		
		if(playerid==null||playerid.isEmpty()||toplayerid==null||toplayerid.isEmpty())
			return null;
		response.put("res","black");
		String friendtype = myJdbcTemplate.queryForObject("select top 1 friendtype from player_friends where playerid="+toplayerid+" and friendid="+playerid,String.class);
		if("0".equals(friendtype))
		{
			String isreaded="0";
			if(tosession!=null)
			{
				HashMap<String,Object> mesresponse=new HashMap<String,Object>();
				mesresponse.put("wsurl","chatmessage");
				mesresponse.put("messagetype", "playermessage");
				mesresponse.put("playerid", playerid);
				mesresponse.put("message", message);
				mesresponse.put("nickname", nickname);
				mesresponse.put("sex", sex);
				String jsonstr=JSON.toJSONString(mesresponse);
				String aesstr=EncryptUtil.aesEncrypt(jsonstr);
				tosession.getRemote().sendStringByFuture(aesstr);
				isreaded="1";
			}
			if(type==null)
				type="0";
			Object[] para={toplayerid,playerid,type,message,isreaded};
			myJdbcTemplate.update("insert into player_chat_message(playerid,friendid,messtype,mess,isreaded) values(?,?,?,?,?)", para);
			if(type.equals("3"))
				FinishTaskHelper.finishEverydayTask(playerid, "31", "1");
			response.put("res","true");
		}
		return response;
	}
}
