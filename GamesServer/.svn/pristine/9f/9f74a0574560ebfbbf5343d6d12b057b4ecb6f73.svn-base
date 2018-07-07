package com.mangni.vegaplan.servletsrc.updatedata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.SkilllvData;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FighterSkillUplv implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) throws Exception {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String fighterid=(String)request.get("fighterid");
		String skillinfo=(String)request.get("skillinfo");//1,2,3,4哪一个技能
		int breaknum=Integer.parseInt((String)request.get("breaknum"));//升级次数
		List<String> res=new ArrayList<String>();
		Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
		Class<? extends Znx_FighterData> clazz=znx_fighterdata.getClass();
		Method methodgetlv=clazz.getMethod("getFighterskill"+skillinfo+"lv");
		String skilllv=String.valueOf(methodgetlv.invoke(znx_fighterdata));
		for(int i=0;i<breaknum;i++){
			String resinfo=skillUplv(playerid, fighterid, skillinfo, skilllv);
			res.add(resinfo);
		}

		response.put("res", res);
		return response;
	}

	private String skillUplv(String playerid,String fighterid,String skillinfo,String oldlv) throws Exception{
		String res="false";
		Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
		Class<? extends Znx_FighterData> clazz=znx_fighterdata.getClass();
		Method methodgetlv=clazz.getMethod("getFighterskill"+skillinfo+"lv");
		String skilllv=String.valueOf(methodgetlv.invoke(znx_fighterdata));
		Method methodgetexp=clazz.getMethod("getFighterskill"+skillinfo+"exp");
		String skillexp=String.valueOf(methodgetexp.invoke(znx_fighterdata));
		SkilllvData skilllvdata=Bean.getSkilllvmap().get(skilllv);
		//5连升升级后不再升
		if(!oldlv.equals(skilllv)){
			res="0";
			return res;
		}
		//机甲星级必须符合条件
		if(znx_fighterdata.getFighterstar()<skilllvdata.getFighterstar()||skilllvdata.getMaxvalue()==0){
			res="0";
			return res;
		}
		Random random=new Random();
		int rannum=random.nextInt(99);
		int upnum=0;
		int pro1=skilllvdata.getPro1();
		int pro2=pro1+skilllvdata.getPro2();
		//int pro3=pro2+skilllvdata.getPromax();
		if(rannum<pro1){
			upnum=1;
			if((Integer.parseInt(skillexp)+upnum)>=skilllvdata.getMaxvalue())
				upnum=3;
		}else if(rannum>=pro1&&rannum<pro2){
			upnum=2;
			if((Integer.parseInt(skillexp)+upnum)>=skilllvdata.getMaxvalue())
				upnum=3;
		}else{
			upnum=3;
		}

		String sql1="update player_bag set goodsnum=goodsnum-"+skilllvdata.getNeedskillnum()+" where playerid="+playerid+" and goodsid=202";
		String sql2;
		if(upnum==3){
			sql2="update player_newfighter set fighterskill"+skillinfo+"lv=fighterskill"+skillinfo+"lv+1,fighterskill"+skillinfo+"exp=0 where id="+fighterid;

		}else{
			sql2="update player_newfighter set fighterskill"+skillinfo+"exp=fighterskill"+skillinfo+"exp+"+upnum+" where id="+fighterid;

		}
		myJdbcTemplate.update(sql1);
		myJdbcTemplate.update(sql2);
		FinishTaskHelper.finishEverydayTask(playerid, "8", String.valueOf(skilllvdata.getNeedskillnum()));

		if(upnum==3){
			Method methodsetlv=clazz.getMethod("setFighterskill"+skillinfo+"lv",int.class);
			methodsetlv.invoke(znx_fighterdata, Integer.parseInt(skilllv)+1);
			Method methodsetexp=clazz.getMethod("setFighterskill"+skillinfo+"exp",int.class);
			methodsetexp.invoke(znx_fighterdata, 0);
		}else{
			Method methodsetexp=clazz.getMethod("setFighterskill"+skillinfo+"exp",int.class);
			methodsetexp.invoke(znx_fighterdata, Integer.parseInt(skillexp)+upnum);
		}
		res=String.valueOf(upnum);
		return res;
	}

}
