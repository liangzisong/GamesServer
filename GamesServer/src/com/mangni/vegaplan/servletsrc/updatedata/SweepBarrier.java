package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class SweepBarrier implements IReceiveMessage {
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
		String sweeptype = (String) request.get("sweeptype");
		String znxbarrierid=(String) request.get("znxbarrierid");
		String fighter1id=(String) request.get("fighter1id");
		String fighter2id=(String) request.get("fighter2id");
		String fighter3id=(String) request.get("fighter3id");

		int needviplv=3;
		int star=0;
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		BreakBarrier breakbarrier=(BreakBarrier) Bean.getCtx().getBean("breakbarrier");
		star=myJdbcTemplate.queryForObject("select finishstar from player_barrier where playerid="+playerid+" and barrierid="+znxbarrierid,Integer.class);
		if(sweeptype.equals("1"))
		{
			int viplv=Bean.getZnx_playermap().get(playerid).getViplv();
			needviplv=1;	
			if(viplv<needviplv)
			{	
				if(star<3)
					return response;
			}
			int vitcut=5;
			String vitres=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", "-"+vitcut);
			if(!"-1".equals(vitres))
			{		
				list.add(breakbarrier.breakbarrier(playerid, znxbarrierid, fighter1id, fighter2id, fighter3id, star));
			}
			else
			{
				response.put("res", "not enough");
				return response;
			}
		}
		else if(sweeptype.equals("10"))
		{
			int vitcut=50;
			int viplv=Bean.getZnx_playermap().get(playerid).getViplv();
			if(viplv>=needviplv)
			{
				String vitres=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", "-"+vitcut);
				if("-1".equals(vitres))
				{ 
					response.put("res", "not enough");
					return response;
				}
				
				for(int i=0;i<10;i++){
					list.add(breakbarrier.breakbarrier(playerid, znxbarrierid, fighter1id, fighter2id, fighter3id, star));
				}
			}
			else
			{
				response.put("res", "not enough");
				return response;
			}
		}
		FinishTaskHelper.finishEverydayTask(playerid, "14", sweeptype);
		response.put("res", "true");
		response.put("sweepinfo", list);
		return response;
	}
}
