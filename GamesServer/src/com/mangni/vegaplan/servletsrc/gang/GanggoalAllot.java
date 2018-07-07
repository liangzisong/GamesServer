package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class GanggoalAllot implements IReceiveMessage {
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
		String allotid=(String)request.get("allotid");
		String toplayerid=(String)request.get("toplayerid");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		response.put("res", "false");
		if(znx_playerdata.getGangduties()!=1)
			return response;
		
		List<Map<String, Object>> goodsinfo=myJdbcTemplate.queryForList("select * from player_gang_goalaward with(updlock) where id="+allotid);
		if(goodsinfo==null||goodsinfo.size()==0)
			return response;
		
		if(!goodsinfo.get(0).get("gangid").equals(znx_playerdata.getGangid()))
			return response;
		
		if(goodsinfo.get(0).get("toplayerid")!=null)
			return response;
		
		String havecount=myJdbcTemplate.queryForObject("select top 1 id from player_gang_goalaward where gettime>dateadd(HH,-18,getdate()) and toplayerid="+toplayerid,String.class);
		if(havecount!=null){
			response.put("res", "timelimit");
			return response;
		}
		
		String goodstype=String.valueOf(goodsinfo.get(0).get("goodstype"));
		String goodsid=String.valueOf(goodsinfo.get(0).get("goodsid"));
		String goodsnum=String.valueOf(goodsinfo.get(0).get("goodsnum"));
		
		String sql1="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM,MAILCONTENT) VALUES(?,?,?,?,?,?,?)";
		String[] sqlpara1={toplayerid,"8","雷霆出击奖励分配",goodstype,goodsid,goodsnum,"鉴于你在雷霆出击中英勇善战，特此奖励！"};
		
		String sql2="UPDATE PLAYER_GANG_GOALAWARD SET TOPLAYERID=?,gettime=getdate() WHERE ID=?";
		Object[] sqlpara2={toplayerid,allotid};
				
		myJdbcTemplate.update(sql2, sqlpara2);
		SendMessageHelper.sendMail(sql1, sqlpara1, sqlpara1[0]);
		response.put("res", "true");
		return response;
	}

}
