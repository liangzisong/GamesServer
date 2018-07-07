package com.mangni.vegaplan.servletsrc.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.datatable.RECShopData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;


public class RechargeMoneySdk extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException  
	{  
		doPost(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		PrintWriter out = response.getWriter();
		Map<String,String[]> requestParams=request.getParameterMap();
		String app=requestParams.get("app")[0];
		String cbi=requestParams.get("cbi")[0];
		String ct=requestParams.get("ct")[0];
		String fee=requestParams.get("fee")[0];
		String pt=requestParams.get("pt")[0];
		String sdk=requestParams.get("sdk")[0];
		String ssid=requestParams.get("ssid")[0];
		String st=requestParams.get("st")[0];
		String tcd=requestParams.get("tcd")[0];
		String uid=requestParams.get("uid")[0];
		String ver=requestParams.get("ver")[0];
		String sign=requestParams.get("sign")[0];

		String str="app="+app+"&cbi="+cbi+"&ct="+ct+"&fee="+fee+"&pt="+pt+"&sdk="+sdk+"&ssid="+ssid+"&st="+st+"&tcd="+tcd+"&uid="+uid+"&ver="+ver;
		str+=Bean.getLsdkkey();
		String md5str=EncryptUtil.EncoderByMd5(str);
		if(!md5str.equals(sign.toLowerCase()))
			return;

		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String isc=myJdbcTemplate.queryForObject("SELECT TOP 1 playertradenum FROM PLAYER_VIP_PAY WHERE playertradenum=?",new String[]{ssid},String.class);
		if(isc!=null){
			out.println("SUCCESS");//请不要修改或删除
			System.out.println("SUCCESS");
			out.flush();
			out.close();
			return;
		}
		
		String res="false";
		String alires="fail";
		HashMap<String,String> respon=new HashMap<String,String>();
		respon.put("wsurl", "rechargemoney");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//


		String [] da=StringUtils.split(cbi,"$");
		if(da.length!=2)
			return;
		String playerid=da[0];
		String buytype=da[1];

		int ratio=1;
		String remark="";
		String firstreceive=null;
		String promotion=null;
		String promotetime=null;
		if(Bean.getZnx_playermap().containsKey(playerid))
		{
			Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
			firstreceive=znx_playerdata.getFirstreceive();
			uid=znx_playerdata.getUid();
			promotion=znx_playerdata.getPromotion();
			promotetime=znx_playerdata.getPromotetime();
		}
		else
		{
			Map<String,Object> pfmap=myJdbcTemplate.queryForMap("select top 1 uid,firstreceive,promoter,promotetime from znx_player where id="+playerid);
			uid=String.valueOf(pfmap.get("uid"));
			firstreceive=String.valueOf(pfmap.get("firstreceive"));
			promotion=String.valueOf(pfmap.get("promoter"));
			promotetime=String.valueOf(pfmap.get("promotetime"));
		}
		if(firstreceive==null||firstreceive.equals(""))
			firstreceive="0000000";
		int buyid=Integer.parseInt(buytype);
		StringBuilder newreceive=new StringBuilder();
		if(buyid<=7)
		{
			if(firstreceive.charAt(buyid-1)==48)
			{
				ratio=2;
				remark="first reward";
				for (int i=0;i<firstreceive.length();i++) 
				{
					if(i!=buyid-1)
					{
						newreceive.append(String.valueOf(firstreceive.charAt(i)));
					}
					else
					{
						newreceive.append('1');
					}
				}
			}
			else
			{
				newreceive.append(firstreceive);
			}
		}
		else
		{
			newreceive.append(firstreceive);
		}
		RECShopData recshopdata=Bean.getRecshopmap().get(buytype);
		if(recshopdata.getNeedrmb()==(Double.valueOf(fee)/100))
		{
			int addstone=recshopdata.getStone()*ratio;
			res=LvupHelper.AddPlayerStone(playerid, addstone, ssid, tcd, uid, String.valueOf((Double.valueOf(fee)/100)), buytype, remark, newreceive.toString());
			if(res.equals("true")&&promotion!=null&&!promotion.equals("")){
				res=SendHttpRequest.sendrequest("adduserconsume", str+"&uid="+uid+"&sdkid="+sdk+"&payamount="+(Double.valueOf(fee)/100)+"&promoter="+promotion+"&promotetime="+promotetime+"&serverid="+Bean.getServerid());
			}
			
			//刷新会员卡时间
			if(buyid==8)
			{
				FinishTaskHelper.finishEverydayTask(playerid, "18", "1");
			}
			else if(buyid==9)
			{
				FinishTaskHelper.finishEverydayTask(playerid, "19", "1");
			}
			else if(buyid==10)
			{
				FinishTaskHelper.finishEverydayTask(playerid, "20", "1");
			}
			respon.put("res", res);
			if(res.equals("true")){
				if(Bean.getZnx_playermap().containsKey(playerid))
					respon.put("viplv", Bean.getZnx_playermap().get(playerid).getViplv()+"");
				alires="SUCCESS";
			}
		}

		if(Bean.getPlayersession().containsKey(playerid))
		{
			Session session=Bean.getPlayersession().get(playerid);
			if(session.isOpen())
			{
				String jasonres=JSON.toJSONString(respon);
				String aesres=EncryptUtil.aesEncrypt(jasonres);
				//String aesda=EncryptUtil.aesDecrypt(aesres);
				Bean.getPlayersession().get(playerid).getRemote().sendStringByFuture(aesres);
			}
		}
		out.println(alires);//请不要修改或删除
		System.out.println("SUCCESS");
		out.flush();
		out.close();
	}
}
