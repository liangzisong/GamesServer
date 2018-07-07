package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReadMail implements IReceiveMessage {
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
		List<Map<String,Object>> res=new ArrayList<Map<String,Object>>();
		String playerid=(String)request.get("playerid");
		String mailid=(String)request.get("mailid");

		String isread=myJdbcTemplate.queryForObject("select top 1 id from player_mail_readed where playerid="+playerid+" and mailid="+mailid,String.class);
		if(isread==null)
		{
			res=myJdbcTemplate.queryForList("select goods1type,goods1id,goods1num,goods2type,goods2id,goods2num,goods3type,goods3id,goods3num from player_mail where id="+mailid);

			String goods1type=String.valueOf(res.get(0).get("goods1type"));
			String goods1id=String.valueOf(res.get(0).get("goods1id"));
			String goods1num=String.valueOf(res.get(0).get("goods1num"));

			String goods2type=String.valueOf(res.get(0).get("goods2type"));
			String goods2id=String.valueOf(res.get(0).get("goods2id"));
			String goods2num=String.valueOf(res.get(0).get("goods2num"));

			String goods3type=String.valueOf(res.get(0).get("goods3type"));
			String goods3id=String.valueOf(res.get(0).get("goods3id"));
			String goods3num=String.valueOf(res.get(0).get("goods3num"));

			String playergoodsid1="0";
			String playergoodsid2="0";
			String playergoodsid3="0";

			if(goods1type!=null&&!goods1type.equals("0")&&!"".equals(goods1type)&&EnumGoodsTypes.isCanValue(goods1type.toUpperCase()))
				playergoodsid1=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(goods1type.toUpperCase()), goods1id, goods1num);
			if(goods2type!=null&&!goods2type.equals("0")&&!"".equals(goods2type)&&EnumGoodsTypes.isCanValue(goods2type.toUpperCase()))
				playergoodsid2=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(goods2type.toUpperCase()), goods2id, goods2num);
			if(goods3type!=null&&!goods3type.equals("0")&&!"".equals(goods3type)&&EnumGoodsTypes.isCanValue(goods3type.toUpperCase()))
				playergoodsid3=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(goods3type.toUpperCase()), goods3id, goods3num);

			myJdbcTemplate.update("insert into player_mail_readed(playerid,mailid) values("+playerid+","+mailid+")");
			response.put("res", "true");
			response.put("goodsid1", playergoodsid1);
			response.put("goodsid2", playergoodsid2);
			response.put("goodsid3", playergoodsid3);
		}else{
			response.put("res", "havereaded");
		}
		return response;
	}
}
