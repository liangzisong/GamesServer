package com.mangni.vegaplan.jettyserver;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jetty.websocket.api.Session;

import com.mangni.vegaplan.toolshelper.Bean;

public class SendPongThread implements Runnable
{	
	@Override
	public void run()
	{ 			
		//String sendString="1";
		//ByteBuffer bf=ByteBuffer.wrap(sendString.getBytes("UTF-8"));
		ConcurrentHashMap<String,Session> sessionmap=Bean.getPlayersession();
		for(Entry<String, Session> entry:sessionmap.entrySet())
		{
			if(entry.getValue().isOpen())
				try {
					entry.getValue().getRemote().sendString("1");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
