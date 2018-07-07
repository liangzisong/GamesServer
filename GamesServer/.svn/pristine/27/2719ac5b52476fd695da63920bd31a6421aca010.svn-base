package com.mangni.vegaplan.jobscheduling;

import java.util.Calendar;
import java.util.Date;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

public class MainScheduler {
	private static Scheduler sched;
	public MainScheduler()
	{
		try {
			GetRank gr=new GetRank();
			gr.execute(null);
			// 创建一个Scheduler 
			SchedulerFactory schedFact =  new StdSchedulerFactory();
			sched = schedFact.getScheduler();
			sched.start();
			// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
			//该Job负责定义需要执行任务
			//刷新排行榜
			JobDetail getRankjob = new JobDetail("getRank", "getRankGroup",GetRank.class);
			// 从当前时间的下一秒开始执行
			Calendar rightnow=Calendar.getInstance();
			rightnow.set(Calendar.MINUTE,(rightnow.get(Calendar.MINUTE)/10+1)*10);
			rightnow.set(Calendar.SECOND,0);
			rightnow.set(Calendar.MILLISECOND,0);
			//更新排行
			SimpleTrigger ranktrigger = new SimpleTrigger("RankTrigger","getRankGroup",rightnow.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,10*60*1000);
			//重置领取体力
			JobDetail resetznx_playerjob = new JobDetail("Reset", "ResetGroup",ResetZnx_Player.class);
			//发放猎杀奖励
			JobDetail championsjob = new JobDetail("ChampionsReward", "ChampionsGroup",ChampionsTopMail.class);
			//发放试练奖励
			JobDetail trialjob = new JobDetail("TrialReward","TrialGroup",TrialTopMail.class);
			//发放斗牛排行榜奖励
			JobDetail MilitaryRankjob = new JobDetail("MilitaryRankTopMail","MilitaryRankGroup",MilitaryRankTopMail.class);
			//发放战力排行榜奖励
			JobDetail PowerRankjob = new JobDetail("PowerRankTopMail","PowerRankGroup",PowerRankTopMail.class);
			//刷新赏金任务
			JobDetail MercenaryTaskRefreshjob = new JobDetail("MercenaryTaskRefresh","MercenaryTaskRefreshGroup",MercenaryTaskRefresh.class);
			//更新大服务器在线人数
			JobDetail UpdateOnlinecountjob=new  JobDetail("UpdateOnlinecount","UpdateOnlinecountGroup",UpdateOnlinecount.class);
			//刷新每日任务
			JobDetail RefreshDailytaskjob=new  JobDetail("RefreshDailytask","RefreshDailytaskGroup",RefreshDailytask.class);
			//获得奖励排行
			JobDetail GetRewardRankjob=new JobDetail("GetRewardRank","GetRewardRankGroup",GetRewardRank.class);
			//赛季回收房间
			JobDetail competitionsensonroomjob=new JobDetail("CompetitionSensonRoom","CompetitionSensonRoomGroup",CompetitionSensonRoomJob.class);
			//帮战匹配
			JobDetail gangbattlematchingjob=new JobDetail("GangBattleMatching","GangBattleMatchingGroup",GangBattleMatching.class);
			//帮战12分钟结算
			JobDetail gangbattleminbalancejob=new JobDetail("GangBattleMinBalance","GangBattleMinBalanceGroup",GangBattleMinBalance.class);
			//帮战日结算
			JobDetail gangbattledaybalancejob=new JobDetail("GangBattleDayBalance","GangBattleDayBalanceGroup",GangBattleDayBalance.class);
			
			Calendar AM0=Calendar.getInstance();//每日0点
			AM0.set(Calendar.HOUR_OF_DAY, 0);
			AM0.set(Calendar.MINUTE, 0);
			AM0.set(Calendar.SECOND,1);
			AM0.set(Calendar.MILLISECOND,0);
			
			SimpleTrigger AM0trigger1 = new SimpleTrigger("AM0Trigger1","AM0triggerGroup1",AM0.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			
			Calendar AM3=Calendar.getInstance();//每日凌晨3点
			AM3.set(Calendar.HOUR_OF_DAY, 3);
			AM3.set(Calendar.MINUTE, 0);
			AM3.set(Calendar.SECOND,0);
			AM3.set(Calendar.MILLISECOND,0);
			SimpleTrigger AM3trigger1 = new SimpleTrigger("AM3Trigger1","AM3triggerGroup1",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger2 = new SimpleTrigger("AM3Trigger2","AM3triggerGroup2",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger3 = new SimpleTrigger("AM3Trigger3","AM3triggerGroup3",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger4 = new SimpleTrigger("AM3Trigger4","AM3triggerGroup4",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger5 = new SimpleTrigger("AM3Trigger5","AM3triggerGroup5",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger6 = new SimpleTrigger("AM3Trigger6","AM3triggerGroup6",new Date(),null,SimpleTrigger.REPEAT_INDEFINITELY,5*60*1000);
			SimpleTrigger AM3trigger7 = new SimpleTrigger("AM3Trigger7","AM3triggerGroup7",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger8 = new SimpleTrigger("AM3Trigger8","AM3triggerGroup8",AM3.getTime(),null,SimpleTrigger.REPEAT_INDEFINITELY,24*60*60*1000);
			SimpleTrigger AM3trigger9 = new SimpleTrigger("AM3Trigger9","AM3triggerGroup9",new Date(),null,SimpleTrigger.REPEAT_INDEFINITELY,95*1000);
			SimpleTrigger AM3trigger10 = new SimpleTrigger("AM3Trigger10","AM3triggerGroup10",new Date(),null,SimpleTrigger.REPEAT_INDEFINITELY,12*60*1000);
			
			Calendar furture=Calendar.getInstance();//未来某个时间
			furture.set(Calendar.YEAR, 2017);
			furture.set(Calendar.MONTH, 6);
			furture.set(Calendar.DAY_OF_MONTH, 8);
			furture.set(Calendar.HOUR_OF_DAY, 9);
			furture.set(Calendar.MINUTE, 00);
			furture.set(Calendar.SECOND,1);
			furture.set(Calendar.MILLISECOND,0);
			SimpleTrigger furturetrigger1 = new SimpleTrigger("furtureTrigger1","triggerGroup1",furture.getTime(),null,0,24*60*60*1000);
			
			CronTrigger everymonday=new CronTrigger("ererymondaytrigger1","everymondaygroup1");
			//CronExpression cexp = new CronExpression("0 0 12 ? * FRI");
			CronExpression cexp = new CronExpression("0 0 6 ? * MON");//创建表达式 ,每周一6点
			everymonday.setCronExpression(cexp); 
			
			// 用scheduler将JobDetail与Trigger关联在一起，开始调度任务	
			sched.scheduleJob(getRankjob, ranktrigger);
			sched.scheduleJob(resetznx_playerjob, AM3trigger1);
			sched.scheduleJob(championsjob,AM3trigger2);
			sched.scheduleJob(MilitaryRankjob,AM3trigger3);
			sched.scheduleJob(PowerRankjob,AM3trigger4);
			sched.scheduleJob(MercenaryTaskRefreshjob,AM3trigger5);
			sched.scheduleJob(UpdateOnlinecountjob,AM3trigger6);
			sched.scheduleJob(RefreshDailytaskjob,AM3trigger7);
			sched.scheduleJob(trialjob,AM3trigger8);
			sched.scheduleJob(GetRewardRankjob,furturetrigger1);
			sched.scheduleJob(competitionsensonroomjob,AM3trigger9);
			sched.scheduleJob(gangbattlematchingjob,everymonday);
			sched.scheduleJob(gangbattleminbalancejob,AM3trigger10);
			sched.scheduleJob(gangbattledaybalancejob,AM0trigger1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static Scheduler getSched() {
		return sched;
	}
	public static void setSched(Scheduler sched) {
		MainScheduler.sched = sched;
	}
}
