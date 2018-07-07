package com.mangni.vegaplan.servletsrc.matching;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage; 
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;

public class CompetitionSeason implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String type=(String)request.get("type");//cancel or confirm
		if("cancel".equals(type)){
			Bean.getCompetitionseasonmap().remove(playerid);
			Thread csthread=Bean.getCsthreadmap().get(playerid);
			if(csthread!=null){
				csthread.stop();
				Bean.getCsthreadmap().remove(playerid);
			}
			response.put("res", "true");
			return response;
		}else{
			Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
			CompetitionSeasonThread csthread=new CompetitionSeasonThread(znx_playerdata);
			csthread.start();
			response.put("res", "true");
			return response;
		}
	}
}
