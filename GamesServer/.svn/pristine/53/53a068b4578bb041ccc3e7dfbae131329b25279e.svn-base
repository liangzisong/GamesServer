package com.mangni.vegaplan.servletsrc.gang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GangBattleSite implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		int topositionx=Integer.parseInt((String)request.get("topositionx"));
		int topositiony=Integer.parseInt((String)request.get("topositiony"));

		Calendar now=Calendar.getInstance();
		if(now.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY){
			if(now.get(Calendar.HOUR_OF_DAY)<3){//周一3点开启
				response.put("res", "close");
				return response;
			}
		}
		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		int battleid=Bean.getZnx_gangmap().get(playerdata.getGangid()).getBattleid();
		String posid=myJdbcTemplate.queryForObject("select top 1 playerid from player_ganginfo A with(updlock) left join player_gang B with(updlock) on A.gangid=B.id where gangid in(select id from player_gang where battleid="+battleid+" and id!="+playerdata.getGangid()+") and A.positiontime>B.battletime and battlepositionx="+topositionx+" and battlepositiony="+topositiony, String.class);
		if(posid!=null){//存在其他帮派成员，不能占领
			response.put("res", "haveplayer");
			return response;
		}

		String positiontime=playerdata.getPositiontime();
		if(positiontime!=null&&!"null".equals(positiontime)){
			try {
				Calendar positiondate=Calendar.getInstance();
				positiondate.setTime(Bean.getDateFormat().parse(positiontime));
				positiondate.setFirstDayOfWeek(Calendar.MONDAY);
				now.setFirstDayOfWeek(Calendar.MONDAY);
				positiondate.add(Calendar.HOUR_OF_DAY, -1);//1小时余量
				now.add(Calendar.HOUR_OF_DAY, -1);//1小时余量

				if(!isSameDate(positiondate, now)){//是否处于同一周内
					initPosition(playerid,myJdbcTemplate);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			initPosition(playerid,myJdbcTemplate);
		}
		int positionx=playerdata.getBattlepositionx();
		int positiony=playerdata.getBattlepositiony();

		if(!canChallenge(positionx, positiony, topositionx, topositiony)){//移动距离错误，移动失败
			response.put("res", "false");
			return response;
		}
		String res=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ACTPOWER, "0", "-10");
		if("-1".equals(res)){
			response.put("res", "not enough");
			return response;
		}
		myJdbcTemplate.update("update player_ganginfo set battlepositionx="+topositionx+",battlepositiony="+topositiony+",positiontime=getdate() where playerid="+playerid);
		playerdata.setBattlepositionx(topositionx);
		playerdata.setBattlepositiony(topositiony);
		playerdata.setPositiontime(Bean.getDateFormat().format(new Date()));
		response.put("res", "true");
		return response;
	}

	public static boolean canChallenge(int positionx,int positiony,int topositionx,int topositiony){
		//10X+Y后，大数为X1Y1，小数为X2Y2，得数必须为1或0才能移动
		int pnum=positionx*10+positiony;//原始位置
		int tpnum=topositionx*10+topositiony;//要移动到的位置
		int x1=0,x2=0,y1=0,y2=0;
		if(pnum==tpnum)//原位置与移动位置相同，移动失败
			return false;

		if(pnum>tpnum){
			x1=positionx;y1=positiony;
			x2=topositionx;y2=topositiony;
		}else{
			x2=positionx;y2=positiony;
			x1=topositionx;y1=topositiony;
		}

		if(!((x1-x2==1||x1-x2==0)&&(y1-y2==1||y1-y2==0))){//移动距离错误，移动失败
			return false;
		}
		return true;
	}

	public static int[] initPosition(String playerid,MyJdbcTemplate myJdbcTemplate){
		int gangposition=myJdbcTemplate.queryForObject("select top 1 battleposition from player_ganginfo A with(updlock) left join player_gang B with(updlock) on A.gangid=B.id where A.playerid="+playerid, Integer.class);
		int px=0;
		int py=0;
		if(gangposition==1){
			px=1;
			py=1;
		}else if(gangposition==2){
			px=7;
			py=1;
		}else{
			px=7;
			py=7;
		}
		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		int viplv=0;
		if(playerdata!=null){
			viplv=playerdata.getViplv();
		}else{
			viplv=myJdbcTemplate.queryForObject("select viplv from znx_player where id="+playerid, Integer.class);
		}
		
		myJdbcTemplate.update("update player_ganginfo set endurance=100+5*"+viplv+",battlepositionx="+px+",battlepositiony="+py+" where playerid="+playerid);
		
		if(playerdata!=null){
			Znx_GangData gangdata=Bean.getZnx_gangmap().get(playerdata.getGangid());
			playerdata.setEndurance(100+5*playerdata.getViplv()+gangdata.getFlameslv());
			playerdata.setBattlepositionx(px);
			playerdata.setBattlepositiony(py);
		}
		int[] i={px,py};
		return i;

	}

	public static boolean isSameDate(Calendar cal1,Calendar cal2){
		int subYear = cal1.get(Calendar.YEAR)-cal2.get(Calendar.YEAR);
		//subYear==0,说明是同一年
		if(subYear == 0){
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		//例子:cal1是"2005-1-1"，cal2是"2004-12-25"
		//java对"2004-12-25"处理成第52周
		// "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
		//大家可以查一下自己的日历
		//处理的比较好
		//说明:java的一月用"0"标识，那么12月用"11"
		else if(subYear==1 && cal2.get(Calendar.MONTH)==11){
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		//例子:cal1是"2004-12-31"，cal2是"2005-1-1"
		else if(subYear==-1 && cal1.get(Calendar.MONTH)==11){
			if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;

		}
		return false;
	}
}
