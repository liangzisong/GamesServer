package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 挑战成功，客户端发送 playerid=xx，服务器返回ture or false
 * @author Administrator
 *
 */
public class ChallengeMilitAfter implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String) request.get("playerid");
		String result=(String) request.get("result");
		/*
			String playerpassrank=(String) Bean.getPlayerchellangerank().get(playerid).get(0);

			String [] sqlpara={playerid,playerpassrank};
			Thread thread = (Thread) Bean.getPlayerchellangerank().get(playerid).get(1);
			Bean.getPlayerchellangerank().remove(playerid);

			thread.interrupt();

			while(thread.isAlive()){}

			if(result.equals("true"))
			{
				res=SqlHelper.DbExecute("challenge_militaryrank_after(?,?,?)", sqlpara, true);
			}
			else
			{
				res="true";
			}
		}

		response.put("res",res);*/
		Map<String,Object> rankinfo=myJdbcTemplate.queryForMap("select datediff(\"SS\",challengetime,getdate()) AS difftime,challengerank,militaryrankdan,militaryrank,maxrank from znx_player where id="+playerid);
		int difftime=Integer.parseInt(String.valueOf(rankinfo.get("difftime")));
		if(difftime>100){
			response.put("res", "false");
			return response;
		}

		int challengerank=Integer.parseInt(String.valueOf(rankinfo.get("challengerank")));
		int militaryrankdan=Integer.parseInt(String.valueOf(rankinfo.get("militaryrankdan")));
		int militaryrank=Integer.parseInt(String.valueOf(rankinfo.get("militaryrank")));
		int maxrank=Integer.parseInt(String.valueOf(rankinfo.get("maxrank")));

		String passidplayerid=myJdbcTemplate.queryForObject("select top 1 id from znx_player where militaryrankdan="+militaryrankdan+" and militaryrank="+challengerank, String.class);
		if("true".equals(result)){//胜利
			if(militaryrank>challengerank){//挑战比自己名次高的玩家
				if(!"0".equals(passidplayerid)){
					myJdbcTemplate.update("update znx_player set militaryrank="+militaryrank+",challengetime=getdate()-1 where id="+passidplayerid);
					Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
					playerdata.setMilitaryrank(challengerank);
					if(maxrank>challengerank){
						playerdata.setMaxrank(challengerank);
					}
					myJdbcTemplate.update("update znx_player set militaryrank="+challengerank+",maxrank="+playerdata.getMaxrank()+",challengetime=getdate()-1 where id="+playerid);
					if(Bean.getZnx_playermap().containsKey(passidplayerid)){
						Znx_PlayerData passplayerdata=Bean.getZnx_playermap().get(passidplayerid);
						passplayerdata.setMilitaryrank(militaryrank);
					}
				}
			}else{//挑战比自己名次低的玩家
				setChallenge(playerid,militaryrank,challengerank);
			}
		}else{//失败
			setChallenge(playerid,militaryrank,challengerank);
		}
		response.put("res", "true");
		return response;
	}
	public void setChallenge(String playerid,int militaryrankdan,int challengerank){
		Object[] obj={playerid,militaryrankdan,challengerank};
		myJdbcTemplate.update("update znx_player set challengerank=0,challengetime=getdate()-1 where id=? or (militaryrankdan=? and militaryrank=?)",obj);
	}
}
