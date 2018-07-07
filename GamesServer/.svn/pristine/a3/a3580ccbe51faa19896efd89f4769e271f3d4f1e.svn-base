package com.mangni.vegaplan.servletsrc.economicsystem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.SevendayData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.TimeHelper;

public class ReceiveSevenday implements IReceiveMessage {
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
		response.put("res", "false");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		String sevendaylasttime=znx_playerdata.getSevendaylasttime();
		if(!"null".equals(sevendaylasttime)){
			Date sevendaydate=null;
			try {
				sevendaydate=Bean.getDateFormat().parse(sevendaylasttime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar sevenc=Calendar.getInstance();
			sevenc.setTime(sevendaydate);
			if(!TimeHelper.isAfterThree(sevenc, Calendar.getInstance()))
				return response;
		}
		String sevendayreceive=znx_playerdata.getSevendayreceive();
		if(sevendayreceive==null)
			sevendayreceive="0000000";
		int first0=1;
		int isfirst=0;
		StringBuilder newcount=new StringBuilder();
		for (int i=0;i<sevendayreceive.length();i++) 
		{
			if(sevendayreceive.charAt(i)=='1')
			{
				newcount.append('1');
				first0++;
			}
			else
			{
				if(isfirst==0){
					newcount.append('1');
					isfirst++;
				}else{
					newcount.append('0');
				}
			}
		}
		List<String> dataid=new ArrayList<String>();
		SevendayData sevendaydata=Bean.getSevendaymap().get(String.valueOf(first0));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype1()), sevendaydata.getItemid1(), sevendaydata.getItemnum1()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype2()), sevendaydata.getItemid2(), sevendaydata.getItemnum2()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype3()), sevendaydata.getItemid3(), sevendaydata.getItemnum3()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype4()), sevendaydata.getItemid4(), sevendaydata.getItemnum4()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype5()), sevendaydata.getItemid5(), sevendaydata.getItemnum5()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype6()), sevendaydata.getItemid6(), sevendaydata.getItemnum6()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype7()), sevendaydata.getItemid7(), sevendaydata.getItemnum7()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype8()), sevendaydata.getItemid8(), sevendaydata.getItemnum8()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype9()), sevendaydata.getItemid9(), sevendaydata.getItemnum9()));
		dataid.add(GetGoodsHelper.getGoods(playerid, GetGoodsHelper.getGoodstype(sevendaydata.getItemtype10()), sevendaydata.getItemid10(), sevendaydata.getItemnum10()));
		znx_playerdata.setSevendayreceive(newcount.toString());
		znx_playerdata.setSevendaylasttime(Bean.getDateFormat().format(new Date()));
		myJdbcTemplate.update("update znx_player set sevendayreceive="+newcount.toString()+",sevendaylasttime=getdate() where id="+playerid);
		response.put("res", "true");
		response.put("dataid", dataid);
		return response;
	}
	
}
