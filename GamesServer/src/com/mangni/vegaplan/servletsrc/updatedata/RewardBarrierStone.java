package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.BarrierRewardData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class RewardBarrierStone implements IReceiveMessage{
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
		String rewardid=(String)request.get("rewardid");
		
		String check=myJdbcTemplate.queryForObject("select top 1 id from player_barrier_reward where playerid="+playerid+" and barrierrewardid="+rewardid, String.class);
		if(check!=null){
			response.put("res", "isreveived");
			return response;
		}
		
		BarrierRewardData barrierrewarddata=Bean.getBarrierrewardmap().get(rewardid);
		Integer havestar=myJdbcTemplate.queryForObject("select sum(finishstar) from player_barrier where playerid="+playerid+" and barrierid between "
				+barrierrewarddata.getStartbarrierid()+" and "+barrierrewarddata.getEndbarrierid(), Integer.class);
		
		if(havestar<barrierrewarddata.getNeedstar()){
			response.put("res", "not enough");
			return response;
		}
		
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.STONE, "0", barrierrewarddata.getStone());
		myJdbcTemplate.update("insert into player_barrier_reward(playerid,barrierrewardid) values(?,?)",new Object[]{playerid,rewardid});
		response.put("res", "true");
		return response;
	}

}
