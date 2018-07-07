package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.HolidayinfoData;
import com.mangni.vegaplan.datatable.ReceiveData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ExchangeHolidaygold implements IReceiveMessage {
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
		HolidayinfoData hi=Bean.getHolidayinfomap().get("1");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		response.put("res", "false");
		if(type.equals("getsum")){
			String sql="select isnull(sum(spendstone),0) from spendstone_log where playerid="+playerid+" and uptime>='"+hi.getStartdate()+"' and uptime<='"+hi.getEnddate()+"'";
			int spendstone=myJdbcTemplate.queryForObject(sql,Integer.class);
			//znx_playerdata.setHolidayspendstone(spendstone);
			response.put("res", spendstone);
		}else{
			String res="false";
			String starttime=hi.getStartdate();
			String endtime=hi.getEnddate();
			Date enddate=null;
			Date startdate=null;
			try{
				startdate=Bean.getDateFormat().parse(starttime);
				enddate=Bean.getDateFormat().parse(endtime);
			}catch(Exception e){
				e.printStackTrace();
			}
			Date now=new Date();
			if(now.getTime()<startdate.getTime()||now.getTime()>enddate.getTime())
				return response;
			
			String receiveidstr=(String)request.get("receiveid");
			int receiveid=Integer.parseInt(receiveidstr);
			ReceiveData receivedata=Bean.getReceivemap().get(receiveidstr);
			String sql="select isnull(sum(spendstone),0) from spendstone_log where playerid="+playerid+" and uptime>='"+hi.getStartdate()+"' and uptime<='"+hi.getEnddate()+"'";
			int spendstone=myJdbcTemplate.queryForObject(sql,Integer.class);
			if(spendstone>=receivedata.getSetstone()){
				String exchangegold=znx_playerdata.getExchangeholidaygold();
				String exchangegoldtime=znx_playerdata.getExchangeholidaygoldtime();
				if(!hi.getStartdate().equals(exchangegoldtime)){
					StringBuilder receivesb=new StringBuilder();
					for (int i=0;i<exchangegold.length();i++) 
					{
						receivesb.append('0');
					}
					exchangegold=receivesb.toString();
				}
				StringBuilder newreceive=new StringBuilder();
				for (int i=0;i<exchangegold.length();i++) 
				{
					if(i!=receiveid-1)
					{
						newreceive.append(exchangegold.charAt(i));
					}
					else
					{
						newreceive.append('1');
					}
				}			
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.HOLIDAYGOLD, "0", String.valueOf(receivedata.getGetcurrency()));
				myJdbcTemplate.update("update znx_player set exchangeholidaygold='"+newreceive.toString()+"',exchangeholidaygoldtime='"+hi.getStartdate()+"' where id="+playerid);
				znx_playerdata.setExchangeholidaygold(newreceive.toString());
				znx_playerdata.setExchangeholidaygoldtime(hi.getStartdate());
				res="true";
			}
			response.put("res", res);
		}
		return response;
	}

}
