package com.mangni.vegaplan.jettyserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.alibaba.druid.support.http.ResourceServlet;
import com.alibaba.druid.support.http.StatViewServlet;
import com.mangni.vegaplan.servletsrc.pay.RechargeMoney;
import com.mangni.vegaplan.servletsrc.pay.RechargeMoneySdk;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.websocketsrc.CustomServlet;
import com.mangni.vegaplan.websocketsrc.MainServlet;

public class GamesServer extends Thread
{
	@Override
	public void run()
	{ 	
		new ServerReset();
		//ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
		//long period1 = 3*60; 
		//service.scheduleAtFixedRate(new OnlineReset(), 0,period1, TimeUnit.SECONDS);
		//service.scheduleAtFixedRate(new CheckEco(), 0,1, TimeUnit.DAYS);		 
			
		ServerConnector connector = new ServerConnector(Bean.getServer());
		connector.setPort(Bean.getPort());
		Bean.getServer().setConnectors(new Connector[] { connector });
		
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addServlet(new ServletHolder(new MainServlet()),"/games/");
		context.addServlet(new ServletHolder(new RechargeMoney()),"/rechargemoney/");
		context.addServlet(new ServletHolder(new RechargeMoneySdk()),"/rechargemoneysdk/");
		context.addServlet(new ServletHolder(new CustomServlet()), "/gamemanager/");
		ServletHolder druid=new ServletHolder(new StatViewServlet());//druid/
		druid.setInitParameter(ResourceServlet.PARAM_NAME_USERNAME, "admin");
		druid.setInitParameter(ResourceServlet.PARAM_NAME_PASSWORD, "admin");
		context.addServlet(druid,"/druid/*");
		
		
		Bean.getServer().setHandler(context);
		try
		{
			Bean.getServer().start();
			System.out.println("游戏server开始监听"+Bean.getPort()+"端口");
			synchronized (this)
			{
				this.notifyAll();
			}
			Bean.getServer().join();
		} catch (Exception e) {
			synchronized (this)
			{
				this.notifyAll();
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date nowdate=new Date();		
			String time=dateFormat.format(nowdate.getTime());
			Bean.getTextArea().append("\r\n");
			Bean.getTextArea().append(time+"：错误："+e.getMessage());
			Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
		}
	}	
}
