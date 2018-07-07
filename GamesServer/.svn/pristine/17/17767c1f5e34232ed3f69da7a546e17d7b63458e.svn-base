package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;

public class OnekeyPcbset implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		JSONObject jso=(JSONObject) request.get("equipinfo");
		HashMap<String,HashMap<String,Object>> equipinfo=JSON.parseObject(JSON.toJSONString(jso),new TypeReference<HashMap<String,HashMap<String,Object>>>(){});;
		Iterator<Entry<String, HashMap<String, Object>>>  iterator=equipinfo.entrySet().iterator();
		IReceiveMessage ir=(IReceiveMessage) Bean.getCtx().getBean("updatepcbset");
		while(iterator.hasNext())
		{ 
			Entry<String,HashMap<String, Object>> entry=iterator.next();
			String key=entry.getKey();
			HashMap<String, Object> value=entry.getValue();
			response.put(key, ir.dopost(value));
		}
		return response;
	}

}
