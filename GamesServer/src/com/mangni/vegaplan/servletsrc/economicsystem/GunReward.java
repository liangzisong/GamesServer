package com.mangni.vegaplan.servletsrc.economicsystem;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.GunData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.ErrlogHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GunReward implements IReceiveMessage{
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
		String gunid=(String)request.get("gunid");
		String stake=(String)request.get("stake");
		GunData gundata=Bean.getGunmap().get(gunid);
		String startdatestr=gundata.getStartdate();
		String enddatestr=gundata.getEnddate();
		Date startdate = null,enddate = null;
	    startdate=Bean.getDateFormat().parse(startdatestr);
	    enddate=Bean.getDateFormat().parse(enddatestr);
		
		Date nowdate=new Date();
		long nowtime=nowdate.getTime();
		if(startdate.getTime()<=nowtime&&enddate.getTime()>nowtime){
			String finishcount=SqlHelper.getOneRead("SELECT TOP 1 ID FROM PLAYER_GUNREWARD WHERE PLAYERID="+playerid+" AND ");
			
		}
		
	}

}
