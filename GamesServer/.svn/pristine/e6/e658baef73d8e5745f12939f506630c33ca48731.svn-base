package com.mangni.vegaplan.servletsrc.gang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.datatable.GangbossawardData;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_GanggoalData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GanggoalThread implements Runnable {
	private String gangid;
	private long sleeptime;
	private MyJdbcTemplate myJdbcTemplate;
	public GanggoalThread(String gangid,long sleeptime){
		this.gangid=gangid;
		this.sleeptime=sleeptime;
	}
	@Override
	public void run() {
		try {
			Thread.sleep(sleeptime);
			goalAward();
		} catch (InterruptedException e) {
			goalAward();
		}
	}

	private void goalAward(){
		if(!Bean.getZnx_ganggoalmap().containsKey(gangid))
			return;

		HashMap<String,Object> msgresponse=new HashMap<String,Object>();
		Znx_GanggoalData ganggoaldata=Bean.getZnx_ganggoalmap().get(gangid);
		Znx_GangData znx_gangdata=Bean.getZnx_gangmap().get(gangid);
		int goallv=0;
		if(znx_gangdata==null){
			goallv=myJdbcTemplate.queryForObject("SELECT GOALLV FROM PLAYER_GANG WHERE ID="+gangid,Integer.class);
		}else{
			goallv=Bean.getZnx_gangmap().get(gangid).getGoallv();
		}

		HashMap<String,GangbossawardData> gangbossawardmap=Bean.getGangbossawardmap();
		GangbossawardData gangbossawarddata=new GangbossawardData();
		for(int i=1;i<=gangbossawardmap.size();i++){
			gangbossawarddata=gangbossawardmap.get(String.valueOf(i));
			if(goallv<=gangbossawarddata.getMaxlv()){
				break;
			}
		}

		long hp=ganggoaldata.getHp();
		//被击杀
		int pro=0;
		if(hp<=0){
			pro=100;
		}else{//未击杀
			long maxhp=ganggoaldata.getMaxhp();
			pro=(int)(((maxhp-hp)*100/maxhp));
		}

		String sql="insert into player_gang_goalaward(gangid,goodstype,goodsid,goodsnum) values(?,?,?,?)"; 
		List<Object[]> paralist=new ArrayList<Object[]>();
		if(pro<15){
			msgresponse.put("res", "timeout");
		}else if(pro>=15){
			String goodsid=gangbossawarddata.getPer15itemid();
			int goodsnum=gangbossawarddata.getPer15itemnum();
			String jewelcharacter=gangbossawarddata.getPer15jewelcharacter();
			String znxjewelid=GetGoodsHelper.getRanJewAlertChar(jewelcharacter);
			for(int i=0;i<goodsnum;i++){
				String[] para1={gangid,"goods",goodsid,"1"};
				paralist.add(para1);
			}
			String[] para2={gangid,"jewel",znxjewelid,"1"};
			paralist.add(para2);
			msgresponse.put("per15znxjewelid", znxjewelid);
		}
		if(pro>=30){
			String goodsid=gangbossawarddata.getPer30itemid();
			int goodsnum=gangbossawarddata.getPer30itemnum();
			String jewelcharacter=gangbossawarddata.getPer30jewelcharacter();
			String znxjewelid=GetGoodsHelper.getRanJewAlertChar(jewelcharacter);
			for(int i=0;i<goodsnum;i++){
				String[] para1={gangid,"goods",goodsid,"1"};
				paralist.add(para1);
			}
			String[] para2={gangid,"jewel",znxjewelid,"1"};
			paralist.add(para2);
			msgresponse.put("per30znxjewelid", znxjewelid);
		}
		if(pro>=60){
			String goodsid=gangbossawarddata.getPer60itemid();
			int goodsnum=gangbossawarddata.getPer60itemnum();
			String color=gangbossawarddata.getPer60chipcolor();
			String znxchipid=GetGoodsHelper.getRanChipAlertColor(color);
			for(int i=0;i<goodsnum;i++){
				String[] para1={gangid,"goods",goodsid,"1"};
				paralist.add(para1);
			}
			String[] para2={gangid,"chip",znxchipid,"1"};
			paralist.add(para2);
			msgresponse.put("per60znxchipid", znxchipid);
		}
		if(pro==100){
			String goodsid=gangbossawarddata.getPer100itemid();
			int goodsnum=gangbossawarddata.getPer100itemnum();
			String color=gangbossawarddata.getPer100chipcolor();
			String znxchipid=GetGoodsHelper.getRanChipAlertColor(color);
			for(int i=0;i<goodsnum;i++){
				String[] para1={gangid,"goods",goodsid,"1"};
				paralist.add(para1);
			}
			String[] para2={gangid,"chip",znxchipid,"1"};
			paralist.add(para2);
			msgresponse.put("per100znxchipid", znxchipid);
		}

		for(int i=0;i<paralist.size();i++){
			myJdbcTemplate.update(sql,paralist.get(i));
		}

		Bean.getZnx_ganggoalmap().remove(gangid);

		
		/*
		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();

		if(znx_gangdata!=null){

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

		}*/

	}
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}

}
