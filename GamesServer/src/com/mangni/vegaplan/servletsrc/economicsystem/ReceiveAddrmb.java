package com.mangni.vegaplan.servletsrc.economicsystem;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.AddrmbData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveAddrmb implements IReceiveMessage {
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

		AddrmbData addrmbdata=Bean.getAddrmbmap().get(String.valueOf(receiveid));

		int needrmb=addrmbdata.getNeedrmb();

		String starttime=addrmbdata.getStartdate();

		String endtime=addrmbdata.getEnddate();

		Double rmb=myJdbcTemplate.queryForObject("SELECT isnull(sum(total_fee),0) FROM PLAYER_VIP_PAY WHERE PLAYERID="+playerid+" AND UPTIME>='"+starttime+"' AND UPTIME<'"+endtime+"'",Double.class);

		if(rmb>=needrmb){

			Map<String, Object> receivemap=myJdbcTemplate.queryForMap("SELECT LJCZRECEIVELASTTIME,LJCZRECEIVE FROM ZNX_PLAYER WHERE ID="+playerid);
			
			String lastreceivetimestr=String.valueOf(receivemap.get("LJCZRECEIVELASTTIME"));
			
			String receive=String.valueOf(receivemap.get("LJCZRECEIVE"));
			
			Date stardate=null;
			
			Date enddate=null;
			
			Date lastreceivedate=null;
			
			try {
				stardate=Bean.getDateFormat().parse(starttime);
				enddate=Bean.getDateFormat().parse(endtime);
				lastreceivedate=Bean.getDateFormat().parse(lastreceivetimestr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if(stardate.getTime()>lastreceivedate.getTime()){
				StringBuilder receivesb=new StringBuilder();
				for (int i=0;i<receive.length();i++){
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
			
			String sql="UPDATE ZNX_PLAYER SET GOLD=GOLD+"+addrmbdata.getGold()+",STONE=STONE+"+addrmbdata.getStone()+",ljczreceive='"+newreceive+"',ljczreceivelasttime=getdate() WHERE ID="+playerid;
			myJdbcTemplate.update(sql);
			
			String chipid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CHIP, addrmbdata.getChipid(), "1");
			String jewelid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.JEWEL, addrmbdata.getJewelid(), "1");
			response.put("datachipid", chipid);
			response.put("datajewelid", jewelid);
			res="true";
			
		}
		response.put("res", res);
		return response;

	}

}
