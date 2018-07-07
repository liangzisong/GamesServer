package com.mangni.vegaplan.servletsrc.customsystem.tools;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.eclipse.jetty.websocket.api.Session;

import com.mangni.vegaplan.datatable.Znx_GmData;

public class Initialise {
	private static HashMap<String,Session> gmsession;
	private static LinkedHashMap<String,List<Integer>> gmcount;
	private static HashMap<String,Znx_GmData> znx_gmmap;
	public Initialise()
	{
		gmsession=new HashMap<String,Session>();
		gmcount=new LinkedHashMap<String,List<Integer>>();
		znx_gmmap=new HashMap<String,Znx_GmData>();
	}
	public static HashMap<String,Session> getGmsession() {
		return gmsession;
	}
	public static void setGmsession(HashMap<String,Session> customsession) {
		Initialise.gmsession = customsession;
	}
	public static LinkedHashMap<String,List<Integer>> getGmcount() {
		return gmcount;
	}
	public static void setGmcount(LinkedHashMap<String,List<Integer>> gmcount) {
		Initialise.gmcount = gmcount;
	}
	public static HashMap<String,Znx_GmData> getZnx_gmmap() {
		return znx_gmmap;
	}
	public static void setZnx_gmmap(HashMap<String,Znx_GmData> znx_gmmap) {
		Initialise.znx_gmmap = znx_gmmap;
	}
}
