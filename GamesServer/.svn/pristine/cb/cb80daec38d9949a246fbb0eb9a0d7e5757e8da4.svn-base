package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GetFriendsList implements IReceiveMessage{
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
		Object obj=request.get("gettype");
		String gettype=null;
		if(obj!=null)
		{                                                                                                                                                                              
			gettype=obj.toString();
		}
		if(gettype==null||gettype.equals("friendlist"))
		{
			String playerid=(String)request.get("playerid");
			String sql1="SELECT znx_player.id,znx_player.nickname,znx_player.sex,znx_player.playerlv,znx_player.viplv,player_friends.friendtype FROM znx_player LEFT JOIN player_friends ON player_friends.friendid=znx_player.id WHERE player_friends.playerid="+playerid;
			String sql2="SELECT znx_player.id,znx_player.nickname,znx_player.sex,znx_player.playerlv,znx_player.viplv,player_friends.friendtype FROM znx_player LEFT JOIN player_friends ON player_friends.playerid=znx_player.id WHERE player_friends.friendid="+playerid+" AND playerid NOT IN(SELECT friendid FROM player_friends WHERE playerid="+playerid+")";
	
			List<Map<String, Object>> friendlist=myJdbcTemplate.queryForList(sql1);
			List<Map<String, Object>> strangerlist=myJdbcTemplate.queryForList(sql2);
			
			response.put("gettype", "friendlist");
			response.put("friendlist", friendlist);
			response.put("strangerlist", strangerlist);
		}
		else if(gettype.equals("selectfriend"))
		{
			String nickname=(String)request.get("nickname");
			String sql="SELECT znx_player.id,znx_player.nickname,znx_player.sex,znx_player.playerlv,znx_player.viplv,player_friends.friendtype FROM znx_player LEFT JOIN player_friends ON player_friends.friendid=znx_player.id WHERE znx_player.nickname='"+nickname+"'";

			List<Map<String, Object>> friendlist=myJdbcTemplate.queryForList(sql);
			response.put("gettype", "selectfriend");
			response.put("friendlist", friendlist);
		}
		else if(gettype.equals("recommend"))
		{	
			String playerid=(String)request.get("playerid");
			String sql="SELECT TOP 5 id,nickname,sex,playerlv,viplv FROM znx_player WHERE id NOT IN(SELECT friendid FROM player_friends WHERE playerid="+playerid+") AND id!="+playerid+" ORDER BY updatetime DESC";
			List<Map<String, Object>> friendlist=myJdbcTemplate.queryForList(sql);
			response.put("gettype", "recommend");
			response.put("friendlist", friendlist);                                     
		}
		return response;
	}
}
