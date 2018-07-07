package com.mangni.vegaplan.MainIReceive;

import java.util.HashMap;

public interface IReceiveMessage {
	public HashMap<String,Object> dopost(HashMap<String,Object> request) throws Exception;
}
