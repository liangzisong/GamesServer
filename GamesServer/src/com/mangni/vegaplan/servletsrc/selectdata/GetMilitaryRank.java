package com.mangni.vegaplan.servletsrc.selectdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

/**
 * 客户端发送playerid=xx
 * @author Administrator
 *
 */
public class GetMilitaryRank implements IReceiveMessage{
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
		String playerid = (String) request.get("playerid");
		
		String sqlgetself = "select militaryrankdan, militaryrank from znx_player where id="+playerid;
		Map<String,Object> military = myJdbcTemplate.queryForMap(sqlgetself);
		int militaryrankdan = Integer.parseInt(String.valueOf(military.get("militaryrankdan")));
		int militaryrank = Integer.parseInt(String.valueOf(military.get("militaryrank")));
		String sqlgettop10 = "SELECT TOP 10 id,nickname,headimageid,playerlv,viplv,sex,fightpower,militaryrankdan,militaryrank,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,SKINID1,SKINID2,SKINID3 from playerrank WHERE militaryrankdan="+militaryrankdan+" and battletype=5 and militaryrank<11 ORDER BY militaryrank ASC";
		String sqlgetup=null;
		List<Map<String,Object>> top10=null;

		//String[] top10para={"id","nickname","sex","fightpower","militaryrankdan","militaryrank","fighterposition1","fighterposition2","fighterposition3"};
		top10 = myJdbcTemplate.queryForList(sqlgettop10);
		List<String> rank=getRank(militaryrank);

		if(militaryrank>5&&militaryrank<11)
		{
			int top=militaryrank-5;
			sqlgetup="SELECT TOP "+top+" id,nickname,sex,headimageid,playerlv,viplv,fightpower,militaryrankdan,militaryrank,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,SKINID1,SKINID2,SKINID3 from playerrank WHERE militaryrankdan="+militaryrankdan+" and battletype=5 and militaryrank in(";
			for(int i=1;i<=top;i++)
			{
				sqlgetup += (militaryrank+i);
			}
			sqlgetup += ") ORDER BY militaryrank ASC";
		}
		else if(militaryrank>=11)
		{	
			sqlgetup="SELECT TOP 11 id,nickname,sex,headimageid,playerlv,viplv,fightpower,militaryrankdan,militaryrank,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,SKINID1,SKINID2,SKINID3 from playerrank WHERE militaryrankdan="+militaryrankdan+" and battletype=5 and militaryrank in("+rank.get(4)+","+rank.get(3)+","+rank.get(2)+","+rank.get(1)+","+rank.get(0)+","+militaryrank+","+(militaryrank+1)+","+(militaryrank+2)+","+(militaryrank+3)+","+(militaryrank+4)+","+(militaryrank+5)+") ORDER BY militaryrank ASC";
		}
		List<Map<String,Object>> milit=null;

		//String[] getuppara={"id","nickname","sex","fightpower","militaryrankdan","militaryrank","fighterposition1","fighterposition2","fighterposition3"};
		if(sqlgetup!=null)
			milit=myJdbcTemplate.queryForList(sqlgetup);
		response.put("res","true");
		response.put("top10",top10);
		response.put("milittop",milit);
		response.put("allrank", rank);

		return response;
	}
	public List<String> getRank(int militaryrank)
	{
		int rankspace=0;
		ArrayList<String> ranklist=new ArrayList<String>();
		for(int i=0;i<5;i++)
		{
			if(militaryrank>30000||militaryrank==0)
			{
				rankspace=3072;
			}
			else if(militaryrank>10000&&militaryrank<30001)
			{
				rankspace=1024;
			}
			else if(militaryrank>3000&&militaryrank<10001)
			{
				rankspace=198;
			}
			else if(militaryrank>1000&&militaryrank<3001)
			{
				rankspace=98;
			}
			else if(militaryrank>350&&militaryrank<1001)
			{
				rankspace=68;
			}
			else if(militaryrank>150&&militaryrank<351)
			{
				rankspace=28;
			}
			else if(militaryrank>100&&militaryrank<151)
			{
				rankspace=12;
			}
			else if(militaryrank>30&&militaryrank<101)
			{
				rankspace=6;
			}
			else if(militaryrank>15&&militaryrank<31)
			{
				rankspace=3;
			}
			else if(militaryrank>0&&militaryrank<16)
			{
				rankspace=1;
			}
			militaryrank-=rankspace;
			ranklist.add(String.valueOf(militaryrank));
		}
		return ranklist;
	}
}
