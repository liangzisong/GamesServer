package com.mangni.vegaplan.servletsrc.economicsystem;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.HolidayData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class HolidayReceive implements IReceiveMessage {
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
		String receivetype=(String)request.get("receivetype");
		int receiveid = Integer.parseInt((String)request.get("receiveid"));
		String res="false";
		String jeweldataid = null;
		String chipdataid = null;
		String jewelznxid = null;
		String chipznxid = null;
		HolidayData holidaydata=Bean.getHolidaymap().get(String.valueOf(receiveid));
		String starttime=holidaydata.getStartdate();
		String endtime=holidaydata.getEnddate();
		Date stardate=null;	
		Date enddate=null;
		try {
			stardate=Bean.getDateFormat().parse(starttime);
			enddate=Bean.getDateFormat().parse(endtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date nowdate=new Date();
		if(nowdate.getTime()>stardate.getTime()&&nowdate.getTime()<enddate.getTime()){

			if(receivetype.equals("free")){
				Map<String, Object> receivemap=myJdbcTemplate.queryForMap("SELECT holidaygetlasttime,holidaygetreceive FROM ZNX_PLAYER WHERE ID="+playerid);
				String lastreceivetimestr=String.valueOf(receivemap.get("holidaygetlasttime"));
				String receive=String.valueOf(receivemap.get("holidaygetreceive"));

				Date lastreceivedate=null;
				try {
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

				if(receive.charAt(receiveid-1)=='1'){
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
				String sql="UPDATE ZNX_PLAYER SET GOLD=GOLD+"+holidaydata.getGetgold()+",STONE=STONE+"+holidaydata.getGetstone()+",holidaygetreceive='"+newreceive+"',holidaygetlasttime=getdate() WHERE ID="+playerid;
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, holidaydata.getGetitem1id(), holidaydata.getGetitem1num());
				GetGoodsHelper.getGoods(playerid,EnumGoodsTypes.GOODS, holidaydata.getGetitem2id(), holidaydata.getGetitem2num());
				myJdbcTemplate.update(sql);
				res="true";
			}else if(receivetype.equals("notfree")){
				List<String> receivelist=myJdbcTemplate.queryForList("SELECT holidaylasttime,holidayreceive,stone FROM ZNX_PLAYER WHERE ID="+playerid,String.class);
				String lastreceivetimestr=receivelist.get(0);
				String receive=receivelist.get(1);
				int havestone=Integer.parseInt(receivelist.get(2));
				int needstone=holidaydata.getNeedstone();
				if(havestone<needstone){
					response.put("res", "not enough");
					return response;
				}

				Date lastreceivedate=null;
				try {
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

				if(receive.charAt(receiveid-1)=='1'){	
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

				String sql="UPDATE ZNX_PLAYER SET stone=stone-"+needstone+",holidayreceive='"+newreceive+"',holidaylasttime=getdate() WHERE ID="+playerid;
				LvupHelper.spendStone(playerid, needstone, "holidayreceive", sql, request, false);
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", holidaydata.getVit());
				jewelznxid=holidaydata.getJewelid();
				if(jewelznxid.equals("0")){
					jewelznxid=GetGoodsHelper.getRanJewAlertChar(holidaydata.getJewelcharacter());
				}
				chipznxid=holidaydata.getChipid();
				if(chipznxid.equals("0")){
					chipznxid=GetGoodsHelper.getRanChipAlertColor(holidaydata.getChipcolor());
				}
				jeweldataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.JEWEL, jewelznxid, "1");
				chipdataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CHIP, chipznxid, "1");
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, holidaydata.getItemid(), holidaydata.getItemnum());
				res="true";
			}
		}
		response.put("res", res);	
		response.put("jewelznxid", jewelznxid);
		response.put("chipznxid", chipznxid);
		response.put("jeweldataid", jeweldataid);
		response.put("chipdataid", chipdataid);
		return response;
	}

}
