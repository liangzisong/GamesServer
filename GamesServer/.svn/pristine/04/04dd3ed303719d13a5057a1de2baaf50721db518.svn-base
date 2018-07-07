package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 点击挑战，客户端发送 自己id=xx&被挑战名次=xx，服务器返回 ture or wait or false
 * @author Administrator
 *
 */
public class ChallengeMilitBefore implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request){
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String) request.get("playerid");
		String passplayerid=(String) request.get("passplayerid");
		String playerpassrank=(String) request.get("playerpassrank");

		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		int challengenum=playerdata.getChallengenum();
		if(challengenum>16){
			response.put("res", "false");
			return response;
		}
		int playerrankdan=playerdata.getMilitaryrankdan();
		int realplayerpassrank=0;
		int realplayerpassrankdan=0;
		if(!"0".equals(passplayerid)){
			if(Bean.getZnx_playermap().containsKey(passplayerid)){
				Znx_PlayerData playerpassdata=Bean.getZnx_playermap().get(passplayerid);
				realplayerpassrank=playerpassdata.getMilitaryrank();
				realplayerpassrankdan=playerpassdata.getMilitaryrankdan();
			}else{
				Map<String,Object> map=myJdbcTemplate.queryForMap("select militaryrank,militaryrankdan from znx_player where id="+passplayerid);
				realplayerpassrank=Integer.parseInt(String.valueOf(map.get("militaryrank")));
				realplayerpassrankdan=Integer.parseInt(String.valueOf(map.get("militaryrankdan")));
			}
		}

		if("0".equals(passplayerid)||(playerrankdan==realplayerpassrankdan&&Integer.parseInt(playerpassrank)==realplayerpassrank)){
			if(playerdata.getChallengenum()>16){
				response.put("res", "not enough");
				return response;
			}

			int challengess=myJdbcTemplate.queryForObject("SELECT min(datediff(\"SS\",challengetime,GETDATE())) FROM znx_player WHERE id="+passplayerid+" or id="+playerid,Integer.class); 
			if(challengess<100){
				response.put("res", "wait");
				return response;
			}
			if(!"0".equals(passplayerid))
				myJdbcTemplate.update("update znx_player set challengetime=getdate() where id="+passplayerid);
			
			myJdbcTemplate.update("update znx_player set challengenum=challengenum+1,challengetime=getdate(),challengerank="+playerpassrank+" where id="+playerid);
			playerdata.setChallengenum(playerdata.getChallengenum()+1);

			FinishTaskHelper.finishEverydayTask(playerid, "15", "1");
			FinishTaskHelper.finishHolidayTask(playerid, "23");
			response.put("res", "true");
		}else{
			response.put("res", "refresh");
		}
		return response;
	}
}