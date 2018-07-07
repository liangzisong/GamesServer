package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumChipColor;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class JewOrChipRecast implements IReceiveMessage {
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
		String type=(String)request.get("type");
		String id1=(String)request.get("id1");
		String id2=(String)request.get("id2");
		String res="false";
		response.put("res", res);
		if(type.equals("chip")){
			String goodsnum=SqlHelper.getOneRead("SELECT goodsnum FROM player_bag WHERE playerid="+playerid+" and goodsid=60");
			if(goodsnum==null||Integer.parseInt(goodsnum)<=0)
				return response;
			
			List<HashMap<String,String>> equipinfo=SqlHelper.getData("SELECT * FROM PLAYER_CHIP WHERE ID IN("+id1+","+id2+")");

			if(equipinfo==null||equipinfo.size()!=2)
				return response;
			if(!(playerid.equals(equipinfo.get(0).get("playerid"))&&playerid.equals(equipinfo.get(1).get("playerid"))))
				return response;

			int sumstar=0;
			int sumgold=0;
			for(HashMap<String,String> equipmap:equipinfo){
				sumstar+=Integer.parseInt(equipmap.get("star"));
				sumgold+=Bean.getLvlupmap().get(equipmap.get("lv")).getChipsumgold();
			} 
			String color1=Bean.getChipmap().get(equipinfo.get(0).get("chipid")).getChipcolor();
			String color2=Bean.getChipmap().get(equipinfo.get(1).get("chipid")).getChipcolor();
			if(!color1.equals(color2))
				return response;
			
			String newchipid="";
			while(true){
				newchipid=GetGoodsHelper.getRanChipAlertColor(color1);
				if(!equipinfo.get(0).get("chipid").equals(newchipid)&&!equipinfo.get(1).get("chipid").equals(newchipid))
					break;
			}
			
			String [] goodspara={playerid,EnumChipColor.getStrengthenGoods(EnumChipColor.valueOf(color1).value()),String.valueOf(sumstar)};
			SqlHelper.DbExecute("insert_player_bag(?,?,?)", goodspara);
			SqlHelper.Updatedb("UPDATE ZNX_PLAYER SET GOLD=GOLD+"+sumgold+" WHERE ID="+playerid);
			SqlHelper.Updatedb("update player_bag set goodsnum=goodsnum-1 where playerid="+playerid+" and goodsid=60");
			String [] sqlpara={id1,newchipid,id2};
			res = SqlHelper.DbExecute("Compose_player_chip(?,?,?,?)", sqlpara, true);
			response.put("res", res);
			response.put("znxid", newchipid);
			response.put("dataid", id1);
		}else{
			String goodsnum=SqlHelper.getOneRead("SELECT goodsnum FROM player_bag WHERE playerid="+playerid+" and goodsid=61");
			if(goodsnum==null||Integer.parseInt(goodsnum)<=0)
				return response;
			
			List<HashMap<String,String>> equipinfo=SqlHelper.getData("SELECT * FROM PLAYER_JEWEL WHERE ID IN("+id1+","+id2+")");

			if(equipinfo==null)
				return response; 
			
			if(!playerid.equals(equipinfo.get(0).get("playerid")))
				return response;
			
			String character1=Bean.getJewelmap().get(equipinfo.get(0).get("jewelid")).getJewelcharacter();
			if(!character1.equals("Circle"))
				return response;
			
			if(equipinfo.size()==2){
				if(!playerid.equals(equipinfo.get(1).get("playerid")))
					return response;		
				String character2=Bean.getJewelmap().get(equipinfo.get(1).get("jewelid")).getJewelcharacter();
				if(!character1.equals(character2))
					return response;
			}
			
			
			String newjewelid="";
			int i=0;
			while(i==0){
				newjewelid=GetGoodsHelper.getRanJewAlertChar(character1);
				for(int j=0;j<equipinfo.size();j++){
					if(equipinfo.get(j).get("jewelid").equals(newjewelid))
						break;
					i=1;				
				}
			}
			SqlHelper.Updatedb("update player_jewel set num=num-1 where id in("+id1+","+id2+")");
			SqlHelper.Updatedb("update player_bag set goodsnum=goodsnum-1 where playerid="+playerid+" and goodsid=61");
			String[] para={playerid,newjewelid,"1"};
			String dataid=SqlHelper.DbExecute("insert_player_jewel(?,?,?,?)", para, true);
			response.put("res", "true");
			response.put("znxid", newjewelid);
			response.put("dataid", dataid);
		}
		return response;
	}

}
