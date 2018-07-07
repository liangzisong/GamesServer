package com.mangni.vegaplan.servletsrc.gang;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.TurnnumData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.RandomHelper;

public class TurnGet implements IReceiveMessage {
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

		String type=(String)request.get("type");

		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);

		if(type.equals("get")){

			String turnid=znx_playerdata.getGangturnid();

			if(turnid==null||turnid.equals("0")){

				Map<String, Object> turninfo=myJdbcTemplate.queryForMap("select turnid,datediff(dd,dateadd(hour,-3,refreshturnidtime),dateadd(hour,-3,getdate())) AS timeday from player_ganginfo with(updlock) where playerid="+playerid);

				turnid=String.valueOf(turninfo.get("turnid"));
				
				int timeday=Integer.parseInt(String.valueOf(turninfo.get("timeday")));

				if(turnid.equals("0")){

					turnid=RandomHelper.getRanTurnid();
					myJdbcTemplate.update("update player_ganginfo set isturned=1,turnid="+turnid+" ,refreshturnidtime=getdate() where playerid="+playerid);

				}else{

					if(timeday>0){

						turnid=RandomHelper.getRanTurnid();
						myJdbcTemplate.update("update player_ganginfo set isturned=1,turnid="+turnid+" ,refreshturnidtime=getdate() where playerid="+playerid);

					}

				}

				znx_playerdata.setGangturnid(turnid);
				response.put("turnid", turnid);

			}else{
				
				if(znx_playerdata.getGangisturned()==0)
					myJdbcTemplate.update("update player_ganginfo set isturned=1,refreshturnidtime=getdate() where playerid="+playerid);
				
				response.put("turnid", turnid);

			}
			
			znx_playerdata.setGangisturned(1);

		}else if(type.equals("refresh")){

			int refreshnum=znx_playerdata.getGangturnrefreshnum()+1;

			String moneytype=null;

			int needmoney=0;

			String res="false";

			if(refreshnum>Bean.getTurnnummap().size()){

				TurnnumData turnnumdata=Bean.getTurnnummap().get(String.valueOf(Bean.getTurnnummap().size()));

				moneytype=turnnumdata.getTurntype();

				needmoney=turnnumdata.getNeedmoney();

			}else{

				TurnnumData turnnumdata=Bean.getTurnnummap().get(String.valueOf(refreshnum));

				moneytype=turnnumdata.getTurntype();

				needmoney=turnnumdata.getNeedmoney();

			}

			String sql1=null;

			if(moneytype.equals("Gold")){
				sql1="update znx_player set gold=gold-"+needmoney+" where id="+playerid;
				myJdbcTemplate.update(sql1);
			}else if(moneytype.equals("Stone")){
				sql1="update znx_player set stone=stone-"+needmoney+" where id="+playerid;
				res=LvupHelper.spendStone(playerid, needmoney, "turn refresh", sql1, request, true);
			}

			if(sql1!=null){

				String turnid=RandomHelper.getRanTurnid();

				String sql2="update player_ganginfo set isturned=1,turnid="+turnid+",refreshnum=refreshnum+1 where playerid="+playerid;

				myJdbcTemplate.update(sql2);

				znx_playerdata.setGangturnrefreshnum(znx_playerdata.getGangturnrefreshnum()+1);

				znx_playerdata.setGangturnid(turnid);
				
				znx_playerdata.setGangisturned(1);

				response.put("turnid", turnid);
				
				res="true";

			}

			response.put("res", res);

		}
		return response;

	}

}
