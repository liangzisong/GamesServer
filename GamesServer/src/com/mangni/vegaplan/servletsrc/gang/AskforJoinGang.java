package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class AskforJoinGang implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response = new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String gangid=(String)request.get("gangid");
		String res="false";
		String lastdatestr=myJdbcTemplate.queryForObject("SELECT MAX(TAKETIME) AS TAKETIME FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid+" AND GANGID!=0",String.class);
		Date lastdate=null;
		if(lastdatestr!=null){
			try {
				lastdate=Bean.getDateFormat().parse(lastdatestr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Date nowdate=new Date();
		if(lastdatestr==null||nowdate.getTime()-lastdate.getTime()>=500*60*60){
			lastdatestr=myJdbcTemplate.queryForObject("SELECT MAX(TAKETIME) FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid+" AND GANGID=0",String.class);
			if(lastdatestr!=null){
				try {
					lastdate=Bean.getDateFormat().parse(lastdatestr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(nowdate.getTime()-lastdate.getTime()<500*60*60){
					response.put("res", res);
					return response;
				}
			}
			String sql="INSERT INTO PLAYER_GANGINFO(GANGID,PLAYERID,DUTIES) VALUES(?,?,?)";
			Object[] sqlpara={gangid,playerid,"4"};
			myJdbcTemplate.update(sql, sqlpara);
			res="true";	
		}
		response.put("res", res);
		return response;
	}
}