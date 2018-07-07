package com.mangni.vegaplan.servletsrc.customsystem.servletsrc;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.servletsrc.customsystem.tools.Initialise;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class SendMail implements IReceiveMessage {
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
		String gmid=(String)request.get("gmid");
		response.put("res", "false");
		if(!Initialise.getZnx_gmmap().containsKey(gmid))
			return response;
		String[] nicknamepara=((String)request.get("nickname")).split(",");
		String mailtype="9";
		String mailtitle=(String)request.get("mailtitle");
		String goods1type=(String)request.get("goods1type")!=null?(String)request.get("goods1type"):"0";
		String goods1id=(String)request.get("goods1id")!=null?(String)request.get("goods1id"):"0";
		String goods1num=(String)request.get("goods1num")!=null?(String)request.get("goods1num"):"0";
		String goods2type=(String)request.get("goods2type")!=null?(String)request.get("goods2type"):"0";
		String goods2id=(String)request.get("goods2id")!=null?(String)request.get("goods2id"):"0";
		String goods2num=(String)request.get("goods2num")!=null?(String)request.get("goods2num"):"0";
		String goods3type=(String)request.get("goods3type")!=null?(String)request.get("goods3type"):"0";
		String goods3id=(String)request.get("goods3id")!=null?(String)request.get("goods3id"):"0";
		String goods3num=(String)request.get("goods3num")!=null?(String)request.get("goods3num"):"0";
		String mailcontent=(String)request.get("mailcontent");
		String showtime=(String)request.get("showtime");
		for(int i=0;i<nicknamepara.length;i++){
			String nickname=nicknamepara[i];
			String playerid="0";
			if(!nickname.equals("0"))
				playerid=myJdbcTemplate.queryForObject("select top 1 id from znx_player where nickname='"+nickname+"'",String.class);

			if(playerid==null)
				return response;

			if(showtime==null||"".equals(showtime)){
				String[] mailpara={playerid,mailtype,mailtitle,goods1type,goods1id,goods1num,goods2type,goods2id,goods2num,goods3type,goods3id,goods3num,mailcontent};
				String sql="insert into player_mail(playerid,mailtype,mailtitle,goods1type,goods1id,goods1num,goods2type,goods2id,goods2num,goods3type,goods3id,goods3num,mailcontent) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
				SendMessageHelper.sendMail(sql, mailpara, playerid);
			}else{
				String[] mailpara={playerid,mailtype,mailtitle,goods1type,goods1id,goods1num,goods2type,goods2id,goods2num,goods3type,goods3id,goods3num,mailcontent,showtime};
				String sql="insert into player_mail(playerid,mailtype,mailtitle,goods1type,goods1id,goods1num,goods2type,goods2id,goods2num,goods3type,goods3id,goods3num,mailcontent,showtime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				SendMessageHelper.sendMail(sql, mailpara, playerid);
			}
		}
		response.put("res", "true");
		return response;
	}

}
