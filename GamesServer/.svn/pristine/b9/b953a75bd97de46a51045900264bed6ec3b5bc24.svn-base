package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class MilitaryRankdanRiseAfter implements IReceiveMessage{
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

		String result=(String) request.get("result");

		Map<String,Object> rankinfo=myJdbcTemplate.queryForMap("select datediff(\"SS\",challengetime,getdate()) AS difftime,challengerank,militaryrankdan,militaryrank,maxrank,maxrankdan from znx_player where id="+playerid);
		int difftime=Integer.parseInt(String.valueOf(rankinfo.get("difftime")));
		if(difftime>100){
			response.put("res", "false");
			return response;
		}
		int challengerank=Integer.parseInt(String.valueOf(rankinfo.get("challengerank")));
		int militaryrankdan=Integer.parseInt(String.valueOf(rankinfo.get("militaryrankdan")));
		int militaryrank=Integer.parseInt(String.valueOf(rankinfo.get("militaryrank")));
		int maxrank=Integer.parseInt(String.valueOf(rankinfo.get("maxrank")));
		int maxrankdan=Integer.parseInt(String.valueOf(rankinfo.get("maxrankdan")));

		String passplayerid=myJdbcTemplate.queryForObject("select top 1 id from znx_player where militaryrankdan="+militaryrankdan+" and militaryrank="+challengerank, String.class);
		
		if("true".equals(result))
		{
			//			res=(String) myJdbcTemplate.execute(   
			//					new CallableStatementCreator() {   
			//						@Override
			//						public CallableStatement createCallableStatement(Connection con) throws SQLException {   
			//							String storedProc = "{militaryrankdanrise_After(?,?,?)}";// 调用的sql   
			//							CallableStatement cs = con.prepareCall(storedProc);   
			//							cs.setString(1, playerid);// 设置输入参数的值   
			//							cs.setString(2, playerpassrank);
			//							cs.registerOutParameter(3,Types.VARCHAR);// 注册输出参数的类型   
			//							return cs;   
			//						}   
			//					}, new CallableStatementCallback<Object>() {   
			//						@Override
			//						public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {   
			//							cs.execute();   
			//							return cs.getString(3);// 获取输出参数的值   
			//						}   
			//					});
			Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
			if(Bean.getZnx_playermap().containsKey(passplayerid)){
				Znx_PlayerData passplayerdata=Bean.getZnx_playermap().get(passplayerid);
				passplayerdata.setMaxrankdan(militaryrankdan);
				passplayerdata.setMaxrank(militaryrank);
			}
			if(maxrankdan<=militaryrankdan){
				switch(militaryrankdan){
				case 0:
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "1", "30");
					break;
				case 1:
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "4", "5");
					break;
				case 2:
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "3", "3");
					break;
				case 3:
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "2", "1");
					break;
				case 4:
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "301", "1");
					break;
				default:
					break;
				}
				playerdata.setMilitaryrankdan(militaryrankdan+1);
				playerdata.setMilitaryrank(challengerank);
				playerdata.setMaxrankdan(militaryrankdan+1);
				playerdata.setMaxrank(challengerank);
			}else if(maxrankdan>militaryrankdan+1){
				playerdata.setMilitaryrankdan(militaryrankdan+1);
				playerdata.setMilitaryrank(challengerank);
			}else if(maxrankdan==militaryrankdan+1){
				if(maxrank<challengerank){
					playerdata.setMilitaryrankdan(militaryrankdan+1);
					playerdata.setMilitaryrank(challengerank);
				}else{
					playerdata.setMilitaryrankdan(militaryrankdan+1);
					playerdata.setMilitaryrank(challengerank);
					playerdata.setMaxrank(challengerank);
				}
			}
			String sql1="update znx_player set militaryrankdan=?,militaryrank=?,challengetime=getdate()-1 where id=?";
			Object[] sql1para={militaryrankdan,militaryrank,passplayerid};
			String sql2="update znx_player set militaryrankdan=?,militaryrank=?,maxrankdan=?,maxrank=?,challengerank=0,challengetime=getdate()-1 where id=?";
			Object[] sql2para={playerdata.getMilitaryrankdan(),playerdata.getMilitaryrank(),playerdata.getMaxrankdan(),playerdata.getMaxrank(),playerid};
			myJdbcTemplate.update(sql1,sql1para);
			myJdbcTemplate.update(sql2,sql2para);
		}else{
			Object[] obj={playerid,militaryrankdan,challengerank};
			myJdbcTemplate.update("update znx_player set challengerank=0,challengetime=getdate()-1 where id=? or (militaryrankdan=? and militaryrank=?)",obj);
		}

		response.put("res","true");
		return response;
	}

}
