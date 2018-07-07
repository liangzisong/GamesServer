package com.mangni.vegaplan.toolshelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashMapHelper 
{
	public static List<String> getKeyList(HashMap hm,String methodname,String value)
	{
		List<String> list=new ArrayList<String>();
		Iterator iterator=hm.entrySet().iterator();
		while(iterator.hasNext())
		{
			Map.Entry  mapentry = (Map.Entry) iterator.next();
			String key=mapentry.getKey().toString();
			//Class cla=hm.get(key).getClass();
			Class cla=mapentry.getValue().getClass();
			
			String keyvalue=null;
			try
			{
				Method method=cla.getMethod(methodname, String.class);
				keyvalue=(String) method.invoke(mapentry.getValue(), value);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			if(keyvalue!=null)
			{
				list.add(keyvalue);
			}
		}
		return list;
	}
}
