package com.mangni.vegaplan.servletsrc.intodata;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;


public class InsertFighter implements IReceiveMessage {
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
		String resstr;
		String playerid;
		String fighterznxid;
		playerid=(String)request.get("playerid");
		fighterznxid=(String)request.get("fighterznxid");
		if(fighterznxid.equals("1")||fighterznxid.equals("3")||fighterznxid.equals("7"))
		{
			String res="database have more than one count";
			int ishave=myJdbcTemplate.queryForObject("select count(*) from player_fighter where playerid="+playerid,Integer.class);
			if(ishave<4)
			{
				resstr=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, fighterznxid, "1");
				String countid=myJdbcTemplate.queryForObject("select count(id) from player_tobattle where playerid="+playerid,String.class);
				if(countid.equals("1"))
				{
					String sql="update player_tobattle set fighterposition1="+fighterznxid+" where playerid="+playerid;			
					myJdbcTemplate.update(sql);
					res="true";
				}
				else if(countid.equals("0"))
				{
					String sql="insert into player_tobattle(playerid,fighterposition1) values("+playerid+","+fighterznxid+")";
					myJdbcTemplate.update(sql);
					res="true";
				}
				myJdbcTemplate.update("update player_tobattle set fighterposition1="+fighterznxid+" where playerid="+playerid);
				response.put("res",res);
				response.put("data",resstr);
			}
			else
			{
				response.put("res","false");
			}
		}
		else
		{
			response.put("res","false");
		}
		return response;
	}
}
