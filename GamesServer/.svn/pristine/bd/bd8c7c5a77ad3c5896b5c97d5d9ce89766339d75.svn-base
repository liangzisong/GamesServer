package com.mangni.HotLoadSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import com.mangni.vegaplan.toolshelper.XmlHelper;

public class ClassFileSystemMap {
	private static HashMap<String,Long> javafiles = new HashMap<String, Long>();
	private final static String javapath = System.getProperty("user.dir")+"//src";
	public HashMap<String,Long> getJavaFiles() {
		return javafiles;
	}

	public static void addJavaFiles()
	{
		String curDir=System.getProperty("user.dir");
		File profile = new File(curDir+"\\config\\db.properties");
		if(profile.exists())
		{
			try
			{
				FileInputStream in = new FileInputStream(profile);
				Properties prop=new Properties();
				prop.load(in);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		//setPort(Integer.parseInt(prop.getProperty("port")));
		List<String> filespath=XmlHelper.fileList(javapath,"java");
		for(String filepath:filespath)
		{
			File file=new File(filepath);
			Long lastmodifytime=file.lastModified();
			javafiles.put(filepath, lastmodifytime);
		}
	}
	/*
	public static List<String> isCreate()
	{
		List<String> createfiles=new ArrayList<String>();
		List<String> filespath=XmlHelper.fileList(javapath,"java");
		for(String filepath:filespath)
		{
			if(!javafiles.containsKey(filepath))
			{
				createfiles.add(filepath);
			}
		}
		return createfiles;
	}
	 */
	public static List<String> isMofity()
	{
		List<String> mofityfiles=new ArrayList<String>();
		List<String> filespath=XmlHelper.fileList(javapath,"java");
		for(String filepath:filespath)
		{
			if(javafiles.containsKey(filepath))
			{
				File file=new File(filepath);
				Long lastmofitytime=file.lastModified();
				if(!(javafiles.get(filepath)==lastmofitytime))
				{
					mofityfiles.add(filepath);
				}
			}
			else
			{
				mofityfiles.add(filepath);
			}
		}
		return mofityfiles;
	}

	public static List<String> isDelete()
	{
		List<String> deletefiles=new ArrayList<String>();
		List<String> filespath=XmlHelper.fileList(javapath,"java");
		synchronized (filespath) {
			Iterator<Entry<String, Long>> javafilesiterator=javafiles.entrySet().iterator();
			while(javafilesiterator.hasNext())
			{
				Map.Entry<String,Long>  mapentry = javafilesiterator.next();
				String filepath=mapentry.getKey();
				if(!filespath.contains(filepath))
				{
					deletefiles.add(filepath);
				}
			}
		}
		return deletefiles;
	}
}
