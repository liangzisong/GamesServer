package com.mangni.vegaplan.servletsrc.matching;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.CompetitionSeasonRoom;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class CompetitionSensonComplete implements IReceiveMessage {
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
		//String playerid=(String)request.get("playerid");
		String roomid=(String)request.get("roomid");
		String winnerteam=(String)request.get("winnerteam");//1,2 玩家队伍
		String winnerid="0";
		String loserid="0";
		CompetitionSeasonRoom csr=Bean.getCompetitionseasonroommap().get(roomid);
		if(csr!=null){
			synchronized (csr) {
				if(csr!=null){
					if("1".equals(winnerteam)){
						winnerid=csr.getPlayer1id();
						loserid=csr.getPlayer2id();
					}else{
						winnerid=csr.getPlayer2id();
						loserid=csr.getPlayer1id();
					}
					Bean.getCompetitionseasonroommap().remove(roomid);
				}
			}
		}
		Znx_PlayerData winnerdata;
		Znx_PlayerData loserdata;
		if(!"0".equals(winnerid)){
			myJdbcTemplate.update("update znx_player set csstar=csstar+1 where id="+winnerid);
			winnerdata=Bean.getZnx_playermap().get(winnerid);
			if(winnerdata!=null)
				winnerdata.setCsstar(winnerdata.getCsstar()+1);	
		}
		if(!"0".equals(loserid)){
			myJdbcTemplate.update("update znx_player set csstar=csstar-1 where id="+loserid+" and csstar>0");
			loserdata=Bean.getZnx_playermap().get(loserid);
			if(loserdata!=null)
				if(loserdata.getCsstar()>0)
					loserdata.setCsstar(loserdata.getCsstar()-1);
		}
		response.put("res", "true");
		return response;
		
	}

}
