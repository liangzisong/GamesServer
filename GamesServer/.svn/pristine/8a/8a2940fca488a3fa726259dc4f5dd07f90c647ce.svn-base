package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ProcessJoinAsking implements IReceiveMessage {
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

		String auditerid=(String)request.get("auditerid");

		String type=(String)request.get("type");

		String res="false";

		if(type.equals("agree")){

			int gangcount=myJdbcTemplate.queryForObject("SELECT COUNT(*) FROM PLAYER_GANGINFO WITH(UPDLOCK) WHERE PLAYERID="+auditerid+" AND gangid!=0 AND ischecked=1",Integer.class);
			
			if(gangcount==0){
				
				Map<String, Object> playerinfo=myJdbcTemplate.queryForMap("SELECT GANGID,DUTIES FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid+" AND ISCHECKED=1");
				
				String gangid=String.valueOf(playerinfo.get("gangid"));
				
				int duties=Integer.parseInt(String.valueOf(playerinfo.get("DUTIES")));
				
				if(duties<=2){
					
					String sql1="DELETE FROM PLAYER_GANGINFO WHERE PLAYERID="+auditerid+" AND GANGID!="+gangid;
					String sql2="DELETE FROM player_ganginfo WHERE playerid="+auditerid+" AND gangid="+gangid+" AND taketime not in(SELECT MAX(taketime) FROM player_ganginfo WHERE playerid="+auditerid+" AND gangid="+gangid+")";
					String sql3="UPDATE PLAYER_GANGINFO SET ISCHECKED=1,ACTPOWER=(SELECT 100+VIPLV*5 FROM ZNX_PLAYER WHERE ID="+auditerid+"),endurance=(SELECT 100+VIPLV*5 FROM ZNX_PLAYER WHERE ID="+auditerid+") WHERE PLAYERID="+auditerid+" AND GANGID="+gangid;
					myJdbcTemplate.update(sql1);
					myJdbcTemplate.update(sql2);
					myJdbcTemplate.update(sql3);
					res="true";
				}
				
			}

			if(res.equals("true")){
				
				String gangid=Bean.getZnx_playermap().get(playerid).getGangid();
				
				if(Bean.getZnx_playermap().containsKey(auditerid)){
					
					List<String> onlineplayerid=Bean.getZnx_gangmap().get(gangid).getOnlineplayerid();
					synchronized (onlineplayerid) {	
						onlineplayerid.add(auditerid);
					}
					
					Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(auditerid);
					
					znx_playerdata.setGangid(gangid);
					
					znx_playerdata.setGangduties(4);
					
				}
			}

		}else{

			String gangid=myJdbcTemplate.queryForObject("SELECT GANGID FROM PLAYER_GANGINFO WHERE PLAYERID="+playerid,String.class);

			myJdbcTemplate.update("update player_ganginfo set gangid=0 where playerid="+auditerid+" AND GANGID="+gangid);
			
			//SqlHelper.Updatedb("DELETE FROM PLAYER_GANGINFO WHERE PLAYERID="+auditerid+" AND GANGID="+gangid);

			res="ignore";

		}
		response.put("res", res);

		return response;

	}

}
