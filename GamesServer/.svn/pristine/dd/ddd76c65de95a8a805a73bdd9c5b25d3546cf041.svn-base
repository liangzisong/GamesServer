package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.List;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FriendVit implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		String playerid=(String)request.get("playerid");
		String type=(String)request.get("type");
		//Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		HashMap<String,Object> response=new HashMap<String,Object>();
		if("give".equals(type)){
			String givetype=(String)request.get("givetype");
			if("onekey".equals(givetype)){
				List<String> friendsid=myJdbcTemplate.queryForList("select friendid from player_friends where playerid="+playerid,String.class);
				myJdbcTemplate.update("update player_friends set givevittime=getdate() where friendid="+playerid);
				FinishTaskHelper.finishEverydayTask(playerid, "12", String.valueOf(friendsid.size()));
				for(int i=0;i<friendsid.size();i++){
					if(Bean.getPlayersession().containsKey(friendsid)){
						Session fsession=Bean.getPlayersession().get(friendsid);
						if(fsession!=null)
						{
							HashMap<String,Object> mesresponse=new HashMap<String,Object>();
							mesresponse.put("wsurl","chatmessage");
							mesresponse.put("messagetype", "givevit");
							mesresponse.put("playerid",playerid);
							fsession.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
						}
					}
				}
				
			}else{
				String friendid=(String)request.get("friendid");
				myJdbcTemplate.update("update player_friends set givevittime=getdate() where playerid="+friendid+" and friendid="+playerid);
				FinishTaskHelper.finishEverydayTask(playerid, "12", "1");
				if(Bean.getPlayersession().containsKey(friendid)){
					Session fsession=Bean.getPlayersession().get(friendid);
					if(fsession!=null)
					{
						HashMap<String,Object> mesresponse=new HashMap<String,Object>();
						mesresponse.put("wsurl","chatmessage");
						mesresponse.put("messagetype", "givevit");
						mesresponse.put("playerid",playerid);
						fsession.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
					}
				}
			}
			
			     
		}else{
			String rewardtype=(String)request.get("rewardtype");
			if("onekey".equals(rewardtype)){
				String friendid=(String)request.get("friendid");
				int vitcount=5*myJdbcTemplate.update("update rewardvittime=getdate() where playerid="+playerid+" and friendid="+friendid+" and ((rewardvittime is null and givevittime is not null) or (rewardvittime<givevittime))");
				response.put("vitcount", String.valueOf(vitcount));
				
			}else{		
				int vitcount=5*myJdbcTemplate.update("update rewardvittime=getdate() where playerid="+playerid+" and ((rewardvittime is null and givevittime is not null) or (rewardvittime<givevittime))");
				response.put("vitcount", String.valueOf(vitcount));
				
			}
			
		}
		response.put("res", "true");
		return response;
	}

}
