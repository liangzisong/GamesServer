package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 客户端发送 playerid=xx&fighterid=xx&eattype=xx&eatnum=xx,服务器返回true 或 flase 或 not enough
 * @author Administrator
 *
 */
public class UseGoods implements IReceiveMessage{
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
		String playerid=(String)request.get("playerid");
		String eattype=(String)request.get("eattype");
		String goodsid=(String)request.get("goodsid");
		int usenum=Integer.parseInt((String)request.get("eatnum"));
		
		switch(eattype){
		case "EXPPOOL":
			if(goodsid.equals("222")||goodsid.equals("223")||goodsid.equals("224"))
			{
				int havenum=myJdbcTemplate.queryForObject("select isnull(SUM(goodsnum),0) from player_bag where playerid="+playerid+" and goodsid="+goodsid,Integer.class);
				if(havenum>=usenum)
				{
					int addnum=Bean.getItemmap().get(goodsid).getAddnum()*usenum;
					myJdbcTemplate.update("update player_bag set goodsnum=goodsnum-"+usenum+" where playerid="+playerid+" and goodsid="+goodsid);
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.EXPPOOL, "0", String.valueOf(addnum));	
					response.put("res","true");
				}else
				{
					response.put("res","not enough");
				}

			}
			break;
			
		case "SKILL":
			if(goodsid.equals("225")||goodsid.equals("226")||goodsid.equals("227"))
			{
				int havenum=myJdbcTemplate.queryForObject("select isnull(SUM(goodsnum),0) from player_bag where playerid="+playerid+" and goodsid="+goodsid,Integer.class);
				if(havenum>=usenum)
				{
					int addnum=Bean.getItemmap().get(goodsid).getAddnum()*usenum;
					myJdbcTemplate.update("update player_bag set goodsnum=goodsnum-"+usenum+" where playerid="+playerid+" and goodsid="+goodsid);
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "202", String.valueOf(addnum));	
					response.put("res","true");
				}else
				{
					response.put("res","not enough");
				}

			}
			break;
			
		default:
			break;

		}
		return response;	
	}
}
