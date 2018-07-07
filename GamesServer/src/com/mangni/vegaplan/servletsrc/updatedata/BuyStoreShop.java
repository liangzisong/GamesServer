package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.ChipData;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.datatable.StoreData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyStoreShop implements IReceiveMessage {
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
		String buyid=(String) request.get("buyid");
		int playerviplv=Bean.getZnx_playermap().get(playerid).getViplv();

		StoreData storeshop=Bean.getStoreshopmap().get(buyid);
		String znxgoodsid=storeshop.getItemid();
		String goodsnum=storeshop.getGoodsnum();
		String goodsid="0";

		int needgold=storeshop.getNeedgold();
		int needstone=storeshop.getNeedstone("vip"+playerviplv);
		int numlimit=storeshop.getNumlimit();
		int getgold=storeshop.getGetgold();
		int getexp=storeshop.getGetexp();

		String buynum=SqlHelper.getOneRead("select buynum from player_buyshopnum where playerid="+playerid+" and shoptype=1 and templateid="+buyid);
		if(buynum==null||Integer.parseInt(buynum)<numlimit||numlimit==0)
		{
			List<String> havemoney=SqlHelper.getMyData("select gold, stone from znx_player where id="+playerid);

			if(Long.parseLong(havemoney.get(0))>=needgold&&Long.parseLong(havemoney.get(1))>=needstone)
			{
				String buytype=storeshop.getItemtype();
				if(buytype.equals("Item"))
				{
					String [] sqlpara={playerid,znxgoodsid,goodsnum};
					SqlHelper.DbExecute("insert_player_bag(?,?,?)",sqlpara);
				}
				else if(buytype.equals("Gem"))
				{
					if(znxgoodsid.equals("0"))
					{
						List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter",storeshop.getJewelcharacter());
						HashMap<String,JewelData> screenmap=new HashMap<String,JewelData>();
						for(String str2:screenlist)
						{
							screenmap.put(str2, Bean.getJewelmap().get(str2));
						}
						List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlterJewelcolor",storeshop.getColor());
						Random random = new Random();
						znxgoodsid = finallist.get(random.nextInt(finallist.size()));
					}
					String [] sqlpara={playerid,znxgoodsid,"1"};
					goodsid=SqlHelper.DbExecute("insert_player_jewel(?,?,?,?)",sqlpara,true);
				}
				else if(buytype.equals("Chip"))
				{
					if(znxgoodsid.equals("0"))
					{
						List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertChiptype",storeshop.getChiptype());
						HashMap<String,ChipData> screenmap=new HashMap<String,ChipData>();
						for(String str2:screenlist)
						{
							screenmap.put(str2, Bean.getChipmap().get(str2));
						}
						List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlertColor",storeshop.getColor());
						Random random = new Random();
						znxgoodsid = finallist.get(random.nextInt(finallist.size()));
					}

					String [] sqlpara={playerid,znxgoodsid};
					goodsid=SqlHelper.DbExecute("insert_player_chip(?,?,?)",sqlpara,true);

				}

				String sqlstr="update znx_player set gold=gold-"+needgold+"+"+getgold+",stone=stone-"+needstone+" where id="+playerid;
				LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlstr, request, false);
				LvupHelper.UpPlayerExp(playerid, getexp);
				String [] numsqlpara={playerid,"1",buyid,"1"};
				SqlHelper.DbExecute("update_player_buyshopnum(?,?,?,?)",numsqlpara);

				if(buyid.equals("1"))
					FinishTaskHelper.finishHolidayTask(playerid, "29");
				if(buyid.equals("2"))
					FinishTaskHelper.finishHolidayTask(playerid, "30");
				if(buyid.equals("3"))
					FinishTaskHelper.finishHolidayTask(playerid, "31");
				
				FinishTaskHelper.finishEverydayTask(playerid, "35","1");
				
				response.put("res","true");
				response.put("znxgoodsid", znxgoodsid);
				response.put("goodsid", goodsid);
			}
		}else{
			response.put("res", "exceed num");
		}
		return response;
	}
}
