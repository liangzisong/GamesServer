package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;

public class UseExppool implements IReceiveMessage {
	
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String fighterid=(String)request.get("fighterid");//数据id
		int uplv=Integer.parseInt((String)request.get("uplv"));
		Znx_PlayerData playerdata=Bean.getZnx_playermap().get(playerid);
		Znx_FighterData fighterdata=Bean.getZnx_fightermap().get(fighterid);
		int playerlv=playerdata.getPlayerlv();
		long fighterexp=fighterdata.getFighterexp();
		int fighterlv=fighterdata.getFighterlv();
		if(playerlv<fighterlv+uplv){
			response.put("res", "false");
			return response;
		}
		long needexp=0;
		for(int i=fighterlv;i<fighterlv+uplv;i++){
			String fighterlvstr=String.valueOf(i);
			if(Bean.getLvtablemap().containsKey(fighterlvstr)){
				long lvneedexp=Bean.getLvtablemap().get(fighterlvstr).getFighterexp();
				if(lvneedexp==0)//满级
					break;
				needexp+=lvneedexp;
			}
		}
		needexp-=fighterexp;
		long exppool=playerdata.getExppool();
		long useexp=0;
		if(needexp>exppool){
			useexp=exppool;
		}else{
			useexp=needexp;
		}
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.EXPPOOL, "0", "-"+useexp);
		LvupHelper.UpFighterExp(new String[]{fighterid}, useexp);
		response.put("res", "true");
		return response;
	}
}
