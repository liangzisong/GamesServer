package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class KickPlayer implements IReceiveMessage {
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
		String playerid=(String)request.get("playerid");//操作人
		String kickplayerid=(String)request.get("kickplayerid");//被踢出玩家
		String gangpwd=(String)request.get("gangpwd");
		String res="false";
		String sql="SELECT A.GANGID,A.DUTIES FROM PLAYER_GANGINFO A with(updlock) WHERE PLAYERID="+playerid+" AND EXISTS(SELECT TOP 1 PLAYERID FROM PLAYER_GANGINFO B WHERE B.PLAYERID="+kickplayerid+" AND B.GANGID=A.GANGID AND B.DUTIES>3)";
		Map<String, Object> ganginfo=myJdbcTemplate.queryForMap(sql);
		if(ganginfo!=null&&!ganginfo.isEmpty()){
			String gangid=String.valueOf(ganginfo.get("gangid"));
			String duities=String.valueOf(ganginfo.get("duties"));
			if(Integer.parseInt(duities)<3){ 
				String pwd=myJdbcTemplate.queryForObject("SELECT GANGPWD FROM PLAYER_GANG WHERE ID="+gangid,String.class);
				if(pwd.equals(gangpwd)){
					String sql1="UPDATE PLAYER_GANGINFO SET GANGID=0,ISCHECKED=0 WHERE PLAYERID="+kickplayerid;
					String sql2="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,MAILCONTENT) VALUES(?,?,?,?)";
					Object[] sqlpara2={kickplayerid,"4","帮派消息","你已被踢出帮派"};
					myJdbcTemplate.update(sql1);
					myJdbcTemplate.update(sql2,sqlpara2);
					res="true";
					List<String> onlineplayerid=Bean.getZnx_gangmap().get(gangid).getOnlineplayerid();
					synchronized (onlineplayerid) {	
						onlineplayerid.remove(kickplayerid);
					}
					if(Bean.getZnx_playermap().containsKey(kickplayerid))
						Bean.getZnx_playermap().get(kickplayerid).initgang();
				}

				if(Bean.getPlayersession().containsKey(kickplayerid)){
					HashMap<String,Object> mesresponse=new HashMap<String,Object>();
					mesresponse.put("wsurl","chatmessage");
					mesresponse.put("messagetype", "ganginsidemessage");
					mesresponse.put("message", "true");
					Session session=Bean.getPlayersession().get(kickplayerid);
					if(session.isOpen()){
						session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(mesresponse)));
					}
				}
			}
		}
		response.put("res", res);
		return response;
	}

}
