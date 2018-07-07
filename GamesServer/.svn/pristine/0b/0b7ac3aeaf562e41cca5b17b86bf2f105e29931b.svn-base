package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GangBattleChallenge implements IReceiveMessage,Runnable {
	private String playerid,challengeplayerid,playergangid;
	private int killnum=0,killednum=0;
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
		playerid=(String)request.get("playerid");
		String type=(String)request.get("type");
		
		if("before".equals(type)){
			challengeplayerid=(String)request.get("challengeplayerid");
			int challengess=myJdbcTemplate.queryForObject("SELECT min(datediff(\"SS\",gangchallengetime,GETDATE())) FROM player_ganginfo with(updlock) WHERE playerid="+challengeplayerid+" or playerid="+playerid,Integer.class); 
			if(challengess<100){//是否正在被挑战 
				response.put("res", "wait");
				return response;
			}
			Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
			playergangid=playerdata.getGangid();
			String positiontime=playerdata.getPositiontime();
			if(!"null".equals(positiontime)){
				try {
					Calendar positiondate=Calendar.getInstance();
					Calendar now=Calendar.getInstance();
					positiondate.setTime(Bean.getDateFormat().parse(positiontime));
					positiondate.setFirstDayOfWeek(Calendar.MONDAY);
					now.setFirstDayOfWeek(Calendar.MONDAY);
					positiondate.add(Calendar.HOUR_OF_DAY, -1);//1小时余量
					now.add(Calendar.HOUR_OF_DAY, -1);//1小时余量

					if(!GangBattleSite.isSameDate(positiondate, now)){//是否处于同一周内
						GangBattleSite.initPosition(playerid,myJdbcTemplate);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				GangBattleSite.initPosition(playerid,myJdbcTemplate);
			}
			
			int ppositionx=playerdata.getBattlepositionx();
			int ppositiony=playerdata.getBattlepositiony();
			int cpositionx=0;
			int cpositiony=0;
			if(Bean.getZnx_playermap().containsKey(challengeplayerid)){
				Znx_PlayerData challengeplayerdata=Bean.getZnx_playermap().get(challengeplayerid);
				cpositionx=challengeplayerdata.getBattlepositionx();
				cpositiony=challengeplayerdata.getBattlepositiony();
			}else{
				Map<String,Object> map=myJdbcTemplate.queryForMap("select battlepositionx,battlepositiony from player_ganginfo where playerid="+challengeplayerid);
				cpositionx=Integer.parseInt(String.valueOf(map.get("battlepositionx")));
				cpositiony=Integer.parseInt(String.valueOf(map.get("battlepositiony")));
			}
			if(!GangBattleSite.canChallenge(ppositionx, ppositiony, cpositionx, cpositiony)){
				response.put("res", "false");
				return response;
			}
			
			Thread thread=new Thread(this);
			thread.start();
			Bean.getGangbattlethreadmap().put(playerid, thread);
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENDURANCE, "0", "-10");
			GetGoodsHelper.getGoods(challengeplayerid, EnumGoodsTypes.ENDURANCE, "0", "-10");
			response.put("res", "true");
		}else{//战斗结果
			String result=(String)request.get("result");//true或false
			killnum=Integer.parseInt((String)request.get("killnum"));//击杀人数
			killednum=Integer.parseInt((String)request.get("killednum"));//被击杀人数
			Thread thread=Bean.getGangbattlethreadmap().get(playerid);
			if(thread==null){
				response.put("res", "false");
			}
			thread.interrupt();
			challengeResult(result,killnum,killednum);
			response.put("res", "true");
		}
		return response;
		
	}
	
	public void challengeResult(String result,int killnum,int killednum){
		if("true".equals(result)){//胜利
			GangBattleSite.initPosition(challengeplayerid,myJdbcTemplate);
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENDURANCE, "0", String.valueOf(killednum*15));
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CONTRIBUTION, "0", "10");
			SendMessageHelper.sendGangMessage(Bean.getZnx_gangmap().get(playergangid), playerid, "4", "1", 1);
		}else{//失败
			GangBattleSite.initPosition(playerid,myJdbcTemplate);
			GetGoodsHelper.getGoods(challengeplayerid, EnumGoodsTypes.ENDURANCE, "0", String.valueOf(killednum*15));
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CONTRIBUTION, "0", "5");
			SendMessageHelper.sendGangMessage(Bean.getZnx_gangmap().get(playergangid), playerid, "4", "0", 1);
		}
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(100*1000);//100秒之后
			challengeResult("false",0,0);
		} catch (InterruptedException e) {
			
		}
		
	}

}
