package com.mangni.vegaplan.servletsrc.updatedata;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.ItemData;
import com.mangni.vegaplan.datatable.MaterialproData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;

public class Rafflenew implements IReceiveMessage {

	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		String playerid = (String) request.get("playerid");
		String raffletype = (String) request.get("raffletype");// fighter||material
		int oddtype = Integer.parseInt((String) request.get("oddtype"));// 1||10
		if ("fighter".equals(raffletype)) {
			List<HashMap<String, Object>> raffleinfo = new ArrayList<HashMap<String, Object>>();
			if (oddtype == 10) {
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "243", "-10");
				raffleinfo.add(rafflefighterone(playerid, true));
				for (int i = 0; i < 9; i++) {
					raffleinfo.add(rafflefighterone(playerid, false));
				}
			} else {
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "243", "-1");
				raffleinfo.add(rafflefighterone(playerid, false));
			}
			FinishTaskHelper.finishEverydayTask(playerid, "19", String.valueOf(oddtype));
			response.put("res", raffleinfo);

		} else if ("material".equals(raffletype)) {
			List<HashMap<String, Object>> raffleinfo = new ArrayList<HashMap<String, Object>>();
			if (oddtype == 10) {
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "241", "-10");
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "243", "1");
				for (int i = 0; i < 10; i++) {
					raffleinfo.add(raffleItemone(playerid));
				}
			} else {
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "241", "-1");
				raffleinfo.add(raffleItemone(playerid));
			}
			FinishTaskHelper.finishEverydayTask(playerid, "20", String.valueOf(oddtype));
			response.put("res", raffleinfo);
		}
		return response;
	}

	private HashMap<String, Object> rafflefighterone(String playerid, boolean isten) {
		HashMap<String, Object> res = new HashMap<String, Object>();
		Random random = new Random();
		int fighterorpath = random.nextInt(99);
		if (isten)
			fighterorpath = 99;
		if (fighterorpath >= 98) {// 抽中机甲
			List<String> prolist = Bean.getRafflefighterone();
			if (isten)
				prolist = Bean.getRafflefighterten();
			int listposition = random.nextInt(prolist.size() - 1);// 随机链表下标
			String fighterid = prolist.get(listposition);// 抽中机甲的模版id
			String dataid = GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, fighterid, "1");
			res.put("res", "fighter");
			res.put("fighterid", fighterid);
			res.put("dataid", dataid);
		} else {// 抽中碎片
			List<String> prolist = Bean.getRafflefighterone();
			int listposition = random.nextInt(prolist.size() - 1);// 随机链表下标
			String fighterid = prolist.get(listposition);// 抽中机甲的模版id
			int star = Bean.getFightermap().get(fighterid).getDefaultstar();
			int pro1 = 0, pro2 = 0, pro3 = 0, pro4 = 0;
			if (star == 1) {
				pro1 = 40;
				pro2 = 30 + pro1;
				pro3 = 20 + pro2;
				pro4 = 10 + pro3;
			} else if (star == 2) {
				pro1 = 49;
				pro2 = 30 + pro1;
				pro3 = 12 + pro2;
				pro4 = 6 + pro3;
			} else {
				pro1 = 40;
				pro2 = 25 + pro1;
				pro3 = 20 + pro2;
				pro4 = 10 + pro3;
			}
			int rewardnum = 0;
			int rannum = random.nextInt(99);
			if (rannum < pro1) {
				rewardnum = 1;
			} else if (rannum >= pro1 && rannum < pro2) {
				rewardnum = 2;
			} else if (rannum >= pro2 && rannum < pro3) {
				rewardnum = 3;
			} else if (rannum >= pro3 && rannum < pro4) {
				rewardnum = 4;
			} else {
				rewardnum = 5;
			}
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, fighterid, String.valueOf(rewardnum));
			res.put("res", "fighterpath");
			res.put("goodsid", fighterid);
			res.put("goodsnum", String.valueOf(rewardnum));
		}
		return res;
	}

	private HashMap<String, Object> raffleItemone(String playerid) {
		Random ran = new Random();
		int rannum = ran.nextInt(99);
		String lv;
		if (rannum < 40) {
			lv = "1";
		} else if (rannum >= 40 && rannum < 65) {
			lv = "2";
		} else if (rannum >= 65 && rannum < 85) {
			lv = "3";
		} else if (rannum >= 85 && rannum < 95) {
			lv = "4";
		} else {
			lv = "5";
		}
		Znx_PlayerData znx_playerdata = Bean.getZnx_playermap().get(playerid);
		int playerlv = znx_playerdata.getPlayerlv();
		int templateid = (playerlv / 5) + 1;
		int materiallv = 0;
		MaterialproData materialdata = Bean.getMaterialpromap().get(String.valueOf(templateid));
		Class<? extends MaterialproData> clazz = materialdata.getClass();
		try {
			Method method = clazz.getMethod("getMaterial" + lv + "lv");
			materiallv = (int) method.invoke(materialdata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Integer> list = new ArrayList<Integer>();
		Iterator<Entry<String, ItemData>> iterator = Bean.getItemmap().entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, ItemData> mapentry = iterator.next();
			String key = mapentry.getKey().toString();
			ItemData itemdata = mapentry.getValue();
			if (itemdata.getIdbyTypeAndLv("MATERIAL", materiallv) != null) {
				list.add(Integer.valueOf(key));
			}
		}
		Collections.sort(list);
		int listpos = 0;
		rannum = ran.nextInt(99);
		if (rannum < 22) {
			listpos = 0;
		} else if (rannum >= 22 && rannum < 38) {
			listpos = 1;
		} else if (rannum >= 38 && rannum < 52) {
			listpos = 2;
		} else if (rannum >= 52 && rannum < 67) {
			listpos = 3;
		} else if (rannum >= 67 && rannum < 83) {
			listpos = 4;
		} else {
			listpos = 5;
		}
		rannum = ran.nextInt(99);
		String goodsnum = "0";
		if (rannum < 40) {
			goodsnum = "1";
		} else if (rannum >= 40 && rannum < 65) {
			goodsnum = "2";
		} else if (rannum >= 65 && rannum < 85) {
			goodsnum = "3";
		} else if (rannum >= 85 && rannum < 95) {
			goodsnum = "4";
		} else {
			goodsnum = "5";
		}
		String goodsid = String.valueOf(list.get(listpos));
		GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, goodsid, goodsnum);
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.put("res", "goods");
		res.put("goodsid", goodsid);
		res.put("goodsnum", goodsnum);
		return res;
	}
}
