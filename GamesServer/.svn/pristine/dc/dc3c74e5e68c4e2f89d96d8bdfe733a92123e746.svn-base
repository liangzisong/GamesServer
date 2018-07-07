package com.mangni.vegaplan.servletsrc.beforeplay;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;

public class GetTemplateTable implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response = new HashMap<String,Object>();
		String typepara=(String)request.get("type");
		String[] type=typepara.split(",");
		try {
			for(int i=0;i<type.length;i++){
				Class<Bean> c=(Class<Bean>) Class.forName("com.mangni.vegaplan.toolshelper.Bean");
				Method method = c.getMethod(type[i]);
				response.put(type[i], method.invoke(method, null));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

}
