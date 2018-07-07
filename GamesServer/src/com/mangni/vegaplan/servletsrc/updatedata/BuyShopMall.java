package com.mangni.vegaplan.servletsrc.updatedata;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.StoreData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyShopMall implements IReceiveMessage {
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
		String playerid=(String) request.get("playerid");
		String buyid=(String) request.get("buyid");
		String res="false";
		StoreData storedata=Bean.getStoreshopmap().get(buyid);
		String startstr=storedata.getStartdate();
		String endstr=storedata.getEnddate();
		Date startdate = null;
		Date enddate = null;
		Date nowdate=new Date();
		try {
			startdate=Bean.getDateFormat().parse(startstr);
			enddate=Bean.getDateFormat().parse(endstr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nowdate.getTime()<startdate.getTime()||nowdate.getTime()>enddate.getTime())
			return response;
		
		int shoptype=storedata.getShoptype();
		String needgold=storedata.getNeedgold();
		int numlimit=storedata.getNumlimit();
		String buynum=myJdbcTemplate.queryForObject("select buynum from player_buyshopnum where playerid="+playerid+" and shoptype=1 and templateid="+buyid,String.class);
		if(buynum==null||Integer.parseInt(buynum)<numlimit||numlimit==0){
			switch(shoptype){
			case 1:
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", "-"+needgold);
				break;
			case 2:
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "204", "-"+needgold);
				break;
			case 3:
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.STONE, "0", "-"+needgold);
				break;
			case 4:
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "205", "-"+needgold);
				break;
			case 5:
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.STONE, "0", "-"+needgold);
				break;
			case 6:
				int uprow=myJdbcTemplate.update("update player_ganginfo contribution=contribution-"+needgold+" where playerid="+playerid);
				if(uprow==0)
					return response;
				break;
			default:
				return response;
			}
			String goodsid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(storedata.getItemtype()), storedata.getItemid(), storedata.getGoodsnum());
			
			int itemid=Integer.parseInt(storedata.getItemid());
			if(itemid>=4001&&itemid<=4013){
				FinishTaskHelper.finishEverydayTask(playerid, "5", "1");
			}
			response.put("goodsid", goodsid);
			res="true";	
		}
		response.put("res", res);
		return response;
	}
}
