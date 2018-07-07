package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.ChipData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.GangShopData;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyGangShop implements IReceiveMessage {
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
		String playerid=(String)request.get("playerid");
		String buyid=(String) request.get("buyid");
		int playerviplv=Bean.getZnx_playermap().get(playerid).getViplv();

		GangShopData gangshop=Bean.getGangshopmap().get(buyid);
		String znxgoodsid=gangshop.getGoodsid();
		String goodsnum=gangshop.getGoodsnum();
		String goodsid="0";
		int needturn=gangshop.getNeedturn();
		int needgold=gangshop.getNeedgold();
		int needstone=gangshop.getNeedstone("vip"+playerviplv);
		int minpost=gangshop.getMinarmypost();
		int numlimit=gangshop.getExchangenum();

		String buynum=myJdbcTemplate.queryForObject("select buynum from player_buyshopnum where playerid="+playerid+" and shoptype=4 and templateid="+buyid,String.class);
		if(buynum==null||Integer.parseInt(buynum)<numlimit)
		{
			Map<String, Object> havemoney=myJdbcTemplate.queryForMap("select gold, stone from znx_player where id="+playerid);

			if(Long.parseLong(String.valueOf(havemoney.get("gold")))>=needgold&&Long.parseLong(String.valueOf(havemoney.get("stone")))>=needstone)
			{
				Map<String, Object> limited=myJdbcTemplate.queryForMap("select contribution,gangmilitarylv from player_ganginfo where playerid="+playerid);
				if(Integer.parseInt(String.valueOf(limited.get("contribution")))>=needturn&&Integer.parseInt(String.valueOf(limited.get("gangmilitarylv")))>=minpost)
				{
					String buytype=gangshop.getGoodstype();
					if(buytype.equals("Item"))
					{
						GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, znxgoodsid, goodsnum);
					}
					else if(buytype.equals("Gem"))
					{
						if(znxgoodsid.equals("0"))
						{
							List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter",gangshop.getJewelcharacter());
							HashMap<String,JewelData> screenmap=new HashMap<String,JewelData>();
							for(String str2:screenlist)
							{
								screenmap.put(str2, Bean.getJewelmap().get(str2));
							}
							List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlterJewelcolor",gangshop.getColor());
							Random random = new Random();
							znxgoodsid = finallist.get(random.nextInt(finallist.size()));
						}
						goodsid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.JEWEL, znxgoodsid, goodsnum);
					}
					else if(buytype.equals("Chip"))
					{
						if(znxgoodsid.equals("0"))
						{
							List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertChiptype",gangshop.getChiptype());
							HashMap<String,ChipData> screenmap=new HashMap<String,ChipData>();
							for(String str2:screenlist)
							{
								screenmap.put(str2, Bean.getChipmap().get(str2));
							}
							List<String> finallist=HashMapHelper.getKeyList(screenmap,"getIdAlertColor",gangshop.getColor());
							Random random = new Random();
							znxgoodsid = finallist.get(random.nextInt(finallist.size()));
						}
						goodsid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CHIP, znxgoodsid, "1");

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
					myJdbcTemplate.update("update player_ganginfo set contribution=contribution-"+needturn+" where playerid="+playerid);
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.BUYSHOPNUM, "4,"+buyid, "1");

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

		return response;
	}

}
