package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class MilitaryRankdanRiseBefore implements IReceiveMessage {
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
		List<Integer> check=new ArrayList<Integer>();
		int playerrank=0;
		int playerrankdan=0;
		String playerid=(String) request.get("playerid");


		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		int challengenum=playerdata.getChallengenum();
		if(challengenum>999){
			response.put("res", "false");
			return response;
		}
		playerrankdan=playerdata.getMilitaryrankdan();
		playerrank=playerdata.getMilitaryrank();
		check=checkRank(playerrankdan,playerrank);
		if(check.get(0)==1)
		{
			Random ran=new Random();
			int randrank=check.get(2)-ran.nextInt(check.get(1));
			String playerpassid=myJdbcTemplate.queryForObject("select id from znx_player where militaryrankdan="+(playerrankdan+1+" and militaryrank="+randrank),String.class);

			int challengess=myJdbcTemplate.queryForObject("SELECT min(datediff(\"SS\",challengetime,GETDATE())) FROM znx_player WHERE id="+playerpassid+" or id="+playerid,Integer.class); 
			if(challengess<100){
				response.put("res", "wait");
				return response;
			}
			if(!"0".equals(playerpassid))
				myJdbcTemplate.update("update znx_player set challengetime=getdate() where id="+playerpassid);
			myJdbcTemplate.update("update znx_player set challengenum=challengenum+1,challengetime=getdate(),challengerank="+randrank+" where id="+playerid);
			playerdata.setChallengenum(playerdata.getChallengenum()+1);

			FinishTaskHelper.finishEverydayTask(playerid, "7", "1");
			FinishTaskHelper.finishHolidayTask(playerid, "23");


			String sql="SELECT id,nickname,sex,headimageid,fightpower,militaryrankdan,militaryrank,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,skinid1,skinid2,skinid3 from playerrank WHERE id="+playerpassid+" AND battletype=5";
			//String[] sqlcum={"id","nickname","fightpower","militaryrankdan","militaryrank","fighterposition1","fighterposition2","fighterposition3"};
			List<Map<String, Object>> passplayerdata=null;
			passplayerdata=myJdbcTemplate.queryForList(sql);

			FinishTaskHelper.finishEverydayTask(playerid, "15", "1");
			FinishTaskHelper.finishHolidayTask(playerid, "23");
			response.put("res","true");
			response.put("passplayerdata", passplayerdata);
			response.put("passplayerrankdan", playerrankdan+1);
			response.put("passplayerrank", randrank);
		}else{
			response.put("res","false");
		}

		return response;
	}	
	private List<Integer> checkRank(int playerrankdan,int playerrank)
	{
		List<Integer> list=new ArrayList<Integer>();
		switch(playerrankdan)
		{
		case 0:
			if(playerrank<=150)
			{
				list.add(1);
				list.add(150);
				list.add(1150);
			}else
			{
				list.add(0);
			}
		case 1:
			if(playerrank<=100)
			{
				list.add(1);
				list.add(100);
				list.add(700);
			}else
			{
				list.add(0);
			}
		case 2:
			if(playerrank<=20)
			{
				list.add(1);
				list.add(20);
				list.add(320);
			}else
			{
				list.add(0);
			}
		case 3:
			if(playerrank<=10)
			{
				list.add(1);
				list.add(10);
				list.add(110);
			}else
			{
				list.add(0);
			}
		case 4:
			if(playerrank<=5)
			{
				list.add(1);
				list.add(5);
				list.add(20);
			}else
			{
				list.add(0);
			}
		default :
			list.add(0);
		}
		return list;
	}
}
