package com.mangni.vegaplan.servletsrc.economicsystem;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.AddbuyData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveAddbuy implements IReceiveMessage {
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
		
		int receiveid=Integer.parseInt((String)request.get("receiveid"));
		
		String res="false";
		
		AddbuyData addbuydata=Bean.getAddbuymap().get(String.valueOf(receiveid));
		
		int needspendstone=addbuydata.getSpendstone();
		
		String starttime=addbuydata.getStartdate();
		
		String endtime=addbuydata.getEnddate();
		
		int spendstone=myJdbcTemplate.queryForObject("SELECT isnull(sum(spendstone),0) FROM spendstone_log WHERE PLAYERID="+playerid+" AND UPTIME>='"+starttime+"' AND UPTIME<'"+endtime+"'",Integer.class);
		
		if(spendstone>=needspendstone){
			
			Map<String, Object> receivemap=myJdbcTemplate.queryForMap("SELECT ljxfreceivelasttime,ljxfreceive FROM ZNX_PLAYER WHERE ID="+playerid);
			
			String lastreceivetimestr=String.valueOf(receivemap.get("ljxfreceivelasttime"));
			
			String receive=String.valueOf(receivemap.get("ljxfreceive"));
			
			Date stardate=null;
			
			Date enddate=null;
			
			Date lastreceivedate=null;
			
			try {
				stardate=Bean.getDateFormat().parse(starttime);
				enddate=Bean.getDateFormat().parse(endtime);
				lastreceivedate=Bean.getDateFormat().parse(lastreceivetimestr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(stardate.getTime()>lastreceivedate.getTime()){
				StringBuilder receivesb=new StringBuilder();
				for (int i=0;i<receive.length();i++) 
				{
					receivesb.append('0');
				}
				receive=receivesb.toString();
			}
				
			
			if(new Date().getTime()>enddate.getTime()||receive.charAt(receiveid-1)=='1'){
				
				response.put("res", res);
				
				return response;
				
			}
			
			StringBuilder newreceive=new StringBuilder();
			for (int i=0;i<receive.length();i++) 
			{
				if(i!=receiveid-1)
				{
					newreceive.append(receive.charAt(i));
				}
				else
				{
					newreceive.append('1');
				}
			}
			
			String chipid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CHIP, addbuydata.getChipid(), "1");
			String jewelid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.JEWEL, addbuydata.getJewelid(), "1");
			String fighterinfo=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, addbuydata.getFighterid(), "1");
			
			String sql="UPDATE ZNX_PLAYER SET GOLD=GOLD+"+addbuydata.getGold()+",ljxfreceive='"+newreceive+"',ljxfreceivelasttime=getdate() WHERE ID="+playerid;
			myJdbcTemplate.update(sql);
			response.put("datachipid", chipid);
			response.put("datajewelid", jewelid);
			response.put("fighterinfo", fighterinfo);
			res="true";	
		}
		
		response.put("res", res);
		return response;
		
	}

}
