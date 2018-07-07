package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class SiCompose implements IReceiveMessage {
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
		int goodsznxid=Integer.parseInt((String)request.get("goodsznxid"));
		String res="false";
		response.put("res", res);
		String needgoodsid=null;

		switch(goodsznxid){
		case 3:
			needgoodsid="63";
			break;
		case 4:
			needgoodsid="62";
			break;
		default:
			return response;
		}

		int havesinum=myJdbcTemplate.queryForObject("select isnull(goodsnum,0) from player_bag where playerid="+playerid+" and goodsid="+goodsznxid,Integer.class);
		int havescrollnum=myJdbcTemplate.queryForObject("select isnull(goodsnum,0) from player_bag where playerid="+playerid+" and goodsid="+needgoodsid,Integer.class);
		if(havesinum>=5&&havescrollnum>0){
			myJdbcTemplate.update("update player_bag set goodsnum=goodsnum-1 where playerid="+playerid+" and goodsid="+needgoodsid);
			myJdbcTemplate.update("update player_bag set goodsnum=goodsnum-5 where playerid="+playerid+" and goodsid="+goodsznxid);
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, String.valueOf(goodsznxid-1), "1");
			res="true";
		}
		FinishTaskHelper.finishHolidayTask(playerid, "20");
		response.put("res", res);
		return response;

	}

}
