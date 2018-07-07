package com.mangni.vegaplan.jobscheduling;

import java.util.ArrayList;
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

public class TrialTopMail implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		sendMail();	
	}
	
	private void sendMail(){
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String[] gettype=new String [5];
		gettype[0]="triallv=1 order by triallength desc,trialusetime asc";
		gettype[1]="triallv=2 order by triallength desc,trialusetime asc";
		gettype[2]="triallv=3 order by triallength desc,trialusetime asc";
		gettype[3]="triallv=4 order by triallength desc,trialusetime asc";
		gettype[4]="triallv=5 order by triallength desc,trialusetime asc";
		
		Calendar nowdate=Calendar.getInstance();

		String sqltime=null;

		String sqltimemax=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"00:05:00";

		nowdate.add(Calendar.DAY_OF_MONTH, -1);

		String sqltimemin=nowdate.get(Calendar.YEAR)+"-"+(nowdate.get(Calendar.MONTH)+1)+"-"+nowdate.get(Calendar.DAY_OF_MONTH)+" "+"10:00:00";

		sqltime="(trialtime>'"+sqltimemin+"' and trialtime<'"+sqltimemax+"') and ";
		
		List<List<Map<String,Object>>> trialranklist=new ArrayList<List<Map<String,Object>>>();
		
		for(int i=0;i<gettype.length;i++){

			String sql="select top 20 id from playerrank with(updlock) where "+sqltime+gettype[i];

			trialranklist.add(myJdbcTemplate.queryForList(sql));	
			
		}

		for(int i=0;i<trialranklist.size();i++){

			List<Map<String,Object>> playeridlist=trialranklist.get(i);
			
			if(playeridlist==null){
				continue;
			}

			for(int j=0;j<playeridlist.size();j++){

				String goodsid="";

				String goodsnum="";

				String goodsid2="";
				
				String goodsnum2="";
				
				String playerid=String.valueOf(playeridlist.get(j).get("id"));

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
				String[] insertsqlpara={playerid,"2","试炼排行奖励","goods",goodsid,goodsnum,"goods",goodsid2,goodsnum2,"恭喜你在试炼排行中获得第"+(j+1)+"名！"};
				SendMessageHelper.sendMail(insertsql, insertsqlpara, playerid);
			}
		}
		
	}

}
