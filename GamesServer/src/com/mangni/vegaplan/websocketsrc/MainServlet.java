package com.mangni.vegaplan.websocketsrc;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
//@WebServlet(name = "MyEcho WebSocket Servlet", urlPatterns = { "/echo" })
public class MainServlet extends WebSocketServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	public void configure(WebSocketServletFactory factory) {
		System.out.println("MainServlet.configure()");
		// TODO Auto-generated method stub
		factory.getPolicy().setIdleTimeout(300000);
		factory.register(MainListener.class);
	}
}