package com.mangni.vegaplan.websocketsrc;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
//@WebServlet(name = "MyEcho WebSocket Servlet", urlPatterns = { "/echo" })
public class CustomServlet extends WebSocketServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void configure(WebSocketServletFactory factory) {
		// TODO Auto-generated method stub
		factory.getPolicy().setIdleTimeout(30000);
		factory.register(CustomListener.class);
	}
}