package com.mangni.vegaplan.servletsrc.updatedata;

import java.util.Calendar;
import java.util.HashMap;

import com.mangni.vegaplan.MainIReceive.IReceiveMessage;
import com.mangni.vegaplan.datatable.ChampionsData;
import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.LvupHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;
/**
 * 完成夺标时客户端发送playerid=xx&tablename&key1=value1,key2=value2... 服务器返回true或false
 */
public class FiChampions implements IReceiveMessage{
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
		ChampionsData cham=new ChampionsData();
		int awardexp=0;
		int bosshp=1,sumhurt=1;
		HashMap<String,Object> response=new HashMap<String,Object>();
		response=new HashMap<String,Object>();
		String playerid=(String) request.get("playerid");
		//根据等级判断击杀的是哪一个boss
		String fighter1id=(String) request.get("fighter1id");
		String fighter2id=(String) request.get("fighter2id");
		String fighter3id=(String) request.get("fighter3id");
		String isspendstone=(String) request.get("isspendstone");
		int finishlv=Bean.getZnx_playermap().get(playerid).getPlayerlv();
		String isfinish=myJdbcTemplate.queryForObject("select isfinish from player_champions where playerid="+playerid,String.class);
		Calendar nowdate=Calendar.getInstance();
		int nowhour=nowdate.get(Calendar.HOUR_OF_DAY);
		if(nowhour>=0&&nowhour<=23)
		{
			if(isfinish==null||isfinish.equals("0")||true)
			{
				HashMap<String,ChampionsData>chammap=Bean.getChampionsmap();
				for(int i=chammap.size();i>0;i--)
				{
					cham=chammap.get(String.valueOf(i));
					int minlv=cham.getMinlv();
					if(finishlv<minlv)
						continue;
					bosshp=Integer.parseInt(cham.getBosshp());
					sumhurt=Integer.parseInt((String) request.get("sumhurt"));
					awardexp=AwardExp(bosshp,sumhurt,cham,playerid,isspendstone,request);
					break;
				}
				
				String[] fighterid={fighter1id,fighter2id,fighter3id};
				LvupHelper.UpPlayerExp(playerid, awardexp);
				LvupHelper.UpFighterExp(fighterid, awardexp);


				Object [] cum={String.valueOf(finishlv),fighter1id,(String) request.get("fighter1lv"),(String) request.get("fighter1hurt"),(String) request.get("fighter2id"),(String) request.get("fighter2lv"),(String) request.get("fighter2hurt"),(String) request.get("fighter3id"),(String) request.get("fighter3lv"),(String) request.get("fighter3hurt"),(String) request.get("battletime"),(String) request.get("sumhurt")};
				if(isfinish!=null)
				{
					String sql="update player_champions set finishlv=?,fighter1id=?,fighter1lv=?,fighter1hurt=?,fighter2id=?,fighter2lv=?,fighter2hurt=?,fighter3id=?,fighter3lv=?,fighter3hurt=?,battletime=?,sumhurt=?,isfinish='1',updatetime=getdate() where playerid="+playerid;	
					myJdbcTemplate.update(sql,cum);
					response.put("res","true");
				}
				else
				{
					String sql="insert into player_champions(playerid,finishlv,fighter1id,fighter1lv,fighter1hurt,fighter2id,fighter2lv,fighter2hurt,fighter3id,fighter3lv,fighter3hurt,battletime,sumhurt) values("+playerid+",?,?,?,?,?,?,?,?,?,?,?,?)";
					myJdbcTemplate.update(sql,cum);
					response.put("res","true");
				}
				FinishTaskHelper.finishEverydayTask(playerid, "25","1");
				FinishTaskHelper.finishHolidayTask(playerid, "25");
			}
			else
			{
				response.put("res","false");
			}
		}
		return response;
	}
	//得到经验奖励数额
	private int AwardExp(int bosshp,int sumhurt,ChampionsData cham,String playerid,String isspendstone,HashMap<String,Object> request){
		double hurtper=Math.floor((double)sumhurt*100/bosshp);
		int awardexp=0;
		int awardgold=0;
		String awardgoodsid15=null;
		String awardgoodsnum15=null;
		String awardgoodsid30=null;
		String awardgoodsnum30=null;
		String awardgoodsid60=null;
		String awardgoodsnum60=null;
		String awardgoodsid100=null;
		String awardgoodsnum100=null;

		awardgold=Integer.parseInt(cham.getPer15gold());
		if(isspendstone.equals("1"))
		{
			String sql="UPDATE ZNX_PLAYER SET GOLD=GOLD+"+awardgold+",STONE=STONE-100 WHERE ID="+playerid;
			LvupHelper.spendStone(playerid, 100, this.getClass().getName(), sql, request, true);
		}
		else
		{
			myJdbcTemplate.update("update znx_player set gold=gold+"+awardgold+" where id="+playerid);
		}
		
		if(hurtper>=15&&hurtper<30)
		{
			awardexp=Integer.parseInt(cham.getPer15exp());
			awardgoodsid15=cham.getPer15goodsid();
			awardgoodsnum15=cham.getPer15goodsnum();
			if(!awardgoodsid15.equals("0")&&!awardgoodsnum15.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid15, awardgoodsnum15);
			}		
		}
		else if(hurtper>=30&&hurtper<60)
		{
			awardexp=Integer.parseInt(cham.getPer15exp())+Integer.parseInt(cham.getPer30exp());
			awardgoodsid15=cham.getPer15goodsid();
			awardgoodsnum15=cham.getPer15goodsnum();
			awardgoodsid30=cham.getPer30goodsid();
			awardgoodsnum30=cham.getPer30goodsnum();

			if(!awardgoodsid15.equals("0")&&!awardgoodsnum15.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid15, awardgoodsnum15);
			}
			if(!awardgoodsid30.equals("0")&&!awardgoodsnum30.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid30, awardgoodsnum30);
			}
		}
		else if(hurtper>=60&&hurtper<100)
		{
			awardexp=Integer.parseInt(cham.getPer15exp())+Integer.parseInt(cham.getPer30exp())+Integer.parseInt(cham.getPer60exp());
			awardgoodsid15=cham.getPer15goodsid();
			awardgoodsnum15=cham.getPer15goodsnum();
			awardgoodsid30=cham.getPer30goodsid();
			awardgoodsnum30=cham.getPer30goodsnum();
			awardgoodsid60=cham.getPer60goodsid();
			awardgoodsnum60=cham.getPer60goodsnum();

			if(!awardgoodsid15.equals("0")&&!awardgoodsnum15.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid15, awardgoodsnum15);
			}
			if(!awardgoodsid30.equals("0")&&!awardgoodsnum30.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid30, awardgoodsnum30);
			}
			if(!awardgoodsid60.equals("0")&&!awardgoodsnum60.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid60, awardgoodsnum60);
			}
		}
		else if(hurtper>=100)
		{
			awardexp=Integer.parseInt(cham.getPer15exp())+Integer.parseInt(cham.getPer30exp())+Integer.parseInt(cham.getPer60exp())+Integer.parseInt(cham.getPer100exp());
			awardgoodsid15=cham.getPer15goodsid();
			awardgoodsnum15=cham.getPer15goodsnum();
			awardgoodsid30=cham.getPer30goodsid();
			awardgoodsnum30=cham.getPer30goodsnum();
			awardgoodsid60=cham.getPer60goodsid();
			awardgoodsnum60=cham.getPer60goodsnum();
			awardgoodsid100=cham.getPer100goodsid();
			awardgoodsnum100=cham.getPer100goodsnum();

			if(!awardgoodsid15.equals("0")&&!awardgoodsnum15.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid15, awardgoodsnum15);
			}
			if(!awardgoodsid30.equals("0")&&!awardgoodsnum30.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid30, awardgoodsnum30);
			}
			if(!awardgoodsid60.equals("0")&&!awardgoodsnum60.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid60, awardgoodsnum60);
			}
			if(!awardgoodsid100.equals("0")&&!awardgoodsnum100.equals("0"))
			{
				GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, awardgoodsid100, awardgoodsnum100);
			}
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOLD, "0",String.valueOf(awardgold));
		}	
		return awardexp;
	}
}
