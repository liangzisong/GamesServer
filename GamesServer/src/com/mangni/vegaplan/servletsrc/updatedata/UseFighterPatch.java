package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class UseFighterPatch implements IReceiveMessage{
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		String res="false";
		//String playerid=(String) request.get("playerid");
		String playerfighterid=(String) request.get("fighterid");
		int usenum=Integer.parseInt((String) request.get("usenum"));		

		LvupHelper.UsefighterSP(playerfighterid, usenum);
		res="true";

		response.put("res",res);
		return response;
	}
}