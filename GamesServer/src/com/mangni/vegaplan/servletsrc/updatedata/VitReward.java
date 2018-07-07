package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class VitReward implements IReceiveMessage {
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

		String playerid=(String) request.get("playerid");
		
		Znx_PlayerData znx_playerdata=Bean.getZnx_playermap().get(playerid);

		String rewardvittime=znx_playerdata.getRewardvittime();

		String res="false";

		String sql=null;

		try{

			if("null".equals(rewardvittime)){

				sql="UPDATE znx_player SET rewardvittime=getdate() WHERE id="+playerid;

			}else{

				Calendar calendar=Calendar.getInstance();

				int nowhour=calendar.get(Calendar.HOUR_OF_DAY);

				if(nowhour>=11&&nowhour<14){

					calendar.set(Calendar.HOUR_OF_DAY, 11);
					
					calendar.set(Calendar.MINUTE, 0);
					
					calendar.set(Calendar.SECOND, 0);
					
					calendar.set(Calendar.MILLISECOND, 0);


				}else if(nowhour>=17&&nowhour<20){

					calendar.set(Calendar.HOUR_OF_DAY, 17);
					
					calendar.set(Calendar.MINUTE, 0);
					
					calendar.set(Calendar.SECOND, 0);
					
					calendar.set(Calendar.MILLISECOND, 0);

				}
				
				Date rewardvitdate=Bean.getDateFormat().parse(rewardvittime);

				if(calendar.getTime().getTime()>=rewardvitdate.getTime()){

					sql="UPDATE znx_player SET rewardvittime=getdate() WHERE id="+playerid;

				}

			}

		}catch(Exception e){

			String time=Bean.getDateFormat().format(new Date().getTime());
			Bean.getTextArea().append("\r\n");
			Bean.getTextArea().append(time+"："+e.getMessage()+"\r");
			Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
			e.printStackTrace();

		}

		if(sql!=null){

			myJdbcTemplate.update(sql);
			
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.VIT, "0", "40");
			
			Date date=new Date();
			
			String rewardvittimestr=Bean.getDateFormat().format(date);
			
			znx_playerdata.setRewardvittime(rewardvittimestr);
			
			FinishTaskHelper.finishEverydayTask(playerid, "13", "1");
			
			FinishTaskHelper.finishEverydayTask(playerid, "26","1");

			res="true";

		}


		response.put("res", res);

		return response;

	}
}
