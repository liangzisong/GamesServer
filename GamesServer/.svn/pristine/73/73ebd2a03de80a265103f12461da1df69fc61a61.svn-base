package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 更新上阵信息客户端发送playerid=xx&tablename&cum1=value1,cum2=value2... 
 * 服务器返回true或false
 */
public class UpTobattle implements IReceiveMessage{
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
		String countid;
		String playerid=(String)request.get("playerid");
		String battletype=(String)request.get("battletype");
		if(!("1".equals(battletype)||"2".equals(battletype)||"3".equals(battletype)||"4".equals(battletype)||"5".equals(battletype)||"6".equals(battletype)||"0".equals(playerid)))
			return response;
		
		String fighterposition1=(String)request.get("fighterposition1");
		String fighterposition2=(String)request.get("fighterposition2");
		String fighterposition3=(String)request.get("fighterposition3");

		ArrayList<String> fighterlist=new ArrayList<String>();
		ArrayList<String> fighterlist2=new ArrayList<String>();
		if(!fighterposition1.equals("0"))
		{
			fighterlist.add(fighterposition1);
			fighterlist2.add(fighterposition1);
		}
		if(!fighterposition2.equals("0"))
		{
			fighterlist.add(fighterposition2);
			if(!fighterlist2.contains(fighterposition2))
				fighterlist2.add(fighterposition2);
		}
		if(!fighterposition3.equals("0"))
		{
			fighterlist.add(fighterposition3);
			if(!fighterlist2.contains(fighterposition3))
				fighterlist2.add(fighterposition3);
		}
		if(fighterlist.size()==fighterlist2.size())
		{
			String sqlishavefighter="select count(playerid) from player_newfighter where playerid="+playerid+" and fighterid in(";
			int flistsize=fighterlist.size();
			for(int i=0;i<flistsize;i++)
			{
				sqlishavefighter+=fighterlist.get(i);
				if(i!=flistsize-1){
					sqlishavefighter+=",";
				}else{
					sqlishavefighter+=")";
				}		
			}
			int ishavefighter=flistsize;
			if(fighterlist.size()>0){
				ishavefighter=myJdbcTemplate.queryForObject(sqlishavefighter,Integer.class);
			}
			if(ishavefighter==flistsize)
			{
				countid=myJdbcTemplate.queryForObject("select count(id) from player_tobattle where playerid="+playerid+" and battletype="+battletype,String.class);
				if(countid.equals("1"))
				{
					//String sql="update player_tobattle set fighterposition1=?,fighterposition2=?,fighterposition3=?,fighter1=?,fighter2=?,fighter3=?,soldier1=?,soldier2=?,soldier3=?,soldier4=?,=soldier5=?,uptime=getdate()";	
					String sql="update player_tobattle set fightpower="+(String)request.get("fightpower")+",fighterposition1="+fighterposition1+",fighterposition2="+fighterposition2+",fighterposition3="+fighterposition3+",fighter1="+(String)request.get("fighter1")+",fighter2="+(String)request.get("fighter2")+",fighter3="+(String)request.get("fighter3")+",soldier1="+(String)request.get("soldier1")+",soldier2="+(String)request.get("soldier2")+",soldier3="+(String)request.get("soldier3")+",soldier4="+(String)request.get("soldier4")+",soldier5="+(String)request.get("soldier5")+",uptime=getdate() where playerid="+playerid+" and battletype="+battletype;
					//String [] cum={datamap.get("fighterposition1"),datamap.get("fighterposition2"),datamap.get("fighterposition3"),datamap.get("fighter1"),datamap.get("fighter2"),datamap.get("fighter3"),datamap.get("soldier1"),datamap.get("soldier2"),datamap.get("soldier3"),datamap.get("soldier4"),datamap.get("soldier5")};
					//SqlHelper.Updatedb(sql,cum);
					myJdbcTemplate.update(sql);
					response.put("res","true");
				}
				else if(countid.equals("0"))
				{
					String sql="insert into player_tobattle(playerid,battletype,fightpower,fighterposition1,fighterposition2,fighterposition3,fighter1,fighter2,fighter3,soldier1,soldier2,soldier3,soldier4,soldier5) values("+playerid+",?,?,?,?,?,?,?,?,?,?,?,?,?)";
					Object [] cum={battletype,(String)request.get("fightpower"),(String)request.get("fighterposition1"),(String)request.get("fighterposition2"),(String)request.get("fighterposition3"),(String)request.get("fighter1"),(String)request.get("fighter2"),(String)request.get("fighter3"),(String)request.get("soldier1"),(String)request.get("soldier2"),(String)request.get("soldier3"),(String)request.get("soldier4"),(String)request.get("soldier5")};
					myJdbcTemplate.update(sql,cum);
					response.put("res","true");
				}
			}
			else
			{
				response.put("res","nothavefighter");
			}
		}else{
			response.put("res","retobattle");
		}
		return response;
	}
}
