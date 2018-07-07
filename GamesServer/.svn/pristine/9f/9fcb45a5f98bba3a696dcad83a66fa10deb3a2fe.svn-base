package com.mangni.vegaplan.servletsrc.economicsystem;

import java.util.HashMap;
import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

public class GrowthFund implements IReceiveMessage {
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

		String type=(String) request.get("type");

		int playerlv=Bean.getZnx_playermap().get(playerid).getPlayerlv();

		String res="false";

		String growthfund = myJdbcTemplate.queryForObject("SELECT growthfund FROM ZNX_PLAYER WHERE ID="+playerid,String.class);

		if(type.equals("buy")){

			if(growthfund==null)
			{

				if(playerlv<16){

					String sql="UPDATE ZNX_PLAYER SET stone=stone-600, growthfund='0000000000' WHERE ID="+playerid;

					res=LvupHelper.spendStone(playerid, 600, this.getClass().getName(), sql, request, true);

				}

			}else{

				res="have";

			}

		}else if(type.equals("receive")){

			if(growthfund!=null)
			{

				int receiveid=Integer.valueOf((String)request.get("receiveid"));

				int needlv=10+receiveid*5;

				if(growthfund.charAt(receiveid-1)=='0'){

					if(playerlv>=needlv){

						StringBuilder newreceive=new StringBuilder();

						for (int i=0;i<growthfund.length();i++) {

							if(i!=receiveid-1){

								newreceive.append(growthfund.charAt(i));

							}else{

								newreceive.append('1');

							}

						}
						
						int awardstone=490+receiveid*10;
						
						String awardstonesql="UPDATE ZNX_PLAYER SET STONE=STONE+"+awardstone+" ,growthfund='"+newreceive+"' WHERE ID="+playerid;

						myJdbcTemplate.update(awardstonesql);

						res="true";

					}

				}

			}

		}

		response.put("res", res);

		return response;

	}

}
