package com.mangni.vegaplan.servletsrc.beforeplay;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.websocket.api.Session;

import com.mangni.vegaplan.datatable.EnumGoodsTypes;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;
import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.FinishTaskHelper;
import com.mangni.vegaplan.toolshelper.GetGoodsHelper;
import com.mangni.vegaplan.toolshelper.MyJdbcTemplate;

/**
 * 客户端成功登陆后发送用户名，服务器返回玩家信息
 */
public class UserLogin{
	private MyJdbcTemplate myJdbcTemplate;
	public UserLogin(){
		myJdbcTemplate=(MyJdbcTemplate) Bean.getCtx().getBean("myJdbcTemplate");
	}
	public HashMap<String,Object> dopost(HashMap<String,Object> request,Session session)
	{
		HashMap<String,Object> response=new HashMap<String,Object>();
		int onlinenum=Bean.getZnx_playermap().size();
		if(onlinenum>1500){
			response.put("res", "wait");
			return response;
		}
		response=login(request,session);
		return response;
	}

	public void loadGang(){
		List<Map<String,Object>> ganginfo=myJdbcTemplate.queryForList("select * from player_gang");
		for(Map<String,Object> gangmap:ganginfo){
			Znx_GangData znx_gangdata=new Znx_GangData();
			String gangid=String.valueOf(gangmap.get("id"));
			String gangname=String.valueOf(gangmap.get("gangid"));
			String gangpwd=String.valueOf(gangmap.get("gangpwd"));
			String limitlv=String.valueOf(gangmap.get("limitlv"));
			String ganglv=String.valueOf(gangmap.get("ganglv"));
			String gangexp=String.valueOf(gangmap.get("gangexp"));
			String turnlv=String.valueOf(gangmap.get("turnlv"));
			String turnexp=String.valueOf(gangmap.get("turnexp"));
			String points=String.valueOf(gangmap.get("points"));
			String goallockedtime=String.valueOf(gangmap.get("goallockedtime"));
			String goallv=String.valueOf(gangmap.get("goallv"));
			String battleid=String.valueOf(gangmap.get("battleid"));
			String battleposition=String.valueOf(gangmap.get("battleposition"));
			String gangbattletime=String.valueOf(gangmap.get("battletime"));
			String gangintegral=String.valueOf(gangmap.get("gangintegral"));
			String basecamplv=String.valueOf(gangmap.get("basecamplv"));
			String basecampexp=String.valueOf(gangmap.get("basecampexp"));
			String commissariatlv=String.valueOf(gangmap.get("commissariatlv"));
			String commissariatexp=String.valueOf(gangmap.get("commissariatexp"));
			String researchcenterlv=String.valueOf(gangmap.get("researchcenterlv"));
			String researchcenterexp=String.valueOf(gangmap.get("researchcenterexp"));
			String flameslv=String.valueOf(gangmap.get("flameslv"));
			String flamesexp=String.valueOf(gangmap.get("flamesexp"));
			znx_gangdata.setId(gangid);
			znx_gangdata.setGangname(gangname);
			znx_gangdata.setGangpwd(gangpwd);
			znx_gangdata.setLimitlv(Integer.parseInt(limitlv));
			znx_gangdata.setGanglv(Integer.parseInt(ganglv));
			znx_gangdata.setGangexp(Integer.parseInt(gangexp));
			znx_gangdata.setTurnlv(Integer.parseInt(turnlv));
			znx_gangdata.setTurnexp(Integer.parseInt(turnexp));
			znx_gangdata.setPoints(Integer.parseInt(points));
			znx_gangdata.setGoallockedtime(goallockedtime);
			znx_gangdata.setGoallv(Integer.parseInt(goallv));
			znx_gangdata.setBattleid(Integer.parseInt(battleid));
			znx_gangdata.setBattleposition(Integer.parseInt(battleposition));
			znx_gangdata.setBattletime(gangbattletime);
			znx_gangdata.setGangintegral(Integer.parseInt(gangintegral));
			znx_gangdata.setBasecamplv(Integer.parseInt(basecamplv));
			znx_gangdata.setBasecampexp(Integer.parseInt(basecampexp));
			znx_gangdata.setCommissariatlv(Integer.parseInt(commissariatlv));
			znx_gangdata.setCommissariatexp(Integer.parseInt(commissariatexp));
			znx_gangdata.setResearchcenterlv(Integer.parseInt(researchcenterlv));
			znx_gangdata.setResearchcenterexp(Integer.parseInt(researchcenterexp));
			znx_gangdata.setFlameslv(Integer.parseInt(flameslv));
			znx_gangdata.setFlamesexp(Integer.parseInt(flamesexp));
			Bean.getZnx_gangmap().put(gangid, znx_gangdata);
		}
	}

	public HashMap<String,Object> login(HashMap<String,Object> request,Session session){
		HashMap<String,Object> response=new HashMap<String,Object>();
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		String uid=(String)request.get("uid");
		String sdk=(String)request.get("sdk");
		if(uid!=null){
			if(sdk==null||sdk=="")
				return null;
			result=myJdbcTemplate.queryForList("select * from znx_player where uid=? and sdkid=?",new Object[]{uid,sdk}); 
		}else{
			String username=(String)request.get("username");

			if(username!=null&&username!="")
			{
				int chr=username.charAt(0);
				if(chr<48||chr>57)
				{
					result=myJdbcTemplate.queryForList("select * from znx_player where username=?",username);
				}
				else
				{
					result=myJdbcTemplate.queryForList("select * from znx_player where phonenumber=?",username);
				}
			}
		}
		if(result.size()>0)
		{
			String playerid=String.valueOf(result.get(0).get("id"));
			String nickname=String.valueOf(result.get(0).get("nickname"));

			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "241", "100");
			GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.GOODS, "243", "100");

			if(nickname!=null)
			{
				String lastip=session.getRemoteAddress().getAddress().getHostAddress();
				myJdbcTemplate.update("update znx_player set lastip='"+lastip+"',updatetime=getdate() where id="+playerid);
				String thistime=myJdbcTemplate.queryForObject("SELECT updatetime FROM znx_player WHERE id="+playerid,String.class);
				response.put("thistime", thistime);
				String sqlhavefighter="select top 1 id from player_newfighter where playerid="+result.get(0).get("id");
				String ishavefighter=myJdbcTemplate.queryForObject(sqlhavefighter,String.class);
				if(ishavefighter==null)
				{
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, "101", "1");
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, "102", "1");
					GetGoodsHelper.getGoods(playerid, EnumGoodsTypes.FIGHTER, "103", "1");
				}

				List<Map<String, Object>> ganginfo=myJdbcTemplate.queryForList("select A.*,B.* from player_ganginfo A left join player_gang B on A.gangid=B.id where A.playerid="+playerid+" and A.ischecked=1");
				String phonenumber=String.valueOf(result.get(0).get("phonenumber"));
				String sex=String.valueOf(result.get(0).get("sex"));
				String csstar=String.valueOf(result.get(0).get("csstar"));
				String playerlv=String.valueOf(result.get(0).get("playerlv"));
				String playerexp=String.valueOf(result.get(0).get("playerexp"));
				String vit=String.valueOf(result.get(0).get("vit"));
				String vittime=String.valueOf(result.get(0).get("vittime"));
				String energy=String.valueOf(result.get(0).get("energy"));
				String energytime=String.valueOf(result.get(0).get("energytime"));
				String rewardvittime=String.valueOf(result.get(0).get("rewardvittime"));
				String viplv=String.valueOf(result.get(0).get("viplv"));
				String vipreceive=String.valueOf(result.get(0).get("vipreceive"));
				String buyvitnum=String.valueOf(result.get(0).get("buyvitnum"));
				String buyenergynum=String.valueOf(result.get(0).get("buyenergynum"));
				String buygoldnum=String.valueOf(result.get(0).get("buygoldnum"));
				String rmbstone=String.valueOf(result.get(0).get("rmbstone"));
				String firstreceive=String.valueOf(result.get(0).get("firstreceive"));
				String promotion=String.valueOf(result.get(0).get("promoter"));
				String promotetime=String.valueOf(result.get(0).get("promotetime"));
				String updatetime=String.valueOf(result.get(0).get("updatetime"));
				String holidaygoldtime=String.valueOf(result.get(0).get("holidaygoldtime"));
				String holidaygold=String.valueOf(result.get(0).get("holidaygold"));
				String exchangeholidaygold=String.valueOf(result.get(0).get("exchangeholidaygold"));
				String exchangeholidaygoldtime=String.valueOf(result.get(0).get("exchangeholidaygoldtime"));
				String gangcontributenum=String.valueOf(result.get(0).get("gangcontributenum"));
				String gangcontributenumtime=String.valueOf(result.get(0).get("gangcontributenumtime"));

				String turnid=null;
				int isturned=0;
				String refreshnum=null;
				String turnpaynum=null;
				String gangmilitarylv=null;
				String gangmilitaryexp=null;
				String rewardpaytime=null;
				int goalhurtnum=0;
				String goalhurttime=null;
				int actpower=0;
				String actpowertime=null;
				int endurance=0;
				int battlepositionx=0;
				int battlepositiony=0;
				String positiontime=null;

				Znx_PlayerData znx_playerdata=new Znx_PlayerData();

				if(ganginfo.isEmpty()){				
					turnid="0";	
					refreshnum="0";				
					turnpaynum="0";					
					gangmilitarylv="1";			
					gangmilitaryexp="0";
				}else{
					turnid=String.valueOf(ganginfo.get(0).get("turnid"));
					isturned=Integer.parseInt(String.valueOf(ganginfo.get(0).get("isturned")));
					refreshnum=String.valueOf(ganginfo.get(0).get("refreshnum"));
					turnpaynum=String.valueOf(ganginfo.get(0).get("turnpaynum"));
					gangmilitarylv=String.valueOf(ganginfo.get(0).get("gangmilitarylv"));
					gangmilitaryexp=String.valueOf(ganginfo.get(0).get("gangmilitaryexp"));
					rewardpaytime=String.valueOf(ganginfo.get(0).get("rewardpaytime"));
					goalhurtnum=Integer.parseInt(String.valueOf(ganginfo.get(0).get("goalhurtnum")));
					goalhurttime=String.valueOf(ganginfo.get(0).get("goalhurttime"));
					actpower=Integer.parseInt(String.valueOf(ganginfo.get(0).get("actpower")));
					actpowertime=String.valueOf(ganginfo.get(0).get("actpowertime"));
					endurance=Integer.parseInt(String.valueOf(ganginfo.get(0).get("endurance")));
					battlepositionx=Integer.parseInt(String.valueOf(ganginfo.get(0).get("battlepositionx")));
					battlepositiony=Integer.parseInt(String.valueOf(ganginfo.get(0).get("battlepositiony")));
					positiontime=String.valueOf(ganginfo.get(0).get("positiontime"));

					String gangid=String.valueOf(ganginfo.get(0).get("gangid"));
					if(!gangid.equals("0")){
						List<String> onlineplayerid=Bean.getZnx_gangmap().get(gangid).getOnlineplayerid();
						synchronized (onlineplayerid) {	
							onlineplayerid.add(playerid);
						}
						znx_playerdata.setGangid(String.valueOf(ganginfo.get(0).get("gangid")));
						znx_playerdata.setGangduties(Integer.parseInt(String.valueOf(ganginfo.get(0).get("duties"))));
					}
				}


				znx_playerdata.setId(playerid);
				znx_playerdata.setNickname(nickname);
				znx_playerdata.setPhonenumber(phonenumber);
				znx_playerdata.setUid(uid);
				znx_playerdata.setSex(sex);
				znx_playerdata.setSdkid(sdk);
				znx_playerdata.setCsstar(Integer.parseInt(csstar));
				znx_playerdata.lvlupPlayerlv(Integer.parseInt(playerlv));
				znx_playerdata.setVit(Integer.parseInt(vit));
				znx_playerdata.setEnergy(Integer.parseInt(energy));
				try {
					znx_playerdata.setVittime(Bean.getDateFormat().parse(vittime));
					znx_playerdata.setEnergytime(Bean.getDateFormat().parse(energytime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				znx_playerdata.setExppool(Long.parseLong(String.valueOf(result.get(0).get("exppool"))));
				znx_playerdata.setViplv(Integer.parseInt(viplv));
				znx_playerdata.setVipreceive(vipreceive);
				znx_playerdata.setFirstreceive(firstreceive);
				znx_playerdata.setPlayerexp(Long.parseLong(playerexp));
				znx_playerdata.setRewardvittime(rewardvittime);
				znx_playerdata.addBuyvitnum(Integer.parseInt(buyvitnum));
				znx_playerdata.addBuyenergynum(Integer.parseInt(buyenergynum));
				znx_playerdata.addBuygoldnum(Integer.parseInt(buygoldnum));
				znx_playerdata.UpUsedfightersp(Integer.parseInt(String.valueOf(result.get(0).get("usedfightersp"))));
				znx_playerdata.addRmbstone(Double.parseDouble(rmbstone));
				znx_playerdata.setPromotion(promotion);
				znx_playerdata.setPromotetime(promotetime);
				znx_playerdata.setGangturnid(turnid);
				znx_playerdata.setGangisturned(isturned);
				znx_playerdata.setGangturnrefreshnum(Integer.parseInt(refreshnum));
				znx_playerdata.setGangturnpaynum(Integer.parseInt(turnpaynum));
				znx_playerdata.setGangmilitarylv(Integer.parseInt(gangmilitarylv));
				znx_playerdata.setGangmilitaryexp(Integer.parseInt(gangmilitaryexp));
				znx_playerdata.setRewardpaytime(rewardpaytime);
				znx_playerdata.setUpdatetime(updatetime);
				znx_playerdata.setGoalhurtnum(goalhurtnum);
				znx_playerdata.setGoalhurttime(goalhurttime);
				znx_playerdata.setActpower(actpower);

				try {
					if(actpowertime!=null)
						znx_playerdata.setActpowertime(Bean.getDateFormat().parse(actpowertime));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				znx_playerdata.setEndurance(endurance);
				znx_playerdata.setBattlepositionx(battlepositionx);
				znx_playerdata.setBattlepositiony(battlepositiony);
				znx_playerdata.setPositiontime(positiontime);
				if(Bean.getHolidayinfomap().get("1").getStartdate().equals(holidaygoldtime)){
					znx_playerdata.setHolidaygold(Integer.parseInt(holidaygold));
				}else{
					znx_playerdata.setHolidaygold(0);
				}
				znx_playerdata.setExchangeholidaygold(exchangeholidaygold);
				znx_playerdata.setExchangeholidaygoldtime(exchangeholidaygoldtime);
				znx_playerdata.setSevendayreceive(String.valueOf(result.get(0).get("sevendayreceive")));
				znx_playerdata.setSevendaylasttime(String.valueOf(result.get(0).get("sevendaylasttime")));
				znx_playerdata.setMilitaryrankdan(Integer.parseInt(String.valueOf(result.get(0).get("militaryrankdan"))));
				znx_playerdata.setMilitaryrank(Integer.parseInt(String.valueOf(result.get(0).get("militaryrank"))));
				znx_playerdata.setMaxrank(Integer.parseInt(String.valueOf(result.get(0).get("maxrank"))));
				znx_playerdata.setChallengenum(Integer.parseInt(String.valueOf(result.get(0).get("challengenum"))));
				znx_playerdata.setBuychallengenum(Integer.parseInt(String.valueOf(result.get(0).get("buychallengenum"))));
				znx_playerdata.setGangcontributenum(Integer.parseInt(gangcontributenum));
				znx_playerdata.setGangcontributenumtime(gangcontributenumtime);

				Bean.getZnx_playermap().put(playerid, znx_playerdata);
				FinishTaskHelper.finishEverydayTask(playerid, "1", "1");
				FinishTaskHelper.finishHolidayTask(playerid, "1");
				response.put("res","true");
				response.put("data",result);
				response.put("ganginfo", ganginfo);
			}
			else
			{
				response.put("res","false");
				response.put("data",playerid);
			}
		}
		else
		{
			response.put("res","nicknamereg");
		}
		return response;
	}

}
