package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GetOutlineMessage implements IReceiveMessage {
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
		String sqlselect="select A.id,A.friendid,A.messtype,A.mess,B.nickname,B.sex,B.playerlv,B.viplv from player_chat_message A left join znx_player B ON A.friendid=B.id where B.updatetime>(getdate()-7) and A.playerid="+playerid+" AND A.isreaded=0";
		String sqldelete="update player_chat_message set isreaded=1 where id in(";
		List<Map<String,Object>> messagelist=myJdbcTemplate.queryForList(sqlselect);
		response.put("res","true");
		response.put("data",messagelist);
		for(Map<String,Object> hm:messagelist)
		{
			String id=String.valueOf(hm.get("id"));
			if(id!=null)
				sqldelete+=hm.get("id")+",";
		}
		sqldelete=sqldelete.substring(0, sqldelete.length()-1);
		sqldelete+=")";
		if(!messagelist.isEmpty())
			myJdbcTemplate.update(sqldelete);
		
		return response;
	}
}
