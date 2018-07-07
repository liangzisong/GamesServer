package com.mangni.vegaplan.servletsrc.updatedata;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.EquipData;
import com.mangni.vegaplan.datatable.LvtableData;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;


public class FighterEquipUplv implements IReceiveMessage {
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String, Object> dopost(HashMap<String, Object> request) throws Exception {
		HashMap<String,Object> response=new HashMap<String,Object>();
		String res="false";
		String playerid=(String)request.get("playerid");
		String fighterid=(String)request.get("fighterid");
		String uptype=(String)request.get("uptype");//lv或star
		Znx_FighterData znx_fighterdata=Bean.getZnx_fightermap().get(fighterid);

		if("lv".equals(uptype)){
			String equipinfo=(String)request.get("equipinfo");//1,2,3,4
			Class<? extends Znx_FighterData> clazz=znx_fighterdata.getClass();
			Method method=clazz.getMethod("getEquip"+equipinfo+"lv");
			int equiplv=Integer.parseInt(String.valueOf(method.invoke(znx_fighterdata)));
			int fighterlv=znx_fighterdata.getFighterlv();
			int uplv=Integer.parseInt((String)request.get("uplv"));
			int sumlv=equiplv+uplv;
			if(sumlv<=fighterlv){
				long needgold=0;
				for(int i=equiplv;i<sumlv;i++)
				{
					long gold=Bean.getLvtablemap().get(String.valueOf(i)).getCoin();
					if(gold==0){
						response.put("res", "false");
						return response;
					}
					needgold+=gold;
				}

				String result=GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0", String.valueOf(-needgold));
				if("1".equals(result)){//成功
					myJdbcTemplate.update("update player_newfighter set equip"+equipinfo+"lv="+sumlv+" where id="+fighterid);
					Method methodequip=clazz.getMethod("setEquip"+equipinfo+"lv",int.class);
					methodequip.invoke(znx_fighterdata, sumlv);
					FinishTaskHelper.finishEverydayTask(playerid, "22", String.valueOf(uplv));
					res="true";
				}
				response.put("res", res);
			}


		}else if("star".equals(uptype)){
			String equipinfo=(String)request.get("equipinfo");//1,2,3,4
			String equipid=(String)request.get("equipid");
			EquipData equipdata=Bean.getEquipmap().get(equipid);
			if(equipdata.getFighterid().equals(znx_fighterdata.getFighterid())&&equipdata.getEquippos().equals(equipinfo)){
				Class<? extends Znx_FighterData> clazz=znx_fighterdata.getClass();
				Method method=clazz.getMethod("getEquip"+equipinfo+"star");
				int equipstar=Integer.parseInt(String.valueOf(method.invoke(znx_fighterdata)));
				LvtableData lvtabledata=Bean.getLvtablemap().get(String.valueOf(equipstar));
				if(znx_fighterdata.getFighterlv()>=lvtabledata.getEquiplv()){
					int i=0;
					/*
						String needsql="SELECT COUNT(*) AS count,MIN(goodsnum) AS min FROM PLAYER_BAG WHERE playerid="+playerid+" and goodsid in(";
						String sqlbu="";
						if(equipstar<=1){
							sqlbu+=equipdata.getMaterial1();
							i++;
						}else if(equipstar<=3){
							sqlbu+=equipdata.getMaterial1()+","+equipdata.getMaterial2();
							i+=2;
						}else if(equipstar<=6){
							sqlbu+=equipdata.getMaterial1()+","+equipdata.getMaterial2()+","+equipdata.getMaterial3();
							i+=3;
						}else{
							sqlbu+=equipdata.getMaterial1()+","+equipdata.getMaterial2()+","+equipdata.getMaterial3()+","+equipdata.getMaterial4();
							i+=4;
						}
					 */
					String needsql="SELECT COUNT(*) AS count,MIN(goodsnum) AS min FROM PLAYER_BAG WHERE playerid="+playerid+" and goodsid in(";
					String sqlbu="";
					int paraint=(equipstar-1)*6+300;
					sqlbu+=(paraint+Integer.parseInt(equipdata.getMaterial1()))+","+(paraint+Integer.parseInt(equipdata.getMaterial2()))+","+(paraint+Integer.parseInt(equipdata.getMaterial3()))+","+(paraint+Integer.parseInt(equipdata.getMaterial4()));
					i+=4;

					needsql+=sqlbu+")";
					Map<String, Object> needmap=myJdbcTemplate.queryForMap(needsql);
					if(Integer.parseInt(String.valueOf(needmap.get("count")))==i&&Integer.parseInt(String.valueOf(needmap.get("min")))>=lvtabledata.getMaterial()){
						String sql="update player_bag set goodsnum=goodsnum-"+lvtabledata.getMaterial()+" where playerid="+playerid+" and goodsid in("+sqlbu+")";
						myJdbcTemplate.update(sql);
						myJdbcTemplate.update("update player_newfighter set equip"+equipinfo+"star=equip"+equipinfo+"star+1 where id="+fighterid);
						Method methodequip=clazz.getDeclaredMethod("setEquip"+equipinfo+"star",int.class);
						methodequip.invoke(znx_fighterdata, equipstar+1);
						res="true";
						FinishTaskHelper.finishEverydayTask(playerid, "11", "1");
					}
				}
			}
			response.put("res", res);
		}else if("allstar".equals(uptype)){
			int [] starinfo={znx_fighterdata.getEquip1star(),znx_fighterdata.getEquip2star(),znx_fighterdata.getEquip3star(),znx_fighterdata.getEquip4star()};
			int allstar=znx_fighterdata.getEquipallstar();
			Arrays.sort(starinfo);
			if(allstar<starinfo[0]){
				myJdbcTemplate.update("update player_newfighter set equipallstar=equipallstar+1 where id="+fighterid);
				znx_fighterdata.setEquipallstar(allstar+1);
				FinishTaskHelper.finishEverydayTask(playerid, "11", "1");
				res="true";
			}
			response.put("res", res);
		}

		return response;
	}
}
