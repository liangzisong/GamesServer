package com.mangni.vegaplan.servletsrc.chatandmail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class MakeFriends implements IReceiveMessage {
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
		String friendtype=(String)request.get("friendtype");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		String playernickname=znx_playerdata.getNickname();
		String friendlv=null;
		String friendsex=null;

		if(Bean.getZnx_playermap().containsKey(friendid))
		{
			Znx_PlayerData playermap=Bean.getZnx_playermap().get(friendid);
			friendlv=String.valueOf(playermap.getPlayerlv());
			friendsex=playermap.getSex();
		}
		else
		{
			Map<String, Object> friendinfo=myJdbcTemplate.queryForMap("select playerlv, nickname, sex from znx_player where id="+friendid);
			friendlv=String.valueOf(friendinfo.get("playerlv"));
			friendsex=String.valueOf(friendinfo.get("sex"));
		}
		
		String res=(String) myJdbcTemplate.execute(
			     new CallableStatementCreator() {   
			         @Override
					public CallableStatement createCallableStatement(Connection con) throws SQLException {   
			            String storedProc = "{call make_friendstype(?,?,?,?)}";// 调用的sql   
			            CallableStatement cs = con.prepareCall(storedProc);   
			            cs.setString(1, playerid);// 设置输入参数的值   
			            cs.setString(2, friendid);
			            cs.setString(3, friendtype);
			            cs.registerOutParameter(4,Types.VARCHAR);// 注册输出参数的类型   
			            return cs;   
			         }   
			      }, new CallableStatementCallback<Object>() {   
			          @Override
					public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
			            cs.execute();   
			            return cs.getString(4);// 获取输出参数的值   
			      }   
			   });//have or true
		
		Session fsession=Bean.getPlayersession().get(friendid);
		if(friendtype.equals("0"))
		{
			if(fsession!=null)
			{
				HashMap<String,Object> mesresponse=new HashMap<String,Object>();
				mesresponse.put("wsurl","chatmessage");
				mesresponse.put("messagetype", "makefriends");
				mesresponse.put("nickname",playernickname);
				mesresponse.put("playerid",playerid);
				mesresponse.put("viplv",znx_playerdata.getViplv());
				mesresponse.put("playerlv", znx_playerdata.getPlayerlv());
				mesresponse.put("sex", friendsex);
				fsession.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
			}
			
		}
		else if(friendtype.equals("2"))
		{
			if(fsession!=null)
			{
				HashMap<String,Object> mesresponse=new HashMap<String,Object>();
				mesresponse.put("wsurl","chatmessage");
				mesresponse.put("messagetype", "agreemakefriends");
				mesresponse.put("nickname",playernickname);
				mesresponse.put("playerid",playerid);
				mesresponse.put("viplv",znx_playerdata.getViplv());
				mesresponse.put("playerlv", znx_playerdata.getPlayerlv());
				mesresponse.put("sex", friendsex);
				fsession.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
			}
			else
			{
				myJdbcTemplate.update("insert into player_chat_message(playerid,friendid,messtype) values("+friendid+","+playerid+",1)");
			}
		}
		response.put("res",res);
		response.put("friendlv", friendlv);
		response.put("friendtype", friendtype);

		return response;
	}
}
