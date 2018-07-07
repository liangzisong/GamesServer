package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.VipPackageData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class BuyVipShop implements IReceiveMessage {
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

		ArrayList<String> znxjewelid=new ArrayList<String>();

		ArrayList<String> datajewelid=new ArrayList<String>();

		ArrayList<String> znxchipid=new ArrayList<String>();

		ArrayList<String> datachipid=new ArrayList<String>();


		String res="false";

		String playerid=(String) request.get("playerid");

		int buyid=Integer.valueOf((String) request.get("buyid"));

		int playerviplv=Bean.getZnx_playermap().get(playerid).getViplv();

		VipPackageData vippackage=Bean.getVippackagemap().get(String.valueOf(buyid));

		String buynum=SqlHelper.getOneRead("select buynum from player_buyshopnum where playerid="+playerid+" and shoptype=3 and templateid="+buyid);

		if(buynum==null||buynum.equals("0"))
		{

			if(playerviplv>=buyid-1){

				int havestone=Integer.parseInt(SqlHelper.getOneRead("SELECT stone FROM znx_player WHERE id="+playerid));

				if(havestone>=vippackage.getNeedstone()){

					int awardgold=vippackage.getGold();

					String speedsql="UPDATE ZNX_PLAYER SET GOLD=GOLD+" + awardgold + ",STONE=STONE-" + vippackage.getNeedstone() + " WHERE ID="+playerid;

					LvupHelper.spendStone(playerid, vippackage.getNeedstone(), this.getClass().getName(), speedsql, request, false);

					int jewelnum=vippackage.getJewelnum();

					for(int i=0;i<jewelnum;i++){

						String znxjewid=GetGoodsHelper.getRanJewAlertChar(vippackage.getJewelcharacter());

						String datajewid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.JEWEL, znxjewid, "1");

						znxjewelid.add(znxjewid);

						datajewelid.add(datajewid);

					}

					int chipnum=vippackage.getChipnum();

					for(int i=0;i<chipnum;i++){

						String znxchid=GetGoodsHelper.getRanChipAlertColor(vippackage.getChipcolor());

						String datachid=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.CHIP, znxchid, "1");

						znxchipid.add(znxchid);

						datachipid.add(datachid);

					}

					int goodsnum=vippackage.getItemnum();

					if(goodsnum>0)

						GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, vippackage.getItemid(), String.valueOf(goodsnum));
					
					String [] numsqlpara={playerid,"3",String.valueOf(buyid),"1"};
					SqlHelper.DbExecute("update_player_buyshopnum(?,?,?,?)",numsqlpara);

					res="true";
					
					FinishTaskHelper.finishEverydayTask(playerid, "30","1");
					
					FinishTaskHelper.finishHolidayTask(playerid, "28");

				}else{

					res="not enough";

				}

			}

		}

		response.put("res", res);

		response.put("znxjewelid", znxjewelid);

		response.put("datajewelid", datajewelid);

		response.put("znxchipid", znxchipid);

		response.put("datachipid", datachipid);

		return response;
		
	}

}
