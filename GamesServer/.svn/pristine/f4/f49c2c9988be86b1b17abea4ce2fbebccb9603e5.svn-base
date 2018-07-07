package com.mangni.vegaplan.toolshelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrlogHelper
{
	public static void Errlog(String playerid,String statement,Exception e)
	{
		/*
		Logger logger=Logger.getLogger(c);
		MDC.put("classname",c);
		MDC.put("playerid",playerid);
		MDC.put("statement",statement);
		logger.warn(e.getMessage().replace("'","''"));
		MDC.clear();
		*/
		String exceptionmessage;
		exceptionmessage="\r\n"+e.getMessage();
		StackTraceElement[] stpara=e.getStackTrace();
		for(StackTraceElement st:stpara)
		{
			exceptionmessage+="\r"+st.getClassName()+"("+st.getLineNumber()+")";
		}
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String sql="INSERT INTO errlog(loglv,playerid,statement,classname,methodname,logmes) VALUES(?,?,?,?,?,?)";
		myJdbcTemplate.update(sql,new Object[]{"WARN",playerid,statement,exceptionmessage,"dopost",e.getMessage()});
		Date nowdate=new Date();		
		SimpleDateFormat dateFormat = Bean.getDateFormat();
		String time=dateFormat.format(nowdate.getTime());
		Bean.getTextArea().append("\r\n");
		Bean.getTextArea().append(time+"："+exceptionmessage+"\r");
		Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
		e.printStackTrace();
	}
}