package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class CreateGang implements IReceiveMessage {
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
		
		String gangname=(String)request.get("gangname");
		
		String gangpwd=(String)request.get("gangpwd");
		
		String limitlv=(String)request.get("limitlv");
		
		String icon=(String)request.get("icon");
		
		String res="false";
		
		String gangid = null;
		
		if(limitlv==null)
			
			limitlv="0";
		
		String[] param={gangname,gangpwd,limitlv,icon};
		
		int count=myJdbcTemplate.queryForObject("SELECT COUNT(*) FROM PLAYER_GANG WHERE GANGNAME='"+gangname+"'",Integer.class);
		
		if(count==0){
		
			int stone=myJdbcTemplate.queryForObject("select stone from znx_player where id="+playerid,Integer.class);
			
			if(stone<1500)
				return response;
			
			String countgangsql="SELECT COUNT(*) FROM PLAYER_GANGINFO WITH(UPDLOCK) WHERE PLAYERID="+playerid+" AND ischecked=1";
			int countgang=myJdbcTemplate.queryForObject(countgangsql, Integer.class);
			String insertsql;
			if(countgang==0){
				myJdbcTemplate.update("DELETE FROM player_ganginfo WHERE playerid="+playerid);
				insertsql="INSERT INTO player_gang(gangname,gangpwd,limitlv,icon) VALUES(?,?,?,?)";
				gangid=String.valueOf(myJdbcTemplate.insertAndGetKey(insertsql, param));
				myJdbcTemplate.update("INSERT INTO PLAYER_GANGINFO(playerid,gangid,duties,ischecked) VALUES("+playerid+","+gangid+",1,1)");
				res="true";
			}else{
				res="false";
			}
			
		
		}else{
			
			res="have";
			
		}
		
		if(res.equals("true")){
			
			LvupHelper.spendStone(playerid, 1500, "creategang", "update znx_player set stone=stone-1500 where id="+playerid, request, false);
			
			//String gangid=myJdbcTemplate.queryForObject("select gangid from player_ganginfo where playerid="+playerid+" and duties=1",String.class);
			
			Znx_GangData znx_gangdata=new Znx_GangData();
			
			znx_gangdata.setId(gangid);
			
			znx_gangdata.setGangname(gangname);

			znx_gangdata.setGangpwd(gangpwd);
			
			znx_gangdata.setLimitlv(Integer.parseInt(limitlv));
			
			Bean.getZnx_gangmap().put(gangid, znx_gangdata);
			
			List<String> onlineplayerid=Bean.getZnx_gangmap().get(gangid).getOnlineplayerid();
			synchronized (onlineplayerid) {		
				onlineplayerid.add(playerid);
			}
			
			Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
			
			znx_playerdata.setGangid(gangid);
			
			znx_playerdata.setGangduties(1);
			
		}
		
		response.put("res", res);
		
		response.put("gangid", gangid);
		
		return response;
		
	} 
	
}
