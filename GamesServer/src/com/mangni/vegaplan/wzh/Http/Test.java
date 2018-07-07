package com.mangni.vegaplan.wzh.Http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class Test extends Thread {
	public MyJdbcTemplate myJdbcTemplate;
	public static Object object=new Object();
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}

	public static void main(String[] args) throws ParseException{
		Date d=new Date();
		d.setTime(1497249001123l);
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
		Object o=d;
		String s=String.valueOf(o);
		d=df.parse(s);
		System.out.println(s);
	}
}
