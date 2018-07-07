package com.mangni.vegaplan.servletsrc.vipsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.EnumJewelCharacter;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.HashMapHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReceiveFirstRecharge implements IReceiveMessage {
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
		String firstrecharge=SqlHelper.getOneRead("SELECT firstrecharge FROM znx_player WHERE id="+playerid);
		int viplv=Bean.getZnx_playermap().get(playerid).getViplv();	
		if(viplv>0&&firstrecharge.equals("0"))
		{
			ArrayList<String> jewelznxlist=new ArrayList<String>();
			ArrayList<String> jeweldatalist=new ArrayList<String>();
			String upcharacter=EnumJewelCharacter.valueOf(1).toString();
			List<String> screenlist=HashMapHelper.getKeyList(Bean.getJewelmap(),"getIdAlterCharacter", upcharacter);
			Random random = new Random();
			for(int i=0;i<5;i++)
			{
				int ranjewindex = random.nextInt(screenlist.size()-1);
				String jewelznxid=screenlist.get(ranjewindex);
				jewelznxlist.add(jewelznxid);
				String[] sqlgem={playerid,jewelznxid,"1"};
				String gid=SqlHelper.DbExecute("insert_player_jewel(?,?,?,?)", sqlgem, true);
				jeweldatalist.add(gid);
			}		
			String [] sqlpara={playerid,"19",String.valueOf(Bean.getFightermap().get("19").getDefaultstar())};
			String fighterid=SqlHelper.DbExecute("insert_fighter(?,?,?,?)", sqlpara,true);
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "4", "3");
			SqlHelper.Updatedb("UPDATE znx_player SET stone=stone+300,gold=gold+100000,firstrecharge=1 WHERE id="+playerid);
			response.put("res", "true");
			response.put("znxid", jewelznxlist);
			response.put("dataid", jeweldatalist);
			response.put("fighterid", fighterid);
		}
		else
		{
			response.put("res", "false");
		}
		return response;
	}

}
