package com.mangni.vegaplan.servletsrc.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.api.Session;
import com.alibaba.fastjson.JSON;
import com.mangni.vegaplan.datatable.RECShopData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;

public class RechargeMoney extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException  
	{  
		doPost(request,response);
	}
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
	{
		//获取支付宝POST过来反馈信息
		PrintWriter out = response.getWriter();
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号	
		String out_trade_no = new String(request.getParameter("out_trade_no"));

		//支付宝交易号	
		String trade_no = new String(request.getParameter("trade_no"));

		//交易状态
		String trade_status = new String(request.getParameter("trade_status"));

		String body = new String(request.getParameter("body"));
		String buyer_id = new String(request.getParameter("buyer_id"));
		String res="false";
		String alires="fail";
		HashMap<String,String> respon=new HashMap<String,String>();
		respon.put("wsurl", "rechargemoney");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

			if(trade_status.equals("TRADE_FINISHED")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				System.out.println("TRADE_FINISHED");
				//注意：
				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				out.println("success");
			} else if (trade_status.equals("TRADE_SUCCESS")){
				//判断该笔订单是否在商户网站中已经做过处理
				//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				//如果有做过处理，不执行商户的业务程序
				//System.out.println("TRADE_SUCCESS");
				//注意：
				//付款完成后，支付宝系统发送该交易状态通知
				//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
				String [] da=StringUtils.split(body,"\\$");
				String playerid=da[0];
				String buytype=da[1];
				String total_fee=new String(request.getParameter("total_fee"));

				int ratio=1;
				String remark="";
				String firstreceive=null;
				String phonenumber=null;
				String promotion=null;
				String promotetime=null;
				/*
				if(Bean.getZnx_playermap().containsKey(playerid))
				{
					znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
					firstreceive=znx_playerdata.getFirstreceive();
					phonenumber=znx_playerdata.getPhonenumber();
					promotion=znx_playerdata.getPromotion();
					promotetime=znx_playerdata.getPromotetime();
				}
				else
				{
				 */
				MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
				Map<String,Object> pfmap=myJdbcTemplate.queryForMap("select top 1 phonenumber,firstreceive,promoter,promotetime from znx_player where id="+playerid);
				phonenumber=String.valueOf(pfmap.get("phonenumber"));
				firstreceive=String.valueOf(pfmap.get("firstreceive"));
				promotion=String.valueOf(pfmap.get("promoter"));
				promotetime=String.valueOf(pfmap.get("promotetime"));

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
				if(recshopdata.getNeedrmb()!=Double.valueOf(total_fee))
				{
					int addstone=recshopdata.getStone()*ratio;
					res=LvupHelper.AddPlayerStone(playerid, addstone, trade_no, out_trade_no, buyer_id, total_fee, buytype, remark, newreceive.toString());
					if(res.equals("true")&&promotion!=null&&!promotion.equals("")){
						res=SendHttpRequest.sendrequest("adduserconsume", "phonenumber="+phonenumber+"&payamount="+total_fee+"&promoter="+promotion+"&promotetime="+promotetime+"&serverid="+Bean.getServerid());
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
						alires="success";
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
			}
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——	
			System.out.println("success");
			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			out.println("fail");
			System.out.println("fail");
		}
		out.flush();
		out.close();
	}
}
