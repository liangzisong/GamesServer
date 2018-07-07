package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveContributeStone implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) throws Exception {
		HashMap<String, Object> response = new HashMap<String,Object>();
		String playerid = (String) request.get("playerid");
		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		Znx_GangData gangData=Bean.getZnx_gangmap().get(playerdata.getGangid());
		
		
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.STONE, "0", "100");
		response.put("res", "true");
		return response;
	}

	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}

	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}

}
