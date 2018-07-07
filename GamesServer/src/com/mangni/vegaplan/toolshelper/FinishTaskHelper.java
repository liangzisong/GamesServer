package com.mangni.vegaplan.toolshelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.HolidayinfoData;
import com.mangni.vegaplan.datatable.WizardData;

public class FinishTaskHelper {
	public static void finishEverydayTask(String playerid,String taskid,String num){
		if(Bean.getDailytaskmap().containsKey(taskid)){
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.DAILYTASK, taskid, num);
		}
	}

	public static void finishHolidayTask(String playerid,String tasktypeid){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		WizardData wizarddata=Bean.getWizardmap().get(tasktypeid);
		if(wizarddata.getUse()!=1)
			return;

		HolidayinfoData holidayinfodata=Bean.getHolidayinfomap().get("1");
		String starttime=holidayinfodata.getStartdate();
		String endtime=holidayinfodata.getEnddate();
		Date startdate = null;
		Date enddate = null;
		try {
			startdate=Bean.getDateFormat().parse(starttime);
			enddate=Bean.getDateFormat().parse(endtime);

			Date nowdate=new Date();
			if(nowdate.getTime()<=startdate.getTime()&&nowdate.getTime()>=enddate.getTime())
				return;

			if(tasktypeid.equals("1")){	
					Calendar updatec=Calendar.getInstance();
					Calendar nowc=Calendar.getInstance();
					updatec.setTime(Bean.getDateFormat().parse(starttime));
					int betweendays=(int)(nowc.getTimeInMillis()-updatec.getTimeInMillis())/(24*3600*1000);
					int maxdays=myJdbcTemplate.queryForObject("select isnull(max(templateid),0) from player_holidaytask where playerid="+playerid+" and templateid<11 and starttime='"+starttime+"'",Integer.class);
					if(maxdays>betweendays)
						return;
					tasktypeid=String.valueOf(betweendays+1);
				/*
				Calendar starttimec=Calendar.getInstance();
				Calendar nowc=Calendar.getInstance();
				starttimec.setTime(startdate);
				int days=1;
				while(starttimec.before(nowc)){
		            days++;
		            starttimec.add(Calendar.DAY_OF_YEAR, 1);
		        }
				 */
			}
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.HOLIDAYTASK, tasktypeid+",'"+starttime+"'", "1");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void finishHolidayTask(String playerid,String tasktypeid,String num){
		HolidayinfoData holidayinfodata=Bean.getHolidayinfomap().get("1");
		String starttime=holidayinfodata.getStartdate();
		String endtime=holidayinfodata.getEnddate();
		Date startdate = null;
		Date enddate = null;
		try {
			startdate=Bean.getDateFormat().parse(starttime);
			enddate=Bean.getDateFormat().parse(endtime);

			Date nowdate=new Date();
			if(nowdate.getTime()<=startdate.getTime()&&nowdate.getTime()>=enddate.getTime())
				return;

			if(tasktypeid.equals("1"))
				return;

			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.HOLIDAYTASK, tasktypeid+",'"+starttime+"'", num);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
