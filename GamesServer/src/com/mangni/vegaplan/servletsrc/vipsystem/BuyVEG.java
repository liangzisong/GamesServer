package com.mangni.vegaplan.servletsrc.vipsystem;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.MaxBuyData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyVEG implements IReceiveMessage {
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
		int buynum=100;
		int vitnum=20;
		int energynum=20;
		int goldnum=10000;
		int needstone=0;
		String playerid = (String) request.get("playerid");
		String buytype = (String) request.get("buytype");
		MaxBuyData maxbuydata=Bean.getMaxbuymap().get(String.valueOf(Bean.getZnx_playermap().get(playerid).getViplv()));
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		String res="false";
		String sqlup=null;
		switch(buytype)
		{
		case "buyvit":
			buynum = znx_playerdata.getBuyvitnum();
			if(buynum<maxbuydata.getMaxbuyvit())
			{
				needstone=Bean.getBuystonemap().get(String.valueOf(buynum+1)).getBuyvitstone();//已购买次数加一
				sqlup="UPDATE znx_player SET buyvitnum=buyvitnum+1,stone=stone-"+needstone+" where id="+playerid;			
				res=LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlup, request, true);
				if("true".equals(res)){
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", String.valueOf(vitnum));
					znx_playerdata.addBuyvitnum(1);
					FinishTaskHelper.finishEverydayTask(playerid, "4", "1");
				}
			}
			else
			{
				response.put("res", "max");
				return response;
			}
			break;
		case "buyenergy":
			buynum = znx_playerdata.getBuyenergynum();
			if(buynum<maxbuydata.getMaxbuyenergy())
			{
				needstone=Bean.getBuystonemap().get(String.valueOf(buynum+1)).getBuyenergystone();
				sqlup="UPDATE znx_player SET buyenergynum=buyenergynum+1,stone=stone-"+needstone+" where id="+playerid;
				res=LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlup, request, true);
				if("true".equals(res)){
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENERGY, "0", String.valueOf(energynum));
					znx_playerdata.addBuyenergynum(1);
				}
			}
			else
			{
				response.put("res", "max"); 
				return response;
			}
			break;
		case "buygold":
			buynum = znx_playerdata.getBuygoldnum();
			if(buynum<maxbuydata.getMaxbuygold())
			{
				needstone=Bean.getBuystonemap().get(String.valueOf(buynum+1)).getBuygoldstone();
				sqlup="UPDATE znx_player SET gold=gold+"+goldnum+",buygoldnum=buygoldnum+1,stone=stone-"+needstone+" where id="+playerid;
				res=LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlup, request, true);
				if("true".equals(res)){
					znx_playerdata.addBuygoldnum(1);
					FinishTaskHelper.finishEverydayTask(playerid, "3", "1");
				}
			}
			else
			{
				response.put("res", "max");
				return response;
			}
			break;
		case "buychallengenum":
			Map<String,Object> challengeinfo=myJdbcTemplate.queryForMap("SELECT challengenum,buychallengenum FROM player_tobattle WHERE playerid="+playerid);
			int challengenum= Integer.parseInt(String.valueOf(challengeinfo.get("challengenum")));
			if(challengenum==0)
			{
				buynum = Integer.parseInt(String.valueOf(challengeinfo.get("buychallengenum")));
				if(buynum<maxbuydata.getMaxbuychallenge())
				{
					needstone=Bean.getBuystonemap().get(String.valueOf(buynum+1)).getBuybullfightstone();
					sqlup="UPDATE player_tobattle SET challengenum=challengenum+1,buychallengenum=buychallengenum+1 WHERE playerid="+playerid;
					res=LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlup, request, true);
				}
			}
			break;
		default:
			break;
		}
		
		response.put("res", res);
		return response;
	}
}
