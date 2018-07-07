package com.mangni.vegaplan.servletsrc.matching;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.datatable.CompetitionSeasonRoom;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class CompetitionSeasonThread extends Thread {

	private Znx_PlayerData znx_playerdata;
	private String matchingplayerid;

	public CompetitionSeasonThread(Znx_PlayerData znx_playerdata){
		this.znx_playerdata=znx_playerdata;
	}

	@Override
	public void run() {
		int loopnum=0;
		int csstar=znx_playerdata.getCsstar();
		String playerid=znx_playerdata.getId();
		znx_playerdata.setCsmaxstar(csstar);
		znx_playerdata.setCsminstar(csstar);
		ConcurrentHashMap<String, Znx_PlayerData> csmap=Bean.getCompetitionseasonmap();
		csmap.put(playerid, znx_playerdata);
		Bean.getCsthreadmap().put(playerid, this);
		while(loopnum<2){
			znx_playerdata.setCsmaxstar(csstar+loopnum);
			znx_playerdata.setCsminstar(csstar-loopnum);
			loopnum++;
			Iterator<Entry<String, Znx_PlayerData>> iterator=csmap.entrySet().iterator();
			while(iterator.hasNext())
			{
				Entry<String, Znx_PlayerData>  mapentry = iterator.next();
				String key=mapentry.getKey();
				if(key.equals(znx_playerdata.getId()))
					continue;

				Znx_PlayerData matchingdata=mapentry.getValue();
				int matchingminstar=matchingdata.getCsminstar();
				int matchingmaxstar=matchingdata.getCsmaxstar();
				if(znx_playerdata.getCsminstar()<=matchingmaxstar&&znx_playerdata.getCsmaxstar()>=matchingminstar){//匹配成功
					//httpmessage
					CompetitionSeasonThread csthread=(CompetitionSeasonThread) Bean.getCsthreadmap().get(matchingdata.getId());
					//csthread.setMatchingplayerid(playerid);
					csthread.stop();
					String roomid=UUID.randomUUID().toString();
					matchingsuccess(key,roomid);
					Bean.getCompetitionseasonmap().remove(key);
					Bean.getCompetitionseasonmap().remove(playerid);
					Bean.getCsthreadmap().remove(key);
					Bean.getCsthreadmap().remove(playerid);
					FinishTaskHelper.finishEverydayTask(playerid, "16", "1");
					return;
					
				}
			}
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				//matchingsuccess(matchingplayerid,2);
			}

		}
		//httpmessage
		String roomid=UUID.randomUUID().toString();
		matchingsuccess("0",roomid);
		Bean.getCompetitionseasonmap().remove(playerid);
		Bean.getCsthreadmap().remove(playerid);
		FinishTaskHelper.finishEverydayTask(playerid, "16", "1");
	}

	public void matchingsuccess(String toplayerid,String roomid){
		ConcurrentHashMap<String, Znx_PlayerData> csmap=Bean.getCompetitionseasonmap();
		csmap.remove(znx_playerdata.getId());
		Bean.getCsthreadmap().remove(this.znx_playerdata.getId());
		HashMap<String,Object> response=new HashMap<String,Object>();
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		List<Map<String,Object>> playerdata=myJdbcTemplate.queryForList("select id,nickname,headimageid from znx_player where id="+znx_playerdata.getId()+" or id="+toplayerid);
		List<Map<String,Object>> uptobattledata=myJdbcTemplate.queryForList("select playerid,battletype,fightpower,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,soldier1,soldier2,soldier3,soldier4,soldier5 from player_tobattle where (playerid="+znx_playerdata.getId()+" or playerid="+toplayerid+") and battletype in(1,2,3)");
		HashMap<String,String> fighterdatastr=new HashMap<String,String>();
		fighterdatastr.put(znx_playerdata.getId(), "");
		fighterdatastr.put(toplayerid, "");
		for(Map<String,Object> map:uptobattledata){
			String playerid=String.valueOf(map.get("playerid"));
			String str=fighterdatastr.get(playerid);
			str+=String.valueOf(map.get("fighterposition1"))+","+String.valueOf(map.get("fighterposition2"))+","+String.valueOf(map.get("fighterposition3"))+",";
			fighterdatastr.put(playerid, str);
		}
		String str=fighterdatastr.get(znx_playerdata.getId());
		String str1=str.substring(0,str.length()-1);
		str=fighterdatastr.get(toplayerid);
		if(str=="")
			str="0,";
		String str2=str.substring(0,str.length()-1);
		List<Map<String,Object>> fighterdata=myJdbcTemplate.queryForList("select * from player_newfighter where (playerid="+znx_playerdata.getId()+" and fighterid in("+str1+")) or (playerid="+toplayerid+" and fighterid in ("+str2+"))");
		response.put("wsurl", "competitionseasonmatching");
		response.put("team", 1);
		response.put("playerdata", playerdata);
		response.put("uptobattledata", uptobattledata);
		response.put("fighterdata", fighterdata);
		response.put("servername", Bean.getServername());
		response.put("roomid",roomid);
		
		CompetitionSeasonRoom csd=new CompetitionSeasonRoom(roomid,znx_playerdata.getId(),toplayerid);
		Bean.getCompetitionseasonroommap().put(roomid, csd);
		
		String jsonstr=JSON.toJSONString(response);
		String aesstr=EncryptUtil.aesEncrypt(jsonstr);
		Session tosession=Bean.getPlayersession().get(znx_playerdata.getId());
		if(tosession!=null&&tosession.isOpen()){
			tosession.getRemote().sendStringByFuture(aesstr);
		}
		if(!"0".equals(toplayerid)){
			tosession=Bean.getPlayersession().get(toplayerid);
			if(tosession!=null&&tosession.isOpen()){
				response.put("team", 2);
				jsonstr=JSON.toJSONString(response);
				aesstr=EncryptUtil.aesEncrypt(jsonstr);
				tosession.getRemote().sendStringByFuture(aesstr);
			}
		}
	}

	public String getMatchingplayerid() {
		return matchingplayerid;
	}

	public void setMatchingplayerid(String matchingplayerid) {
		this.matchingplayerid = matchingplayerid;
	}
}
