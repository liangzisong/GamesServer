package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.datatable.PcbData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 更换电路板上的宝石或芯片
 * 客户端发送playerid=xx&pcbdataid=xx&pcbznxid=xx&jeweldataid=xx&jewelznxid=xx&position=jewel1
 * @author Administrator
 *
 */
public class UpdatePcbSet implements IReceiveMessage{
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
		String res="false";
		String playerid=(String) request.get("playerid");
		String pcbdataid=(String) request.get("pcbdataid");
		String chiporjewel=(String) request.get("chiporjewel");
		String chiporjeweldataid=(String) request.get("chiporjeweldataid");
		//String chiporjewznxid=(String) request.get("chiporjewelznxid");
		String position=(String) request.get("position");

		String chiporjewznxid=null;
		String[] ischecked=checkpcb(pcbdataid,playerid);
		if(ischecked[0].equals("true"))
		{
			String pcbznxid=ischecked[1];
			PcbData pcbdata=Bean.getPcbmap().get(pcbznxid);
			if(chiporjewel.equals("chip"))
			{
				String [] para={"chip",chiporjeweldataid,pcbdataid,position};
				if(chiporjeweldataid.equals("0"))
				{
					res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
				}
				else
				{
					List<String> playeridlist=SqlHelper.getMyData("SELECT playerid, chipid FROM player_chip WHERE id="+chiporjeweldataid+" AND isequipped=0");
					if(!playeridlist.isEmpty())
					{
						String dataplayerid=playeridlist.get(0);
						chiporjewznxid=playeridlist.get(1);

						if(dataplayerid.equals(playerid))
						{
							switch(position)
							{
							case "chip1":
								if(Bean.getChipmap().get(chiporjewznxid).getChiptype().equals(pcbdata.getChiptype1()))
								{
									res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
								}
								break;
							case "chip2":
								if(Bean.getChipmap().get(chiporjewznxid).getChiptype().equals(pcbdata.getChiptype2()))
								{
									res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
								}
								break;
							case "chip3":
								if(Bean.getChipmap().get(chiporjewznxid).getChiptype().equals(pcbdata.getChiptype3()))
								{
									res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
								}
								break;
							}
						}
					}
				}
			}
			else if(chiporjewel.equals("jewel"))
			{
				List<String> playeridlist=SqlHelper.getMyData("SELECT playerid, jewelid, num, isequipped FROM player_jewel WHERE id="+chiporjeweldataid);
				String [] para={"jewel",chiporjeweldataid,pcbdataid,position};
				if(chiporjeweldataid.equals("0"))
				{
					res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
				}
				else
				{
					if(!playeridlist.isEmpty())
					{
						String dataplayerid=playeridlist.get(0);
						chiporjewznxid=playeridlist.get(1);
						int jewelnum=Integer.parseInt(playeridlist.get(2));
						int isequipped=Integer.parseInt(playeridlist.get(3));

						if(dataplayerid.equals(playerid))
						{
							if(jewelnum-isequipped>=1)
							{
								JewelData jewelznxdata=Bean.getJewelmap().get(chiporjewznxid);
								switch(position)
								{
								case "jewel1":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor1()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel2":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor2()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel3":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor3()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel4":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor4()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel5":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor5()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel6":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor6()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								case "jewel7":
									if(jewelznxdata.getJewelcolor().equals(pcbdata.getJewelcolor7()))
									{
										res=SqlHelper.DbExecute("update_pcbset(?,?,?,?,?)", para,true);
									}
									break;
								}	
							}
						}
					}
				}
			}
		}
		response.put("res",res);
		return response;
	}
	
	public HashMap<String,Object> onekeyPcbSet(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		JSONObject jso=(JSONObject) request.get("equipinfo");
		HashMap<String,HashMap<String,Object>> equipinfo=JSON.parseObject(JSON.toJSONString(jso),new TypeReference<HashMap<String,HashMap<String,Object>>>(){});;
		Iterator<Entry<String, HashMap<String, Object>>>  iterator=equipinfo.entrySet().iterator();
		while(iterator.hasNext())
		{ 
			Entry<String,HashMap<String, Object>> entry=iterator.next();
			String key=entry.getKey();
			HashMap<String, Object> value=entry.getValue();
			response.put(key, dopost(value));
		}
		return response;
	}
	
	private String[] checkpcb(String pcbdataid,String playerid)
	{
		String[] res={"false","0"};
		List<String> pcbinfo=SqlHelper.getMyData("SELECT B.fighterid, B.fighterlv, A.pcbid, A.playerid FROM player_pcb A LEFT JOIN player_fighter B ON A.fighterid=B.id WHERE A.id="+pcbdataid);
		if(pcbinfo.size()>0)
		{
			String pcbplayerid=pcbinfo.get(3);
			if(pcbplayerid.equals(playerid))
			{
				int fighterid=Integer.parseInt(pcbinfo.get(0));
				int fighterlv=Integer.parseInt(pcbinfo.get(1));
				int pcbid=Integer.parseInt(pcbinfo.get(2));
				res[1]=pcbid+"";
				if(fighterlv>=40){
					if(pcbid<=fighterid*5)
						res[0]="true";
				}else if(fighterlv<40&&fighterlv>=30){
					if(pcbid<=fighterid*5-1)
						res[0]="true";
				}else if(fighterlv<30&&fighterlv>=20){
					if(pcbid<=fighterid*5-2)
						res[0]="true";
				}else if(fighterlv<20&&fighterlv>=10){
					if(pcbid<=fighterid*5-3)
						res[0]="true";
				}else if(fighterlv<10){
					if(pcbid==fighterid*5-4)
						res[0]="true";
				}
			}
		}
		return res;
	}
}
