package com.mangni.vegaplan.toolshelper;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;

public class LvupHelper 
{
	public static void UpPlayerExp(String playerid,int playerexpadd)
	{
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		if(znx_playerdata!=null)
		{
			long playeroldexp=znx_playerdata.getPlayerexp();
			int playeroldlv=znx_playerdata.getPlayerlv();
			long [] lvexpup=CheckLvup(playeroldlv,playeroldexp+playerexpadd,Bean.getLvtablemap(),"getPlayerexp");
			if(lvexpup[0]!=0||lvexpup[1]!=0)
			{
				int vitadd=(int)lvexpup[0]*10;
				int energyadd=(int)lvexpup[0]*5;
				String playersql="update znx_player set playerlv=playerlv+"+lvexpup[0]+",playerexp="+lvexpup[1]+",stone=stone+"+10*lvexpup[0]+",gold=gold+"+10000*lvexpup[0]+" where id="+playerid;

				myJdbcTemplate.update(playersql);
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", String.valueOf(vitadd));
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.ENERGY, "0", String.valueOf(energyadd));
				znx_playerdata.lvlupPlayerlv((int)lvexpup[0]);
				znx_playerdata.setPlayerexp(lvexpup[1]);
				Bean.getZnx_playermap().put(playerid, znx_playerdata);
			}
		}
	}
	public static void UpFighterExp(String [] fighteridpara,long fighterexpadd)
	{
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		for(String fighterid:fighteridpara)
		{
			if(fighterid!=null)
			{
				Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
				long fighteroldexp=znx_fighterdata.getFighterexp();
				int fighteroldlv=znx_fighterdata.getFighterlv();
				long [] lvexpup=CheckLvup(fighteroldlv,fighteroldexp+fighterexpadd,Bean.getLvtablemap(),"getFighterexp");

				if(lvexpup[0]!=0||lvexpup[1]!=0)
				{
					int playerlv=Bean.getZnx_playermap().get(znx_fighterdata.getPlayerid()).getPlayerlv();
					String fightersql;
					if(playerlv>fighteroldlv+lvexpup[0])
					{
						fightersql="update player_newfighter set fighterlv=fighterlv+"+lvexpup[0]+",fighterexp="+lvexpup[1]+" where id="+fighterid;
						znx_fighterdata.lvlupFighterlv((int)lvexpup[0]);
						znx_fighterdata.setFighterexp(lvexpup[1]);
						myJdbcTemplate.update(fightersql);
						Bean.getZnx_fightermap().put(fighterid, znx_fighterdata);
					}
					else
					{
						//机甲等级不能超过玩家等级
						long maxexp=Bean.getLvtablemap().get(String.valueOf(playerlv)).getFighterexp();
						if(lvexpup[1]>maxexp||fighteroldlv+lvexpup[0]>playerlv)
							lvexpup[1]=maxexp;
						fightersql="update player_newfighter set fighterlv="+playerlv+",fighterexp="+lvexpup[1]+" where id="+fighterid;
						znx_fighterdata.setFighterlv(playerlv);
						znx_fighterdata.setFighterexp(lvexpup[1]);
						myJdbcTemplate.update(fightersql);
						Bean.getZnx_fightermap().put(fighterid, znx_fighterdata);
					}
					if(lvexpup[0]>0)
						FinishTaskHelper.finishEverydayTask(znx_fighterdata.getPlayerid(), "21", String.valueOf(lvexpup[0]));
				}
			}
		}
	}
	public static void UpContributeExp(String playerid ,int expadd ,String contributestr)
	{
		Znx_PlayerData playerdata = Bean.getZnx_playermap().get(playerid);
		String gangid = playerdata.getGangid();
		Znx_GangData gangdata = Bean.getZnx_gangmap().get(gangid);
		Class<? extends Znx_GangData> clazz = gangdata.getClass();
		Method method = null;
		int contributelv = 0;
		try {
			method = clazz.getMethod("get"+contributestr+"lv");
			contributelv = Integer.parseInt(String.valueOf(method.invoke(gangdata)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(contributelv>=gangdata.getGanglv()){//建筑等级不得超过军团等级
			return;
		}
		long[] contributeinfo = LvupHelper.CheckLvup(contributelv, 20, Bean.getLvtablemap(), "getFortexp");
		int newcontributelv = contributelv+(int) contributeinfo[0];
		int newcontributeexp= (int) contributeinfo[1];
		if(newcontributelv!=0||newcontributeexp!=0)
		{
			if(gangdata.getGanglv()>=newcontributelv)
			{
				newcontributelv=gangdata.getGanglv();
				int maxexp=Integer.parseInt(Bean.getLvtablemap().get(String.valueOf(gangdata.getGanglv())).getFortexp());
				if(newcontributeexp>maxexp||newcontributelv>gangdata.getGanglv())
					newcontributeexp=maxexp;
			}
			String contributesql="update player_gang set "+contributestr+"lv="+newcontributelv+","+contributestr+"exp="+
					newcontributeexp+" where id="+gangdata.getId();
			try {
				method = clazz.getMethod("set"+contributestr+"lv");
				method.invoke(gangdata,newcontributelv);
				method = clazz.getMethod("set"+contributestr+"exp");
				method.invoke(gangdata,newcontributeexp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
			myJdbcTemplate.update(contributesql);

		}
	}

	/*
	public static void UsefighterSP(String fighterid,int numsp)
	{
		Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);
		if(znx_fighterdata==null)
		{
			List<String> fighterdata=SqlHelper.getMyData("select playerid, fighterid, fighterlv, fighterexp, fighterstar, fighterstarexp from player_fighter where id="+fighterid);
			znx_fighterdata=new Znx_FighterData();
			znx_fighterdata.setId(fighterid);
			znx_fighterdata.setPlayerid(fighterdata.get(0));
			znx_fighterdata.setFighterid(fighterdata.get(1));
			znx_fighterdata.lvlupFighterlv(Integer.parseInt(fighterdata.get(2)));
			znx_fighterdata.setFighterexp(Long.parseLong(fighterdata.get(3)));
			znx_fighterdata.LvlupFighterstar(Integer.parseInt(fighterdata.get(4)));
			znx_fighterdata.setFighterstarexp(Integer.parseInt(fighterdata.get(5)));
			Bean.getZnx_fightermap().put(fighterid, znx_fighterdata);
		}
		int maxusedfightersp=Bean.getMaxbuymap().get(String.valueOf(Bean.getZnx_playermap().get(znx_fighterdata.getPlayerid()).getViplv())).getMaxexchangepatch();
		int usedfightersp=Bean.getZnx_playermap().get(znx_fighterdata.getPlayerid()).getUsedfightersp();
		if(usedfightersp<maxusedfightersp)
		{
			if(numsp<=(maxusedfightersp-usedfightersp))
			{
				int fighteroldstar=znx_fighterdata.getFighterstar();
				int fighteroldsp=znx_fighterdata.getFighterstarexp();

				long[] lvlupsp=CheckLvup(fighteroldstar,fighteroldsp+numsp,Bean.getLvlupmap(),"getMechupneedsp");
				if(lvlupsp[0]!=0||lvlupsp[1]!=0)
				{
					String[] sqlpara={fighterid,znx_fighterdata.getPlayerid(),"1",String.valueOf(numsp),String.valueOf(lvlupsp[0]),String.valueOf(lvlupsp[1])};
					SqlHelper.DbExecute("strengthen_player_fighter(?,?,?,?,?,?,?)", sqlpara, true);
					znx_fighterdata.LvlupFighterstar((int)lvlupsp[0]);
					znx_fighterdata.setFighterstarexp((int)lvlupsp[1]);
					Bean.getZnx_fightermap().put(fighterid, znx_fighterdata);
					if(lvlupsp[0]>0)
						FinishTaskHelper.finishHolidayTask(znx_fighterdata.getPlayerid(), "24", String.valueOf(lvlupsp[0]));
				}
			}
		}
	}
	 */
	/**
	 * playerid,addstone,playertradenum,ali_tradenum,ali_buyer_id,total_fee,buytype
	 * @param playerid
	 * @param addstone
	 * @param playertradenum
	 * @param ali_tradenum
	 * @param ali_buyer_id
	 * @param total_fee
	 * @param buytype
	 * @return
	 * @throws SQLException
	 */
	public static String AddPlayerStone(String playerid,int addstone,String playertradenum,String ali_tradenum,String ali_buyer_id,String total_fee,String buytype,String remark,String firstreceive)
	{
		String res="false";
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		try
		{
			MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
			Map<String, Object> stoneandvip=myJdbcTemplate.queryForMap("select rmbstone, viplv from znx_player where id="+playerid);
			double oldrmb=Double.parseDouble(String.valueOf(stoneandvip.get("rmbstone")));
			int oldviplv=Integer.parseInt(String.valueOf(stoneandvip.get("viplv")));
			double [] viplvup=CheckLvup(oldviplv+1,oldrmb+Double.parseDouble(total_fee),Bean.getVipmap(),"getConsumedrmb",true);
			/*
				String vipsql="update znx_player set viplv=viplv+"+viplvup[0]+",stone=stone+"+addstone+" where playerid="+playerid;
				SqlHelper.Updatedb(vipsql);
			 */

			Object[] sql1param={playertradenum,ali_tradenum,ali_buyer_id,total_fee,playerid,buytype,remark,String.valueOf(addstone)};
			String sql1="INSERT INTO player_vip_pay(playertradenum,ali_tradenum,ali_buyer_id,total_fee,playerid,buytype,remark,stone) VALUES(?,?,?,?,?,?,?,?)";
			String sql2="UPDATE znx_player SET viplv=viplv+"+String.valueOf((int)viplvup[0])+",stone=stone+"+String.valueOf(addstone)+",rmbstone=rmbstone+"+total_fee+",firstreceive='"+firstreceive+"' WHERE id="+playerid;
			myJdbcTemplate.update(sql1,sql1param);
			myJdbcTemplate.update(sql2);
			res="true";
			if(znx_playerdata!=null)
			{
				znx_playerdata.setViplv(oldviplv+(int)viplvup[0]);
				znx_playerdata.setFirstreceive(firstreceive);
				znx_playerdata.addRmbstone(Double.parseDouble(total_fee));
				if(viplvup[0]>0)
				{
					SendMessageHelper sh=new SendMessageHelper(playerid,"2","vip","0",String.valueOf(oldviplv+(int)viplvup[0]));
					Thread th=new Thread(sh);
					th.start();
				}
			}
		}
		catch(Exception e)
		{
			res="false";
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * b==true 需要检测
	 */
	public static String spendStone(String playerid,int spendstone,String name,String sql,Object request,boolean b)
	{	
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String res="false";
		Object[] paras={playerid,name,request.toString(),String.valueOf(spendstone)};
		String sqlin="INSERT INTO spendstone_log(playerid,do,request,spendstone) values(?,?,?,?)";
		if(b)
		{
			String havestone=myJdbcTemplate.queryForObject("SELECT stone FROM znx_player WHERE id="+playerid,String.class);
			if(spendstone<=Integer.parseInt(havestone))
			{

				myJdbcTemplate.update(sql);
				myJdbcTemplate.update(sqlin, paras);
				FinishTaskHelper.finishEverydayTask(playerid, "6", String.valueOf(spendstone));
				res="true";
			}
		}
		else
		{
			myJdbcTemplate.update(sql);
			myJdbcTemplate.update(sqlin, paras);
			FinishTaskHelper.finishEverydayTask(playerid, "6", String.valueOf(spendstone));
			res="true";
		}
		return res;
	}
	/*
	 * b==true 需要检测
	 */
	public static String spendStone(String playerid,int spendstone,String name,String sql,Object[] sqlpara,Object request,boolean b)
	{	
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String res="false";
		Object[] paras={playerid,name,request.toString(),String.valueOf(spendstone)};
		String sqlin="INSERT INTO spendstone_log(playerid,do,request,spendstone) values(?,?,?,?)";
		if(b)
		{
			String havestone=myJdbcTemplate.queryForObject("SELECT stone FROM znx_player WHERE id="+playerid,String.class);
			if(spendstone<=Integer.parseInt(havestone))
			{
				myJdbcTemplate.update(sql,sqlpara);
				myJdbcTemplate.update(sqlin, paras);
				FinishTaskHelper.finishEverydayTask(playerid, "6", String.valueOf(spendstone));
				res="true";
			}
		}
		else
		{
			myJdbcTemplate.update(sql,sqlpara);
			myJdbcTemplate.update(sqlin, paras);
			FinishTaskHelper.finishEverydayTask(playerid, "6", String.valueOf(spendstone));
			res="true";
		}
		return res;
	}

	public static synchronized HashMap<String,String> addGangExp(String gangid,int exp,int turnexp,List<String> sqllist){
		HashMap<String,String> response=new HashMap<String,String>();
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String res="false";
		String sql=null;
		Znx_GangData znx_gangdata=Bean.getZnx_gangmap().get(gangid);
		long[] turninfo=new long[2];
		long[] ganglvinfo=LvupHelper.CheckLvup(znx_gangdata.getGanglv(), znx_gangdata.getGangexp()+exp, Bean.getGanglvupmap(), "getGangexp");			
		if(turnexp==0){
			sql="update player_gang set ganglv=ganglv+"+ganglvinfo[0]+",gangexp="+ganglvinfo[1]+",points=points+"+exp+" where id="+gangid;
		}else{
			turninfo=LvupHelper.CheckLvup(znx_gangdata.getTurnlv(), znx_gangdata.getTurnexp()+1, Bean.getTurnlvmap(), "getLvlupexp");
			sql="update player_gang set ganglv=ganglv+"+ganglvinfo[0]+",gangexp="+ganglvinfo[1]+",points=points+"+exp+",turnlv=turnlv+"+turninfo[0]+",turnexp="+turninfo[1]+" where id="+gangid;
		}
		sqllist.add(sql);
		for(int i=0;i<sqllist.size();i++){
			myJdbcTemplate.update(sqllist.get(i));
		}
		res="true";
		if(res.equals("true")){
			znx_gangdata.setGanglv((int)(znx_gangdata.getGanglv()+ganglvinfo[0]));
			znx_gangdata.setGangexp((int)ganglvinfo[1]);
			znx_gangdata.setPoints(znx_gangdata.getPoints()+exp);
			if(!(turnexp==0)){
				znx_gangdata.setTurnlv((int)(znx_gangdata.getTurnlv()+turninfo[0]));
				znx_gangdata.setTurnexp((int)turninfo[1]);
			}
		}
		response.put("res", res);
		response.put("ganglv", String.valueOf(znx_gangdata.getGanglv()));
		response.put("gangexp", String.valueOf(znx_gangdata.getGangexp()));
		response.put("turnlv", String.valueOf(znx_gangdata.getTurnlv()));
		response.put("turnexp", String.valueOf(znx_gangdata.getTurnexp()));
		return response;
	}

	/**
	 * 当前等级，新的总经验，模版map，取经验数值方法，返回生了几级，剩余经验值
	 * @param lv
	 * @param exp
	 * @param hm
	 * @param methodname
	 * @return 生了几级，剩余经验值
	 */
	public static long[] CheckLvup(int lv,long exp,HashMap hm,String methodname)
	{
		int lvup=0;
		try {	
			if(hm.size()!=0)
			{
				long needexp;
				for(int i=lv;i<=hm.size();i++)
				{
					needexp=Long.parseLong(String.valueOf(hm.get(String.valueOf(i)).getClass().getMethod(methodname).invoke(hm.get(String.valueOf(i)))));
					if(needexp==0)
					{
						exp=0;
						break;
					}
					if(exp>=needexp)
					{
						lvup++;
						if(i!=hm.size())
						{
							exp-=needexp;
						}
						else
						{
							exp=0;
						}
					}
					else
					{
						break;
					}
				}		
			}
			else
			{
				System.out.println("经验XML为空");
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long [] lvandexp={lvup,exp};
		return lvandexp;
	}
	//充值VIP升级
	public static double[] CheckLvup(int lv,double exp,HashMap hm,String methodname,boolean b)
	{
		int lvup=0;
		try {	
			if(hm.size()!=0)
			{
				double needexp;
				for(int i=lv;i<=hm.size();i++)
				{
					needexp=Double.parseDouble(hm.get(String.valueOf(i)).getClass().getMethod(methodname, new Class[]{}).invoke(hm.get(String.valueOf(i)), new Object[]{}).toString());
					if(needexp==0)
					{
						break;
					}
					if(exp>=needexp)
					{
						lvup++;
					}
					else
					{
						break;
					}
				}		
			}
			else
			{
				System.out.println("经验XML为空");
			}
		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double [] lvandexp={lvup,exp};
		return lvandexp;
	}
}
