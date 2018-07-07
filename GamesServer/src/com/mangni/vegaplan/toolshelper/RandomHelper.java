package com.mangni.vegaplan.toolshelper;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.mangni.vegaplan.datatable.TurnData;

public class RandomHelper {
	public static String getRanTurnid(){
		Random random=new Random();
		int randomnum=random.nextInt(Bean.getTurnsum());
		int sum=0;
		String turnid=null;
		Iterator<Entry<String, TurnData>> turniterator=Bean.getTurnmap().entrySet().iterator();
		while(turniterator.hasNext())
		{
			Map.Entry<String,TurnData>  mapentry = turniterator.next();
			turnid=mapentry.getKey();
			TurnData turndata=mapentry.getValue();
			int pro=turndata.getProbability();
			sum+=pro;
			if(sum>=randomnum){
				break;
			}
		}
		return turnid;
	}
}
