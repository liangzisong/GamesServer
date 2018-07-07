package com.mangni.vegaplan.websocketsrc;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.jettyserver.ConnectClosed;
import com.mangni.vegaplan.servletsrc.beforeplay.NicknameReg;
import com.mangni.vegaplan.servletsrc.beforeplay.UserLogin;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.BfstrHelper;
import com.mangni.vegaplan.toolshelper.EncryptUtil;

/**
 * Example EchoSocket using Listener.
 */
public class MainListener implements WebSocketListener {
	private Session outbound;
	private String playerkey="0";
	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int len) {
		//System.out.println(payload);
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		ConnectClosed cc=new ConnectClosed(playerkey,outbound);
		Bean.getExecutor().execute(cc);
		this.outbound = null;		
	}                                                                                                     

	@Override
	public void onWebSocketConnect(Session session) {
		this.outbound = session;
		Bean.getAllplayersum().setText("在线人数："+(Bean.getPlayersession().size()+1));
		//System.out.println(session.getRemoteAddress().getAddress().getHostAddress()+":lianjie");
	}

	@Override
	public void onWebSocketError(Throwable cause) {

	}

	@Override
	public void onWebSocketText(String message) {
		//outbound.getRemote().sendString(message+"1", null);
		if ((outbound != null) && (outbound.isOpen()))
		{
			HashMap<String,Object> request = new HashMap<String,Object>();
			Map<String,Object> response = new HashMap<String,Object>();
			String str_request = null;
			try 
			{
				str_request = EncryptUtil.aesDecrypt(message);
				request = JSON.parseObject(str_request,new TypeReference<HashMap<String,Object>>(){});
				System.out.println(str_request);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			String tampplayerid = (String) request.get("tampplayerid");
			String wsurl = ((String) request.get("wsurl")).toLowerCase();

			if(BfstrHelper.getBfstr(request))
			{
				if(tampplayerid.equals("0"))
				{
					switch(wsurl)
					{
					case "nicknamereg":
						NicknameReg nicknamereg=new NicknameReg();
						response = nicknamereg.method(request,outbound);
						break;
					case "userlogin":
						UserLogin userlogin=new UserLogin();
						response = userlogin.dopost(request,outbound);
						break;
					default:
						return;
					}
					if(response.get("res").equals("true"))
					{
						playerkey = String.valueOf(((List<Map<String,Object>>)response.get("data")).get(0).get("id"));
					}
				}
				else
				{
					playerkey=tampplayerid;
				}
				if(!playerkey.equals("0")){
					if(Bean.getPlayersession().containsKey(playerkey))
					{
						Session playersession = Bean.getPlayersession().get(playerkey);
						if(playersession!=outbound)
						{
							if(wsurl.equals("userlogin")||wsurl.equals("nicknamereg"))
							{
								HashMap<String,Object> mes = new HashMap<String,Object>();
								mes.put("wsurl", "kick");
								mes.put("message", "otherlogin");
								try 
								{
									if(playersession.isOpen())
										playersession.getRemote().sendString(EncryptUtil.aesEncrypt(JSON.toJSONString(mes)));
								} 
								catch (IOException e) 
								{
									e.printStackTrace();
								}
								Bean.getPlayersession().put(playerkey, outbound);
								playersession.close();
							}
							else
							{
								return;
							}
						}
						else
						{
							if(!tampplayerid.equals("0")){
								try {
									response = ExecuteWsurl(wsurl,request);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
					else
					{
						if(wsurl.equals("userlogin")||wsurl.equals("nicknamereg"))
						{
							Bean.getPlayersession().put(playerkey, outbound);
						}
					}

				}
			}
			else
			{
				return;
			}

			try 
			{
				response.put("servertime", Calendar.getInstance().getTimeInMillis());
				response.put("tampcount", request.get("tampcount"));
				String jasonres=JSON.toJSONString(response);
				System.out.println(jasonres);
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

	private HashMap<String,Object> ExecuteWsurl(String wsurl,HashMap<String,Object> request) throws Exception
	{
		HashMap<String,Object> response = new HashMap<String,Object>();
		IReceiveMessage irm=(IReceiveMessage)Bean.getCtx().getBean(wsurl);
		response = irm.dopost(request);
		return response;
	}

	private void addPlayer(List<Map<String,Object>> result){
		String playerid=String.valueOf(result.get(0).get("id"));
		String nickname=String.valueOf(result.get(0).get("nickname"));
		String sex=String.valueOf(result.get(0).get("sex"));
		String uid=String.valueOf(result.get(0).get("uid"));
		String playerlv=String.valueOf(result.get(0).get("playerlv"));
		String playerexp=String.valueOf(result.get(0).get("playerexp"));
		String viplv=String.valueOf(result.get(0).get("viplv"));
		String vipreceive=String.valueOf(result.get(0).get("vipreceive"));
		String buyvitnum=String.valueOf(result.get(0).get("buyvitnum"));
		String buyenergynum=String.valueOf(result.get(0).get("buyenergynum"));
		String buygoldnum=String.valueOf(result.get(0).get("buygoldnum"));
		String rmbstone=String.valueOf(result.get(0).get("rmbstone"));
		String firstreceive=String.valueOf(result.get(0).get("firstreceive"));
		String promotion=String.valueOf(result.get(0).get("promoter"));
		String promotetime=String.valueOf(result.get(0).get("promotetime"));

		Znx_PlayerData znx_playerdata=new Znx_PlayerData();
		znx_playerdata.setId(playerid);
		znx_playerdata.setNickname(nickname);
		znx_playerdata.setUid(uid);
		znx_playerdata.setSex(sex);
		znx_playerdata.lvlupPlayerlv(Integer.parseInt(playerlv));
		znx_playerdata.setViplv(Integer.parseInt(viplv));
		znx_playerdata.setVipreceive(vipreceive);
		znx_playerdata.setFirstreceive(firstreceive);
		znx_playerdata.setPlayerexp(Long.parseLong(playerexp));
		znx_playerdata.addBuyvitnum(Integer.parseInt(buyvitnum));
		znx_playerdata.addBuyenergynum(Integer.parseInt(buyenergynum));
		znx_playerdata.addBuygoldnum(Integer.parseInt(buygoldnum));
		znx_playerdata.UpUsedfightersp(Integer.parseInt(String.valueOf(result.get(0).get("usedfightersp"))));
		znx_playerdata.addRmbstone(Double.parseDouble(rmbstone));
		znx_playerdata.setPromotion(promotion);
		znx_playerdata.setPromotetime(promotetime);
		Bean.getZnx_playermap().put(playerid, znx_playerdata);
	}
}
