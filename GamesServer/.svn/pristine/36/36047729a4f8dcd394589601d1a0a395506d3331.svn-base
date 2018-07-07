package com.mangni.vegaplan.servletsrc.selectdata;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

/**
 * 用于查询玩家信息，客户端发送playerid=1&tablename&cum1,cum2,cum3,服务器返回XX&XX|YY&YY
 */
public class GetChampionstop implements IReceiveMessage{
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
		String data=(String) request.get("needtop");
		if(data.equals("i need champions top"))
		{
			String gettype=(String) request.get("gettype");
			Calendar nowdate=Calendar.getInstance();
			int nowhour=nowdate.get(Calendar.HOUR_OF_DAY);
			String sqltime=null;
			if(nowhour<20)
			{
				String sqltimemax=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"00:05:00";
				nowdate.add(Calendar.DAY_OF_MONTH, -1);
				String sqltimemin=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"20:00:00";
				sqltime="(c.updatetime>'"+sqltimemin+"' and c.updatetime<'"+sqltimemax+"') and ";
			}
			else
			{
				String sqltimemin=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"20:00:00";
				sqltime="c.updatetime>'"+sqltimemin+"' and ";
			}
			
			String sql="select top 20 p.id,p.nickname,p.sex,c.*,f1.fighterid AS znxfighter1id,f2.fighterid AS znxfighter2id,f3.fighterid AS znxfighter3id from player_champions c LEFT JOIN znx_player p ON c.playerid=p.id LEFT JOIN player_fighter f1 on c.fighter1id=f1.id LEFT JOIN player_fighter f2 ON c.fighter2id=f2.id LEFT JOIN player_fighter f3 ON c.fighter3id=f3.id where "+sqltime+gettype+" order by c.battletime asc,c.sumhurt desc";
			//String[] sqlpara={"nickname","battletime","sumhurt"};
			List<Map<String,Object>> topdata=myJdbcTemplate.queryForList(sql);	
			response.put("res","true");
			response.put("data",topdata);
		}
		return response;	
	}
}