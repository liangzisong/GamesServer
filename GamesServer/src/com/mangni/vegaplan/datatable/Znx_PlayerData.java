package com.mangni.vegaplan.datatable;

import java.util.Calendar;
import java.util.Date;

public class Znx_PlayerData {
	private String id,phonenumber,uid,sdkid,promotion,nickname,vipreceive,firstreceive,sex,rewardvittime,promotetime,gangid,gangturnid,rewardpaytime,
	updatetime,exchangeholidaygold,exchangeholidaygoldtime,goalhurttime,sevendayreceive,sevendaylasttime,positiontime,gangcontributenumtime;
	public String getGangcontributenumtime() {
		return gangcontributenumtime;
	}
	public void setGangcontributenumtime(String gangcontributenumtime) {
		this.gangcontributenumtime = gangcontributenumtime;
	}
	public int getGangcontributenum() {
		return gangcontributenum;
	}
	public void setGangcontributenum(int gangcontributenum) {
		this.gangcontributenum = gangcontributenum;
	}
	private int csstar=0,csminstar=0,csmaxstar=0,playerlv=0,viplv=0,gangduties=0,gangturnrefreshnum=0,gangturnpaynum=0,gangmilitarylv=1,gangmilitaryexp=0,gangisturned=0,holidaygold,
	goalhurtnum=0,militaryrankdan=0,militaryrank=0,maxrank=0,maxrankdan=0,challengenum=0,buychallengenum=0,actpower=0,endurance=0,gangcontributenum=0;
	private long playerexp=0,exppool=0;;
	private int vit=0,energy=0,usedfightersp=0,buyvitnum=0,buyenergy=0,buygoldnum=0,battlepositionx=0,battlepositiony=0;
	private double rmbstone=0;
	private boolean canhurtgoal=false;
	private Date vittime,energytime,actpowertime;
	
	
	public int getActpower() {
		return actpower;
	}
	public int[] getRealActpower() {
		int maxactpower=100+viplv*5;
		if(actpower>maxactpower)
			return new int[]{actpower,-1};
		
		Calendar now=Calendar.getInstance();
		int diffactpower=(int)((now.getTimeInMillis()-actpowertime.getTime())/1000/60/6);
		actpower=actpower+diffactpower;
		if(actpower>maxactpower){
			diffactpower=-1;
			actpower=maxactpower;
		}
		int[] actpowerp={actpower,diffactpower};
		return actpowerp;
	}
	public void setActpower(int actpower) {
		this.actpower = actpower;
	}
	public int getEndurance() {
		return endurance;
	}
	
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	public Date getActpowertime() {
		return actpowertime;
	}
	public void setActpowertime(Date actpowertime) {
		this.actpowertime = actpowertime;
	}
	public int getVit() {
		return vit;
	}
	public int[] getRealVit() {
		int maxvit=60+viplv*5;
		if(vit>=maxvit)
			return new int[]{vit,-1};
		
		Calendar now=Calendar.getInstance();
		int diffvit=(int)((now.getTimeInMillis()-vittime.getTime())/1000/60/6);
		vit=vit+diffvit;
		if(vit>maxvit){
			diffvit=-1;
			vit=maxvit;
		}
		
		int[] vitp={vit,diffvit};
		return vitp;
	}
	public void setVit(int vit) {
		this.vit = vit;
	}
	public int getEnergy() {
		return energy;
	}
	public int[] getRealEnergy() {
		int maxenergy=40+viplv*5;
		if(energy>maxenergy)
			return new int[]{energy,-1};
		Calendar now=Calendar.getInstance();
		int diffenergy=(int)((now.getTimeInMillis()-energytime.getTime())/1000/60/6);
		energy=energy+diffenergy;
		if(energy>maxenergy){
			diffenergy=-1;
			energy=maxenergy;	
		}
		
		int[] energyp={energy,diffenergy};
		return energyp;
	}
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	public Date getVittime() {
		return vittime;
	}
	public void setVittime(Date vittime) {
		this.vittime = vittime;
	}
	public Date getEnergytime() {
		return energytime;
	}
	public void setEnergytime(Date energytime) {
		this.energytime = energytime;
	}
	public int getMilitaryrank() {
		return militaryrank;
	}
	public void setMilitaryrank(int militaryrank) {
		this.militaryrank = militaryrank;
	}
	public int getMaxrank() {
		return maxrank;
	}
	public void setMaxrank(int maxrank) {
		this.maxrank = maxrank;
	}
	public int getMaxrankdan() {
		return maxrankdan;
	}
	public void setMaxrankdan(int maxrankdan) {
		this.maxrankdan = maxrankdan;
	}
	
	public int getChallengenum() {
		return challengenum;
	}
	public void setChallengenum(int challengenum) {
		this.challengenum = challengenum;
	}
	public int getBuychallengenum() {
		return buychallengenum;
	}
	public void setBuychallengenum(int buychallengenum) {
		this.buychallengenum = buychallengenum;
	}
	public int getBuyenergy() {
		return buyenergy;
	}
	public void setBuyenergy(int buyenergy) {
		this.buyenergy = buyenergy;
	}
	public void setPlayerlv(int playerlv) {
		this.playerlv = playerlv;
	}
	public void setRmbstone(double rmbstone) {
		this.rmbstone = rmbstone;
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname=nickname;
	}
	
	public String getVipreceive()
	{
		return vipreceive;
	}
	public void setVipreceive(String vipreceive)
	{
		this.vipreceive=vipreceive;
	}
	
	public int getPlayerlv()
	{
		return playerlv;
	}
	public void lvlupPlayerlv(int playerlv)
	{
		this.playerlv=this.playerlv+playerlv;
	}
	public long getPlayerexp()
	{
		return playerexp;
	}
	public void setPlayerexp(long playerexp)
	{
		this.playerexp=playerexp;
	}
	
	public int getViplv()
	{
		return viplv;
	}
	public void setViplv(int viplv)
	{
		this.viplv=viplv;
	}
	
	public int getUsedfightersp()
	{
		return usedfightersp;
	}
	public void setUsedfightersp(int usedfightersp){
		this.usedfightersp=usedfightersp;
	}
	public void UpUsedfightersp(int usedfightersp)
	{
		this.usedfightersp+=usedfightersp;
	}
	
	public int getBuyvitnum()
	{
		return buyvitnum;
	}
	public void setBuyvitnum(int buyvitnum){
		this.buyvitnum=buyvitnum;
	}
	public void addBuyvitnum(int buyvitnum)
	{
		this.buyvitnum+=buyvitnum;
	}
	
	public int getBuyenergynum()
	{
		return buyenergy;
	}
	public void setBuyenergynum(int buyenergy){
		this.buyenergy=buyenergy;
	}
	public void addBuyenergynum(int buyenergy)
	{
		this.buyenergy+=buyenergy;
	}
	
	public int getBuygoldnum()
	{
		return buygoldnum;
	}
	public void setBuygoldnum(int buygoldnum){
		this.buygoldnum=buygoldnum;
	}
	public void addBuygoldnum(int buygoldnum)
	{
		this.buygoldnum+=buygoldnum;
	}
	
	public double getRmbstone()
	{
		return rmbstone;
	}
	public void addRmbstone(double rmbstone)
	{
		this.rmbstone+=rmbstone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getFirstreceive() {
		return firstreceive;
	}
	public void setFirstreceive(String firstreceive) {
		this.firstreceive = firstreceive;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	public String getPromotetime() {
		return promotetime;
	}
	public void setPromotetime(String promotetime) {
		this.promotetime = promotetime;
	}
	public String getGangid() {
		return gangid;
	}
	public void setGangid(String gangid) {
		this.gangid = gangid;
	}
	public String getGangturnid() {
		return gangturnid;
	}
	public void setGangturnid(String gangturnid) {
		this.gangturnid = gangturnid;
	}
	public int getGangturnrefreshnum() {
		return gangturnrefreshnum;
	}
	public void setGangturnrefreshnum(int gangturnrefreshnum) {
		this.gangturnrefreshnum = gangturnrefreshnum;
	}
	public int getGangturnpaynum() {
		return gangturnpaynum;
	}
	public void setGangturnpaynum(int gangturnpaynum) {
		this.gangturnpaynum = gangturnpaynum;
	}
	public String getRewardvittime() {
		return rewardvittime;
	}
	public void setRewardvittime(String rewardvittime) {
		this.rewardvittime = rewardvittime;
	}
	public int getGangmilitarylv() {
		return gangmilitarylv;
	}
	public void setGangmilitarylv(int gangmilitarylv) {
		this.gangmilitarylv = gangmilitarylv;
	}
	public int getGangmilitaryexp() {
		return gangmilitaryexp;
	}
	public void setGangmilitaryexp(int gangmilitaryexp) {
		this.gangmilitaryexp = gangmilitaryexp;
	}
	public String getRewardpaytime() {
		return rewardpaytime;
	}
	public void setRewardpaytime(String rewardpaytime) {
		this.rewardpaytime = rewardpaytime;
	}
	public void initgang(){
		this.gangid=null;
		this.gangduties=0;
		this.gangmilitaryexp=0;
		this.gangmilitarylv=1;
		this.gangturnid="0";
		this.gangisturned=0;
		this.gangturnpaynum=0;
		this.gangturnrefreshnum=0;
		this.rewardpaytime=null;
	}
	public int getGangisturned() {
		return gangisturned;
	}
	public void setGangisturned(int gangisturned) {
		this.gangisturned = gangisturned;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getHolidaygold() {
		return holidaygold;
	}
	public void setHolidaygold(int holidaygold) {
		this.holidaygold = holidaygold;
	}
	public String getExchangeholidaygold() {
		return exchangeholidaygold;
	}
	public void setExchangeholidaygold(String exchangeholidaygold) {
		this.exchangeholidaygold = exchangeholidaygold;
	}
	public String getExchangeholidaygoldtime() {
		return exchangeholidaygoldtime;
	}
	public void setExchangeholidaygoldtime(String exchangeholidaygoldtime) {
		this.exchangeholidaygoldtime = exchangeholidaygoldtime;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public int getGangduties() {
		return gangduties;
	}
	public void setGangduties(int gangduties) {
		this.gangduties = gangduties;
	}
	public String getGoalhurttime() {
		return goalhurttime;
	}
	public void setGoalhurttime(String goalhurttime) {
		this.goalhurttime = goalhurttime;
	}
	public int getGoalhurtnum() {
		return goalhurtnum;
	}
	public void setGoalhurtnum(int goalhurtnum) {
		this.goalhurtnum = goalhurtnum;
	}
	public boolean getCanhurtgoal() {
		return canhurtgoal;
	}
	public void setCanhurtgoal(boolean canhurtgoal) {
		this.canhurtgoal = canhurtgoal;
	}
	public String getSevendayreceive() {
		return sevendayreceive;
	}
	public void setSevendayreceive(String sevendayreceive) {
		this.sevendayreceive = sevendayreceive;
	}
	public String getSevendaylasttime() {
		return sevendaylasttime;
	}
	public void setSevendaylasttime(String sevendaylasttime) {
		this.sevendaylasttime = sevendaylasttime;
	}
	public String getSdkid() {
		return sdkid;
	}
	public void setSdkid(String sdkid) {
		this.sdkid = sdkid;
	}
	public int getCsstar() {
		return csstar;
	}
	public void setCsstar(int csstar) {
		this.csstar = csstar;
	}
	public int getCsminstar() {
		return csminstar;
	}
	public void setCsminstar(int csminstar) {
		this.csminstar = csminstar;
	}
	public int getCsmaxstar() {
		return csmaxstar;
	}
	public void setCsmaxstar(int csmaxstar) {
		this.csmaxstar = csmaxstar;
	}
	public long getExppool() {
		return exppool;
	}
	public void setExppool(long exppool) {
		this.exppool = exppool;
	}
	public int getMilitaryrankdan() {
		return militaryrankdan;
	}
	public void setMilitaryrankdan(int militaryrankdan) {
		this.militaryrankdan = militaryrankdan;
	}
	public int getBattlepositionx() {
		return battlepositionx;
	}
	public void setBattlepositionx(int battlepositionx) {
		this.battlepositionx = battlepositionx;
	}
	public int getBattlepositiony() {
		return battlepositiony;
	}
	public void setBattlepositiony(int battlepositiony) {
		this.battlepositiony = battlepositiony;
	}
	public String getPositiontime() {
		return positiontime;
	}
	public void setPositiontime(String positiontime) {
		this.positiontime = positiontime;
	}
}