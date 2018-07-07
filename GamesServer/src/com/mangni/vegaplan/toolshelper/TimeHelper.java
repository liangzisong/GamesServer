package com.mangni.vegaplan.toolshelper;

import java.util.Calendar;

public class TimeHelper {
	public static boolean isAfterThree(Calendar calendar,Calendar nowc){
		calendar.add(Calendar.HOUR_OF_DAY, -3);
		nowc.add(Calendar.HOUR_OF_DAY, -3);
		int diffday=nowc.get(Calendar.YEAR)-calendar.get(Calendar.YEAR);
		if(diffday==0)
			diffday=nowc.get(Calendar.DAY_OF_YEAR)-calendar.get(Calendar.DAY_OF_YEAR);
		if(diffday>0){
			return true;
		}else{
			return false;
		}
	}
}
