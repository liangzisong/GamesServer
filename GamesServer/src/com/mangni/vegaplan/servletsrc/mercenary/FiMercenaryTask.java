package com.mangni.vegaplan.servletsrc.mercenary;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FiMercenaryTask implements IReceiveMessage {
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
		
		String taskid=(String)request.get("taskid");
		
		String fighter1id=(String) request.get("fighter1id");
		
		String fighter2id=(String) request.get("fighter2id");
		
		String fighter3id=(String) request.get("fighter3id");
		
		String res="false";
		
		int playerviplv=Bean.getZnx_playermap().get(playerid).getViplv();
		
		int finum=myJdbcTemplate.queryForObject("SELECT COUNT(id) FROM PLAYER_MERCENARYTASK WHERE PLAYERID="+playerid+" AND datediff(DAY,DATEADD(HOUR,-3,updatetime),DATEADD(HOUR,-3,getdate()))=0",Integer.class);
		
		if(playerviplv+8>finum){//次数没有达到上限
			
			String taskinfosql="SELECT expaward,goldaward,stoneaward,jewelid,@chipid=chipid,@goodsid=goodsid FROM znx_mercenarytask WITH(UPDLOCK) WHERE id=@taskid";
			
			Map<String,Object> taskinfo=myJdbcTemplate.queryForMap(taskinfosql);
			
			String[] sqlpara={playerid,taskid};
			
			List<String> award=SqlHelper.DbExecute("finish_player_mercenarytask(?,?,?,?,?,?)", sqlpara, 4);
			
			if(!award.get(0).equals("false")){
				
				String[] fighteridpara={fighter1id,fighter2id,fighter3id};
				
				int awardexp=Integer.parseInt(award.get(0));
				
				LvupHelper.UpPlayerExp(playerid, awardexp);
				
				LvupHelper.UpFighterExp(fighteridpara, awardexp);
				
				FinishTaskHelper.finishEverydayTask(playerid, "34","1");
				
				res="true";
				
				response.put("jeweldataid",award.get(1));
				response.put("chipdataid", award.get(2));
				
			}
					
		}
		
		response.put("res", res);
		
		return response;
		
	}

}
