package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendHttpRequest;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

public class PromotionReward implements IReceiveMessage {
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
		String promotion=(String)request.get("promotion");
		String qudao=(String)request.get("qudao");
		if(qudao==null)
			qudao="0";
		
		String res="false";
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);
		if(znx_playerdata.getPhonenumber()==null){
			response.put("res", res);
			return response;
		}
		String isaward=myJdbcTemplate.queryForObject("SELECT promotionaward FROM ZNX_PLAYER WHERE ID="+playerid,String.class);
		if(isaward.equals("0")){
			String ischeck=SendHttpRequest.sendrequest("checkpromotion","promotion="+promotion+"&qudao="+qudao);
			if(ischeck.equals("true")){
				Date nowdate=new Date();
				String nowtimestr=Bean.getDateFormat().format(nowdate);
				String[] sqlpara1={playerid,"3","推广奖励","stone","0","100","gold","0","10000","chip","67","1"};
				String sql1="INSERT INTO PLAYER_MAIL(PLAYERID,MAILTYPE,MAILTITLE,GOODS1TYPE,GOODS1ID,GOODS1NUM,GOODS2TYPE,GOODS2ID,GOODS2NUM,GOODS3TYPE,GOODS3ID,GOODS3NUM) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
				Object [] sqlpara2={"1",promotion,nowtimestr,playerid};
				String sql2="UPDATE ZNX_PLAYER SET promotionaward=?,promoter=?,promotetime=? WHERE ID=?";
				if(promotion.equalsIgnoreCase("CFUT9ESIERZ")){			
					int viplv=znx_playerdata.getViplv();
					if(viplv==0){
						myJdbcTemplate.update("update znx_player set viplv=1 where id="+playerid+" and viplv=0");
						znx_playerdata.setViplv(1);
					}
				}

				SendMessageHelper.sendMail(sql1, sqlpara1, playerid);
				myJdbcTemplate.update(sql2, sqlpara2);
				
				znx_playerdata.setPromotion(promotion);
				znx_playerdata.setPromotetime(nowtimestr);
				res=SendHttpRequest.sendrequest("adduserconsume", "phonenumber="+znx_playerdata.getPhonenumber()+"&payamount=0&promoter="+promotion+"&promotetime="+nowtimestr+"&serverid="+Bean.getServerid());
			}
		}
		response.put("res", res);
		return response;
	}

}
