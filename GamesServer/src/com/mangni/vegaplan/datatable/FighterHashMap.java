package com.mangni.vegaplan.datatable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class FighterHashMap<K,V> extends HashMap<String,Znx_FighterData> {

	@Override
	public Znx_FighterData get(Object fighterid){
		
		MyJdbcTemplate myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
		
		Znx_FighterData znx_fighterdata=super.get(fighterid);
		if(znx_fighterdata==null)
		{
			List<Map<String,Object>> fighterdata=myJdbcTemplate.queryForList("select * from player_newfighter where id="+fighterid);
			znx_fighterdata=new Znx_FighterData();
			znx_fighterdata.setId(String.valueOf(fighterid));
			znx_fighterdata.setPlayerid(String.valueOf(fighterdata.get(0).get("playerid")));
			znx_fighterdata.setFighterid(String.valueOf(fighterdata.get(0).get("fighterid")));
			znx_fighterdata.LvlupFighterstar(Integer.parseInt(String.valueOf(fighterdata.get(0).get("star"))));
			znx_fighterdata.lvlupFighterlv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterlv"))));
			znx_fighterdata.setFighterexp(Long.parseLong(String.valueOf(fighterdata.get(0).get("fighterexp"))));
			znx_fighterdata.setEquipallstar(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equipallstar"))));
			znx_fighterdata.setEquip1lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip1lv"))));
			znx_fighterdata.setEquip1star(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip1star"))));
			znx_fighterdata.setEquip2lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip2lv"))));
			znx_fighterdata.setEquip2star(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip2star"))));
			znx_fighterdata.setEquip3lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip3lv"))));
			znx_fighterdata.setEquip3star(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip3star"))));
			znx_fighterdata.setEquip4lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip4lv"))));
			znx_fighterdata.setEquip4star(Integer.parseInt(String.valueOf(fighterdata.get(0).get("equip4star"))));
			znx_fighterdata.setTacticslv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("tacticslv"))));
			znx_fighterdata.setTactics1(Integer.parseInt(String.valueOf(fighterdata.get(0).get("tactics1"))));
			znx_fighterdata.setTactics2(Integer.parseInt(String.valueOf(fighterdata.get(0).get("tactics2"))));
			znx_fighterdata.setTactics3(Integer.parseInt(String.valueOf(fighterdata.get(0).get("tactics3"))));
			znx_fighterdata.setTactics4(Integer.parseInt(String.valueOf(fighterdata.get(0).get("tactics4"))));
			znx_fighterdata.setFighterskill1lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill1lv"))));
			znx_fighterdata.setFighterskill1exp(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill1exp"))));
			znx_fighterdata.setFighterskill2lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill2lv"))));
			znx_fighterdata.setFighterskill2exp(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill2exp"))));
			znx_fighterdata.setFighterskill3lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill3lv"))));
			znx_fighterdata.setFighterskill3exp(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill3exp"))));
			znx_fighterdata.setFighterskill4lv(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill4lv"))));
			znx_fighterdata.setFighterskill4exp(Integer.parseInt(String.valueOf(fighterdata.get(0).get("fighterskill4exp"))));
			
			Bean.getZnx_fightermap().put(String.valueOf(fighterid), znx_fighterdata);
		}
		return znx_fighterdata;
	}
}
