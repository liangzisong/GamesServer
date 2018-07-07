package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 扣除体力，客户端发送id。返回empty：体力不足；true：成功；false：失败
 */
public class VitCut implements IReceiveMessage{
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
		String vitorenergy=(String)request.get("vitorenergy");

		if(vitorenergy==null||vitorenergy.equals("vit"))
		{
			int vitcut=2;
			int vit=Bean.getZnx_playermap().get(playerid).getRealVit()[0];
			if(vit<5)
			{
				response.put("res","empty");
			}
			else
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", "-"+vitcut);
				response.put("res","true");
			} 
		}
		else
		{
			int energycut=1;
			String energyres=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENERGY, "0", "-"+energycut);
			if("-1".equals(energyres))
			{
				response.put("res","empty");
			}
			else
			{
				response.put("res","true");
			} 
		}
		return response;
	}
}
