package com.mangni.vegaplan.servletsrc.updatedata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumChipColor;
import com.mangni.vegaplan.datatable.EnumJewelCharacter;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

/**
 * 客户端发送playerid&lottotype&oddtype; 服务器返回id=&templateid=&star=
 * lottotype=gem,chip,mech
 * oddtype=single,ten
 */
public class Raffle implements IReceiveMessage{
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}

	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		List<String> templatelist;
		List<String> dataidlist;
		String strSQL;
		String strcxSQL; 
		int stone;
		int needstone;
		Random random;
		templatelist = new ArrayList<String>();
		dataidlist = new ArrayList<String>();
		random=new Random();
		stone=0;
		needstone=0;
		String playerid=(String) request.get("playerid");
		String lottotype = (String) request.get("lottotype");// chip,gem,mech
		String oddtype = (String) request.get("oddtype");//single,ten
		String isfree = (String) request.get("isfree");
		
		String raffletype=null;
		
		if(isfree.equals("false"))
		{
			stone=Integer.parseInt(SqlHelper.getOneRead("select stone from znx_player where id="+playerid));
			if (lottotype.equals("chip"))
			{
				if (oddtype.equals("single")) 
				{
					//单抽
					needstone=138;
					if(stone>=needstone||isfree.equals("true"))
					{
						int ran=random.nextInt(Bean.getRafflechipone().size());
						templatelist.add(Bean.getRafflechipone().get(ran));
						FinishTaskHelper.finishHolidayTask(playerid, "17");
						raffletype="rafflechip1";
					}
				}//single
				if (oddtype.equals("ten")) 
				{
					//十连抽
					needstone=1200;
					if(stone>=needstone)
					{
						int onesize=Bean.getRafflechipone().size();
						int tensize=Bean.getRafflechipten().size();
						templatelist.add(Bean.getRafflechipten().get(random.nextInt(tensize)));
						for (int i = 1; i < 10; i++) 
						{
							int ran=random.nextInt(onesize);	
							templatelist.add(Bean.getRafflechipone().get(ran));
						}
						FinishTaskHelper.finishHolidayTask(playerid, "33");
						raffletype="rafflechip10";
					}
				}
				//ten
				if(needstone>0)
				{
					for (int i = 0; i < templatelist.size(); i++) 
					{
						String tid=templatelist.get(i);
						strSQL = "insert_player_chip(?,?,?)";
						String[] sqlpara={playerid,tid};
						String cid=SqlHelper.DbExecute(strSQL, sqlpara, true);
						dataidlist.add(cid);
						//znxidlist.add(templatelist.get(i));
						String color=Bean.getChipmap().get(tid).getChipcolor();
						int enumcolor=EnumChipColor.getChipEnumColor(color);
						if(enumcolor>=3)
						{
							SendMessageHelper sh=new SendMessageHelper(playerid,"3","chip",tid,"0");
							Thread th=new Thread(sh);
							th.start();
						}
					}
					FinishTaskHelper.finishEverydayTask(playerid, "10", "10");
				}
				else
				{
					response.put("res","not enough");
				}
			}

			else if (lottotype.equals("gem")) 
			{
				if (oddtype.equals("single")) 
				{
					//单抽
					needstone=98;
					if(stone>=needstone||isfree.equals("true"))
					{
						int ran=random.nextInt(Bean.getRafflejewelone().size());
						templatelist.add(Bean.getRafflejewelone().get(ran));
						FinishTaskHelper.finishHolidayTask(playerid, "15");
						raffletype="rafflejewel1";
					}
				}//single
				if (oddtype.equals("ten")) 
				{
					//十连抽
					needstone=900;
					if(stone>=needstone)
					{
						int onesize=Bean.getRafflejewelone().size();
						int tensize=Bean.getRafflejewelten().size();
						templatelist.add(Bean.getRafflejewelten().get(random.nextInt(tensize)));
						for (int i = 1; i < 10; i++) 
						{
							int ran=random.nextInt(onesize);
							templatelist.add(Bean.getRafflejewelone().get(ran));
						}
						FinishTaskHelper.finishHolidayTask(playerid, "34");
						raffletype="rafflejewel10";
					}
				}//ten
				if(needstone>0)
				{
					for (int i = 0; i < templatelist.size(); i++) 
					{
						String tid=templatelist.get(i);
						strSQL = "insert_player_jewel(?,?,?,?)";
						String[] sqlpara={playerid,tid,"1"};
						String gid=SqlHelper.DbExecute(strSQL, sqlpara, true);
						dataidlist.add(gid);
						//znxidlist.add(templatelist.get(i));
						String jewelCharacter=Bean.getJewelmap().get(tid).getJewelcharacter();
						int enumcharacter=EnumJewelCharacter.getJewelEnumCharacter(jewelCharacter);
						if(enumcharacter>=4)
						{
							SendMessageHelper sh=new SendMessageHelper(playerid,"3","jewel",tid,"0");
							Thread th=new Thread(sh);
							th.start();
						}
					}
					FinishTaskHelper.finishEverydayTask(playerid, "5", "10");
				}
				else
				{
					response.put("res","not enough");
				}
			}
			else if (lottotype.equals("mech"))
			{
				if (oddtype.equals("single")) 
				{
					//单抽
					needstone=228;
					if(stone>=needstone||isfree.equals("true"))
					{
						int ran=random.nextInt(Bean.getRafflefighterone().size());
						templatelist.add(Bean.getRafflefighterone().get(ran));
						FinishTaskHelper.finishHolidayTask(playerid, "16");
						raffletype="rafflefighter1";
					}
				}//single
				if (oddtype.equals("ten")) 
				{
					//十连抽
					needstone=2000;
					if(stone>=needstone)
					{
						int onesize=Bean.getRafflefighterone().size();
						int tensize=Bean.getRafflefighterten().size();
						templatelist.add(Bean.getRafflefighterten().get(random.nextInt(tensize)));
						for (int i = 1; i < 10; i++) 
						{
							int ran=random.nextInt(onesize);
							templatelist.add(Bean.getRafflefighterone().get(ran));
						}
						FinishTaskHelper.finishHolidayTask(playerid, "32");
						raffletype="rafflefighter10";
					}
				}//ten
				if(needstone>0)
				{
					for (int i = 0; i < templatelist.size(); i++) 
					{
						strcxSQL = "select top 1 id from player_fighter where playerid ="+playerid+" and fighterid ="+templatelist.get(i);
						List<String> tempList=SqlHelper.getMyData(strcxSQL);
						if (tempList.isEmpty()) 
						{
							//不存在此机甲
							String znxfighterid=templatelist.get(i);
							String [] sqlp1={playerid,znxfighterid,String.valueOf(Bean.getFightermap().get(znxfighterid).getDefaultstar())};
							String fstr=SqlHelper.DbExecute("insert_fighter(?,?,?,?)", sqlp1,true);
							dataidlist.add(fstr);
							//strSQL = "update player_fighter set fighterstar="+lm.getMechLv()+" where playerid="+playerid+" and fighterid ="+templatelist.get(i)+"";
							//System.out.println(strSQL);
							//SqlHelper.Updatedb(strSQL);
						}
						else
						{
							//存在此机甲
							/*
							int lmlv = Integer.parseInt(lm.getMechLv());
							int sqllv = Integer.parseInt(tempList.get(0));
							LottoMech lmowner = Bean.lottomechmap.get(tempList.get(0));
							if (sqllv>=lmlv)
							{
								//已存在高级机甲,插入碎片
								String[] sqlpara={playerid,"1",lm.getPatch()};
								SqlHelper.DbExecute("insert_player_bag(?,?,?)", sqlpara);
							}else
							{
								//已有机甲级别低，更新级别
								strSQL = "update player_fighter set fighterstar = "+lmlv+",fighterstarexp=0 where fighterid ="+templatelist.get(i)+" and playerid = "+playerid+" ";
								//System.out.println(strSQL);
								SqlHelper.Updatedb(strSQL);
								String[] sqlpara={playerid,"1",lmowner.getPatch()};
								SqlHelper.DbExecute("insert_player_bag(?,?,?)", sqlpara);
							}
							 */
							String znxfighterid=templatelist.get(i);
							String[] sqlpara={playerid,"1",String.valueOf(Bean.getFightermap().get(znxfighterid).getFighterpatch())};
							SqlHelper.DbExecute("insert_player_bag(?,?,?)", sqlpara);
							dataidlist.add("0");
						}
						//znxidlist.add(templatelist.get(i));
					}
					FinishTaskHelper.finishEverydayTask(playerid, "9", "10");
				}else
				{
					response.put("res","not enough");
				}	
			}
			if(!response.containsKey("res")||raffletype!=null)
			{
				String sqlstr="update znx_player set stone=stone-"+needstone+","+raffletype+"="+raffletype+"+1 where id="+playerid;
				LvupHelper.spendStone(playerid, needstone, this.getClass().getName(), sqlstr, request,false);
				response.put("res","true");
				response.put("dataidlist", dataidlist);
				response.put("znxidlist", templatelist);
			}else
			{
				response.put("res","not enough");
			}
		}
		//
		else if(isfree.equals("true")&&oddtype.equals("single"))
		{
			String sqlgettime="username";
			switch(lottotype)
			{
			case "chip":
				sqlgettime="lottochiptime";
				raffletype="rafflechip1";
				break;
			case "gem":
				sqlgettime="lottojeweltime";
				raffletype="rafflejewel1";
				break;
			case "mech":
				sqlgettime="lottofighttime";
				raffletype="rafflefighter1";
				break;
			default:
				return response;
			}
			String sqlt="select "+sqlgettime+" from znx_player where id="+playerid;
			String lottotime=SqlHelper.getOneRead(sqlt);
			long interval=0;
			if(lottotime!=null)
			{
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date=new Date();
				try 
				{
					date = dateFormat.parse(lottotime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				interval = (new Date().getTime() - date.getTime())/ (1000 * 60 * 60);
			}
			if(interval>=22||lottotime==null)
			{
				if (lottotype.equals("chip")) 
				{
					if(lottotime==null)
					{
						templatelist.add("42");	
					}
					else
					{
						int ran=random.nextInt(Bean.getRafflechipone().size());
						String tid=Bean.getRafflechipone().get(ran);
						templatelist.add(tid);
						String color=Bean.getChipmap().get(tid).getChipcolor();
						int enumcolor=EnumChipColor.getChipEnumColor(color);
						if(enumcolor>=3)
						{
							SendMessageHelper sh=new SendMessageHelper(playerid,"3","chip",tid,"0");
							Thread th=new Thread(sh);
							th.start();
						}
					}
					strSQL = "insert_player_chip(?,?,?)";
					String[] sqlpara={playerid,templatelist.get(0)};
					String cid=SqlHelper.DbExecute(strSQL, sqlpara, true);
					dataidlist.add(cid);
					FinishTaskHelper.finishEverydayTask(playerid, "10", "1");
					FinishTaskHelper.finishHolidayTask(playerid, "17");
					//znxidlist.add(templatelist.get(0));
				}
				else if(lottotype.equals("gem"))
				{
					int ran=random.nextInt(Bean.getRafflejewelone().size());
					String tid=Bean.getRafflejewelone().get(ran);
					templatelist.add(tid);
					String jewelCharacter=Bean.getJewelmap().get(tid).getJewelcharacter();
					int enumcharacter=EnumJewelCharacter.getJewelEnumCharacter(jewelCharacter);
					if(enumcharacter>=4)
					{
						SendMessageHelper sh=new SendMessageHelper(playerid,"3","jewel",tid,"0");
						Thread th=new Thread(sh);
						th.start();
					}
					strSQL = "insert_player_jewel(?,?,?,?)";
					String[] sqlpara={playerid,templatelist.get(0),"1"};
					String gid=SqlHelper.DbExecute(strSQL, sqlpara, true);
					FinishTaskHelper.finishEverydayTask(playerid, "5", "1");
					dataidlist.add(gid);
					FinishTaskHelper.finishHolidayTask(playerid, "15");
					//znxidlist.add(templatelist.get(0));
				}
				else if (lottotype.equals("mech"))
				{
					int ran=random.nextInt(Bean.getRafflefighterone().size());
					String znxfighterid=null;
					if(lottotime==null)
					{
						znxfighterid="2";
						SqlHelper.Updatedb("update player_tobattle set fighterposition3=2 where playerid="+playerid);
						templatelist.add("2");

					}
					else
					{
						znxfighterid=Bean.getRafflefighterone().get(ran);
						templatelist.add(znxfighterid);	
					}
					strcxSQL = "select top 1 id from player_fighter where playerid ="+playerid+" and fighterid ="+templatelist.get(0);
					List<String> tempList=SqlHelper.getMyData(strcxSQL);
					if (tempList.isEmpty()) 
					{
						//不存在此机甲
						String [] sqlp1={playerid,znxfighterid,String.valueOf(Bean.getFightermap().get(znxfighterid).getDefaultstar())};
						String fstr=SqlHelper.DbExecute("insert_fighter(?,?,?,?)", sqlp1,true);
						dataidlist.add(fstr);
						//znxidlist.add(templatelist.get(0));
					}
					else
					{
						//存在此机甲
						String[] sqlpara={playerid,"1",String.valueOf(Bean.getFightermap().get(znxfighterid).getFighterpatch())};
						SqlHelper.DbExecute("insert_player_bag(?,?,?)", sqlpara);
						dataidlist.add("0");
						//znxidlist.add(templatelist.get(0));
					}
					FinishTaskHelper.finishEverydayTask(playerid, "9", "1");
					FinishTaskHelper.finishHolidayTask(playerid, "16");
				}
				SqlHelper.Updatedb("update znx_player set "+sqlgettime+"=getdate(),"+raffletype+"="+raffletype+"+1 where id="+playerid);
				response.put("res","true");
				response.put("dataidlist", dataidlist);
				response.put("znxidlist", templatelist);
			}
			else
			{
				response.put("res", "not enough");
			}

		}
		/*
				String result1 = LottoHelper.listtostr(record1list);
				String result2 = LottoHelper.listtostr(record2list);
				String result3 = LottoHelper.listtostr(record3list);
		 */
		//out.print(EncryptUtil.aesEncrypt(result1+"|"+result2+"|"+result3));
		return response;
	}
}
//	private String getRateId(HashMap<Integer,List<String>> oddmap)
//	{
//		Random rnd = new Random();
//		String ratestr = null;
//
//		//概率list
//		List<Integer> oddlist = new ArrayList<Integer>();
//		for (Integer k : oddmap.keySet()) {
//			oddlist.add(k);
//		}
//		Collections.sort(oddlist);//按大小排序
//
//		//获取总权重
//		Integer weightSum = 0;
//		for (int j : oddlist) {
//			weightSum += j;
//		}
//
//		Integer n = rnd.nextInt(weightSum); // n in [0,weightSum-1]
//		Integer m = 0;
//		for (int l : oddlist) {
//			if (m <= n && n < m + l) {
//				//随机选中odd=l的记录
//				//获取odd=l的mapid列表
//				List<String> mapidlist=oddmap.get(l);
//				if (mapidlist.size() > 1) {
//					//对应多个id，需再随机抽取
//					Integer nn = rnd.nextInt(mapidlist.size());
//					ratestr=mapidlist.get(nn);
//				} else {
//					ratestr = mapidlist.get(0);
//				}
//				break;
//			}
//			m += l;
//		}//for
//
//		return ratestr;
//	}
//
//	private String getChipId(String ratestr)
//	{
//		Random rnd = new Random();
//		String resultstr = null;
//
//		LottoChip lp = (LottoChip)Bean.lottochipmap.get(ratestr);
//		List<String> tidlist= lp.getTemplateIdList();
//		if (tidlist.size() > 1) {
//			//对应多个模板id，需再随机抽取
//			Integer nn = rnd.nextInt(tidlist.size()-1);
//			resultstr =tidlist.get(nn);
//		} else {
//			resultstr = tidlist.get(0);
//		}
//
//		return resultstr;
//	}
//
//	private String getGemId(String ratestr)
//	{
//		Random rnd = new Random();
//		String resultstr = null;
//
//		LottoGem lp = (LottoGem)Bean.lottogemmap.get(ratestr);
//		List<String> tidlist= lp.getTemplateIdList();
//		if (tidlist.size() > 1) {
//			//对应多个模板id，需再随机抽取
//			Integer nn = rnd.nextInt(tidlist.size()-1);
//			resultstr =tidlist.get(nn);
//		} else {
//			resultstr = tidlist.get(0);
//		}
//
//		return resultstr;
//	}
//
//	private String getMechId(String ratestr)
//	{
//		//		Random rnd = new Random();
//		String resultstr = null;
//
//		LottoMech lp = (LottoMech)Bean.lottomechmap.get(ratestr);
//		resultstr = getRateId(lp.getRatemap());
//
//
//		/*		
//		List<String> tidlist= lp.getTemplateIdList();
//		 
//		if (tidlist.size() > 1) {
//			//对应多个模板id，需再随机抽取
//			Integer nn = rnd.nextInt(tidlist.size());
//			resultstr =tidlist.get(nn);
//		} else {
//			resultstr = tidlist.get(0);
//		}
//		 */		
//		return resultstr;
//	}
//	
//}
