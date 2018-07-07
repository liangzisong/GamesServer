package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class UpdateGangDuties implements IReceiveMessage {
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
		String upplayerid=(String)request.get("upplayerid");
		String duties=(String)request.get("duties");
		String res="false";
		String gangid=myJdbcTemplate.queryForObject("SELECT GANGID  FROM PLAYER_GANGINFO with(updlock) WHERE PLAYERID="+playerid+" AND DUTIES=1",String.class);

		if(gangid!=null){
			if(Bean.getZnx_gangmap().get(gangid).getGangpwd().equals(gangpwd)){
				int gangcount=myJdbcTemplate.queryForObject("SELECT count(*) FROM PLAYER_GANGINFO WHERE GANGID="+gangid+" and duties="+duties,Integer.class);
				if(duties.equals("2")){
					if(gangcount>=2){
						response.put("res", "full");
						return response;
					}
				}else if(duties.equals("3")){
					if(gangcount>=5){
						response.put("res", "full");
						return response;
					}
				}else if(duties.equals("4")){

				}else if(duties.equals("1")){
					String sql="UPDATE PLAYER_GANGINFO SET DUTIES=4 WHERE PLAYERID="+playerid+" AND GANGID="+gangid;
					myJdbcTemplate.update(sql);
				}else{
					response.put("res", res);
					return response;
				}

				String sql="UPDATE PLAYER_GANGINFO SET DUTIES="+duties+" WHERE PLAYERID="+upplayerid+" AND GANGID="+gangid;
				myJdbcTemplate.update(sql);
				res="true";
			}
		}
		response.put("res", res);
		return response;
	}
}
