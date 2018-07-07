package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumChipColor;
import com.mangni.vegaplan.datatable.EnumJewelCharacter;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
/**
 * 宝石或芯片合成
 * 客户端发送playerid=xx&jewel(chip)&jew1id,jew2id,jew3id,jew4id,jew5id
 * 服务器返回id=xx&jewelid=xx
 * @author Administrator
 *
 */
public class JewOrChipCompose1 implements IReceiveMessage{
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		Random random=new Random();
		String playerid=(String) request.get("playerid");
		String chiporjew=(String) request.get("chiporjewel");
		String str=(String)request.get("chiporjewid");
		String [] chiporjewid=StringUtils.split(str,",");
		HashMap<String,String> retequipid=new HashMap<String,String>();//返还的装备ID
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
						retequipid=retequip(chiporjew, chiporjewid, str);
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
						int length=chiporjewid.length;
						String retid=retequipid.get("equipid");
						List<String> sqllist=new ArrayList<String>();
						for(int i=0;i<length;i++)
						{
							if(chiporjewid[i].equals(retid))
							{
								chiporjewid[i]="0";
								break;
							}
						}
						for(int i=0;i<length;i++)
						{
							if(!chiporjewid[i].equals("0"))
							{
								String sqlcompfail="UPDATE player_jewel SET num=num-1 WHERE id="+chiporjewid[i];
								sqllist.add(sqlcompfail);
							}
						}
						String res=SqlHelper.execTransaction(sqllist);
						if(res.equals("true"))
						{
							response.put("res", "fail");
						}
						else
						{
							response.put("res", "false");
						}
						response.put("retequipid", retid);
					}
					else if(cancomp==0)//错误
					{
						response.put("res","false");
					}
				}
			}
			else if(chiporjew.equals("chip"))
			{
				String sql="select chipid from player_chip where id in("+str+") order by id asc";
				List<String> tempList=SqlHelper.getMyData(sql);
				List<String> chipznxlist= new ArrayList<String>();
				List<String> chipcolorlist=new ArrayList<String>();
				for(String str2:tempList){
					if(!chipznxlist.contains(str2)){
						chipznxlist.add(str2);
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
						String upcolor=EnumChipColor.valueOf(icolor+1).toString();
						List<String> screenlist=HashMapHelper.getKeyList(Bean.getChipmap(),"getIdAlertColor", upcolor);
						int ranchipindex = random.nextInt(screenlist.size()-1);
						String chipznxid=screenlist.get(ranchipindex);
						String [] sqlpara={chiporjewid[0],chipznxid,chiporjewid[1]+","+chiporjewid[2]+","+chiporjewid[3]+","+chiporjewid[4]};
						String res=null;

						String goodsnum=SqlHelper.getOneRead("SELECT SUM(STAR) FROM PLAYER_CHIP WHERE ID IN("+str+")");
						String [] goodspara={playerid,EnumChipColor.getStrengthenGoods(icolor),goodsnum};
						SqlHelper.DbExecute("insert_player_bag", goodspara);
						res = SqlHelper.DbExecute("Compose_player_chip(?,?,?,?)", sqlpara, true);
						if(res.equals("true"))
						{
							response.put("res",res);
							response.put("znxid",chipznxid);
							response.put("dataid",chiporjewid[0]);
						}
						else
						{
							response.put("res","false");
						}
					}
				}
			}
		}
		return response;
	}

	private HashMap<String,String> retequip(String chiporjew,String[] chiporjewid,String str)
	{
		HashMap<String,String> equip=new HashMap<String,String>();
		Set<String> set = new HashSet<String>();
		Random random=new Random();
		int retenum=1;
		int ran=random.nextInt(99);
		if(ran<30&&ran>=10){
			retenum=2;
		}else if(ran<10){
			retenum=3;
		}
		while (set.size() < retenum) {
		      set.add(chiporjewid[random.nextInt(chiporjewid.length)]);
		    }

		if(chiporjew.equals("jewel")){
			for(String jewelid:set){
				
			}
		}else if(chiporjew.equals("chip")){
			
		}
		
		return equip;
	}
}