package com.mangni.vegaplan.jobscheduling;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.datatable.ChampionsData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class ChampionsTopMail implements Job {
	public void sendChampionsMail(){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		myJdbcTemplate.update("update player_champions set isfinish=0");
		String[] gettype=new String [5];
		gettype[0]="finishlv>=1 and finishlv<=35 order by battletime asc,sumhurt desc";
		gettype[1]="finishlv>=36 and finishlv<=55 order by battletime asc,sumhurt desc";
		gettype[2]="finishlv>=56 and finishlv<=75 order by battletime asc,sumhurt desc";
		gettype[3]="finishlv>=76 and finishlv<=90 order by battletime asc,sumhurt desc";
		gettype[4]="finishlv>=91 and finishlv<=100 order by battletime asc,sumhurt desc";

		Calendar nowdate=Calendar.getInstance();

		String sqltime=null;

		String sqltimemax=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"00:05:00";

		nowdate.add(Calendar.DAY_OF_MONTH, -1);

		String sqltimemin=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"20:00:00";

		sqltime="(updatetime>'"+sqltimemin+"' and updatetime<'"+sqltimemax+"') and ";

		for(int i=0;i<gettype.length;i++){

			String sql="select top 20 playerid from player_champions where "+sqltime+gettype[i];

			List<Map<String,Object>> playeridlist=myJdbcTemplate.queryForList(sql);
			
			if(playeridlist==null){
				continue;
			}

			for(int j=0;j<playeridlist.size();j++){

				String goodsid="";

				String goodsnum="";

				String goodsid2="";
				
				String goodsnum2="";
				
				String playerid=String.valueOf(playeridlist.get(j).get("playerid"));

				ChampionsData championsdata=Bean.getChampionsmap().get(String.valueOf(i+1));

				if(j==0){	
					goodsid=championsdata.getFirstrewardgoodsid();
					goodsnum=championsdata.getFirstrewardnum();
					goodsid2=championsdata.getFirstrewardgoodsid2();
					goodsnum2=championsdata.getFirstrewardnum2();
				}else if(j>0&&j<5){
					goodsid=championsdata.getThe2_5rewardgoodsid();
					goodsnum=championsdata.getThe2_5rewardnum();
					goodsid2=championsdata.getThe2_5rewardgoodsid2();
					goodsnum2=championsdata.getThe2_5rewardnum2();
				}else if(j>=5&&j<10){
					goodsid=championsdata.getThe6_10rewardgoodsid();
					goodsnum=championsdata.getThe6_10rewardnum();
					goodsid2=championsdata.getThe6_10rewardgoodsid2();
					goodsnum2=championsdata.getThe6_10rewardnum2();
				}else if(j>=10&&j<20){
					goodsid=championsdata.getThe11_20rewardgoodsid();
					goodsnum=championsdata.getThe11_20rewardnum();
					goodsid2=championsdata.getThe11_20rewardgoodsid2();
					goodsnum2=championsdata.getThe11_20rewardnum2();
				}

				String insertsql="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM,GOODS2TYPE,GOODS2ID,GOODS2NUM,MAILCONTENT) VALUES(?,?,?,?,?,?,?,?,?,?)";
				String[] insertsqlpara={playerid,"2","狩猎排行奖励","goods",goodsid,goodsnum,"goods",goodsid2,goodsnum2,"恭喜你在狩猎排行中获得第"+(j+1)+"名！"};
				SendMessageHelper.sendMail(insertsql, insertsqlpara, playerid);

			}
		}
	}
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		sendChampionsMail();
	}
}

