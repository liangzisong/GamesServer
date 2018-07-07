package com.mangni.vegaplan.servletsrc.vipsystem;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.VipData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;


public class ReceiveVipAward implements IReceiveMessage{
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
		HashMap<String,Object> response = new HashMap<String,Object>();;
		String playerid=(String) request.get("playerid");
		int receivetype=Integer.parseInt((String) request.get("receivetype"));
		Znx_PlayerData znxplayerdata=Bean.getZnx_playermap().get(playerid);
		int viplv=znxplayerdata.getViplv();

		if(receivetype<=viplv)
		{
			String vipreceive=znxplayerdata.getVipreceive();
			if(vipreceive.charAt(receivetype-1)=='0')
			{
				StringBuilder newreceive=new StringBuilder();
				for (int i=0;i<vipreceive.length();i++)
				{
					if(i!=receivetype-1)
					{
						newreceive.append(vipreceive.charAt(i));
					}
					else
					{
						newreceive.append('1');
					}
				}

				VipData vipdata=Bean.getVipmap().get(String.valueOf(receivetype));

				
				String res1=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate1type()), vipdata.getRewardtemplate1id(), vipdata.getRewardtemplate1num());
				String res2=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate2type()), vipdata.getRewardtemplate2id(), vipdata.getRewardtemplate2num());
				String res3=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate3type()), vipdata.getRewardtemplate3id(), vipdata.getRewardtemplate3num());
				String res4=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate4type()), vipdata.getRewardtemplate4id(), vipdata.getRewardtemplate4num());	
				String res5=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate5type()), vipdata.getRewardtemplate5id(), vipdata.getRewardtemplate5num());
				String res6=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(vipdata.getRewardtemplate6type()), vipdata.getRewardtemplate6id(), vipdata.getRewardtemplate6num());	
				String sqlup="update znx_player set vipreceive='"+newreceive+"' where id="+playerid;
				myJdbcTemplate.update(sqlup);
				Bean.getZnx_playermap().get(playerid).setVipreceive(newreceive.toString());
				response.put("res1", res1);
				response.put("res2", res2);
				response.put("res3", res3);
				response.put("res4", res4);
				response.put("res5", res5);
				response.put("res6", res6);
			}
		}
		return response;
	}
	
}
