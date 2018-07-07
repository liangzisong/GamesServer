package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class ReName implements IReceiveMessage {
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
		String newname=(String)request.get("newname");
		if(newname.length()<2)
			return null;
		String res="false";
		String ishave=myJdbcTemplate.queryForObject("select top 1 id from znx_player where nickname=?",new String[]{newname},String.class);
		if(ishave==null){
			List<Map<String, Object>> isrenameinfo=myJdbcTemplate.queryForList("select isrename,stone from znx_player where id="+playerid);
			String isrename=(String)isrenameinfo.get(0).get("isrename");
			if(isrename.equals("0")){
				myJdbcTemplate.update("update znx_player set nickname=?,isrename=1 where id=?", new Object[]{newname,playerid});
				res="true";
			}else{
				int havestone=Integer.parseInt((String)isrenameinfo.get(0).get("stone"));
				if(havestone>=800){
					String sql="update znx_player set nickname=?,isrename=1,stone=stone-800 where id=?";
					String[] sqlpara={newname,playerid};
					LvupHelper.spendStone(playerid, 800, "rename", sql, sqlpara, request, false);
					res="true";
				}else{
					res="not enough";
				}
			}
		}else{
			res="have";
		}
		response.put("res", res);
		return response;
	}

}
