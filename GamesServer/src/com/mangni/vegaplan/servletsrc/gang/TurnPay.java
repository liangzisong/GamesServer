package com.mangni.vegaplan.servletsrc.gang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.TurnData;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.RandomHelper;

public class TurnPay implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response = new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String chiporjewelid=(String)request.get("chiporjewelid");
		String res="false";
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		int turnpaynum=znx_playerdata.getGangturnpaynum();
		if(turnpaynum<9){
			String turnid=znx_playerdata.getGangturnid();
			if(!turnid.equals("0")){
				TurnData turndata=Bean.getTurnmap().get(turnid);
				String gangid=znx_playerdata.getGangid();	
				int awardstone=0;
				if(turnpaynum==2)
					awardstone=10;
				if(turnpaynum==5)
					awardstone=20;
				if(turnpaynum==8)
					awardstone=30;

				String newturnid=RandomHelper.getRanTurnid();
				List<String> sqllist=new ArrayList<String>();
				switch(turndata.getType()){
				case "Item":
					String goodsid=turndata.getGoodsid();
					sqllist.add("update player_bag set goodsnum=goodsnum-1 where playerid="+playerid+" and goodsid="+goodsid);			
					sqllist.add("update znx_player set stone=stone+"+awardstone+" where id="+playerid);
					break;
				case "Gem":
					String character=Bean.getJewelmap().get(chiporjewelid).getJewelcharacter();
					if(!character.equals(turndata.getJewelcharacter())){
						response.put("res", res);   
						return response;
					}
					sqllist.add("update player_jewel set num=num-1 where playerid="+playerid+" and jewelid="+chiporjewelid);					
					sqllist.add("update znx_player set stone=stone+"+awardstone+" where id="+playerid);
					break;
				case "Chip":
					int sellprice=0;
					Map<String, Object> chipinfo=myJdbcTemplate.queryForMap("SELECT chipid, lv, star FROM player_chip WHERE id="+chiporjewelid);
					if(chipinfo.size()>0)
					{
						String chipid=String.valueOf(chipinfo.get("chipid"));
						String chipcolor=Bean.getChipmap().get(chipid).getChipcolor();
						if(!turndata.getChipcolor().equals(chipcolor)){
							response.put("res", res);
							return response;
						}
						int chiplv=Integer.parseInt(String.valueOf(chipinfo.get("lv")));
						String chipstar=String.valueOf(chipinfo.get("star"));
						sellprice+=Bean.getChipmap().get(chipid).getChipprice();
						for(int j=1;j<chiplv;j++)
						{
							sellprice+=Bean.getLvlupmap().get(String.valueOf(j)).getChiplvlupgold();
						}
						String regoodsid=null;
						switch(chipcolor)
						{
						case "Red":
							regoodsid="2";
							break;
						case "Gold":
							regoodsid="3";
							break;
						case "Purple":
							regoodsid="4";
							break;
						default:
							break;
						}
						if(regoodsid!=null)
						{
							GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, regoodsid, chipstar);
						}
						sqllist.add("DELETE FROM player_chip WHERE id="+chiporjewelid);
						sqllist.add("UPDATE znx_player SET gold=gold+"+sellprice+",stone=stone+"+awardstone+" WHERE id="+playerid);
					}
				}

				Znx_GangData znx_gangdata=new Znx_GangData();
				znx_gangdata=Bean.getZnx_gangmap().get(gangid);
				int turnlv=znx_gangdata.getTurnlv();
				int addturnexp=turndata.getAddturn();
				int addcontribution=(int)(addturnexp*Bean.getTurnlvmap().get(String.valueOf(turnlv)).getAddpro());
				long[] gangmilitary=LvupHelper.CheckLvup(znx_playerdata.getGangmilitarylv(), znx_playerdata.getGangmilitaryexp()+addcontribution, Bean.getGangpostmap(), "getNeedcontribution");	
				sqllist.add("update player_ganginfo set isturned=0,refreshturnidtime=getdate(),turnid="+newturnid+",turnpaynum=turnpaynum+1,gangmilitarylv=gangmilitarylv+"+gangmilitary[0]+",gangmilitaryexp="+gangmilitary[1]+",contribution_day=contribution_day+"+addcontribution+",contribution_week=contribution_week+"+addcontribution+",contribution_month=contribution_month+"+addcontribution+",contribution=contribution+"+addcontribution+" where playerid="+playerid);

				response.putAll(LvupHelper.addGangExp(gangid, addcontribution, 1, sqllist));

				if(response.get("res").equals("true")){
					znx_playerdata.setGangturnpaynum(turnpaynum+1);
					znx_playerdata.setGangturnid(newturnid);
					znx_playerdata.setGangisturned(0);
					znx_playerdata.setGangmilitarylv((int)(znx_playerdata.getGangmilitarylv()+gangmilitary[0]));
					znx_playerdata.setGangmilitaryexp((int)gangmilitary[1]);
					response.put("newturnid", newturnid);
					response.put("militarylv", znx_playerdata.getGangmilitarylv());
					response.put("militaryexp", znx_playerdata.getGangmilitaryexp());
					response.put("addcontribution", addcontribution);
					FinishTaskHelper.finishEverydayTask(playerid, "21","1");
					FinishTaskHelper.finishHolidayTask(playerid, "27");
				}
			}
		}
		return response;
	}
}
