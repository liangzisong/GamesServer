package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.TacticsData;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FighterTacticsUplv implements IReceiveMessage {
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
		String fighterid=(String)request.get("fighterid");
		String uptype=(String)request.get("uptype");//before||after||lvup

		if("before".equals(uptype)){
			List<List<Integer>> result=new ArrayList<List<Integer>>();
			int num=Integer.parseInt((String)request.get("num"));
			String moneytype=(String)request.get("moneytype");
			Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
			for(int i=0;i<num;i++){
				List<Integer> tacticslist=tacticsUplv(znx_fighterdata,moneytype);
				result.add(tacticslist);
			}
			int needgoodsnum=Bean.getTacticsmap().get(String.valueOf(znx_fighterdata.getTacticslv())).getMaterial()*num;
			myJdbcTemplate.update("update player_bag set goodsnum=goodsnum-"+needgoodsnum+" where playerid="+znx_fighterdata.getPlayerid()+" and goodsid=201");
			if("senior".equals(moneytype))//高级强化
				myJdbcTemplate.update("update znx_player set stone=stone-"+20*num+" where id="+znx_fighterdata.getPlayerid());

			znx_fighterdata.setTacticslist(result);
			FinishTaskHelper.finishEverydayTask(playerid, "10", String.valueOf(needgoodsnum));
			response.put("res", result);

		}else if("after".equals(uptype)){
			String type=(String)request.get("type");//abandon||accept 代表放弃或接受此次升级
			Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
			if("abandon".equals(type)){
				znx_fighterdata.setTacticslist(null);
			}else{
				String acceptinfo=(String)request.get("acceptinfo");
				String [] acceptinfopara=acceptinfo.split(",");
				List<List<Integer>> tacticslist=znx_fighterdata.getTacticslist();
				if(tacticslist==null){
					response.put("res", "refresh");
					return response;
				}
				for(int i=0;i<tacticslist.size();i++){
					if(Arrays.binarySearch(acceptinfopara, String.valueOf(i))<0)
						continue;
					
					List<Integer> list=tacticslist.get(i);
					int tactics1=znx_fighterdata.getTactics1();
					int tactics2=znx_fighterdata.getTactics2();
					int tactics3=znx_fighterdata.getTactics3();
					int tactics4=znx_fighterdata.getTactics4();
					
					if(tactics1<100){
						tactics1=(tactics1+list.get(0))>100?100:(tactics1+list.get(0));
						if(tactics1<0)
							tactics1=0;
					}
					if(tactics2<100){
						tactics2=(tactics2+list.get(1))>100?100:(tactics2+list.get(1));
						if(tactics2<0)
							tactics2=0;
					}
					if(tactics3<100){
						tactics3=(tactics3+list.get(2))>100?100:(tactics3+list.get(2));
						if(tactics3<0)
							tactics3=0;
					}
					if(tactics4<100){
						tactics4=(tactics4+list.get(3))>100?100:(tactics4+list.get(3));
						if(tactics4<0)
							tactics4=0;
					}
					znx_fighterdata.setTactics1(tactics1);
					znx_fighterdata.setTactics2(tactics2);
					znx_fighterdata.setTactics3(tactics3);
					znx_fighterdata.setTactics4(tactics4);
					String sql="update player_newfighter set tactics1="+tactics1+",tactics2="+tactics2+",tactics3="+tactics3+",tactics4="+tactics4+" where id="+fighterid;
					myJdbcTemplate.update(sql);
					response.put("tactics1", tactics1);
					response.put("tactics2", tactics2);
					response.put("tactics3", tactics3);
					response.put("tactics4", tactics4);
				}
			}
			response.put("res", "true");
		}else if("lvup".equals(uptype)){
			Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
			int fighterlv=znx_fighterdata.getFighterlv();
			int tacticslv=znx_fighterdata.getTacticslv();
			int needfighterlv=Bean.getTacticsmap().get(String.valueOf(tacticslv+1)).getFighterlv();
			if(needfighterlv>fighterlv){
				response.put("res", "false");
				return response;
			}
			if(!(znx_fighterdata.getTactics1()==100&&znx_fighterdata.getTactics2()==100&&znx_fighterdata.getTactics3()==100&&znx_fighterdata.getTactics4()==100)){
				response.put("res", "false");
				return response;		
			}
			String sql="update player_newfighter set tacticslv=tacticslv+1,tactics1=0,tactics2=0,tactics3=0,tactics4=0 where id="+fighterid;
			myJdbcTemplate.update(sql);
			znx_fighterdata.setTactics1(0);
			znx_fighterdata.setTactics2(0);
			znx_fighterdata.setTactics3(0);
			znx_fighterdata.setTactics4(0);
			znx_fighterdata.setTacticslv(tacticslv+1);
			response.put("res", "true");
		}
		return response;
	}

	private List<Integer> tacticsUplv(Znx_FighterData znx_fighterdata,String moneytype){
		String tacticslv=String.valueOf(znx_fighterdata.getTacticslv());
		TacticsData tacticsdata=Bean.getTacticsmap().get(tacticslv);
		if(tacticsdata==null||(tacticsdata.getChance_2()==0&&tacticsdata.getChance_1()==0&&tacticsdata.getChance0()==0&&tacticsdata.getChance1()==0&&tacticsdata.getChance2()==0)){
			throw new RuntimeException();
		}
		List<Integer> result=new ArrayList<Integer>();
		Random random=new Random();	
		for(int i=0;i<4;i++){
			int rannum=random.nextInt(99)+1;
			int c1=tacticsdata.getChance_2();
			int c2=c1+tacticsdata.getChance_1();
			int c3=c2+tacticsdata.getChance0();
			int c4=c3+tacticsdata.getChance1();
			int upexp=0;
			if(rannum<c1){//-2
				upexp=-2;
				if("senior".equals(moneytype)){
					upexp=-4;
				}
			}else if(rannum>=c1&&rannum<c2){//-1
				upexp=-1;
				if("senior".equals(moneytype)){
					upexp=-2;
				}
			}else if(rannum>=c2&&rannum<c3){//0
				upexp=0;
			}else if(rannum>=c3&&rannum<c4){//1
				upexp=1;
				if("senior".equals(moneytype)){
					upexp=2;
				}
			}else{//2
				upexp=2;
				if("senior".equals(moneytype)){
					upexp=4;
				}
			}
			result.add(upexp);
		}
		return result;

	}

}
