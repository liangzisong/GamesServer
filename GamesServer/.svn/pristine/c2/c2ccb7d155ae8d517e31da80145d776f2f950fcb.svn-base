package com.mangni.vegaplan.servletsrc.beforeplay;

import java.util.Date;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;

public class getServerTime implements IReceiveMessage{
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		if(request.get("info").equals("getservertime"))
		{
			Date d=new Date();
			String time= Bean.getDateFormat().format(d.getTime());
			response.put("time", time);		
		}
		return response;
	}
}
