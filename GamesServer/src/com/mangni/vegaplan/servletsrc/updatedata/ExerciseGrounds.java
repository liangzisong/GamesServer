package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Random;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ExerciseGrounds implements IReceiveMessage {
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
		String playerid=(String) request.get("playerid");
		String egtype=(String) request.get("egtype");
		String fighter1id=(String) request.get("fighter1id");
		String fighter2id=(String) request.get("fighter2id");
		String fighter3id=(String) request.get("fighter3id");
		String abtype=(String) request.get("abtype");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		int enerycut=0;
		int ratio=1;
		
		if(egtype.equals("1"))
		{
			enerycut=2;
			ratio=2;		
		}
		else if(egtype.equals("2"))
		{
			enerycut=5;
			ratio=5;			
		}
		else
		{
			response.put("res", "false");
			return response;
		}
		if(abtype.equals("before"))
		{
			int haveenergy=myJdbcTemplate.queryForObject("select energy from znx_player where id="+playerid,Integer.class);
			if(haveenergy>=enerycut)
			{
				int expreward=Bean.getLvmap().get(String.valueOf(znx_playerdata.getPlayerlv())).getFightexpreward()*ratio;
				myJdbcTemplate.update("update znx_player set energy=energy-"+enerycut+" where id="+playerid);
				String [] fighterpara={fighter1id,fighter2id,fighter3id};
				LvupHelper.UpPlayerExp(playerid, expreward);
				LvupHelper.UpFighterExp(fighterpara, expreward);
				response.put("res", "true");
			}
			else
			{
				response.put("res", "not enough");
			}
		}
		else if(abtype.equals("after"))
		{
			int goldreward=Bean.getLvlupmap().get(String.valueOf(znx_playerdata.getPlayerlv())).getFightgoldreward()*ratio;
			myJdbcTemplate.update("update znx_player set gold=gold+"+goldreward+" where id="+playerid);
			Random random=new Random();
			int skinran=random.nextInt(2);
			if(egtype.equals("1"))
			{
				skinran+=1;
				FinishTaskHelper.finishEverydayTask(playerid, "4", "1");
				FinishTaskHelper.finishHolidayTask(playerid, "12");
			}else{
				skinran+=4;
				FinishTaskHelper.finishEverydayTask(playerid, "8", "1");
				FinishTaskHelper.finishHolidayTask(playerid, "13");
			}
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "50", String.valueOf(skinran));
			response.put("res", "true");
			response.put("skinnum", skinran);
		}
		else if("sweep".equals(abtype))
		{
			int needstone=0;
			if("1".equals(request.get("egtype")))
			{
				needstone=2;
			}
			else
			{
				needstone=5;
			}
			int havestone=myJdbcTemplate.queryForObject("select stone from znx_player where id="+playerid,Integer.class);
			if(havestone<needstone){
				response.put("res", "not enough");
				return response;
			}
			request.put("abtype", "before");
			response=dopost(request);
			if("true".equals(response.get("res")))
			{			
				String sql="update znx_player set stone=stone-"+needstone+" where id="+playerid;
				String res=LvupHelper.spendStone(playerid, needstone, "sweepgrounds", sql, request, false);
				if("true".equals(res)){
					request.put("abtype", "after");
					response=dopost(request);
					return response;
				}
			}
			else
			{
				return response;
			}
		}
		else
		{
			response.put("res", "false");
		}
		return response;
	}
}
