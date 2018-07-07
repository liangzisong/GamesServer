package com.mangni.vegaplan.websocketsrc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.servletsrc.customsystem.servletsrc.GmLogin;
import com.mangni.vegaplan.servletsrc.customsystem.tools.Initialise;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.BfstrHelper;
import com.mangni.vegaplan.toolshelper.EncryptUtil;

public class CustomListener implements WebSocketListener{
	private Session outbound;
	private String gmkey="0";
	@Override
	public void onWebSocketClose(int arg0, String arg1) {
		// TODO Auto-generated method stub
		synchronized (this) {
			Initialise.getGmsession().remove(gmkey);
		}
		this.outbound = null;
	}

	@Override
	public void onWebSocketConnect(Session session) {
		// TODO Auto-generated method stub
		this.outbound = session;

	}

	@Override
	public void onWebSocketError(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketBinary(byte[] arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onWebSocketText(String message) {
		// TODO Auto-generated method stub
		if ((outbound != null) && (outbound.isOpen()))
		{
			HashMap<String,Object> request = new HashMap<String,Object>();
			HashMap<String,Object> response = new HashMap<String,Object>();
			try 
			{
				String str_request = EncryptUtil.aesDecrypt(message);
				request = JSON.parseObject(str_request,new TypeReference<HashMap<String,Object>>(){});
				System.out.println(str_request);
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String tampgmid = (String) request.get("tampgmid");
			String wsurl = (String) request.get("wsurl");



			if(BfstrHelper.getGMBfstr(request))
			{
				if(tampgmid.equals("0"))
				{
					switch(wsurl)
					{
					case "gmlogin":
						GmLogin gmlogin=new GmLogin();
						response = gmlogin.dopost(request,outbound);
						break;
					default:
						return;
					}
					if(response.get("res").equals("true"))
					{
						gmkey = ((ArrayList<HashMap<String,String>>)response.get("data")).get(0).get("id");
					}
				}
				else
				{
					gmkey=tampgmid;
				}
				if(!gmkey.equals("0")){
					if(Initialise.getGmsession().containsKey(gmkey))
					{
						Session gmsession = Initialise.getGmsession().get(gmkey);
						if(gmsession!=outbound)
						{
							if(wsurl.equals("gmlogin"))
							{
								HashMap<String,Object> mes = new HashMap<String,Object>();
								mes.put("wsurl", "kick");
								mes.put("message", "otherlogin");
								try 
								{
									if(gmsession.isOpen())
										gmsession.getRemote().sendString(EncryptUtil.aesEncrypt(JSON.toJSONString(mes)));
								} 
								catch (IOException e) 
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								Initialise.getGmsession().put(gmkey, outbound);
								gmsession.close();
							}
							else
							{
								return;
							}
						}
						else
						{
							if(!tampgmid.equals("0"))
								try {
									response = ExecuteWsurl(wsurl, request);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}
					else
					{
						if(wsurl.equals("gmlogin"))
							Initialise.getGmsession().put(gmkey, outbound);
					}
				}
				try 
				{
					response.put("tampcount", request.get("tampcount"));
					String jasonres=JSON.toJSONString(response);
					String aesres=EncryptUtil.aesEncrypt(jasonres);
					//String aesda=EncryptUtil.aesDecrypt(aesres);
					outbound.getRemote().sendStringByFuture(aesres);
				} 
				catch (Exception e)
				{
					Bean.getTextArea().append("\r\n");
					Bean.getTextArea().append(e.getMessage());
					Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
				}
			}
		}
	}

	private HashMap<String,Object> ExecuteWsurl(String wsurl,HashMap<String,Object> request) throws Exception
	{
		HashMap<String,Object> response = new HashMap<String,Object>();

		IReceiveMessage irm=(IReceiveMessage)Bean.getCtxgm().getBean(wsurl);
		response = irm.dopost(request);
		return response;
	}

}
