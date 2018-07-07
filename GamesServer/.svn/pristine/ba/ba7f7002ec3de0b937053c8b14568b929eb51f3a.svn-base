package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.BarrierData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

@Controller
public class BreakBarrier implements IReceiveMessage {
	@Autowired
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
		String znxbarrierid=(String) request.get("znxbarrierid");
		int star=Integer.parseInt((String) request.get("star"));
		String fighter1id=(String) request.get("fighter1id");
		String fighter2id=(String) request.get("fighter2id");
		String fighter3id=(String) request.get("fighter3id");
		String vitres=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", "-3");
		if("-1".equals(vitres)){
			response.put("res", "not enough");
			return response;
		}
		response=breakbarrier(playerid,znxbarrierid,fighter1id,fighter2id,fighter3id,star);
		return response;
	}

	public HashMap<String,Object> breakbarrier(String playerid,String znxbarrierid,String fighter1id,String fighter2id,String fighter3id,int star){
		HashMap<String,Object> response=new HashMap<String,Object>();
		Random random=new Random();
		BarrierData barrier=Bean.getBarriermap().get(znxbarrierid);

		if(star>0)
		{
			String barrres=myJdbcTemplate.queryForObject("select finishstar from player_barrier where playerid="+playerid+" and barrierid="+znxbarrierid,String.class);
			if(barrres==null||Integer.parseInt(barrres)<star){
				int count=myJdbcTemplate.queryForObject("select count(id) from player_barrier where playerid="+playerid+" and barrierid="+znxbarrierid, Integer.class);
				if(count==1){
					Object[] sqlpara={star,playerid,znxbarrierid,star};
					myJdbcTemplate.update("UPDATE player_barrier SET finishstar=? WHERE playerid=? AND barrierid=? AND finishstar<?",sqlpara);
				}else if(count==0){
					Object[] sqlpara={playerid,znxbarrierid,star};
					myJdbcTemplate.update("INSERT INTO player_barrier(playerid,barrierid,finishstar) VALUES(?,?,?)",sqlpara);
					if(!"0".equals(barrier.getFirstpassrewardid())){
						GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, barrier.getFirstpassrewardid(), barrier.getFirstpassrewardnum());
					}
				}else{
					Object[] sqlpara={playerid,znxbarrierid};
					myJdbcTemplate.update("DELETE FROM player_barrier WHERE playerid=? AND barrierid=?",sqlpara);
					Object[] sqlpara2={playerid,znxbarrierid,star};
					myJdbcTemplate.update("INSERT INTO player_barrier(playerid,barrierid,finishstar) VALUES(?,?,?)",sqlpara2);
				}

			}
			String awardgold=barrier.getStar1gold();
			int awardplayerexp=Integer.parseInt(barrier.getAddplayerexp());
			int awardmechexp=Integer.parseInt(barrier.getAddmechexp());
			int ranodds=random.nextInt(100);
			if(ranodds<Double.valueOf(barrier.getProbability())*100){
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, barrier.getMaterial(), "1");
				response.put("material",barrier.getMaterial());
			}

			if(star==2){
				awardgold=barrier.getStar2gold();
			}
			if(star==3){
				awardgold=barrier.getStar3gold();
			}
			String [] fighterpara={fighter1id,fighter2id,fighter3id};
			LvupHelper.UpPlayerExp(playerid, awardplayerexp);
			LvupHelper.UpFighterExp(fighterpara, awardmechexp);
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", awardgold);
			FinishTaskHelper.finishEverydayTask(playerid, "14", "1");
			FinishTaskHelper.finishHolidayTask(playerid, "11");
			response.put("res","true");
			response.put("awardgold", awardgold);
			response.put("awardplayerexp", awardplayerexp);
			response.put("awardmechexp", awardmechexp);
		}
		return response;
	}
}
