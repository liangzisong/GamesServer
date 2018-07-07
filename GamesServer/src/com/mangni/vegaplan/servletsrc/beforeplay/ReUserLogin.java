package com.mangni.vegaplan.servletsrc.beforeplay;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;

public class ReUserLogin implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		response.put("res", "relogin");
		return response;
	}

}
