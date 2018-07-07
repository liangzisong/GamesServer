package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.ChipData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.datatable.RankShopData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyRankShop implements IReceiveMessage {
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

		RankShopData rankshop=Bean.getRankshopmap().get(buyid);
		String znxgoodsid=rankshop.getItemid();
		String goodsnum=rankshop.getGoodsnum();
		String goodsid="0";
		int needpoints=Integer.parseInt(rankshop.getNeedpoints());
		int bullfightcup=rankshop.getBullfightcup();
		int needgold=rankshop.getNeedgold();
		int needstone=rankshop.getNeedstone("vip"+playerviplv);
		int minlist=Integer.parseInt(rankshop.getMinlist());
		int numlimit=rankshop.getNumlimit();
		String havecup=SqlHelper.getOneRead("SELECT GOODSNUM FROM PLAYER_BAG WHERE PLAYERID="+playerid+" AND GOODSID=52");
		if(havecup!=null&&Integer.parseInt(havecup)>=bullfightcup)
		{
			String buynum=SqlHelper.getOneRead("select buynum from player_buyshopnum where playerid="+playerid+" and shoptype=2 and templateid="+buyid);
			if(buynum==null||Integer.parseInt(buynum)<numlimit)
			{
				List<String> havemoney=SqlHelper.getMyData("select gold, stone from znx_player where id="+playerid);

				if(Integer.parseInt(havemoney.get(0))>=needgold&&Integer.parseInt(havemoney.get(1))>=needstone)
				{
					List<String> limited=SqlHelper.getMyData("select militarypoints,militaryrankdan from player_tobattle where playerid="+playerid);
					if(Integer.parseInt(limited.get(0))>=needpoints&&Integer.parseInt(limited.get(1))>=minlist)
					{
						String buytype=rankshop.getItemtype();
						if(buytype.equals("Item"))
						{
							String [] sqlpara={playerid,znxgoodsid,goodsnum};
							SqlHelper.DbExecute("insert_player_bag(?,?,?)",sqlpara);
						}
						else if(buytype.equals("Gem"))
						{
							if(znxgoodsid.equals("0"))
							{
								List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter",rankshop.getJewelcharacter());
								HashMap<String,JewelData> screenmap=new HashMap<String,JewelData>();
								for(String str2:screenlist)
								{
									screenmap.put(str2, Bean.getJewelmap().get(str2));
								}
								List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlterJewelcolor",rankshop.getColor());
								Random random = new Random();
								znxgoodsid = finallist.get(random.nextInt(finallist.size()));
							}
							String [] sqlpara={playerid,znxgoodsid,goodsnum};
							goodsid=SqlHelper.DbExecute("insert_player_jewel(?,?,?,?)",sqlpara,true);
						}
						else if(buytype.equals("Chip"))
						{
							if(znxgoodsid.equals("0"))
							{
								List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertChiptype",rankshop.getChiptype());
								HashMap<String,ChipData> screenmap=new HashMap<String,ChipData>();
								for(String str2:screenlist)
								{
									screenmap.put(str2, Bean.getChipmap().get(str2));
								}
								List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlertColor",rankshop.getColor());
								Random random = new Random();
								znxgoodsid = finallist.get(random.nextInt(finallist.size()));
							}

							String [] sqlpara={playerid,znxgoodsid};
							goodsid=SqlHelper.DbExecute("insert_player_chip(?,?,?)",sqlpara,true);

						}
						else if(buytype.equals("Vit"))
						{
							GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", goodsnum);
						}
						else if(buytype.equals("Energy"))
						{
							GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENERGY, "0", goodsnum);
						}

						String sqlstr="update znx_player set gold=gold-"+needgold+",stone=stone-"+needstone+" where id="+playerid;
						LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlstr, request, false);
						SqlHelper.Updatedb("UPDATE PLAYER_BAG SET GOODSNUM=GOODSNUM-"+bullfightcup+" WHERE PLAYERID="+playerid+" AND GOODSID=52");
						SqlHelper.Updatedb("update player_tobattle set militarypoints=militarypoints-"+needpoints+" where playerid="+playerid);
						String [] numsqlpara={playerid,"2",buyid,"1"};
						SqlHelper.DbExecute("update_player_buyshopnum(?,?,?,?)",numsqlpara);

						FinishTaskHelper.finishEverydayTask(playerid, "22","1");
						response.put("res","true");
						response.put("znxgoodsid", znxgoodsid);
						response.put("goodsid", goodsid);
					}else{
						response.put("res","not enough");
					}
				}else{
					response.put("res","not enough");
				}
			}else{
				response.put("res", "exceed num");
			}
		}else{
			response.put("res", "not enough");
		}
		return response;
	}
}
