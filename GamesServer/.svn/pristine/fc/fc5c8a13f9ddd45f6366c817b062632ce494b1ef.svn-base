package com.mangni.vegaplan.servletsrc.updatedata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.SignData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 客户端发送playerid&signtype; 服务器返回sign=&resign=
 * signtype = week,month
 * 
 */
public class PlayerSign implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		int howhour=3;
		String strSQL;
		String strcxSQL;
		String playerid=(String) request.get("playerid");
		String signtype=(String) request.get("signtype");
		int playerznxsignid=Integer.parseInt((String) request.get("znxsignid"));
		String znxsignid=null;
		//
		if (signtype.equals("week")) {
			boolean cansign=false;
			strcxSQL = "SELECT weekcount,lastweekdate FROM player_sign WHERE playerid="+playerid;
			Map<String, Object> weekinfo=myJdbcTemplate.queryForMap(strcxSQL);
			if (weekinfo.isEmpty()) {
				//不存在,首次
				strSQL = "INSERT INTO player_sign(playerid,weekcount,lastweekdate) VALUES("+playerid+",1,GETDATE())";
				myJdbcTemplate.update(strSQL);
				znxsignid="1";
				cansign=true;
			}else{
				int weekcount=Integer.parseInt((String)weekinfo.get("weekcount"));
				String lastweekstr=(String)weekinfo.get("lastweekdate");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date=null;
				try {
					date=sdf.parse(lastweekstr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar lastweekdate=Calendar.getInstance();
				lastweekdate.setTime(date);
				lastweekdate.add(Calendar.HOUR_OF_DAY, -howhour);
				Calendar nowdate=Calendar.getInstance();
				nowdate.add(Calendar.HOUR_OF_DAY, -howhour);
				int diffday=nowdate.get(Calendar.YEAR)-lastweekdate.get(Calendar.YEAR);
				if(diffday==0)
					diffday=nowdate.get(Calendar.DAY_OF_YEAR)-lastweekdate.get(Calendar.DAY_OF_YEAR);
				weekinfo=null;

				if(diffday==0) {
					//本日，不重复签到

				}else if(diffday==1){
					//第二天
					if (weekcount<7) {
						//未足一周
						strSQL = "UPDATE player_sign SET weekcount=weekcount+1,lastweekdate=getdate() WHERE playerid="+playerid;
						myJdbcTemplate.update(strSQL);
						znxsignid=String.valueOf(weekcount+1);
						cansign=true;
					}else{
						//满一周，重新开始
						strSQL = "UPDATE player_sign SET weekcount=1,lastweekdate=getdate() WHERE playerid="+playerid;
						myJdbcTemplate.update(strSQL);
						znxsignid="1";
						cansign=true;
					}

				}else if (diffday>1){
					//断签，重新开始
					strSQL = "UPDATE player_sign SET weekcount=1,lastweekdate=getdate() WHERE playerid="+playerid;
					myJdbcTemplate.update(strSQL);
					znxsignid="1";
					cansign=true;
				}
			}
			if(cansign)
			{
				weeksign(znxsignid,playerid);
				FinishTaskHelper.finishEverydayTask(playerid, "17", "1");
				response.put("res", "true");
			}else{
				response.put("res", "cannot");
			}
		}

		if (signtype.equals("month")) {
			strcxSQL = "SELECT monthcount, resigncount, lastmonthdate FROM player_sign WHERE playerid="+playerid;
			Map<String, Object> monthinfo=myJdbcTemplate.queryForMap(strcxSQL);
			if (monthinfo.isEmpty()) {
				//不存在,首次
				strSQL = "INSERT INTO player_sign(playerid,monthcount) VALUES("+playerid+",'0000000000000000000000000000000')";
				myJdbcTemplate.update(strSQL);
				monthinfo=myJdbcTemplate.queryForMap(strcxSQL);
			}

			String monthcount=(String)monthinfo.get("monthcount");
			int resigncount=Integer.parseInt((String)monthinfo.get("resigncount"));	
			String lastmonthstr=(String)monthinfo.get("lastmonthdate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=null;
			try {
				date=sdf.parse(lastmonthstr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar lastmonthdate=Calendar.getInstance();
			lastmonthdate.setTime(date);
			lastmonthdate.add(Calendar.HOUR_OF_DAY, -howhour);
			Calendar nowdate=Calendar.getInstance();
			nowdate.add(Calendar.HOUR_OF_DAY, -howhour);
			int nowday=nowdate.get(Calendar.DAY_OF_MONTH);
			int diffday=nowdate.get(Calendar.YEAR)-lastmonthdate.get(Calendar.YEAR);
			if(diffday==0)
				diffday=nowdate.get(Calendar.DAY_OF_YEAR)-lastmonthdate.get(Calendar.DAY_OF_YEAR);

			boolean cansign=false;
			int first0=monthcount.indexOf('0');
			if(playerznxsignid==first0+8){
				//非补签
				String newcount=getMonthcount(monthcount, first0);
				if(diffday>0){
					//未签到
					strSQL = "UPDATE player_sign SET monthcount='"+newcount+"',lastmonthdate=GETDATE() WHERE playerid="+playerid;
					myJdbcTemplate.update(strSQL);
					cansign=true;
				}else{
					//已签到
					//已签到，是否为补签
					if(playerznxsignid<nowday+8)
					{
						//补签

						/*
							int needstone=20*resigncount;
							if(needstone>100)
								needstone=100;
							strSQL = "UPDATE player_sign SET monthcount='"+newcount+"',resigncount=resigncount+1 WHERE playerid="+playerid;
							SqlHelper.Updatedb("UPDATE znx_player SET stone=stone-"+needstone+" WHERE id="+playerid);
							SqlHelper.Updatedb(strSQL);
							cansign=true;
						 */
					}
				}
			}
			if(cansign){
				response=monthsign(String.valueOf(playerznxsignid),playerid);
				FinishTaskHelper.finishEverydayTask(playerid, "18","1");
			}else{
				response.put("res", "cannot");
			}
		}
		return response;
	}

	private String getMonthcount(String oldcount,int first0)
	{
		StringBuilder newcount=new StringBuilder();
		if(oldcount==null)
		{
			oldcount="0000000000000000000000000000000";
		}
		for (int i=0;i<31;i++) 
		{
			if(i!=first0)
			{
				newcount.append(oldcount.charAt(i));
			}
			else
			{
				newcount.append('1');
			}
		}
		return newcount.toString();
	}

	private void weeksign(String dayid,String playerid)
	{
		SignData signdata = Bean.getSignweekmap().get(dayid);
		String strSql="update znx_player set stone=stone+"+signdata.getRewardvalue()+" where id = "+playerid+" ";
		//System.out.println(strSql);
		myJdbcTemplate.update(strSql);
	}
	private HashMap<String,Object> monthsign(String dayid,String playerid)
	{
		SignData signdata = Bean.getSignmonthmap().get(dayid);
		String dataid="0";
		String rewardtype = signdata.getRewardtype();
		int rewardvalue=signdata.getRewardvalue();
		int needvip=signdata.getDoubleneedviplv();
		if(needvip>0){
			if(Bean.getZnx_playermap().get(playerid).getViplv()>=needvip){
				rewardvalue=rewardvalue*2;
			}
		}
		
		dataid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.valueOf(rewardtype), signdata.getRewarditemid(), String.valueOf(rewardvalue));

		HashMap<String,Object> response=new HashMap<String,Object>();
		response.put("res", "true");
		response.put("dataid", dataid);
		response.put("znxid", signdata.getRewarditemid());
		return response;
		/*
		String strSql = "select viplv from znx_player where id = "+playerid+" ";
		List<String> tempList=SqlHelper.getMyData(strSql);
		String viplv = tempList.get(0);
		if (Integer.parseInt(gd.getPay())>0) {
			//vip双倍
			strSql="update znx_player set stone=stone+"+Integer.parseInt(gd.getStone())*2+",playerexp =playerexp+"+Integer.parseInt(gd.getExp())*2+",gold = gold+"+Integer.parseInt(gd.getGold())*2+" where id = "+playerid+" ";
			//System.out.println(strSql);
			SqlHelper.Updatedb(strSql);
			String anum = "0";
			if (gd.getArticleSum().equals("")) {

			}else{
				anum = String.valueOf(Integer.parseInt(gd.getArticleSum())*2);
			}
			EcoHelper.addArticle(playerid, gd.getArticleType(), anum, gd.getArticleLV(), gd.getTemplateIdList());
		}else{
			strSql="update znx_player set stone=stone+"+gd.getStone()+",playerexp =playerexp+"+gd.getExp()+",gold = gold+"+gd.getGold()+" where id = "+playerid+" ";
			//System.out.println(strSql);
			SqlHelper.Updatedb(strSql);
			EcoHelper.addArticle(playerid, gd.getArticleType(), gd.getArticleSum(), gd.getArticleLV(), gd.getTemplateIdList());
		}
		 */
	}	
}
