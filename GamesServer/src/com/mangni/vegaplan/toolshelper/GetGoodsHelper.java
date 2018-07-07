package com.mangni.vegaplan.toolshelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.HolidayinfoData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.servletsrc.gang.GangBattleSite;
public class GetGoodsHelper {
	public static String getGoods(String playerid,EnumGoodsTypes goodstype,String goodsid,String goodsnum)
	{
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		String playergoodsid="0";
		switch(goodstype)
		{
		case GOLD:
			long goldnum=Long.parseLong(goodsnum);
			if(goldnum==0)
				return "0";
			if(goldnum>0){
				myJdbcTemplate.update("update znx_player set gold=gold+"+goodsnum+" where id="+playerid);
			}else{
				Long gold=myJdbcTemplate.queryForObject("select gold from znx_player where id="+playerid,Long.class);
				if(gold>=-goldnum){
					myJdbcTemplate.update("update znx_player set gold=gold+"+goodsnum+" where id="+playerid);
					FinishTaskHelper.finishEverydayTask(playerid, "7", String.valueOf(-goldnum));
					playergoodsid="1";
				}
			}
			break;

		case STONE:
			if(goodsnum.equals("0"))
				return playergoodsid;
			int stonenum=Integer.parseInt(goodsnum);
			if(stonenum==0)
				return playergoodsid;
			if(stonenum>0){
				myJdbcTemplate.update("update znx_player set stone=stone+"+goodsnum+" where id="+playerid);
				playergoodsid="1";
			}else{
				String sql="update znx_player set stone=stone+"+goodsnum+" where id="+playerid;
				playergoodsid=LvupHelper.spendStone(playerid, stonenum, goodsid, sql, "", true);
				if("true".equals(playergoodsid))
					playergoodsid="1";
			}
			break;

		case VIT:
			if(goodsnum.equals("0"))
				return "0";
			Znx_PlayerData vitplayerdata=Bean.getZnx_playermap().get(playerid);
			int vitnum=Integer.parseInt(goodsnum);
			int [] vitp=vitplayerdata.getRealVit();
			
			Date vitdate=new Date();
			String vittimepara="";
			if(vitp[1]!=-1){
				vitdate.setTime(vitplayerdata.getVittime().getTime()+6*60*1000*vitp[1]);
				//vittimepara="dateadd(MM,"+6*vitp[1]+",vittime)";
			}
			vittimepara=Bean.getDateFormat().format(vitdate);
			
			int vit=vitp[0];
			vit=vit+vitnum;
			if(vitnum<0){
				if(vit>=0){
					int vited=-vitnum;
					myJdbcTemplate.update("update znx_player set vit="+vit+",vittime='"+vittimepara+"',vited=vited+"+vited+" where id="+playerid);
					vitplayerdata.setVit(vit);
					vitplayerdata.setVittime(vitdate);
				}else{
					return "-1";
				}
			}else{
				myJdbcTemplate.update("update znx_player set vit="+vit+",vittime='"+vittimepara+"' where id="+playerid);
				vitplayerdata.setVit(vit);
				vitplayerdata.setVittime(vitdate);
			}
			break;

		case ENERGY:
			if(goodsnum.equals("0"))
				return "0";
			Znx_PlayerData energyplayerdata=Bean.getZnx_playermap().get(playerid);
			int energynum=Integer.parseInt(goodsnum);
			int [] energyp=energyplayerdata.getRealEnergy();
			int energy=energyp[0];
			
			Date energydate=new Date();
			String energytimepara="";
			if(energyp[1]!=-1){
				energydate.setTime(energyplayerdata.getEnergytime().getTime()+6*60*1000*energyp[1]);
			}
			energytimepara=Bean.getDateFormat().format(energydate);
			
			energy=energy+energynum;
			if(energynum<0){
				if(energy>=0){
					myJdbcTemplate.update("update znx_player set energy="+energy+",energytime='"+energytimepara+"' where id="+playerid);
					energyplayerdata.setEnergy(energynum);
					energyplayerdata.setEnergytime(energydate);
				}else{
					return "-1";
				}
			}else{
				myJdbcTemplate.update("update znx_player set energy="+energy+",energytime='"+energytimepara+"' where id="+playerid);
				energyplayerdata.setEnergy(energynum);
				energyplayerdata.setEnergytime(energydate);
			}
			break;

		case PLAYEREXP:
			if(goodsnum.equals("0"))
				return "0";
			LvupHelper.UpPlayerExp(playerid, Integer.parseInt(goodsnum));
			break;

		case JEWEL:
			if(goodsnum.equals("0"))
				return "0";
			String jewelsql;
			int jewelcount=myJdbcTemplate.queryForObject("SELECT COUNT(id) FROM player_jewel with(updlock) WHERE playerid="+playerid+" AND jewelid="+goodsid, Integer.class);
			if(jewelcount>0){
				jewelsql="UPDATE player_jewel SET num=num+"+goodsnum+" WHERE playerid="+playerid+" AND jewelid="+goodsid;
			}else{
				jewelsql="INSERT INTO player_jewel(playerid,jewelid,num) VALUES("+playerid+","+goodsid+","+goodsnum+")";
			}
			myJdbcTemplate.update(jewelsql);
			break;

		case GOODS:
			if(goodsnum.equals("0"))
				return "0";
			String goodssql;
			int goodscount=myJdbcTemplate.queryForObject("SELECT COUNT(id) FROM player_bag with(updlock) WHERE playerid="+playerid+" AND goodsid="+goodsid, Integer.class);
			if(goodscount>0){
				goodssql="UPDATE player_bag SET goodsnum=goodsnum+"+goodsnum+" WHERE playerid="+playerid+" AND goodsid="+goodsid;
			}else{
				goodssql="INSERT INTO player_bag(playerid,goodsid,goodsnum) VALUES("+playerid+","+goodsid+","+goodsnum+")";
			}
			myJdbcTemplate.update(goodssql);
			break;

		case FIGHTER:
			if(goodsnum.equals("0"))
				return "0";

			String sql = "select top 1 id from player_newfighter where playerid ="+playerid+" and fighterid ="+goodsid;
			String fightercount=myJdbcTemplate.queryForObject(sql,String.class);
			if (fightercount==null) 
			{
				//不存在此机甲
				String [] fightersqlparam={playerid,goodsid,String.valueOf(Bean.getFightermap().get(goodsid).getDefaultstar()*9-9)};
				String fightersql="insert into player_newfighter(playerid,fighterid,star) values(?,?,?)";
				playergoodsid=String.valueOf(myJdbcTemplate.insertAndGetKey(fightersql, fightersqlparam));
			}
			else
			{
				//存在此机甲
				getGoods(playerid, EnumGoodsTypes.GOODS, goodsid, String.valueOf(Bean.getFightermap().get(goodsid).getFighterpatch()));
			}

			break;

		case HOLIDAYGOLD:
			if(goodsnum.equals("0"))
				return "0";
			HolidayinfoData holidayinfodata=Bean.getHolidayinfomap().get("1");
			String holidaygoldstarttime=holidayinfodata.getStartdate();
			String sql1="select count(holidaygold) from znx_player where id="+playerid+" and holidaygoldtime='"+holidaygoldstarttime+"'";
			String count=myJdbcTemplate.queryForObject(sql1,String.class);
			if(count.equals("0")){
				myJdbcTemplate.update("update znx_player set holidaygold="+goodsnum+",holidaygoldtime='"+Bean.getHolidayinfomap().get("1").getStartdate()+"' where id="+playerid);
				Bean.getZnx_playermap().get(playerid).setHolidaygold(Integer.parseInt(goodsnum));
			}else{
				myJdbcTemplate.update("update znx_player set holidaygold=holidaygold+"+goodsnum+",holidaygoldtime='"+Bean.getHolidayinfomap().get("1").getStartdate()+"' where id="+playerid);
				Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
				znx_playerdata.setHolidaygold(znx_playerdata.getHolidaygold()+Integer.parseInt(goodsnum));
			}
			break;

		case SKIN:
			playergoodsid = myJdbcTemplate.queryForObject("select id from player_skin where playerid="+playerid+" and skinid="+goodsid,String.class);
			String skinsql=null;
			if(playergoodsid!=null){
				skinsql="update player_skin set gettime=getdate(),expirydate=DATEADD(day,"+goodsnum+",GETDATE()) where playerid="+playerid+" and skinid="+goodsid;
				if(goodsnum.equals("0"))
					skinsql="update player_skin set gettime=getdate(),expirydate=null where playerid="+playerid+" and skinid="+goodsid;		

				myJdbcTemplate.update(skinsql);
			}else{
				skinsql="insert into player_skin (playerid,skinid,gettime,expirydate) values("+playerid+","+goodsid+",getdate(),DATEADD(day,"+goodsnum+",GETDATE()))";
				if(goodsnum.equals("0"))
					skinsql="insert into player_skin (playerid,skinid,gettime) values("+playerid+","+goodsid+",getdate())";

				playergoodsid=String.valueOf(myJdbcTemplate.insertAndGetKey(skinsql,null));
			}
			break;

		case BUYSHOPNUM:
			if(goodsnum.equals("0"))
				return "0";
			String buyshopnumsql;
			String[] shopinfo=goodsid.split(",");
			int buyshopnumcount=myJdbcTemplate.queryForObject("SELECT COUNT(id) FROM player_buyshopnum with(updlock) WHERE playerid="+playerid+" AND shoptype="+shopinfo[0]+" AND templateid="+shopinfo[1], Integer.class);
			if(buyshopnumcount>0){
				buyshopnumsql="UPDATE player_buyshopnum SET buynum=buynum+"+goodsnum+" WHERE playerid="+playerid+" AND shoptype="+shopinfo[0]+" AND templateid="+shopinfo[1];
			}else{
				buyshopnumsql="INSERT INTO player_buyshopnum(playerid,shoptype,templateid,buynum) VALUES("+playerid+","+goodsid+","+goodsnum+")";
			}
			myJdbcTemplate.update(buyshopnumsql);
			break;

		case DAILYTASK:
			if(goodsnum.equals("0"))
				return "0";
			String dailytasksql;
			int dailytaskcount=myJdbcTemplate.queryForObject("SELECT COUNT(id) FROM player_dailytask with(updlock) WHERE playerid="+playerid+" AND dutyid="+goodsid, Integer.class);
			if(dailytaskcount>0){
				dailytasksql="UPDATE player_dailytask SET finishcount=finishcount+"+goodsnum+",uptime=GETDATE() WHERE playerid="+playerid+" AND dutyid="+goodsid;
			}else{
				dailytasksql="INSERT INTO player_dailytask(playerid,dutyid,finishcount) VALUES("+playerid+","+goodsid+","+goodsnum+")";
			}
			myJdbcTemplate.update(dailytasksql);
			break;

		case HOLIDAYTASK:
			if(goodsnum.equals("0"))
				return "0";
			String hoildaytasksql;
			String[] hoildaytaskinfo=goodsid.split(",");
			String hoildaytaskstarttime=myJdbcTemplate.queryForObject("SELECT STARTTIME FROM PLAYER_HOLIDAYTASK with(updlock) WHERE PLAYERID="+playerid+" AND TEMPLATEID="+hoildaytaskinfo[0], String.class);
			if(hoildaytaskstarttime==null){
				hoildaytasksql="INSERT INTO PLAYER_HOLIDAYTASK(PLAYERID,TEMPLATEID,STARTTIME,FINISHCOUNT) VALUES("+playerid+","+goodsid+","+goodsnum+")";
			}else{
				if(hoildaytaskinfo[1].equals(hoildaytaskstarttime)){
					hoildaytasksql="UPDATE PLAYER_HOLIDAYTASK SET FINISHCOUNT=FINISHCOUNT+"+goodsnum+" WHERE PLAYERID="+playerid+" AND TEMPLATEID="+hoildaytaskinfo[0];
				}else{
					hoildaytasksql="UPDATE PLAYER_HOLIDAYTASK SET FINISHCOUNT="+goodsnum+",isreceived=0,STARTTIME="+hoildaytaskinfo[1]+" WHERE PLAYERID="+playerid+" AND TEMPLATEID="+hoildaytaskinfo[0];
				}
			}
			myJdbcTemplate.update(hoildaytasksql);
			break;
		case EXPPOOL:
			myJdbcTemplate.update("update znx_player set exppool=exppool+"+goodsnum+" where id="+playerid);
			Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
			playerdata.setExppool(playerdata.getExppool()+Integer.parseInt(goodsnum));
			break;

		case FIGHTEREXP:
			LvupHelper.UpFighterExp(new String[]{goodsid}, Long.parseLong(goodsnum));
			break;

		case ACTPOWER://return 0 成功 return -1 失败
			if(goodsnum.equals("0"))
				return "0";
			Znx_PlayerData actpowerplayerdata=Bean.getZnx_playermap().get(playerid);
			int actpowernum=Integer.parseInt(goodsnum);
			int[] actpowerp=actpowerplayerdata.getRealActpower();
			
			Date actpowerdate=new Date();
			String actpowertimepara="";
			if(actpowerp[1]!=-1){
				actpowerdate.setTime(actpowerplayerdata.getActpowertime().getTime()+3*60*1000*actpowerp[1]);
			}
			actpowertimepara=Bean.getDateFormat().format(actpowerdate);
			
			int actpower=actpowerp[0];
			actpower=actpower+actpowernum;
			if(actpowernum<0){
				if(actpower>=0){
					myJdbcTemplate.update("update player_ganginfo set actpower="+actpower+",actpowertime='"+actpowertimepara+"' where playerid="+playerid);
					actpowerplayerdata.setActpower(actpower);
					actpowerplayerdata.setActpowertime(actpowerdate);
				}else{
					return "-1";
				}
			}else{
				myJdbcTemplate.update("update player_ganginfo set actpower="+actpower+",actpowertime='"+actpowertimepara+"' where playerid="+playerid);
				actpowerplayerdata.setActpower(actpower);
				actpowerplayerdata.setActpowertime(actpowerdate);
			}
			playergoodsid="1";
			break;

		case ENDURANCE://return 0 成功 return -1 失败
			if(goodsnum.equals("0"))
				return "0";
			int endurancenum=Integer.parseInt(goodsnum);
			int endurance=0;
			Znx_PlayerData enduranceplayerdata=Bean.getZnx_playermap().get(playerid);
			if(enduranceplayerdata!=null){
				endurance=enduranceplayerdata.getEndurance();
			}else{
				endurance=myJdbcTemplate.queryForObject("select top 1 endurance from player_ganginfo where playerid="+playerid,Integer.class);
			}


			endurance=endurance+endurancenum;
			if(endurancenum<0){
				if(endurance>=0){
					myJdbcTemplate.update("update player_ganginfo set endurance="+endurance+" where playerid="+playerid);
					if(enduranceplayerdata!=null)
						enduranceplayerdata.setEndurance(endurance);
				}else{
					myJdbcTemplate.update("update player_ganginfo set endurance=0 where playerid="+playerid);
					GangBattleSite.initPosition(playerid, myJdbcTemplate);
				}
			}else{
				myJdbcTemplate.update("update player_ganginfo set endurance="+endurance+" where playerid="+playerid);
				if(enduranceplayerdata!=null)
					enduranceplayerdata.setEndurance(endurance);
			}
			playergoodsid="1";
			break;

		case CONTRIBUTION:
			List<String> consqllist=new ArrayList<String>();
			Znx_PlayerData playerdata1=Bean.getZnx_playermap().get(playerid);
			int gangmilitarylv=1;
			int gangmilitaryexp=0;
			String gangid;
			if(playerdata1!=null){
				gangid=playerdata1.getGangid();
				gangmilitarylv=playerdata1.getGangmilitarylv();
				gangmilitaryexp=playerdata1.getGangmilitaryexp()+Integer.parseInt(goodsnum);
			}else{
				Map<String,Object> map=myJdbcTemplate.queryForMap("select top 1 gangid,gangmilitarylv,gangmilitaryexp from player_ganginfo where playerid="+playerid+" and ischecked=1");
				gangid=String.valueOf(map.get("gangid"));
				gangmilitarylv=Integer.parseInt(String.valueOf(map.get("gangmilitarylv")));
				gangmilitaryexp=Integer.parseInt(String.valueOf(map.get("gangmilitaryexp")))+Integer.parseInt(goodsnum);
			}
			long[] gangmilitary=LvupHelper.CheckLvup(gangmilitarylv, gangmilitaryexp, Bean.getGangpostmap(), "getNeedcontribution");	
			consqllist.add("update player_ganginfo set gangmilitarylv=gangmilitarylv+"+gangmilitary[0]+",gangmilitaryexp="+gangmilitary[1]+",contribution_day=contribution_day+"+goodsnum+",contribution_week=contribution_week+"+goodsnum+",contribution_month=contribution_month+"+goodsnum+",contribution=contribution+"+goodsnum+" where playerid="+playerid);
			LvupHelper.addGangExp(gangid, Integer.parseInt(goodsnum), 0, consqllist);
			if(playerdata1!=null){
				playerdata1.setGangmilitarylv((int)(playerdata1.getGangmilitarylv()+gangmilitary[0]));
				playerdata1.setGangmilitaryexp((int)gangmilitary[1]);
			}
			break;

		default:
			break;
		}

		return playergoodsid;
	}
	public static String getRanJewAlertChar(String character)
	{
		List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter",character);
		Random random = new Random();
		String znxgoodsid = screenlist.get(random.nextInt(screenlist.size()));
		return znxgoodsid;
	}
	public static String getRanChipAlertColor(String color)
	{
		List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertColor",color);
		Random random = new Random();
		String znxgoodsid = screenlist.get(random.nextInt(screenlist.size()));
		return znxgoodsid;
	}
	public static EnumGoodsTypes getGoodstype(String goodstype){
		EnumGoodsTypes e=null;
		switch(goodstype.toUpperCase()){
		case "CHIP":
			e=EnumGoodsTypes.CHIP;
			break;
		case "MECH":
			e=EnumGoodsTypes.FIGHTER;
			break;
		case "GEM":
			e=EnumGoodsTypes.JEWEL;
			break;
		case "GOODS":
			e=EnumGoodsTypes.GOODS;
			break;
		case "ITEM":
			e=EnumGoodsTypes.GOODS;
			break;
		case "VIT":
			e=EnumGoodsTypes.VIT;
			break;
		case "ENERGY":
			e=EnumGoodsTypes.ENERGY;
			break;
		case "GOLD":
			e=EnumGoodsTypes.GOLD;
			break;
		case "STONE":
			e=EnumGoodsTypes.STONE;
			break;
		default:
			break;
		}
		return e;
	}
}
