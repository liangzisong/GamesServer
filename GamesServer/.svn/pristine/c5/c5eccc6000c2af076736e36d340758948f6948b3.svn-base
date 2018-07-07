package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class SellEquip implements IReceiveMessage{
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
		String selltype=(String)request.get("selltype");
		String sellidpara=(String)request.get("sellid");
		List<String> sqllist=new ArrayList<String>();
		String sellidarr[]=StringUtils.split(sellidpara,",");
		int sellprice=0;
		String res="false";
		if(selltype.equals("jewel"))//数据库约束不需要判断是否已装备
		{
			for(int i=0;i<sellidarr.length;i++)
			{
				sellprice+=Bean.getJewelmap().get(sellidarr[i]).getJewelprice();
				String sqlstri="UPDATE player_jewel SET num=num-1 WHERE playerid="+playerid+" AND jewelid="+sellidarr[i];
				sqllist.add(sqlstri);
			}
			String sqlstr1="UPDATE znx_player SET gold=gold+"+sellprice+" WHERE id="+playerid;
			sqllist.add(sqlstr1);
			res=SqlHelper.execTransaction(sqllist);
		}
		else if(selltype.equals("chip"))
		{
			List<String> ischecklist=SqlHelper.getMyData("SELECT COUNT(*), MAX(isequipped) FROM player_chip WHERE id IN("+sellidpara+")");
			int ischecknum=Integer.parseInt(ischecklist.get(0));
			int ischeckeq=Integer.parseInt(ischecklist.get(1));
			if(ischecknum==sellidarr.length&&ischeckeq==0)
			{
				sellchip(playerid,sellidarr);
				res="true";
			}
		}
		FinishTaskHelper.finishEverydayTask(playerid, "29","1");
		FinishTaskHelper.finishHolidayTask(playerid, "14");
		response.put("res", res);
		return response;
	}

	private void sellchip(String playerid,String[] chipdataid)
	{
		int idlength=chipdataid.length;
		int sellprice=0;
		for(int i=0;i<idlength;i++)
		{
			List<String> chipinfo=SqlHelper.getMyData("SELECT chipid, lv, star FROM player_chip WHERE id="+chipdataid[i]);
			if(chipinfo.size()>0)
			{
				String chipid=chipinfo.get(0);
				int chiplv=Integer.parseInt(chipinfo.get(1));
				String chipstar=chipinfo.get(2);
				sellprice+=Bean.getChipmap().get(chipid).getChipprice();
				for(int j=1;j<chiplv;j++)
				{
					sellprice+=Bean.getLvlupmap().get(String.valueOf(j)).getChiplvlupgold();
				}
				String chipcolor=Bean.getChipmap().get(chipid).getChipcolor();
				String goodsid=null;
				switch(chipcolor)
				{
				case "Red":
					goodsid="2";
					break;
				case "Gold":
					goodsid="3";
					break;
				case "Purple":
					goodsid="4";
					break;
				default:
					break;
				}
				if(goodsid!=null)
				{
					String[] bagsql1={playerid,goodsid,chipstar};
					SqlHelper.DbExecute("insert_player_bag(?,?,?)",bagsql1);
				}
				String sqlstr1="DELETE FROM player_chip WHERE id="+chipdataid[i];
				SqlHelper.Updatedb(sqlstr1);
			}
		}
		String sqlstr1="UPDATE znx_player SET gold=gold+"+sellprice+" WHERE id="+playerid;
		SqlHelper.Updatedb(sqlstr1);
	}
}
