package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.JjfyData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveJjfy implements IReceiveMessage {
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
		
		int jjfyid=Integer.parseInt((String)request.get("jjfyid"));
		
		String res="false";
		
		String jjfyreceive=myJdbcTemplate.queryForObject("SELECT JJFYRECEIVE FROM ZNX_PLAYER WHERE ID="+playerid,String.class);
		
		if(jjfyreceive.charAt(jjfyid-1)=='1'){
			
			response.put("res", res);
			
			return response;
			
		}
		
		StringBuilder newreceive=new StringBuilder();
		for (int i=0;i<jjfyreceive.length();i++) 
		{
			if(i!=jjfyid-1)
			{
				newreceive.append(jjfyreceive.charAt(i));
			}
			else
			{
				newreceive.append('1');
			}
		}
		
		JjfyData jjfy=Bean.getJjfymap().get(String.valueOf(jjfyid));
		
		Map<String, Object> rankinfo=myJdbcTemplate.queryForMap("SELECT MAXRANKDAN,MAXRANK FROM PLAYER_TOBATTLE WHERE PLAYERID="+playerid);
		
		int group=Integer.parseInt(String.valueOf(rankinfo.get("MAXRANKDAN")));
		
		int rank=Integer.parseInt(String.valueOf(rankinfo.get("MAXRANK")));
		
		if((group==jjfy.getRankdan()&&rank<=jjfy.getRank())||group>jjfy.getRankdan()){
			
			myJdbcTemplate.update("UPDATE ZNX_PLAYER SET GOLD=GOLD+"+jjfy.getGold()+",STONE=STONE+"+jjfy.getStone()+",jjfyreceive='"+newreceive+"' WHERE ID="+playerid);
			
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, jjfy.getGoodsid()+"", jjfy.getGoodsnum()+"");
			
			res="true";
			
		}
		
		response.put("res",res);
		
		return response;
		
	}

}
