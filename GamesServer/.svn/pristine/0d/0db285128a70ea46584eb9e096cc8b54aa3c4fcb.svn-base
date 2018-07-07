package com.mangni.vegaplan.jettyserver;

import java.util.List;
import org.eclipse.jetty.websocket.api.Session;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ConnectClosed implements Runnable {
	private String playerkey="0";
	private Session session;
	private MyJdbcTemplate myJdbcTemplate;
	public ConnectClosed(String playerkey,Session session){
		this.playerkey=playerkey;
		this.session=session;
		myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
	}
	@Override
	public void run() {
		if((!playerkey.equals("0"))&&((Bean.getPlayersession().containsKey(playerkey)))){
			System.out.println(playerkey+"closed");
			//取消匹配
			Bean.getCompetitionseasonmap().remove(playerkey);
			Thread csthread=Bean.getCsthreadmap().get(playerkey);
			if(csthread!=null){
				csthread.stop();
				Bean.getCsthreadmap().remove(playerkey);
			}

			Session playersession=Bean.getPlayersession().get(playerkey);
			if(playersession==session){
				List<String> fighterid=HashMapHelper.getKeyList(Bean.getZnx_fightermap(),"getIdAlertplayerid", playerkey);
				for(String fid:fighterid)
				{
					Bean.getZnx_fightermap().remove(fid);
				}
				
				synchronized (playerkey) {
					Bean.getPlayercount().remove(playerkey);
					String gangid=Bean.getZnx_playermap().get(playerkey).getGangid();
					if(gangid!=null){
						Znx_GangData znx_gangdata=Bean.getZnx_gangmap().get(gangid);
						List<String> onlineplayerid=znx_gangdata.getOnlineplayerid();
						synchronized (new Object()) {	
							onlineplayerid.remove(playerkey);
						}
//						清除军团缓存，（现已设计为军团缓存常驻内存）
//						if(znx_gangdata.getOnlineplayerid().size()==0){
//							Bean.getZnx_gangmap().remove(gangid);
//						}
					}
					Bean.getZnx_playermap().remove(playerkey);
					Bean.getAllplayersum().setText("在线人数："+(Bean.getPlayersession().size()-1));
					Bean.getPlayersession().remove(playerkey);
				}
				myJdbcTemplate.update("UPDATE ZNX_PLAYER SET ONLINETIME=datediff(ss,updatetime,GETDATE()) WHERE ID="+playerkey);
				//Bean.getPlayersession().get(playerkey).notifyAll();
			}else{
				Bean.getAllplayersum().setText("在线人数："+(Bean.getPlayersession().size()-1));
			}
		}
	}

}
