package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.WizardData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class WizardReceive implements IReceiveMessage {
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
		String receiveid=(String)request.get("receiveid");
		String res="false";
		response.put("res", res);
		WizardData wizarddata=Bean.getWizardmap().get(receiveid);
		if(wizarddata.getTypeid().equals("100")){
			res=finishCount(playerid,wizarddata);
		}else{
			Map<String, Object> taskinfo=myJdbcTemplate.queryForMap("select isreceived,starttime,finishcount from player_holidaytask where playerid="+playerid+" and templateid="+receiveid);
			String starttime=Bean.getHolidayinfomap().get("1").getStartdate();
			if(!String.valueOf(taskinfo.get("starttime")).equals(starttime))
				return response;
			if(!String.valueOf(taskinfo.get("isreceived")).equals("0"))
				return response;
			if(Integer.parseInt(String.valueOf(taskinfo.get("finishcount")))<wizarddata.getTaskcount())
				return response;

			res=finishWizard(playerid,wizarddata);
		}
		response.put("res", res);
		return response;
	}
	private String finishWizard(String playerid,WizardData wizarddata){
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.HOLIDAYGOLD, "0", wizarddata.getRewardcurrency());
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", wizarddata.getRewardgold());
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, wizarddata.getRewarditemid(), wizarddata.getRewarditemnum());
		myJdbcTemplate.update("update player_holidaytask set isreceived=1 where playerid="+playerid+" and templateid="+wizarddata.getId());
		return "true";
	}
	private String finishCount(String playerid,WizardData wizarddata){
		String res="false";
		String datastarttime=myJdbcTemplate.queryForObject("select starttime from player_holidaytask where playerid="+playerid+" and templateid="+wizarddata.getId(),String.class);
		String starttime=Bean.getHolidayinfomap().get("1").getStartdate();
		if(datastarttime==null||!datastarttime.equals(starttime)){
			int count=myJdbcTemplate.queryForObject("select count(*) from player_holidaytask where playerid="+playerid+" and starttime='"+Bean.getHolidayinfomap().get("1").getStartdate()+"' and isreceived=1",Integer.class);
			if(count>=wizarddata.getTaskcount()){
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.HOLIDAYGOLD, "0", wizarddata.getRewardcurrency());
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", wizarddata.getRewardgold());
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, wizarddata.getRewarditemid(), wizarddata.getRewarditemnum());
				String sql=null;
				if(datastarttime==null){
					sql="insert into player_holidaytask(playerid,templateid,starttime) values("+playerid+","+wizarddata.getId()+",'"+starttime+"')";
				}else{
					sql="update player_holidaytask set starttime='"+starttime+"' where playerid="+playerid+" and templateid="+wizarddata.getId();
				}
				myJdbcTemplate.update(sql);
				res="true";
			}
		}
		return res;
	}

}
