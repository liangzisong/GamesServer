package com.mangni.vegaplan.toolshelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.websocket.api.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.mangni.vegaplan.datatable.AddbuyData;
import com.mangni.vegaplan.datatable.AddrmbData;
import com.mangni.vegaplan.datatable.BarrierData;
import com.mangni.vegaplan.datatable.BarrierRewardData;
import com.mangni.vegaplan.datatable.BuyStoneData;
import com.mangni.vegaplan.datatable.ChampionsData;
import com.mangni.vegaplan.datatable.ChipData;
import com.mangni.vegaplan.datatable.CompetitionSeasonRoom;
import com.mangni.vegaplan.datatable.DailytaskData;
import com.mangni.vegaplan.datatable.DigData;
import com.mangni.vegaplan.datatable.EquipData;
import com.mangni.vegaplan.datatable.FighterData;
import com.mangni.vegaplan.datatable.FighterHashMap;
import com.mangni.vegaplan.datatable.GangShopData;
import com.mangni.vegaplan.datatable.GangbossData;
import com.mangni.vegaplan.datatable.GangbossawardData;
import com.mangni.vegaplan.datatable.GanglvupData;
import com.mangni.vegaplan.datatable.GangpostData;
import com.mangni.vegaplan.datatable.TrialData;
import com.mangni.vegaplan.datatable.GiftData;
import com.mangni.vegaplan.datatable.GunData;
import com.mangni.vegaplan.datatable.HolidayData;
import com.mangni.vegaplan.datatable.HolidayinfoData;
import com.mangni.vegaplan.datatable.ItemData;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.datatable.JjfyData;
import com.mangni.vegaplan.datatable.LottoChip;
import com.mangni.vegaplan.datatable.LottoGem;
import com.mangni.vegaplan.datatable.LottoMech;
import com.mangni.vegaplan.datatable.LvtableData;
import com.mangni.vegaplan.datatable.MaterialproData;
import com.mangni.vegaplan.datatable.MaxBuyData;
import com.mangni.vegaplan.datatable.MercenarytaskData;
import com.mangni.vegaplan.datatable.PaycardData;
import com.mangni.vegaplan.datatable.PcbData;
import com.mangni.vegaplan.datatable.RECShopData;
import com.mangni.vegaplan.datatable.RankData;
import com.mangni.vegaplan.datatable.RankShopData;
import com.mangni.vegaplan.datatable.ReceiveData;
import com.mangni.vegaplan.datatable.SevendayData;
import com.mangni.vegaplan.datatable.SignData;
import com.mangni.vegaplan.datatable.SkilllvData;
import com.mangni.vegaplan.datatable.SkinData;
import com.mangni.vegaplan.datatable.StoreData;
import com.mangni.vegaplan.datatable.TacticsData;
import com.mangni.vegaplan.datatable.TurnData;
import com.mangni.vegaplan.datatable.TurnlvData;
import com.mangni.vegaplan.datatable.TurnnumData;
import com.mangni.vegaplan.datatable.VipData;
import com.mangni.vegaplan.datatable.VipPackageData;
import com.mangni.vegaplan.datatable.VipRewardData;
import com.mangni.vegaplan.datatable.ViplvData;
import com.mangni.vegaplan.datatable.WheelData;
import com.mangni.vegaplan.datatable.WizardData;
import com.mangni.vegaplan.datatable.XxddData;
import com.mangni.vegaplan.datatable.Znx_FighterData;
import com.mangni.vegaplan.datatable.Znx_GangData;
import com.mangni.vegaplan.datatable.Znx_GanggoalData;
import com.mangni.vegaplan.datatable.Znx_PlayerData;

public class Bean {
	private static String logfilepath;
	private static Server server;
	private static JTextArea textArea;
	private static JLabel allplayersum;
	private static ConcurrentHashMap<String,Znx_PlayerData> competitionseasonmap;
	private static ConcurrentHashMap<String,CompetitionSeasonRoom> competitionseasonroommap;
	private static HashMap<String,Thread> csthreadmap;//赛季匹配线程
	private static HashMap<String,Thread> gangbattlethreadmap;//帮战线程
	private static List<String> rafflechipone;
	private static List<String> rafflejewelone;
	private static List<String> rafflefighterone;
	private static List<String> rafflechipten;
	private static List<String> rafflejewelten;
	private static List<String> rafflefighterten;
	private static LinkedHashMap<String,List<Integer>> playercount;
	private static ConcurrentHashMap<String,Session> playersession;
	private static HashMap<String,Znx_PlayerData> znx_playermap;
	private static FighterHashMap<String,Znx_FighterData> znx_fightermap;
	private static ConcurrentHashMap<String,Znx_GangData> znx_gangmap;
	private static HashMap<String,ChampionsData> championsmap;
	private static HashMap<String,FighterData> fightermap;
	private static HashMap<String,JewelData> jewelmap;
	private static HashMap<String,PcbData> pcbmap;
	private static HashMap<String,ChipData> chipmap;
	private static HashMap<String,BarrierData> barriermap;
	private static HashMap<String,RankShopData> rankshopmap;
	private static HashMap<String,StoreData> storeshopmap;
	private static HashMap<String,LottoChip> lottochipmap;
	private static HashMap<String,LottoGem> lottogemmap;
	private static HashMap<String,LottoMech> lottomechmap;
	private static HashMap<String,PaycardData> paycardmap;
	private static HashMap<String,ViplvData> viplvmap;
	private static HashMap<String,GiftData> daygiftmap;
	private static HashMap<String,GiftData> breakgiftmap;
	private static HashMap<String,GiftData> challengegiftmap;
	private static HashMap<String,GiftData> fightgiftmap;
	private static HashMap<String,GiftData> paygiftmap;
	private static HashMap<String,GiftData> costgiftmap;
	private static HashMap<String,GiftData> stonegiftmap;
	private static HashMap<String,GiftData> sumpaygiftmap;
	private static HashMap<String,GiftData> weeksignmap;
	private static HashMap<String,GiftData> monthsignmap;
	private static HashMap<String,GiftData> investmap;
	private static HashMap<String,GiftData> growmap;
	private static HashMap<String,WheelData> wheelmap;
	private static HashMap<String,RankData> rankmap;
	private static HashMap<String,VipData> vipmap;
	private static HashMap<String,VipRewardData> viprewardmap;
	private static HashMap<String,MaxBuyData> maxbuymap;
	private static HashMap<String,BuyStoneData> buystonemap;
	private static HashMap<String,RECShopData> recshopmap;
	private static HashMap<String,SignData> signweekmap;
	private static HashMap<String,SignData> signmonthmap;
	private static HashMap<String,DailytaskData> dailytaskmap;
	private static HashMap<String,VipPackageData> vippackagemap;
	private static HashMap<String,XxddData> xxddmap;
	private static HashMap<String,JjfyData> jjfymap;
	private static HashMap<String,AddbuyData> addbuymap;
	private static HashMap<String,AddrmbData> addrmbmap;
	private static HashMap<String,GunData> gunmap;
	private static HashMap<String,TurnData> turnmap;
	private static HashMap<String,TurnnumData> turnnummap;
	private static HashMap<String,TurnlvData> turnlvmap;
	private static HashMap<String,GanglvupData> ganglvupmap;
	private static HashMap<String,GangpostData> gangpostmap;
	private static HashMap<String,MercenarytaskData> mercenarytaskmap;
	private static HashMap<String,TrialData> trialmap;
	private static HashMap<String,GangShopData> gangshopmap;
	private static HashMap<String,HolidayData> holidaymap;
	private static HashMap<String,HolidayinfoData> holidayinfomap;
	private static HashMap<String,DigData> digmap;
	private static HashMap<String,WizardData> wizardmap;
	private static HashMap<String,ReceiveData> receivemap;
	private static HashMap<String,GangbossData> gangbossmap;
	private static HashMap<String,Znx_GanggoalData> znx_ganggoalmap;
	private static HashMap<String,GangbossawardData> Gangbossawardmap;
	private static HashMap<String,SkinData> skinmap;
	private static HashMap<String,SevendayData> sevendaymap;
	private static HashMap<String,LvtableData> lvtablemap;
	private static HashMap<String,TacticsData> tacticsmap;
	private static HashMap<String,EquipData> equipmap;
	private static HashMap<String,SkilllvData> skilllvmap;
	private static HashMap<String,ItemData> itemmap;
	private static HashMap<String,MaterialproData> materialpromap;
	private static HashMap<String,BarrierRewardData> barrierrewardmap;
	
	private static int turnsum=0;
	private static String ip,databaseName,user,passwd,key,biguserserver,serverid,servername,lsdkkey;
	private static int port=0,httpouttime=0;
	private static SimpleDateFormat dateFormat;
	private static ApplicationContext ctx;
	private static ApplicationContext ctxgm;
	private static ExecutorService executor;
	public Bean() throws FileNotFoundException //throws FileNotFoundException
	{
		QueuedThreadPool threadpool=new QueuedThreadPool();
		threadpool.setMinThreads(30);
		threadpool.setMaxThreads(3000);
		Bean.setServer(new Server(threadpool));
		setExecutor(Executors.newCachedThreadPool());

		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		setTextArea(new JTextArea());
		setAllplayersum(new JLabel("在线人数：0"));
		setPlayercount(new LinkedHashMap<String,List<Integer>>());
		setPlayersession(new ConcurrentHashMap<String,Session>());
		setCompetitionseasonmap(new ConcurrentHashMap<String,Znx_PlayerData>());
		setCompetitionseasonroommap(new ConcurrentHashMap<String,CompetitionSeasonRoom>());
		setCsthreadmap(new HashMap<String,Thread>());
		setGangbattlethreadmap(new HashMap<String,Thread>());

		setRafflechipone(new ArrayList<String>(1000));
		setRafflejewelone(new ArrayList<String>(1000));
		setRafflefighterone(new ArrayList<String>(1000));
		setRafflechipten(new ArrayList<String>(1000));
		setRafflejewelten(new ArrayList<String>(1000));
		setRafflefighterten(new ArrayList<String>(1000));

		setZnx_playermap(new HashMap<String,Znx_PlayerData>());
		setZnx_fightermap(new FighterHashMap<String,Znx_FighterData>());
		setZnx_gangmap(new ConcurrentHashMap<String,Znx_GangData>());
		setChampionsmap(new HashMap<String,ChampionsData>());
		setFightermap(new HashMap<String,FighterData>());
		setJewelmap(new HashMap<String,JewelData>());
		setPcbmap(new HashMap<String,PcbData>());
		setChipmap(new HashMap<String,ChipData>());
		setBarriermap(new HashMap<String,BarrierData>());
		setRankshopmap(new HashMap<String,RankShopData>());
		setStoreshopmap(new HashMap<String,StoreData>());
		setPaycardmap(new HashMap<String,PaycardData>());
		setViplvmap(new HashMap<String,ViplvData>());
		setDaygiftmap(new HashMap<String,GiftData>());
		setBreakgiftmap(new HashMap<String,GiftData>());
		setChallengegiftmap(new HashMap<String,GiftData>());
		setFightgiftmap(new HashMap<String,GiftData>());
		setPaygiftmap(new HashMap<String,GiftData>());
		setCostgiftmap(new HashMap<String,GiftData>());
		setStonegiftmap(new HashMap<String,GiftData>());
		setSumpaygiftmap(new HashMap<String,GiftData>());
		setWeeksignmap(new HashMap<String,GiftData>());
		setMonthsignmap(new HashMap<String,GiftData>());
		setInvestmap(new HashMap<String,GiftData>());
		setGrowmap(new HashMap<String,GiftData>());
		setWheelmap(new HashMap<String,WheelData>());
		setRankmap(new HashMap<String,RankData>());
		setLottochipmap(new HashMap<String,LottoChip>());
		setLottogemmap(new HashMap<String,LottoGem>());
		setLottomechmap(new HashMap<String,LottoMech>());
		setVipmap(new HashMap<String,VipData>());
		setViprewardmap(new HashMap<String,VipRewardData>());
		setMaxbuymap(new HashMap<String,MaxBuyData>());
		setBuystonemap(new HashMap<String,BuyStoneData>());
		setRecshopmap(new HashMap<String,RECShopData>());
		setSignweekmap(new HashMap<String,SignData>());
		setSignmonthmap(new HashMap<String,SignData>());
		setDailytaskmap(new HashMap<String,DailytaskData>());
		setVippackagemap(new HashMap<String,VipPackageData>());
		setXxddmap(new HashMap<String,XxddData>());
		setJjfymap(new HashMap<String,JjfyData>());
		setAddbuymap(new HashMap<String,AddbuyData>());
		setAddrmbmap(new HashMap<String,AddrmbData>());
		setGunmap(new HashMap<String,GunData>());
		setTurnmap(new HashMap<String,TurnData>());
		setTurnnummap(new HashMap<String,TurnnumData>());
		setTurnlvmap(new HashMap<String,TurnlvData>());
		setGanglvupmap(new HashMap<String,GanglvupData>());
		setGangpostmap(new HashMap<String,GangpostData>());
		setMercenarytaskmap(new HashMap<String,MercenarytaskData>());
		settrialmap(new HashMap<String,TrialData>());
		setGangshopmap(new HashMap<String,GangShopData>());
		setHolidaymap(new HashMap<String,HolidayData>());
		setHolidayinfomap(new HashMap<String,HolidayinfoData>());
		setDigmap(new HashMap<String,DigData>());
		setWizardmap(new HashMap<String,WizardData>());
		setReceivemap(new HashMap<String,ReceiveData>());
		setGangbossmap(new HashMap<String,GangbossData>());
		setZnx_ganggoalmap(new HashMap<String,Znx_GanggoalData>());
		setGangbossawardmap(new HashMap<String,GangbossawardData>());
		setSkinmap(new HashMap<String,SkinData>());
		setSevendaymap(new HashMap<String,SevendayData>());
		setLvtablemap(new HashMap<String,LvtableData>());
		setTacticsmap(new HashMap<String,TacticsData>());
		setEquipmap(new HashMap<String,EquipData>());
		setSkilllvmap(new HashMap<String,SkilllvData>());
		setItemmap(new HashMap<String,ItemData>());
		setMaterialpromap(new HashMap<String,MaterialproData>());
		setBarrierrewardmap(new HashMap<String,BarrierRewardData>());
		//new XmlHelper();

		//new LottoHelper();
		//new EcoHelper();
		String curDir=System.getProperty("user.dir");

		File file = new File(curDir+"\\config\\db.properties");
		FileInputStream in=new FileInputStream(file);
		PropertyConfigurator.configure(curDir+"\\config\\log4j.properties");
		Properties prop=new Properties();
		try
		{
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in,"UTF-8")); 
			prop.load(buffer);
			in.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		Bean.setCtx(new FileSystemXmlApplicationContext("file:"+curDir+"\\config\\beans.xml"));//读取bean.xml中的内容
		Bean.setCtxgm(new FileSystemXmlApplicationContext("file:"+curDir+"\\config\\gmbeans.xml"));
		Calendar c=Calendar.getInstance();
		setLogfilepath(curDir+"\\log\\"+c.get(Calendar.YEAR)+(c.get(Calendar.MONTH)+1)+c.get(Calendar.DAY_OF_MONTH)+"log.txt");
		
		setPort(Integer.parseInt(prop.getProperty("port")));

		setIp(prop.getProperty("ip"));
		setDatabaseName(prop.getProperty("databaseName"));
		setUser(prop.getProperty("user"));
		setPasswd(prop.getProperty("passwd"));
		setKey(prop.getProperty("aeskey"));	
		setHttpouttime(Integer.parseInt(prop.getProperty("httpouttime")));
		setBiguserserver(prop.getProperty("biguserserver"));
		setServerid(prop.getProperty("serverid"));
		setServername(prop.getProperty("servername"));
		setLsdkkey(prop.getProperty("lsdkkey"));
	}
	public static int getPort()
	{
		return port;
	}
	public static String getKey()
	{
		return key;
	}
	public static String getIp()
	{
		return ip;
	}
	public static String getDatabaseName()
	{
		return databaseName;
	}
	public static String getUser()
	{
		return user;
	}
	public static String getPasswd()
	{
		return passwd;
	}
	public static int getHttpouttime()
	{
		return httpouttime;
	}
	public static String getBiguserserver()
	{
		return biguserserver;
	}

	public static Server getServer() {
		return server;
	}
	public static void setServer(Server server) {
		Bean.server = server;
	}
	public static JTextArea getTextArea() {
		return textArea;
	}
	public static void setTextArea(JTextArea textArea) {
		Bean.textArea = textArea;
	}

	public static JLabel getAllplayersum() {
		return allplayersum;
	}
	public static void setAllplayersum(JLabel allplayersum) {
		Bean.allplayersum = allplayersum;
	}

	public static List<String> getRafflechipone() {
		return rafflechipone;
	}
	public static void setRafflechipone(List<String> rafflechipone) {
		Bean.rafflechipone = rafflechipone;
	}
	public static List<String> getRafflejewelone() {
		return rafflejewelone;
	}
	public static void setRafflejewelone(List<String> rafflejewelone) {
		Bean.rafflejewelone = rafflejewelone;
	}
	public static List<String> getRafflefighterone() {
		return rafflefighterone;
	}
	public static void setRafflefighterone(List<String> rafflefighterone) {
		Bean.rafflefighterone = rafflefighterone;
	}
	public static List<String> getRafflechipten() {
		return rafflechipten;
	}
	public static void setRafflechipten(List<String> rafflechipten) {
		Bean.rafflechipten = rafflechipten;
	}
	public static List<String> getRafflejewelten() {
		return rafflejewelten;
	}
	public static void setRafflejewelten(List<String> rafflejewelten) {
		Bean.rafflejewelten = rafflejewelten;
	}
	public static List<String> getRafflefighterten() {
		return rafflefighterten;
	}
	public static void setRafflefighterten(List<String> rafflefighterten) {
		Bean.rafflefighterten = rafflefighterten;
	}
	public static LinkedHashMap<String,List<Integer>> getPlayercount() {
		return playercount;
	}
	public static void setPlayercount(LinkedHashMap<String,List<Integer>> playercount) {
		Bean.playercount = playercount;
	}
	public static ConcurrentHashMap<String,Session> getPlayersession() {
		return playersession;
	}
	public static void setPlayersession(ConcurrentHashMap<String,Session> playersession) {
		Bean.playersession = playersession;
	}
	public static HashMap<String,Znx_PlayerData> getZnx_playermap() {
		return znx_playermap;
	}
	public static void setZnx_playermap(HashMap<String,Znx_PlayerData> znx_playermap) {
		Bean.znx_playermap = znx_playermap;
	}
	public static FighterHashMap<String,Znx_FighterData> getZnx_fightermap() {
		return znx_fightermap;
	}
	public static void setZnx_fightermap(FighterHashMap<String,Znx_FighterData> znx_fightermap) {
		Bean.znx_fightermap = znx_fightermap;
	}
	public static HashMap<String,ChampionsData> getChampionsmap() {
		return championsmap;
	}
	public static void setChampionsmap(HashMap<String,ChampionsData> championsmap) {
		Bean.championsmap = championsmap;
	}
	public static HashMap<String,FighterData> getFightermap() {
		return fightermap;
	}
	public static void setFightermap(HashMap<String,FighterData> fightermap) {
		Bean.fightermap = fightermap;
	}
	public static HashMap<String,JewelData> getJewelmap() {
		return jewelmap;
	}
	public static void setJewelmap(HashMap<String,JewelData> jewelmap) {
		Bean.jewelmap = jewelmap;
	}
	public static HashMap<String,PcbData> getPcbmap() {
		return pcbmap;
	}
	public static void setPcbmap(HashMap<String,PcbData> pcbmap) {
		Bean.pcbmap = pcbmap;
	}
	public static HashMap<String,ChipData> getChipmap() {
		return chipmap;
	}
	public static void setChipmap(HashMap<String,ChipData> chipmap) {
		Bean.chipmap = chipmap;
	}
	public static HashMap<String,BarrierData> getBarriermap() {
		return barriermap;
	}
	public static void setBarriermap(HashMap<String,BarrierData> barriermap) {
		Bean.barriermap = barriermap;
	}
	public static HashMap<String,RankShopData> getRankshopmap() {
		return rankshopmap;
	}
	public static void setRankshopmap(HashMap<String,RankShopData> rankshopmap) {
		Bean.rankshopmap = rankshopmap;
	}
	public static HashMap<String,StoreData> getStoreshopmap() {
		return storeshopmap;
	}
	public static void setStoreshopmap(HashMap<String,StoreData> storeshopmap) {
		Bean.storeshopmap = storeshopmap;
	}
	public static HashMap<String,LottoChip> getLottochipmap() {
		return lottochipmap;
	}
	public static void setLottochipmap(HashMap<String,LottoChip> lottochipmap) {
		Bean.lottochipmap = lottochipmap;
	}
	public static HashMap<String,LottoGem> getLottogemmap() {
		return lottogemmap;
	}
	public static void setLottogemmap(HashMap<String,LottoGem> lottogemmap) {
		Bean.lottogemmap = lottogemmap;
	}
	public static HashMap<String,LottoMech> getLottomechmap() {
		return lottomechmap;
	}
	public static void setLottomechmap(HashMap<String,LottoMech> lottomechmap) {
		Bean.lottomechmap = lottomechmap;
	}
	public static HashMap<String,PaycardData> getPaycardmap() {
		return paycardmap;
	}
	public static void setPaycardmap(HashMap<String,PaycardData> paycardmap) {
		Bean.paycardmap = paycardmap;
	}
	public static HashMap<String,ViplvData> getViplvmap() {
		return viplvmap;
	}
	public static void setViplvmap(HashMap<String,ViplvData> viplvmap) {
		Bean.viplvmap = viplvmap;
	}
	public static HashMap<String,GiftData> getDaygiftmap() {
		return daygiftmap;
	}
	public static void setDaygiftmap(HashMap<String,GiftData> daygiftmap) {
		Bean.daygiftmap = daygiftmap;
	}
	public static HashMap<String,GiftData> getBreakgiftmap() {
		return breakgiftmap;
	}
	public static void setBreakgiftmap(HashMap<String,GiftData> breakgiftmap) {
		Bean.breakgiftmap = breakgiftmap;
	}
	public static HashMap<String,GiftData> getChallengegiftmap() {
		return challengegiftmap;
	}
	public static void setChallengegiftmap(HashMap<String,GiftData> challengegiftmap) {
		Bean.challengegiftmap = challengegiftmap;
	}
	public static HashMap<String,GiftData> getFightgiftmap() {
		return fightgiftmap;
	}
	public static void setFightgiftmap(HashMap<String,GiftData> fightgiftmap) {
		Bean.fightgiftmap = fightgiftmap;
	}
	public static HashMap<String,GiftData> getPaygiftmap() {
		return paygiftmap;
	}
	public static void setPaygiftmap(HashMap<String,GiftData> paygiftmap) {
		Bean.paygiftmap = paygiftmap;
	}
	public static HashMap<String,GiftData> getCostgiftmap() {
		return costgiftmap;
	}
	public static void setCostgiftmap(HashMap<String,GiftData> costgiftmap) {
		Bean.costgiftmap = costgiftmap;
	}
	public static HashMap<String,GiftData> getStonegiftmap() {
		return stonegiftmap;
	}
	public static void setStonegiftmap(HashMap<String,GiftData> stonegiftmap) {
		Bean.stonegiftmap = stonegiftmap;
	}
	public static HashMap<String,GiftData> getSumpaygiftmap() {
		return sumpaygiftmap;
	}
	public static void setSumpaygiftmap(HashMap<String,GiftData> sumpaygiftmap) {
		Bean.sumpaygiftmap = sumpaygiftmap;
	}
	public static HashMap<String,GiftData> getWeeksignmap() {
		return weeksignmap;
	}
	public static void setWeeksignmap(HashMap<String,GiftData> weeksignmap) {
		Bean.weeksignmap = weeksignmap;
	}
	public static HashMap<String,GiftData> getMonthsignmap() {
		return monthsignmap;
	}
	public static void setMonthsignmap(HashMap<String,GiftData> monthsignmap) {
		Bean.monthsignmap = monthsignmap;
	}
	public static HashMap<String,GiftData> getInvestmap() {
		return investmap;
	}
	public static void setInvestmap(HashMap<String,GiftData> investmap) {
		Bean.investmap = investmap;
	}
	public static HashMap<String,GiftData> getGrowmap() {
		return growmap;
	}
	public static void setGrowmap(HashMap<String,GiftData> growmap) {
		Bean.growmap = growmap;
	}
	public static HashMap<String,WheelData> getWheelmap() {
		return wheelmap;
	}
	public static void setWheelmap(HashMap<String,WheelData> wheelmap) {
		Bean.wheelmap = wheelmap;
	}
	public static HashMap<String,RankData> getRankmap() {
		return rankmap;
	}
	public static void setRankmap(HashMap<String,RankData> rankmap) {
		Bean.rankmap = rankmap;
	}
	public static HashMap<String,VipData> getVipmap() {
		return vipmap;
	}
	public static void setVipmap(HashMap<String,VipData> vipmap) {
		Bean.vipmap = vipmap;
	}
	public static HashMap<String,VipRewardData> getViprewardmap() {
		return viprewardmap;
	}
	public static void setViprewardmap(HashMap<String,VipRewardData> viprewardmap) {
		Bean.viprewardmap = viprewardmap;
	}
	public static HashMap<String,MaxBuyData> getMaxbuymap() {
		return maxbuymap;
	}
	public static void setMaxbuymap(HashMap<String,MaxBuyData> maxbuymap) {
		Bean.maxbuymap = maxbuymap;
	}
	public static HashMap<String,BuyStoneData> getBuystonemap() {
		return buystonemap;
	}
	public static void setBuystonemap(HashMap<String,BuyStoneData> buystonemap) {
		Bean.buystonemap = buystonemap;
	}
	public static HashMap<String,RECShopData> getRecshopmap() {
		return recshopmap;
	}
	public static void setRecshopmap(HashMap<String,RECShopData> recshopmap) {
		Bean.recshopmap = recshopmap;
	}
	public static HashMap<String,SignData> getSignweekmap() {
		return signweekmap;
	}
	public static void setSignweekmap(HashMap<String,SignData> signweekmap) {
		Bean.signweekmap = signweekmap;
	}
	public static HashMap<String,SignData> getSignmonthmap() {
		return signmonthmap;
	}
	public static void setSignmonthmap(HashMap<String,SignData> signmonthmap) {
		Bean.signmonthmap = signmonthmap;
	}
	public static HashMap<String,DailytaskData> getDailytaskmap() {
		return dailytaskmap;
	}
	public static void setDailytaskmap(HashMap<String,DailytaskData> dailytaskmap) {
		Bean.dailytaskmap = dailytaskmap;
	}
	public static void setIp(String ip) {
		Bean.ip = ip;
	}
	public static void setDatabaseName(String databaseName) {
		Bean.databaseName = databaseName;
	}
	public static void setUser(String user) {
		Bean.user = user;
	}
	public static void setPasswd(String passwd) {
		Bean.passwd = passwd;
	}
	public static void setKey(String key) {
		Bean.key = key;
	}
	public static void setBiguserserver(String biguserserver) {
		Bean.biguserserver = biguserserver;
	}
	public static void setPort(int port) {
		Bean.port = port;
	}
	public static void setHttpouttime(int httpouttime) {
		Bean.httpouttime = httpouttime;
	}
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	public static void setDateFormat(SimpleDateFormat dateFormat) {
		Bean.dateFormat = dateFormat;
	}
	public static HashMap<String,VipPackageData> getVippackagemap() {
		return vippackagemap;
	}
	public static void setVippackagemap(HashMap<String,VipPackageData> vippackagemap) {
		Bean.vippackagemap = vippackagemap;
	}
	public static HashMap<String,XxddData> getXxddmap() {
		return xxddmap;
	}
	public static void setXxddmap(HashMap<String,XxddData> xxddmap) {
		Bean.xxddmap = xxddmap;
	}
	public static HashMap<String,JjfyData> getJjfymap() {
		return jjfymap;
	}
	public static void setJjfymap(HashMap<String,JjfyData> jjfymap) {
		Bean.jjfymap = jjfymap;
	}
	public static HashMap<String,AddbuyData> getAddbuymap() {
		return addbuymap;
	}
	public static void setAddbuymap(HashMap<String,AddbuyData> addbuymap) {
		Bean.addbuymap = addbuymap;
	}
	public static HashMap<String,AddrmbData> getAddrmbmap() {
		return addrmbmap;
	}
	public static void setAddrmbmap(HashMap<String,AddrmbData> addrmbmap) {
		Bean.addrmbmap = addrmbmap;
	}
	public static HashMap<String,GunData> getGunmap() {
		return gunmap;
	}
	public static void setGunmap(HashMap<String,GunData> gunmap) {
		Bean.gunmap = gunmap;
	}
	public static ApplicationContext getCtx() {
		return ctx;
	}
	public static void setCtx(ApplicationContext ctx) {
		Bean.ctx = ctx;
	}
	public static String getServerid() {
		return serverid;
	}
	public static void setServerid(String serverid) {
		Bean.serverid = serverid;
	}
	public static String getServername() {
		return servername;
	}
	public static void setServername(String servername) {
		Bean.servername = servername;
	}
	public static HashMap<String,TurnData> getTurnmap() {
		return turnmap;
	}
	public static void setTurnmap(HashMap<String,TurnData> turnmap) {
		Bean.turnmap = turnmap;
	}
	public static int getTurnsum() {
		return turnsum;
	}
	public static void setTurnsum(int turnsum) {
		Bean.turnsum = turnsum;
	}
	public static HashMap<String,TurnnumData> getTurnnummap() {
		return turnnummap;
	}
	public static void setTurnnummap(HashMap<String,TurnnumData> turnnummap) {
		Bean.turnnummap = turnnummap;
	}
	public static HashMap<String,MercenarytaskData> getMercenarytaskmap() {
		return mercenarytaskmap;
	}
	public static void setMercenarytaskmap(HashMap<String,MercenarytaskData> mercenarytaskmap) {
		Bean.mercenarytaskmap = mercenarytaskmap;
	}
	public static ConcurrentHashMap<String,Znx_GangData> getZnx_gangmap() {
		return znx_gangmap;
	}
	public static void setZnx_gangmap(ConcurrentHashMap<String,Znx_GangData> znx_gangmap) {
		Bean.znx_gangmap = znx_gangmap;
	}
	public static HashMap<String,TurnlvData> getTurnlvmap() {
		return turnlvmap;
	}
	public static void setTurnlvmap(HashMap<String,TurnlvData> turnlvmap) {
		Bean.turnlvmap = turnlvmap;
	}
	public static HashMap<String,GangpostData> getGangpostmap() {
		return gangpostmap;
	}
	public static void setGangpostmap(HashMap<String,GangpostData> gangpostmap) {
		Bean.gangpostmap = gangpostmap;
	}
	public static HashMap<String,GanglvupData> getGanglvupmap() {
		return ganglvupmap;
	}
	public static void setGanglvupmap(HashMap<String,GanglvupData> ganglvupmap) {
		Bean.ganglvupmap = ganglvupmap;
	}
	public static HashMap<String,TrialData> gettrialmap() {
		return trialmap;
	}
	public static void settrialmap(HashMap<String,TrialData> gettrailmap) {
		Bean.trialmap = gettrailmap;
	}
	public static HashMap<String,GangShopData> getGangshopmap() {
		return gangshopmap;
	}
	public static void setGangshopmap(HashMap<String,GangShopData> gangshopmap) {
		Bean.gangshopmap = gangshopmap;
	}
	public static HashMap<String,HolidayData> getHolidaymap() {
		return holidaymap;
	}
	public static void setHolidaymap(HashMap<String,HolidayData> holidaymap) {
		Bean.holidaymap = holidaymap;
	}
	public static HashMap<String,HolidayinfoData> getHolidayinfomap() {
		return holidayinfomap;
	}
	public static void setHolidayinfomap(HashMap<String,HolidayinfoData> holidayinfomap) {
		Bean.holidayinfomap = holidayinfomap;
	}
	public static HashMap<String,DigData> getDigmap() {
		return digmap;
	}
	public static void setDigmap(HashMap<String,DigData> digmap) {
		Bean.digmap = digmap;
	}
	public static HashMap<String,WizardData> getWizardmap() {
		return wizardmap;
	}
	public static void setWizardmap(HashMap<String,WizardData> wizardmap) {
		Bean.wizardmap = wizardmap;
	}
	public static HashMap<String,ReceiveData> getReceivemap() {
		return receivemap;
	}
	public static void setReceivemap(HashMap<String,ReceiveData> receivemap) {
		Bean.receivemap = receivemap;
	}
	public static String getLsdkkey() {
		return lsdkkey;
	}
	public static void setLsdkkey(String lsdkkey) {
		Bean.lsdkkey = lsdkkey;
	}
	public static HashMap<String,GangbossData> getGangbossmap() {
		return gangbossmap;
	}
	public static void setGangbossmap(HashMap<String,GangbossData> gangbossmap) {
		Bean.gangbossmap = gangbossmap;
	}
	public static ExecutorService getExecutor() {
		return executor;
	}
	public static void setExecutor(ExecutorService executor) {
		Bean.executor = executor;
	}
	public static HashMap<String,Znx_GanggoalData> getZnx_ganggoalmap() {
		return znx_ganggoalmap;
	}
	public static void setZnx_ganggoalmap(HashMap<String,Znx_GanggoalData> znx_ganggoalmap) {
		Bean.znx_ganggoalmap = znx_ganggoalmap;
	}
	public static HashMap<String,GangbossawardData> getGangbossawardmap() {
		return Gangbossawardmap;
	}
	public static void setGangbossawardmap(HashMap<String,GangbossawardData> gangbossawardmap) {
		Gangbossawardmap = gangbossawardmap;
	}
	public static HashMap<String,SkinData> getSkinmap() {
		return skinmap;
	}
	public static void setSkinmap(HashMap<String,SkinData> skinmap) {
		Bean.skinmap = skinmap;
	}
	public static HashMap<String,SevendayData> getSevendaymap() {
		return sevendaymap;
	}
	public static void setSevendaymap(HashMap<String,SevendayData> sevendaymap) {
		Bean.sevendaymap = sevendaymap;
	}
	public static ApplicationContext getCtxgm() {
		return ctxgm;
	}
	public static void setCtxgm(ApplicationContext ctxgm) {
		Bean.ctxgm = ctxgm;
	}
	public static HashMap<String,LvtableData> getLvtablemap() {
		return lvtablemap;
	}
	public static void setLvtablemap(HashMap<String,LvtableData> lvtablemap) {
		Bean.lvtablemap = lvtablemap;
	}
	public static HashMap<String,TacticsData> getTacticsmap() {
		return tacticsmap;
	}
	public static void setTacticsmap(HashMap<String,TacticsData> tacticsmap) {
		Bean.tacticsmap = tacticsmap;
	}
	public static HashMap<String,EquipData> getEquipmap() {
		return equipmap;
	}
	public static void setEquipmap(HashMap<String,EquipData> equipmap) {
		Bean.equipmap = equipmap;
	}
	public static HashMap<String,SkilllvData> getSkilllvmap() {
		return skilllvmap;
	}
	public static void setSkilllvmap(HashMap<String,SkilllvData> skilllvmap) {
		Bean.skilllvmap = skilllvmap;
	}
	public static HashMap<String,ItemData> getItemmap() {
		return itemmap;
	}
	public static void setItemmap(HashMap<String,ItemData> itemmap) {
		Bean.itemmap = itemmap;
	}
	public static HashMap<String,MaterialproData> getMaterialpromap() {
		return materialpromap;
	}
	public static void setMaterialpromap(HashMap<String,MaterialproData> materialpromap) {
		Bean.materialpromap = materialpromap;
	}
	public static ConcurrentHashMap<String,Znx_PlayerData> getCompetitionseasonmap() {
		return competitionseasonmap;
	}
	public static void setCompetitionseasonmap(ConcurrentHashMap<String,Znx_PlayerData> competitionseasonmap) {
		Bean.competitionseasonmap = competitionseasonmap;
	}
	public static HashMap<String,Thread> getCsthreadmap() {
		return csthreadmap;
	}
	public static void setCsthreadmap(HashMap<String,Thread> csthreadmap) {
		Bean.csthreadmap = csthreadmap;
	}
	public static ConcurrentHashMap<String,CompetitionSeasonRoom> getCompetitionseasonroommap() {
		return competitionseasonroommap;
	}
	public static void setCompetitionseasonroommap(ConcurrentHashMap<String,CompetitionSeasonRoom> competitionseasondatamap) {
		Bean.competitionseasonroommap = competitionseasondatamap;
	}
	public static HashMap<String,Thread> getGangbattlethreadmap() {
		return gangbattlethreadmap;
	}
	public static void setGangbattlethreadmap(HashMap<String,Thread> gangbattlethreadmap) {
		Bean.gangbattlethreadmap = gangbattlethreadmap;
	}
	public static HashMap<String,BarrierRewardData> getBarrierrewardmap() {
		return barrierrewardmap;
	}
	public static void setBarrierrewardmap(HashMap<String,BarrierRewardData> barrierrewardmap) {
		Bean.barrierrewardmap = barrierrewardmap;
	}
	public static String getLogfilepath() {
		return logfilepath;
	}
	public static void setLogfilepath(String logfilepath) {
		Bean.logfilepath = logfilepath;
	}

}
