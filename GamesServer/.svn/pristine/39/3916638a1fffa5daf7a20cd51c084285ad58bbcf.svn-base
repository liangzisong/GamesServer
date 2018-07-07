package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.FighterData;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FighterUpstar implements IReceiveMessage {
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
		String fighterid=(String)request.get("fighterid");
		String fighterpieces=(String)request.get("fighterpieces");//使用的专有英雄碎片的数量
		String currencypieces=(String)request.get("currencypieces");//使用的万能英雄碎片的数量
		Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
		int fighterstar=znx_fighterdata.getFighterstar();
		int star=fighterstar/9+1;
		int needpieces=Bean.getLvtablemap().get(String.valueOf(star)).getChip();
		//判断是否满级
		if(needpieces==0){
			response.put("res", "false");
			return response;
		}
		//判断所需碎片数是否正确
		if(needpieces!=(Integer.parseInt(fighterpieces))+Integer.parseInt(currencypieces)){
			response.put("res", "false");
			return response;
		}
		
		FighterData fighterdata=Bean.getFightermap().get(znx_fighterdata.getFighterid());
		String sql1="update player_bag set goodsnum=goodsnum-"+fighterpieces+" where playerid="+playerid+" and goodsid="+fighterdata.getId();
		if(!currencypieces.equals("0")){
			String sql2="update player_bag set goodsnum=goodsnum-"+currencypieces+" where playerid="+playerid+" and goodsid=203";
			myJdbcTemplate.update(sql2);
		}
		String sql3="update player_newfighter set star=star+1 where id="+fighterid;
		myJdbcTemplate.update(sql1);
		myJdbcTemplate.update(sql3);
		znx_fighterdata.LvlupFighterstar(1);
		FinishTaskHelper.finishEverydayTask(playerid, "9",String.valueOf(needpieces));
		response.put("res", "true");
		return response;
	}

}
