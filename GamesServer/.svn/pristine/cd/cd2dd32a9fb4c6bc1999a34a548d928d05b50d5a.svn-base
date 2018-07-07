package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.TimeHelper;

public class GangContribute implements IReceiveMessage {
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
		String playerid = (String)request.get("playerid");
		String contributebuild = (String)request.get("contributebuild");//Basecamp||Commissariat||Researchcenter||Flames
		String type = (String)request.get("type");
		String res;
		Znx_PlayerData playerdata=Bean.getZnx_playermap().get("playerid");
		String contributetime=playerdata.getGangcontributenumtime();
		int contirbutenum=playerdata.getGangcontributenum();
		try {
			Date contributedate=Bean.getDateFormat().parse(contributetime);
			Calendar contributec=Calendar.getInstance();
			Calendar nowc=contributec;
			contributec.setTime(contributedate);
			if(!TimeHelper.isAfterThree(contributec, nowc))
				contirbutenum=0;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(contirbutenum>=10){
			response.put("res", "false");
			return response;
		}
		Znx_GangData gangdata=Bean.getZnx_gangmap().get(playerdata.getGangid());
		int centerlv=gangdata.getResearchcenterlv();
		if("gold".equals(type)){
			res=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", "-10000");
			if("1".equals(res)){
				LvupHelper.UpContributeExp(playerid, 20, contributebuild);
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CONTRIBUTION, "0", String.valueOf(50*centerlv*0.02));
				response.put("res", "true");
			}else{
				response.put("res", "false");
			}
		}else{
			res=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.STONE, "GangContribute", "-100");
			if("1".equals(res)){
				LvupHelper.UpContributeExp(playerid, 20, contributebuild);
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CONTRIBUTION, "0", String.valueOf(100*centerlv*0.02));
				response.put("res", "true");
			}else{
				response.put("res", "false");
			}
		}
		
		
		return response;
	}

}
