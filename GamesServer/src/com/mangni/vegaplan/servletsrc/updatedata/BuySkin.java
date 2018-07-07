package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.SkinData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuySkin implements IReceiveMessage {
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
		String skinid=(String)request.get("skinid");
		String buytype=(String)request.get("buytype");
		response.put("res", "false");
		SkinData skindata=Bean.getSkinmap().get(skinid);
		String skindataid=null;
		if(skindata==null)
			return response;
		
		if(!skindata.getType().equals("2"))
			return response;
		
		if(("day").equals(buytype)){
			int needticket=skindata.getDayticketnum();
			int haveticket=Integer.parseInt(SqlHelper.getOneRead("select goodsnum from player_bag where playerid="+playerid+" and goodsid=50"));
			if(needticket>haveticket)
				return response;
			
			SqlHelper.Updatedb("update player_bag set goodsnum=goodsnum-"+needticket+" where playerid="+playerid+" and goodsid=50");
			skindataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.SKIN, skinid, "1");
			
		}else if(("week").equals(buytype)){
			int needticket=skindata.getWeekticketnum();
			int haveticket=Integer.parseInt(SqlHelper.getOneRead("select goodsnum from player_bag where playerid="+playerid+" and goodsid=50"));
			if(needticket>haveticket)
				return response;
			
			SqlHelper.Updatedb("update player_bag set goodsnum=goodsnum-"+needticket+" where playerid="+playerid+" and goodsid=50");
			skindataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.SKIN, skinid, "7");
		}else{
			int needstone=skindata.getNeedstone();
			int havestone=Integer.parseInt(SqlHelper.getOneRead("select stone from znx_player where id="+playerid));
			if(needstone>havestone)
				return response;
			
			String sql="update znx_player set stone=stone-"+needstone+" where id="+playerid;
			LvupHelper.spendStone(playerid, needstone, "buyskin", sql, request, false);
			skindataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.SKIN, skinid, "0");
			
		}
		response.put("res", "true");
		response.put("skindataid", skindataid);
		return response;
		
	}
	
}
