package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.HashMap;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class UpdateSkin implements IReceiveMessage {
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
		String fighterid=(String)request.get("fighterid");
		String skinid=(String)request.get("skinid");
		response.put("res", "false");
		Map<String,Object> fighterinfo=myJdbcTemplate.queryForMap("select playerid,fighterid from player_fighter where id="+fighterid);
		if(fighterinfo==null||fighterinfo.isEmpty())
			return response;

		if(!fighterinfo.get("playerid").equals(playerid))
			return response;

		if(!skinid.equals("0")){

			Map<String,Object> skininfo=myJdbcTemplate.queryForMap("select playerid,skinid from player_skin where id="+skinid+" and (expirydate>getdate() or expirydate is null)");
			if(skininfo==null||skininfo.isEmpty())
				return response;

			if(!playerid.equals(skininfo.get("playerid")))
				return response;

			if(!Bean.getSkinmap().get(skininfo.get("skinid")).getFighterid().equals(fighterinfo.get("fighterid")))
				return response;
		}

		myJdbcTemplate.update("update player_fighter set skinid="+skinid+" where id="+fighterid);

		response.put("res", "true");
		return response;

	}

}
