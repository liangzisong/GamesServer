package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.StringUtils;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumChipColor;
import com.mangni.vegaplan.datatable.EnumJewelCharacter;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;
/**
 * 宝石或芯片合成
 * 客户端发送playerid=xx&jewel(chip)&jew1id,jew2id,jew3id,jew4id,jew5id
 * 服务器返回id=xx&jewelid=xx
 * @author Administrator
 *
 */
public class JewOrChipCompose implements IReceiveMessage{
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
		String playerid=(String) request.get("playerid");
		String chiporjew=(String) request.get("chiporjewel");
		String str=(String)request.get("chiporjewid");
		String [] chiporjewid=StringUtils.split(str,",");
		Random random=new Random();
		List<String> retequipid=new ArrayList<String>(8);
		if(chiporjewid.length==5)
		{
			if(chiporjew.equals("jewel"))
			{
				String sql="select jewelid from player_jewel where id in("+str+") order by id asc";
				List<String> tempList=SqlHelper.getMyData(sql);
				List<String> list= new ArrayList<String>();
				int cancomp=0;//能否合成
				String[] sqlpara=new String[7];
				String newjewelznxid=null;
				
				for(String str2:tempList)
				{  
					if(!list.contains(str2))
					{  
						list.add(str2);  
					}  
				}
				
				List<String> jewcharacterlist=new ArrayList<String>();
				for(String str2:list)
				{
					String jewelcharacter=Bean.getJewelmap().get(str2).getJewelcharacter();
					if(!jewcharacterlist.contains(jewelcharacter))
					{
						jewcharacterlist.add(jewelcharacter);
					}
				}
				if(jewcharacterlist.size()==1)//判断宝石形状是否都一样,不一样什么也不做
				{
					//获取成功几率
					int probability=EnumJewelCharacter.getJewelComposePro(jewcharacterlist.get(0));
					//roll
					int dot=random.nextInt(100);
					if(probability<dot)//失败
					{
						retequipid=retequip(chiporjew, chiporjewid);
						cancomp=2;
					}
					else//成功
					{
						for(String str2:tempList)
						{
							if(!list.contains(str2))
							{
								list.add(str2);  
							}
						}
						if(list.size()==1)
						{
							JewelData jeweldata=Bean.getJewelmap().get(list.get(0));
							String character=jeweldata.getJewelcharacter();
							String jewelcolor=jeweldata.getJewelcolor();
							String jewelatt1=jeweldata.getJewelatt1();
							int icharacter=EnumJewelCharacter.getJewelEnumCharacter(character);
							if(icharacter!=0&&icharacter!=5)
							{
								String upcharacter=EnumJewelCharacter.valueOf(icharacter+1).toString();
								List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter", upcharacter);
								HashMap<String,JewelData> screenmap=new HashMap<String,JewelData>();
								for(String str2:screenlist)
								{
									screenmap.put(str2,Bean.getJewelmap().get(str2));
								}
								screenlist=HashMapHelper.getKeyList(screenmap,"getIdAlterJewelcolor", jewelcolor);
								screenmap.clear();
								for(String str2:screenlist)
								{
									screenmap.put(str2,Bean.getJewelmap().get(str2));
								}
								screenlist=HashMapHelper.getKeyList(screenmap,"getIdAlterJewelatt1", jewelatt1);
								newjewelznxid=screenlist.get(0);

								sqlpara[0]=playerid;
								sqlpara[1]=newjewelznxid;
								sqlpara[2]=chiporjewid[0];
								sqlpara[3]=chiporjewid[1];
								sqlpara[4]=chiporjewid[2];
								sqlpara[5]=chiporjewid[3];
								sqlpara[6]=chiporjewid[4];
								cancomp=1;
							}
						}
						else if(list.size()!=1)
						{
							List<String> jewcolorlist=new ArrayList<String>();
							for(String str2:list)
							{
								String jewelcolor=Bean.getJewelmap().get(str2).getJewelcolor();
								if(!jewcolorlist.contains(jewelcolor))
								{
									jewcolorlist.add(jewelcolor);
								}
							}
							if(jewcolorlist.size()==1)//同颜色
							{
								//筛选颜色对应的模版id
								List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterJewelcolor", jewcolorlist.get(0));
								HashMap<String,JewelData> screenmap=new HashMap<String,JewelData>();
								for(String str2:screenlist)
								{
									screenmap.put(str2, Bean.getJewelmap().get(str2));
								}
								//筛选形状对应的模版id
								int icharacter=EnumJewelCharacter.getJewelEnumCharacter(jewcharacterlist.get(0));
								if(icharacter!=0&&icharacter!=5)
								{
									String upcharacter=EnumJewelCharacter.valueOf(icharacter+1).toString();
									screenlist=HashMapHelper.getKeyList(screenmap,"getIdAlterCharacter", upcharacter);

									int ranjewindex = random.nextInt(screenlist.size());
									newjewelznxid = screenlist.get(ranjewindex);
									sqlpara[0]=playerid;
									sqlpara[1]=newjewelznxid;
									sqlpara[2]=chiporjewid[0];
									sqlpara[3]=chiporjewid[1];
									sqlpara[4]=chiporjewid[2];
									sqlpara[5]=chiporjewid[3];
									sqlpara[6]=chiporjewid[4];
									cancomp=1;
								}
							}
							else if(jewcolorlist.size()>1)//不同颜色
							{
								List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterJewelcolor", jewcolorlist.get(0));
								int icharacter=EnumJewelCharacter.getJewelEnumCharacter(jewcharacterlist.get(0));
								if(icharacter!=0&&icharacter!=5)
								{
									String upcharacter=EnumJewelCharacter.valueOf(icharacter+1).toString();
									screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter", upcharacter);
									int ranjewindex = random.nextInt(screenlist.size()-1);
									newjewelznxid=screenlist.get(ranjewindex);
									sqlpara[0]=playerid;
									sqlpara[1]=newjewelznxid;
									sqlpara[2]=chiporjewid[0];
									sqlpara[3]=chiporjewid[1];
									sqlpara[4]=chiporjewid[2];
									sqlpara[5]=chiporjewid[3];
									sqlpara[6]=chiporjewid[4];
									cancomp=1;
								}
							}
						}
					}
					if(cancomp==1)//合成成功
					{
						String res=SqlHelper.DbExecute("Compose_player_jewel(?,?,?,?,?,?,?,?)", sqlpara, true);
						if(!res.equals("false"))
						{
							FinishTaskHelper.finishEverydayTask(playerid, "6", "1");
							FinishTaskHelper.finishHolidayTask(playerid, "19");
							response.put("res","true");			
							response.put("znxid",newjewelznxid);
							response.put("dataid",res);
						}
						else
						{
							response.put("res","false");
						}
					}
					else if(cancomp==2)//合成失败
					{
						List<String> sqllist=new ArrayList<String>();
						for(String jewelid:retequipid){
							String sqlcompfail="UPDATE player_jewel SET num=num-1 WHERE id="+jewelid;
							sqllist.add(sqlcompfail);
						}
						String res=SqlHelper.execTransaction(sqllist);
						if(res.equals("true"))
						{
							response.put("res", "fail");
							response.put("retequipid", retequipid);
						}
						else
						{
							response.put("res", "false");
						}		
					}
					else if(cancomp==0)//错误
					{
						response.put("res","false");
					}
				}
			}
			else if(chiporjew.equals("chip"))
			{
				String sql="select chipid,lv,star from player_chip where id in("+str+") order by id asc";
				List<HashMap<String,String>> tempList=SqlHelper.getData(sql);
				List<String> chipznxlist= new ArrayList<String>();  
				List<String> chipcolorlist=new ArrayList<String>();
				int sumstar=0;
				int sumgold=0;
				for(HashMap<String,String> map2:tempList){
					sumstar+=Integer.parseInt(map2.get("star"));
					sumgold+=Bean.getLvlupmap().get(map2.get("lv")).getChipsumgold();
					if(!chipznxlist.contains(map2.get("chipid"))){
						chipznxlist.add(map2.get("chipid"));  
					}  
				}  
				for(String str2:chipznxlist)
				{
					String chiptype=Bean.getChipmap().get(str2).getChipcolor();
					if(chipcolorlist.indexOf(chiptype)==-1)
					{
						chipcolorlist.add(chiptype);
					}
				}

				if(chipcolorlist.size()!=1)//判断宝石形状是否都一样
				{
					response.put("res","false");
				}
				else
				{
					int icolor=EnumChipColor.getChipEnumColor(chipcolorlist.get(0));
					if(icolor>=4)
					{
						response.put("res","false");
						return response;
					}
					else
					{
						int probability=EnumChipColor.getChipComposePro(icolor);
						//roll
						int dot=random.nextInt(99);
						if(probability<dot)//失败
						{
							retequipid=retequip(chiporjew, chiporjewid);
							List<String> sqllist=new ArrayList<String>();
							int retgold=0;
							int retstar=0;
							
							for(String chipid:retequipid){
								int position=0;
								for(int i=0;i<chiporjewid.length;i++){
									if(chiporjewid.equals(chipid))
										position=i;
								}
								retstar+=Integer.parseInt(tempList.get(position).get("star"));
								retgold+=Bean.getLvlupmap().get(tempList.get(position).get("lv")).getChipsumgold();
								String delsql="delete from player_chip where id="+chipid;
								sqllist.add(delsql);
							}
							sqllist.add("UPDATE ZNX_PLAYER SET GOLD=GOLD+"+retgold+" WHERE ID="+playerid);
							String [] goodspara={playerid,EnumChipColor.getStrengthenGoods(icolor),String.valueOf(retstar)};
							SqlHelper.DbExecute("insert_player_bag(?,?,?)", goodspara);
							String res=SqlHelper.execTransaction(sqllist);
							if(res.equals("true"))
							{
								response.put("res", "fail");
								response.put("retequipid", retequipid);
							}
							else
							{
								response.put("res", "false");
							}
						}
						else
						{
							String upcolor=EnumChipColor.valueOf(icolor+1).toString();
							List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertColor", upcolor);
							int ranchipindex = random.nextInt(screenlist.size()-1);
							String chipznxid=screenlist.get(ranchipindex);
							String [] sqlpara={chiporjewid[0],chipznxid,chiporjewid[1]+","+chiporjewid[2]+","+chiporjewid[3]+","+chiporjewid[4]};
							String res=null;

							String [] goodspara={playerid,EnumChipColor.getStrengthenGoods(icolor),String.valueOf(sumstar)};

							SqlHelper.Updatedb("UPDATE ZNX_PLAYER SET GOLD=GOLD+"+sumgold+" WHERE ID="+playerid);
							SqlHelper.DbExecute("insert_player_bag(?,?,?)", goodspara);
							res = SqlHelper.DbExecute("Compose_player_chip(?,?,?,?)", sqlpara, true);
							if(res.equals("true"))
							{
								FinishTaskHelper.finishEverydayTask(playerid, "12", "1");
								FinishTaskHelper.finishHolidayTask(playerid, "18");
								response.put("res",res);
								response.put("znxid",chipznxid);
								response.put("dataid",chiporjewid[0]);
								String color=Bean.getChipmap().get(chipznxid).getChipcolor();
								int enumcolor=EnumChipColor.getChipEnumColor(color);
								if(enumcolor>=3)
								{
									SendMessageHelper sh=new SendMessageHelper(playerid,"3","chip",chipznxid,"0");
									Thread th=new Thread(sh);
									th.start();
								}
							}
							else
							{
								response.put("res","false");
							}
						}
					}
				}
			}
		}
		return response;
	}
	
	//返回要删除的装备id
	private List<String> retequip(String chiporjew,String[] chiporjewid)
	{
		List<String> set = new ArrayList<String>();
		Random random=new Random();
		int retenum=4;
		int ran=random.nextInt(99);
		if(ran<90&&ran>=70){
			retenum=3;
		}else if(ran>=90){
			retenum=2;
		}
		
		List<String> list=new ArrayList<String>();
		for(int i=0;i<chiporjewid.length;i++){
			list.add(chiporjewid[i]);
		}
		
		while (set.size() < retenum) {	
			int i=random.nextInt(list.size());
			set.add(list.get(i));
			list.remove(i);
		}
		return set;
	}
}