package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.DigData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class DigReceive implements IReceiveMessage {
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
		String digid=(String)request.get("digid");
		String res="false";
		response.put("res", res);
		String starttime=Bean.getHolidayinfomap().get("1").getStartdate();
		DigData digdata=Bean.getDigmap().get(digid);
		if(digdata.getDignum()==0){
			List<Map<String, Object>> playerdig=myJdbcTemplate.queryForList("select starttime from player_dig where playerid="+playerid+" and (digid="+digid+"-1 or digid="+digid+") order by digid asc");
			int addtype=1;
			if(!digid.equals("1")){
				if(playerdig.size()<2){
					String oldstarttime1=(String) playerdig.get(0).get("starttime");
					if(!starttime.equals(oldstarttime1)){
						return response;
					}
					addtype=2;
				}else{
					String oldstarttime1=(String) playerdig.get(0).get("starttime");
					String oldstarttime2=(String) playerdig.get(1).get("starttime");
					if((!starttime.equals(oldstarttime1))||(starttime.equals(oldstarttime2)))
						return response;	
				}		
			}else{
				if(playerdig.size()==0){
					addtype=2;
				}
			}
			res=addDig(playerid,digdata,starttime,addtype);
		}else{
			int ficount=myJdbcTemplate.queryForObject("select count(*) from player_dig where playerid="+playerid+"and digid<="+Bean.getDigmap().get("1").getMaxid()+" and starttime='"+starttime+"'",Integer.class);
			if(ficount>=digdata.getDignum()){
				String count=myJdbcTemplate.queryForObject("select count(*) from player_dig where playerid="+playerid+" and digid="+digdata.getId(),String.class);
				int addtype=0;
				if(count.equals("0")){
					addtype=2;
				}else{
					addtype=1;
				}
				res=addDig(playerid,digdata,starttime,addtype);
			}
		}
		if(!res.equals("false")){
			response.put("res", "true");
			response.put("dataid", res);
		}
		return response;
	}

	private String addDig(String playerid,DigData digdata,String starttime,int addtype){
		String res="false";
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		int holidaygold=znx_playerdata.getHolidaygold();
		int needholidaygold=Integer.parseInt(digdata.getItemcurrency());
		if(needholidaygold>0){
			if(holidaygold>=needholidaygold){
				myJdbcTemplate.update("update znx_player set holidaygold=holidaygold-"+needholidaygold+" where id="+playerid);
				znx_playerdata.setHolidaygold(holidaygold-needholidaygold);
			}
		}
		String itemtype=digdata.getItemtype();
		EnumGoodsTypes goodstype = null;
		switch(itemtype){
		case "Item":
			goodstype=EnumGoodsTypes.GOODS;
			break;
		case "Chip":
			goodstype=EnumGoodsTypes.CHIP;
			break;
		case "Gem":
			goodstype=EnumGoodsTypes.JEWEL;
			break;
		}	
		String sql=null;
		if(addtype==1){
			sql="update player_dig set starttime='"+starttime+"' where playerid="+playerid+" and digid="+digdata.getId();
		}else{
			sql="insert into player_dig(playerid,digid,starttime) values("+playerid+","+digdata.getId()+",'"+starttime+"')";
		}
		myJdbcTemplate.update(sql);
		res=GetGoodsHelper.getGoods(playerid, goodstype, digdata.getItemid(), digdata.getItemnum());
		return res;
	}

}
