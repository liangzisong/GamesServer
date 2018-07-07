package com.mangni.vegaplan.toolshelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoadXmlFiles
{

	public static List<File> getXmlList(String path,String suffix) {
		List<File> list = new ArrayList<File>();
		try {
			File f = new File(path);
			File[] fs = f.listFiles();
			int i = 0;
			while (i<fs.length) {
				if (fs[i].isDirectory())
					getXmlList(fs[i].getPath(), suffix);// 递归查询目录下的文件夹
				if (fs[i].getName().endsWith(suffix))// 查找xls后缀的文件
					list.add(fs[i]);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	
	
	public static List<String> getAllSuffix(String path)
	{
		List<String> suffixlist=new ArrayList<String>();
		File f=new File(path);
		File[] filelist=f.listFiles();
		for(Object obj:filelist)
    	{
    		String filename=obj.toString();
    		if ((filename != null) && (filename.length() > 0)) { 
    			int dot = filename.lastIndexOf('.'); 
    			if ((dot >-1) && (dot < (filename.length() - 1))) { 
    				suffixlist.add(filename.substring(dot + 1)); 
    			} 
    		} 
    	}
		return suffixlist;
	}
	
	public static void main(String [] args)
	{
		String curDir=System.getProperty("user.dir");
		String path=curDir+"\\xmllib\\templates";
		List<File> a=new ArrayList<File>();
		a= getXmlList(path, ".champions");
		System.out.println(a);
	}
}
