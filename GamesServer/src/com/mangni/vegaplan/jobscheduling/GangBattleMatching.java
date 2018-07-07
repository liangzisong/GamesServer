package com.mangni.vegaplan.jobscheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.mangni.vegaplan.servletsrc.beforeplay.UserLogin;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GangBattleMatching implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		myJdbcTemplate.update("update player_gang set battleid=0");
		List<Map<String,Object>> ganglist=myJdbcTemplate.queryForList("select * from player_gang order by points asc");
		if(ganglist==null)
			try {
				throw new Exception("暂无帮派！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		int gangsum=ganglist.size();
		if(gangsum<=1){
			try {
				throw new Exception("参展军团小于2个！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(gangsum<=9){
			matching(ganglist,myJdbcTemplate);
		}else{
			int groupnum=gangsum/9;
			int surplus=gangsum%9;
			if(surplus==1){
				List<Map<String,Object>> grouplist1=new ArrayList<Map<String,Object>>();
				grouplist1.add(ganglist.get(0));
				grouplist1.add(ganglist.get(1));
				List<Map<String,Object>> grouplist2=new ArrayList<Map<String,Object>>();
				for(int i=2;i<10;i++){
					grouplist2.add(ganglist.get(i));
				}
				matching(grouplist1,myJdbcTemplate);
				matching(grouplist2,myJdbcTemplate);
				groupmatching(ganglist,groupnum-1,10,myJdbcTemplate);
			}else{
				List<Map<String,Object>> grouplist1=new ArrayList<Map<String,Object>>();
				for(int i=0;i<surplus;i++){
					grouplist1.add(ganglist.get(i));
				}
				matching(grouplist1,myJdbcTemplate);
				groupmatching(ganglist,groupnum,surplus,myJdbcTemplate);
			}
		}
	}
	
	public void groupmatching(List<Map<String,Object>> ganglist,int groupnum,int start,MyJdbcTemplate myJdbcTemplate){
		List<Map<String,Object>> grouplist=new ArrayList<Map<String,Object>>();
		for(int i=0;i<groupnum;i++){
			for(int j=0;j<9;j++){
				int position=9*i+start+j;
				grouplist.add(ganglist.get(position));
			}
			matching(ganglist,myJdbcTemplate);
		}
	}
	
	
	public void matching(List<Map<String,Object>> ganglist,MyJdbcTemplate myJdbcTemplate){
		int size=ganglist.size();
		String sqladd="select case when max(battleid) is null then 1 else max(battleid)+1 end from player_gang";
		int battleid=myJdbcTemplate.queryForObject(sqladd, Integer.class);
		switch(size){
		case 2:
			String sql21="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam21={1,ganglist.get(1).get("id")};
			
			String sql22="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam22={2,ganglist.get(0).get("id")};
			myJdbcTemplate.update(sql21,sqlparam21);
			myJdbcTemplate.update(sql22,sqlparam22);
			break;
		case 3:
			String sql31="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam31={1,ganglist.get(2).get("id")};
			
			String sql32="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam32={2,ganglist.get(1).get("id")};
			
			String sql33="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam33={3,ganglist.get(0).get("id")};
			myJdbcTemplate.update(sql31,sqlparam31);
			myJdbcTemplate.update(sql32,sqlparam32);
			myJdbcTemplate.update(sql33,sqlparam33);
			break;
		case 4:
			String sql41="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam41={1,ganglist.get(3).get("id")};
			
			String sql42="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam42={2,ganglist.get(2).get("id")};
			
			String sql43="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam43={1,ganglist.get(1).get("id")};
			
			String sql44="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam44={2,ganglist.get(0).get("id")};
			
			myJdbcTemplate.update(sql41,sqlparam41);
			myJdbcTemplate.update(sql42,sqlparam42);
			myJdbcTemplate.update(sql43,sqlparam43);
			myJdbcTemplate.update(sql44,sqlparam44);
			break;
		case 5:
			String sql51="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam51={1,ganglist.get(4).get("id")};
			
			String sql52="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam52={2,ganglist.get(3).get("id")};
			
			String sql53="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam53={3,ganglist.get(2).get("id")};
			
			String sql54="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam54={1,ganglist.get(1).get("id")};
			
			String sql55="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam55={2,ganglist.get(0).get("id")};
			
			myJdbcTemplate.update(sql51,sqlparam51);
			myJdbcTemplate.update(sql52,sqlparam52);
			myJdbcTemplate.update(sql53,sqlparam53);
			myJdbcTemplate.update(sql54,sqlparam54);
			myJdbcTemplate.update(sql55,sqlparam55);
			break;
		case 6:
			String sql61="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam61={1,ganglist.get(5).get("id")};
			
			String sql62="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam62={2,ganglist.get(4).get("id")};
			
			String sql63="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam63={3,ganglist.get(3).get("id")};
			
			String sql64="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam64={1,ganglist.get(2).get("id")};
			
			String sql65="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam65={2,ganglist.get(1).get("id")};
			
			String sql66="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam66={3,ganglist.get(0).get("id")};
			
			myJdbcTemplate.update(sql61,sqlparam61);
			myJdbcTemplate.update(sql62,sqlparam62);
			myJdbcTemplate.update(sql63,sqlparam63);
			myJdbcTemplate.update(sql64,sqlparam64);
			myJdbcTemplate.update(sql65,sqlparam65);
			myJdbcTemplate.update(sql66,sqlparam66);
			break;
		case 7:
			String sql71="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam71={1,ganglist.get(6).get("id")};
			
			String sql72="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam72={2,ganglist.get(5).get("id")};
			
			String sql73="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam73={3,ganglist.get(4).get("id")};
			
			String sql74="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam74={1,ganglist.get(3).get("id")};
			
			String sql75="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam75={2,ganglist.get(2).get("id")};
			
			String sql76="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam76={1,ganglist.get(1).get("id")};
			
			String sql77="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam77={2,ganglist.get(0).get("id")};
			
			myJdbcTemplate.update(sql71,sqlparam71);
			myJdbcTemplate.update(sql72,sqlparam72);
			myJdbcTemplate.update(sql73,sqlparam73);
			myJdbcTemplate.update(sql74,sqlparam74);
			myJdbcTemplate.update(sql75,sqlparam75);
			myJdbcTemplate.update(sql76,sqlparam76);
			myJdbcTemplate.update(sql77,sqlparam77);
			break;
		case 8:
			String sql81="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam81={1,ganglist.get(7).get("id")};
			
			String sql82="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam82={2,ganglist.get(6).get("id")};
			
			String sql83="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam83={3,ganglist.get(5).get("id")};
			
			String sql84="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam84={1,ganglist.get(4).get("id")};
			
			String sql85="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam85={2,ganglist.get(3).get("id")};
			
			String sql86="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam86={3,ganglist.get(2).get("id")};
			
			String sql87="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam87={1,ganglist.get(1).get("id")};
			
			String sql88="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam88={2,ganglist.get(0).get("id")};
			
			myJdbcTemplate.update(sql81,sqlparam81);
			myJdbcTemplate.update(sql82,sqlparam82);
			myJdbcTemplate.update(sql83,sqlparam83);
			myJdbcTemplate.update(sql84,sqlparam84);
			myJdbcTemplate.update(sql85,sqlparam85);
			myJdbcTemplate.update(sql86,sqlparam86);
			myJdbcTemplate.update(sql87,sqlparam87);
			myJdbcTemplate.update(sql88,sqlparam88);
			break;
		case 9:
			Random random=new Random();
			List<Integer> ranlist1=new ArrayList<Integer>();//6-9
			List<Integer> ranlist2=new ArrayList<Integer>();//1-5
			ranlist1.add(1);ranlist1.add(2);ranlist1.add(3);ranlist1.add(4);
			ranlist2.add(5);ranlist2.add(6);ranlist2.add(7);ranlist2.add(8);ranlist2.add(9);
			int rannum=random.nextInt(ranlist2.size()-1);
			int sqlint11=ranlist2.get(rannum);//1-5 第1次随机
			ranlist2.remove(rannum);
			
			rannum=random.nextInt(ranlist2.size()-1);
			int sqlint12=ranlist2.get(rannum);//1-5 第2次随机
			ranlist2.remove(rannum);
			
			rannum=random.nextInt(ranlist1.size()-1);
			int sqlint13=ranlist1.get(rannum);//6-9 第1次随机
			ranlist1.remove(rannum);
			
			rannum=random.nextInt(ranlist2.size()-1);
			int sqlint21=ranlist2.get(rannum);//1-5 第3次随机
			ranlist2.remove(rannum);
			
			rannum=random.nextInt(ranlist2.size()-1);
			int sqlint22=ranlist2.get(rannum);//1-5 第4次随机
			ranlist2.remove(rannum);
			
			rannum=random.nextInt(ranlist1.size()-1);
			int sqlint23=ranlist1.get(rannum);//6-9 第2次随机
			ranlist1.remove(rannum);
			
			int sqlint31=ranlist2.get(0);
			int sqlint32=ranlist1.get(0);
			int sqlint33=ranlist1.get(1);
			
			String sql91="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam91={1,sqlint11};
			
			String sql92="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam92={2,sqlint12};
			
			String sql93="update player_gang set battleid="+battleid+",battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam93={3,sqlint13};
			
			String sql94="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam94={1,sqlint21};
			
			String sql95="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam95={2,sqlint22};
			
			String sql96="update player_gang set battleid="+battleid+"+1,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam96={3,sqlint23};
			
			String sql97="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam97={1,sqlint31};
			
			String sql98="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam98={2,sqlint32};
			
			String sql99="update player_gang set battleid="+battleid+"+2,battleposition=?,battletime=getdate() where id=?";
			Object[] sqlparam99={3,sqlint33};
			
			myJdbcTemplate.update(sql91,sqlparam91);
			myJdbcTemplate.update(sql92,sqlparam92);
			myJdbcTemplate.update(sql93,sqlparam93);
			myJdbcTemplate.update(sql94,sqlparam94);
			myJdbcTemplate.update(sql95,sqlparam95);
			myJdbcTemplate.update(sql96,sqlparam96);
			myJdbcTemplate.update(sql97,sqlparam97);
			myJdbcTemplate.update(sql98,sqlparam98);
			myJdbcTemplate.update(sql99,sqlparam99);
			break;
		default:
			try {
				throw new Exception("帮战匹配算法异常");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		new UserLogin().loadGang();
	}

}
