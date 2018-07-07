package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
import com.mangni.vegaplan.toolshelper.SendMessageHelper;

/**
 * 芯片强化
 * 客户端发送playerid=xx&chipid=xx
 * 服务器返回true or false
 * @author Administrator
 *
 */
public class ChipUpStar implements IReceiveMessage{
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
		List<String> chipdata=new ArrayList<String>();
		String playerid=(String) request.get("playerid");
		String needgoodsid=null;
		String playerchipid=(String) request.get("chipid");

		chipdata = SqlHelper.getMyData("select chipid, star from player_chip where id="+playerchipid);
		String znxchipid=chipdata.get(0);
		int star=Integer.parseInt(chipdata.get(1));
		int odds=0;
		switch(star)
		{
		case 0:
			odds=100;
			break;
		case 1:
			odds=100;
			break;
		case 2:
			odds=50;
			break;
		case 3:
			odds=25;
			break;
		case 4:
			odds=15;
			break;
		default :
			break;
		}

		String chipcolor=Bean.getChipmap().get(znxchipid).getChipcolor();
		switch(chipcolor)
		{
		case "Purple":
			needgoodsid="4";
			break;
		case "Gold":
			needgoodsid="3";
			break;
		case "Red":
			needgoodsid="2";
			break;
		}

		Random ran=new Random();
		int ranodds=ran.nextInt(100);
		if(odds<=ranodds)//失败
		{
			String[] sqlpara={playerchipid};
			SqlHelper.DbExecute("strengthen_player_chip_fail(?,?)", sqlpara, true);
			response.put("res", "false");
		}
		else if(odds>ranodds)//成功
		{
			String[] sqlpara={playerchipid,playerid,needgoodsid,"1"};
			String res=SqlHelper.DbExecute("strengthen_player_chip(?,?,?,?,?)", sqlpara, true);
			FinishTaskHelper.finishEverydayTask(playerid, "11", "1");
			FinishTaskHelper.finishHolidayTask(playerid, "21");
			response.put("res",res);
			if(star>2)
			{
				SendMessageHelper sh=new SendMessageHelper(playerid,"1","chip","0",String.valueOf(star+1));
				Thread th=new Thread(sh);
				th.start();
			}
		}
		return response;
	}
}