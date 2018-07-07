package com.mangni.vegaplan.jobscheduling;

import java.util.List;
import java.util.Map;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GetRank implements Job{
	private static List<Map<String, Object>> militaryrank;
	private static List<Map<String, Object>> fightpowerrank;
	private static List<Map<String, Object>> matchingrrank;
//	private static List<HashMap<String,String>> trialranklv1;
//	private static List<HashMap<String,String>> trialranklv2;
//	private static List<HashMap<String,String>> trialranklv3;
//	private static List<HashMap<String,String>> trialranklv4;
//	private static List<HashMap<String,String>> trialranklv5;
	private final String sql_militaryrank="SELECT TOP 20 row_number() OVER(order BY militaryrankdan DESC,militaryrank asc) AS num, id as playerid, playerlv,headimageid,fightpower, fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3, nickname, sex FROM playerrank where battletype=5";
	private final String sql_fightpowerrank="SELECT TOP 50 row_number() OVER(order BY fightpower DESC) AS num, id as playerid, playerlv, headimageid,fightpower, fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3, nickname, sex FROM playerrank where battletype=4";
	private final String sql_matchingrank="SELECT TOP 50 row_number() OVER(order BY csstar DESC) AS num , id as playerid, playerlv, headimageid,fightpower, csstar, fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3, nickname, sex FROM playerrank where battletype=1";
//	private String sql_trialranklv1="SELECT TOP 20 ROW_NUMBER() OVER(ORDER BY triallength DESC,trialusetime ASC) AS num,id,nickname,trialtime,triallength,trialusetime,triallv,fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3 FROM PLAYERRANK WHERE TRIALLV=1";
//	private String sql_trialranklv2="SELECT TOP 20 ROW_NUMBER() OVER(ORDER BY triallength DESC,trialusetime ASC) AS num,id,nickname,trialtime,triallength,trialusetime,triallv,fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3 FROM PLAYERRANK WHERE TRIALLV=2";
//	private String sql_trialranklv3="SELECT TOP 20 ROW_NUMBER() OVER(ORDER BY triallength DESC,trialusetime ASC) AS num,id,nickname,trialtime,triallength,trialusetime,triallv,fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3 FROM PLAYERRANK WHERE TRIALLV=3";
//	private String sql_trialranklv4="SELECT TOP 20 ROW_NUMBER() OVER(ORDER BY triallength DESC,trialusetime ASC) AS num,id,nickname,trialtime,triallength,trialusetime,triallv,fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3 FROM PLAYERRANK WHERE TRIALLV=4";
//	private String sql_trialranklv5="SELECT TOP 20 ROW_NUMBER() OVER(ORDER BY triallength DESC,trialusetime ASC) AS num,id,nickname,trialtime,triallength,trialusetime,triallv,fighterposition1, fighterposition2, fighterposition3,SKINID1,SKINID2,SKINID3 FROM PLAYERRANK WHERE TRIALLV=5";
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
//		Calendar calendar=Calendar.getInstance();
//		calendar.add(Calendar.HOUR_OF_DAY, -3);
//		String trialtime=" and trialtime>'"+calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)+" 03:00:00'";
		
		militaryrank=myJdbcTemplate.queryForList(sql_militaryrank);
		fightpowerrank=myJdbcTemplate.queryForList(sql_fightpowerrank);
		matchingrrank=myJdbcTemplate.queryForList(sql_matchingrank);

		
//		synchronized (sql_trialranklv1) {
//			trialranklv1=SqlHelper.getData(sql_trialranklv1+trialtime);
//		}
//		synchronized (sql_trialranklv2) {
//			trialranklv2=SqlHelper.getData(sql_trialranklv2+trialtime);
//		}
//		synchronized (sql_trialranklv3) {
//			trialranklv3=SqlHelper.getData(sql_trialranklv3+trialtime);
//		}
//		synchronized (sql_trialranklv4) {
//			trialranklv4=SqlHelper.getData(sql_trialranklv4+trialtime);
//		}
//		synchronized (sql_trialranklv5) {
//			trialranklv5=SqlHelper.getData(sql_trialranklv5+trialtime);
//		}
	}
	public static List<Map<String,Object>> getMilitaryrank(){
		return militaryrank;
	}
	public static List<Map<String,Object>> getFightpowerrank(){
		return fightpowerrank;
	}
	public static List<Map<String,Object>> getMatchingrrank(){
		return matchingrrank;
	}
//	public static List<HashMap<String, String>> getTrialranklv1() {
//		return trialranklv1;
//	}
//	public static List<HashMap<String, String>> getTrialranklv2() {
//		return trialranklv2;
//	}
//	public static List<HashMap<String, String>> getTrialranklv3() {
//		return trialranklv3;
//	}
//	public static List<HashMap<String, String>> getTrialranklv4() {
//		return trialranklv4;
//	}
//	public static List<HashMap<String, String>> getTrialranklv5() {
//		return trialranklv5;
//	}
}
