package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class DismissGang implements IReceiveMessage {
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
		String gangpwd=(String)request.get("gangpwd");
		String res="false";
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		String gangid=myJdbcTemplate.queryForObject("SELECT GANGID FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid+" AND DUTIES=1",String.class);
		String pwd=myJdbcTemplate.queryForObject("SELECT GANGPWD FROM PLAYER_GANG WHERE ID="+gangid,String.class);
		if(gangid!=null&&pwd.equals(gangpwd)){
			String sql1="DELETE FROM PLAYER_GANG_MESSAGE WHERE GANGID="+gangid;
			String sql2="DELETE FROM PLAYER_GANGINFO WHERE GANGID="+gangid;
			String sql3="DELETE FROM PLAYER_GANG_GOALAWARD WHERE GANGID="+gangid;
			String sql4="DELETE FROM PLAYER_GANG WHERE ID="+gangid;
			myJdbcTemplate.update(sql1);
			myJdbcTemplate.update(sql2);
			myJdbcTemplate.update(sql3);
			myJdbcTemplate.update(sql4);
			res="true";
		}
		if(res.equals("true")){
			Bean.getZnx_gangmap().remove(gangid);
			znx_playerdata.initgang();
		}
		response.put("res", res);
		return response;
	}

}
