package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumComplainsType;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveCustomerMessage implements IReceiveMessage{
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
		String complainstype=(String)request.get("complainstype");
		String complains=(String)request.get("complains");
		String sqlstr="INSERT INTO complains_log(playerid,complainstype,complains) VALUES(?,?,?)";
		Object[] sqlparas={playerid,String.valueOf(EnumComplainsType.valueOf(complainstype)),complains};
		myJdbcTemplate.update(sqlstr, sqlparas);
		response.put("res", "true");
		return response;
	}

}
