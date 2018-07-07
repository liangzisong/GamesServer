package com.mangni.vegaplan.toolshelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;

public class SendMessageHelper implements Runnable {
	private String playerid,msgtype,goodstype,goodsid,star;
	public SendMessageHelper(String playerid,String msgtype,String goodstype,String goodsid,String star){
		this.playerid=playerid;
		this.msgtype=msgtype;
		this.goodstype=goodstype;
		this.goodsid=goodsid;
		this.star=star;
	}

	@Override
	public void run() {
		HashMap<String,Object> msgresponse = new HashMap<String,Object>();
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		msgresponse.put("wsurl","flowingmsg");
		msgresponse.put("messagetype", msgtype);
		msgresponse.put("goodstype", goodstype);
		msgresponse.put("goodsid", goodsid);
		msgresponse.put("playerid", playerid);
		msgresponse.put("nickname", znx_playerdata.getNickname());
		msgresponse.put("star", star);
		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();

		for(Entry<String, Session> entry:sessionmap.entrySet())
		{
			if(entry.getValue().isOpen())
				entry.getValue().getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(msgresponse)));
		}
	}

	public static void sendGangMessage(Znx_GangData znx_gangdata,Znx_PlayerData znx_playerdata,String messagetype,String message,int hurtnum){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String uptime=myJdbcTemplate.queryForObject("select updatetime from player_gang_message where playerid="+znx_playerdata.getId()+" and messtype="+messagetype+" and num="+hurtnum,String.class);
		if(uptime==null){
			Object[] para={znx_gangdata.getId(),znx_playerdata.getId(),messagetype,String.valueOf(hurtnum),message};
			myJdbcTemplate.update("insert into player_gang_message(gangid,playerid,messtype,num,mess) values(?,?,?,?,?)",para);
		}else{
			String sql=null;
			Date update=null;
			try {
				update=Bean.getDateFormat().parse(uptime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar updatetime=Calendar.getInstance();
			Calendar now=Calendar.getInstance();
			updatetime.setTime(update);
			updatetime.add(Calendar.HOUR_OF_DAY, -3);
			now.add(Calendar.HOUR_OF_DAY, -3);
			if(updatetime.get(Calendar.DAY_OF_YEAR)==now.get(Calendar.DAY_OF_YEAR)){
				sql="update player_gang_message set gangid=?,mess=mess+"+message+",updatetime=getdate() where playerid=? and messtype=? and num=?";
			}else{
				sql="update player_gang_message set gangid=?,mess='"+message+"',updatetime=getdate() where playerid=? and messtype=? and num=?";
			}
			Object[] para={znx_gangdata.getId(),znx_playerdata.getId(),messagetype,String.valueOf(hurtnum)};
			myJdbcTemplate.update(sql, para);
		}

		HashMap<String,Object> msgresponse = new HashMap<String,Object>();
		msgresponse.put("wsurl","gangmessage");
		msgresponse.put("messagetype", messagetype);
		msgresponse.put("message", message);
		msgresponse.put("viplv", znx_playerdata.getViplv());
		msgresponse.put("playerid", znx_playerdata.getId());
		msgresponse.put("nickname", znx_playerdata.getNickname());

		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();

		List<String> onlineplayerid=znx_gangdata.getOnlineplayerid();

		synchronized (onlineplayerid) {	

			for(String playerid:onlineplayerid){

				Session session=sessionmap.get(playerid);

				if(session!=null){

					if(session.isOpen()){

						session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(msgresponse)));

					}

				}

			}

		}

	}

	public static void sendGangMessage(Znx_GangData znx_gangdata,String playerid,String messagetype,String message,int hurtnum){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");

		Object[] para={znx_gangdata.getId(),playerid,messagetype,String.valueOf(hurtnum),message};
		myJdbcTemplate.update("insert into player_gang_message(gangid,playerid,messtype,num,mess) values(?,?,?,?,?)",para);


		Map<String,Object> msgresponse = myJdbcTemplate.queryForMap("select viplv,nickname from znx_player where id="+playerid);
		if(msgresponse==null)
			msgresponse=new HashMap<String,Object>();
		
		msgresponse.put("wsurl","gangmessage");
		msgresponse.put("messagetype", messagetype);
		msgresponse.put("message", message);
		msgresponse.put("playerid", playerid);

		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();

		if("0".equals(playerid)){

			List<String> onlineplayer=znx_gangdata.getOnlineplayerid();

			synchronized (onlineplayer) {	

				for(String onlineplayerid:onlineplayer){

					Session session=sessionmap.get(onlineplayerid);

					if(session!=null){

						if(session.isOpen()){

							session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(msgresponse)));

						}

					}

				}

			}
		}else{
			Session session=sessionmap.get(playerid);
			if(session!=null){

				if(session.isOpen()){

					session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(msgresponse)));

				}

			}

		}
	}



	public static void sendMail(String sql,String[] sqlpara,String playerid){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		Long id=myJdbcTemplate.insertAndGetKey(sql, sqlpara); 
		if(Bean.getZnx_playermap().containsKey(playerid)){
			HashMap<String,Object> msgresponse = new HashMap<String,Object>();
			msgresponse.put("wsurl","getemail");
			msgresponse.put("mailid", id);
			msgresponse.put("mailinfo", sqlpara);
			ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();
			Session session=sessionmap.get(playerid);

			if(session!=null){

				if(session.isOpen()){

					session.getRemote().sendStringByFuture(EncryptUtil.aesEncrypt(JSON.toJSONString(msgresponse)));
				}
			}
		}
	}
}
