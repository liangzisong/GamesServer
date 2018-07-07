package com.mangni.vegaplan.servletsrc.chatandmail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GetMail implements IReceiveMessage{
	private MyJdbcTemplate myJdbcTemplate;
	public MyJdbcTemplate getMyJdbcTemplate() {
		return myJdbcTemplate;
	}
	public void setMyJdbcTemplate(MyJdbcTemplate myJdbcTemplate) {
		this.myJdbcTemplate = myJdbcTemplate;
	}
	@Override
	public HashMap<String,Object> dopost(HashMap<String,Object> request)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		String playerid=(String)request.get("playerid");
		String sql="SELECT X.* FROM (SELECT A.*,CASE WHEN (EXISTS (SELECT B.mailid FROM player_mail_readed B WHERE B.mailid=A.id AND B.PLAYERID=?)) THEN 1 ELSE 0 END AS isreaded FROM player_mail A WHERE (A.playerid=0 OR A.updatetime>DATEADD(DAY,-7,GETDATE())) AND A.playerid IN(0,?)) X WHERE X.isreaded=0 OR (SHOWTIME IS NOT NULL AND SHOWTIME>GETDATE())";
		List<Map<String, Object>> maildata=myJdbcTemplate.queryForList(sql,new Object[]{playerid,playerid});
		response.put("res", "true");
		response.put("data", maildata);

		return response;
	}
}
