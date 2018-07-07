package com.mangni.vegaplan.toolshelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mangni.vegaplan.servletsrc.customsystem.tools.Initialise;
/**
 * request时间戳处理
 */
public class BfstrHelper 
{
	public static boolean getBfstr(HashMap<String,Object> request)
	{
		String tampplayerid = (String) request.get("tampplayerid");
		int tampcount = Integer.valueOf((String) request.get("tampcount"));
		if("0".equals(tampplayerid))
			return true;
		
		if(Bean.getPlayercount().containsKey(tampplayerid))
		{
			List<Integer> oldcountarray=Bean.getPlayercount().get(tampplayerid);
			if(oldcountarray.contains(tampcount))
			{
				return false;
			}
			else
			{	
				int count1=oldcountarray.get(0);//第一位用于计数
				if(tampcount-oldcountarray.get(count1)<-3)
					return false;
				if(oldcountarray.size()<31)
				{
					oldcountarray.add(tampcount);
					oldcountarray.set(0,count1+1);
				}
				else
				{
					if(count1<30)
					{
						oldcountarray.set(count1+1,tampcount);
						oldcountarray.set(0,count1+1);
					}
					else
					{
						count1=1;
						oldcountarray.set(0,count1);
						oldcountarray.set(1,tampcount);
					}
				}
				return true;
			}
		}
		else
		{
			List<Integer> countarray=new ArrayList<Integer>(46);
			countarray.add(1);
			countarray.add(tampcount);
			Bean.getPlayercount().put(tampplayerid, countarray);
			return true;
		}
	}
	public static boolean getGMBfstr(HashMap<String,Object> request)
	{
		String tampgmid = (String) request.get("tampgmid");
		int tampcount = Integer.valueOf((String) request.get("tampcount"));
		if(Initialise.getGmcount().containsKey("tampgmid"))
		{
			List<Integer> oldcountarray=Initialise.getGmcount().get(tampgmid);
			if(oldcountarray.contains(tampcount))
			{
				return false;
			}
			else
			{		
				int count1=oldcountarray.get(0);
				if(oldcountarray.size()<31)
				{
					oldcountarray.add(tampcount);
					oldcountarray.set(0,count1+1);
				}
				else
				{
					if(count1<30)
					{
						oldcountarray.set(count1,tampcount);
						oldcountarray.set(0,count1+1);
					}
					else
					{
						count1=1;
						oldcountarray.set(0,count1);
						oldcountarray.set(1,tampcount);
					}
				}
				return true;
			}
		}
		else
		{
			List<Integer> countarray=new ArrayList<Integer>(46);
			countarray.add(1);
			countarray.add(tampcount);
			Initialise.getGmcount().put(tampgmid, countarray);
			return true;
		}
	}
}
/*
public class BfstrHelper 
{
	public static boolean getBfstr(HashMap<String,Object> request)
	{
		String tampplayerid = (String) request.get("tampplayerid");
		long tampcount = Long.valueOf((String) request.get("tampcount"));
		if( Bean.playercount.containsKey("tampplayerid"))
		{
			long[] oldcountarray=Bean.playercount.get(tampplayerid);
			if(oldcountlist.contains(tampplayerid))
			{
				return false;
			}
			else
			{

				return true;
			}
		}
		else
		{
			long[] countarray=new long[31];
			countarray[0]=tampcount;
			countarray[30]=1;
			Bean.playercount.put(tampplayerid, countarray);
			return true;
		}
	} 
}*/
