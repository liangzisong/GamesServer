package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.List;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;

public class SendGangMessage implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String message=(String)request.get("message");

		HashMap<String,Object> mesresponse=new HashMap<String,Object>();
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		String gangid=znx_playerdata.getGangid();
		mesresponse.put("wsurl","chatmessage");
		mesresponse.put("messagetype", "gangmessage");
		mesresponse.put("message", message);
		mesresponse.put("playerid", playerid);
		mesresponse.put("nickname", znx_playerdata.getNickname());
		mesresponse.put("viplv", znx_playerdata.getViplv());
		mesresponse.put("sex", znx_playerdata.getSex());
		List<String> onlineplayerid=Bean.getZnx_gangmap().get(gangid).getOnlineplayerid();

		synchronized (onlineplayerid) {	
			for(String sessionplayerid:onlineplayerid){
				Session session=Bean.getPlayersession().get(sessionplayerid);
				if(session!=null&&session.isOpen()){
					session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
				}
			}
		}
		response.put("res","true");
		return response;
	}

}
