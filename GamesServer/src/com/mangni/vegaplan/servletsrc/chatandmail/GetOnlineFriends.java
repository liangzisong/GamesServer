package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;

public class GetOnlineFriends implements IReceiveMessage{
	
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{	
		HashMap<String,Object> response=new HashMap<String,Object>();
		List<String> onlinelist=new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<String> playeridlist=(List<String>) request.get("playeridlist");

		for(String playerid:playeridlist)
		{
			if(Bean.getPlayersession().containsKey(playerid))
			{
				onlinelist.add(playerid);
			}
		}
		Calendar now=Calendar.getInstance();
		response.put("res",onlinelist);
		response.put("time", now.getTime());
		return response;
	}
}
