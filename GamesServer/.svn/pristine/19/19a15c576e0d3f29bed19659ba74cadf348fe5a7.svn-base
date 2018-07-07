package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class AskforLeaveGang implements IReceiveMessage {
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
		String res="false";
		String playergangid=myJdbcTemplate.queryForObject("SELECT GANGID FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid,String.class);
		if(playergangid!=null&&!playerid.equals("0")){
			String sql="UPDATE PLAYER_GANGINFO SET GANGID=0,ISCHECKED=0,TAKETIME=GETDATE() WHERE PLAYERID="+playerid;
			myJdbcTemplate.update(sql);
			Bean.getZnx_playermap().get(playerid).initgang();
			List<String> onlineplayerid=Bean.getZnx_gangmap().get(playergangid).getOnlineplayerid();
			synchronized (onlineplayerid) {		
				onlineplayerid.remove(playerid);	
			}
			res="true";
			
		}
		response.put("res", res);
		return response;
	}
}
