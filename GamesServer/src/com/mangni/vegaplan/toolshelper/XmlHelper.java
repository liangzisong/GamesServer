package com.mangni.vegaplan.toolshelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import com.mangni.vegaplan.datatable.AddbuyData;
import com.mangni.vegaplan.datatable.AddrmbData;
import com.mangni.vegaplan.datatable.GangpostData;
import com.mangni.vegaplan.datatable.BarrierData;
import com.mangni.vegaplan.datatable.BarrierRewardData;
import com.mangni.vegaplan.datatable.BuyStoneData;
import com.mangni.vegaplan.datatable.ChampionsData;
import com.mangni.vegaplan.datatable.ChipData;
import com.mangni.vegaplan.datatable.DigData;
import com.mangni.vegaplan.datatable.EnumChipColor;
import com.mangni.vegaplan.datatable.EquipData;
import com.mangni.vegaplan.datatable.FighterData;
import com.mangni.vegaplan.datatable.GangShopData;
import com.mangni.vegaplan.datatable.GangbossData;
import com.mangni.vegaplan.datatable.GangbossawardData;
import com.mangni.vegaplan.datatable.GanglvupData;
import com.mangni.vegaplan.datatable.TrialData;
import com.mangni.vegaplan.datatable.GunData;
import com.mangni.vegaplan.datatable.HolidayData;
import com.mangni.vegaplan.datatable.HolidayinfoData;
import com.mangni.vegaplan.datatable.ItemData;
import com.mangni.vegaplan.datatable.JewelData;
import com.mangni.vegaplan.datatable.JjfyData;
import com.mangni.vegaplan.datatable.LvtableData;
import com.mangni.vegaplan.datatable.MaterialproData;
import com.mangni.vegaplan.datatable.MaxBuyData;
import com.mangni.vegaplan.datatable.MercenarytaskData;
import com.mangni.vegaplan.datatable.PcbData;
import com.mangni.vegaplan.datatable.RECShopData;
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
import com.mangni.vegaplan.datatable.WizardData;
import com.mangni.vegaplan.datatable.XxddData;

/**
 * @author cwqi
 * @createTime 2014-9-1
 * @version 
 */
public class XmlHelper
{
	/**
	 * @param args
	 */
	public static ArrayList<String> fileList(String path,String suffix) {
		ArrayList<String> filearraylist=new ArrayList<String>();
		File file=new File(path);
		File[] files = file.listFiles();
		if (files != null) 
		{
			for (File f : files) 
			{
				if(f.isDirectory())
				{
					filearraylist.addAll(fileList(f.getAbsolutePath(),suffix));
				}else{
					if(f.getName().split("\\.")[1].equalsIgnoreCase(suffix))
					{
						filearraylist.add(f.getAbsolutePath());
					}
				}
			}
		}
		return filearraylist;
	}
	public XmlHelper()
	{
		SAXBuilder builder = new SAXBuilder();
		String curDir=System.getProperty("user.dir");
		/*
		try 
		{
			//通过XML文件，构造文档对象
			Document read_doc = builder.build(curDir+"\\xmllib\\playerlvexp\\playerlvexp.xml");
			//得到根元素
			Element stu = read_doc.getRootElement();
			//得到student元素的列表
			List list = stu.getChildren("dv");
			//遍历student元素列表
			for(int i=0; i<list.size(); i++)
			{
				//获得并显示所有子元素
				Element e = (Element)list.get(i);
				String lv = e.getAttributeValue("lv");
				String playerupexp = e.getChildText("playerupexp");
				String fighterupexp = e.getChildText("fighterupexp");
				Bean.playerlvexp.put(lv, playerupexp);
				Bean.fighterlvexp.put(lv, fighterupexp);
			}
		} 
		catch (JDOMException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
		try
		{
			String dir=curDir+"\\xmllib\\";
			List<String> pathlist=fileList(dir,"xml");

			for(String filepath:pathlist)
			{
				String filename=filepath.substring(filepath.lastIndexOf("\\")+1,filepath.lastIndexOf("."));

				Document read_doc = builder.build(filepath.toString());
				Element root = read_doc.getRootElement();		
				List list = root.getChildren("data");
				switch(filename)
				{
				case "champions":	
					synchronized (list) {
						Bean.getChampionsmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							ChampionsData chamdata=new ChampionsData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							chamdata.setId(request.getAttributeValue("Value").toString());

							request =  stu.getChild("MinLevel");
							chamdata.setMinlv(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request =  stu.getChild("MaxLevel");
							chamdata.setMaxlv(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request =  stu.getChild("BossHp");
							chamdata.setBosshp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("BossId");
							chamdata.setBossid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per15RewardGold");
							chamdata.setPer15gold(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per15RewardExp");
							chamdata.setPer15exp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per15RewardExp");
							chamdata.setPer15exp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per15RewardItemId");
							chamdata.setPer15goodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per15RewardItemNum");
							chamdata.setPer15goodsnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per30RewardExp");
							chamdata.setPer30exp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per30RewardItemId");
							chamdata.setPer30goodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per30RewardItemNum");
							chamdata.setPer30goodsnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per60RewardExp");
							chamdata.setPer60exp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per60RewardItemId");
							chamdata.setPer60goodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per60RewardItemNum");
							chamdata.setPer60goodsnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per100RewardExp");
							chamdata.setPer100exp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per100RewardItemId");
							chamdata.setPer100goodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("Per100RewardItemNum");
							chamdata.setPer100goodsnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("FistRewardItemId");
							chamdata.setFirstrewardgoodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("FistRewardScore");
							chamdata.setFirstrewardnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("FistRewardItemId2");
							chamdata.setFirstrewardgoodsid2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("FistRewardScore2");
							chamdata.setFirstrewardnum2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The2_5RewardItemId");
							chamdata.setThe2_5rewardgoodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The2_5RewardScore");
							chamdata.setThe2_5rewardnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The2_5RewardItemId2");
							chamdata.setThe2_5rewardgoodsid2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The2_5RewardScore2");
							chamdata.setThe2_5rewardnum2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The6_10RewardItemId");
							chamdata.setThe6_10rewardgoodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The6_10RewardScore");
							chamdata.setThe6_10rewardnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The6_10RewardItemId2");
							chamdata.setThe6_10rewardgoodsid2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The6_10RewardScore2");
							chamdata.setThe6_10rewardnum2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The11_20RewardItemId");
							chamdata.setThe11_20rewardgoodsid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The11_20RewardScore");
							chamdata.setThe11_20rewardnum(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The11_20RewardItemId2");
							chamdata.setThe11_20rewardgoodsid2(request.getAttributeValue("Value").toString());

							request =  stu.getChild("The11_20RewardScore2");
							chamdata.setThe11_20rewardnum2(request.getAttributeValue("Value").toString());

							Bean.getChampionsmap().put(chamdata.getId(), chamdata);
						}
					}
					break;

				case "fighter":
					synchronized (list) {
						Bean.getFightermap().clear();
						for(int i=0; i<list.size(); i++)
						{
							FighterData fighterdata=new FighterData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							fighterdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("PositionID");
							fighterdata.setPositionid(request.getAttributeValue("Value"));

							request = stu.getChild("RaceID");
							fighterdata.setRanceid(request.getAttributeValue("Value"));

							request = stu.getChild("SkillId1");
							fighterdata.setSkillid1(request.getAttributeValue("Value"));

							request = stu.getChild("SkillId1");
							fighterdata.setSkillid1(request.getAttributeValue("Value"));

							request = stu.getChild("SkillId2");
							fighterdata.setSkillid2(request.getAttributeValue("Value"));

							request = stu.getChild("SkillId3");
							fighterdata.setSkillid3(request.getAttributeValue("Value"));

							request = stu.getChild("ProbabilityOne");
							if(request!=null)
							{
								fighterdata.setProbabilityone(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("ProbabilityTen");
							if(request!=null)
							{
								fighterdata.setProbabilityten(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("DefaultStar");
							if(request!=null)
							{
								fighterdata.setDefaultstar(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("MechPatch");
							fighterdata.setFighterpatch(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getFightermap().put(fighterdata.getId(), fighterdata);
						}
					}
					break;

				case "gem":
					synchronized (list) {
						Bean.getJewelmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							JewelData jeweldata=new JewelData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							jeweldata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("GemColor");
							jeweldata.setJewelcolor(request.getAttributeValue("Value"));

							request = stu.getChild("GemCharacter");
							jeweldata.setJewelcharacter(request.getAttributeValue("Value"));

							request = stu.getChild("Att1");
							jeweldata.setJewelatt1(request.getAttributeValue("Value"));

							request = stu.getChild("SalePrice");
							jeweldata.setJewelprice(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ProbabilityOne");
							if(request!=null)
							{
								jeweldata.setProbabilityone(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("ProbabilityTen");
							if(request!=null)
							{
								jeweldata.setProbabilityten(Integer.parseInt(request.getAttributeValue("Value")));
							}

							Bean.getJewelmap().put(jeweldata.getId(), jeweldata);

						}
					}
					break;

				case "chip":
					synchronized (list) {
						Bean.getChipmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							ChipData chipdata=new ChipData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							chipdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("ChipType");
							chipdata.setChiptype(request.getAttributeValue("Value"));

							request = stu.getChild("ChipColor");
							chipdata.setChipcolor(request.getAttributeValue("Value"));

							request = stu.getChild("SalePrice");
							chipdata.setChipprice(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ProbabilityOne");
							if(request!=null)
							{
								chipdata.setProbabilityone(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("ProbabilityTen");
							if(request!=null)
							{
								chipdata.setProbabilityten(Integer.parseInt(request.getAttributeValue("Value")));
							}

							Bean.getChipmap().put(chipdata.getId(), chipdata);	
						}
					}
					break;

				case "circuitboard":
					synchronized (list) {
						Bean.getPcbmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							PcbData pcbdata=new PcbData();
							Element stu = (Element)list.get(i);	

							Element request = stu.getChild("Id");
							pcbdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("CircuitBoardName");
							pcbdata.setPcbname(request.getAttributeValue("Value"));

							request = stu.getChild("CircuitBoardColor");
							pcbdata.setPcbcolor(request.getAttributeValue("Value"));

							request = stu.getChild("ChipType1");
							pcbdata.setChiptype1(request.getAttributeValue("Value"));

							request = stu.getChild("ChipType2");
							pcbdata.setChiptype2(request.getAttributeValue("Value"));

							if(stu.getChild("ChipType3")!=null)
							{
								request = stu.getChild("ChipType3");
								pcbdata.setChiptype3(request.getAttributeValue("Value"));
							}

							request = stu.getChild("GemColor1");
							pcbdata.setJewelcolor1(request.getAttributeValue("Value"));

							request = stu.getChild("GemColor2");
							pcbdata.setJewelcolor2(request.getAttributeValue("Value"));

							request = stu.getChild("GemColor3");
							pcbdata.setJewelcolor3(request.getAttributeValue("Value"));

							if(stu.getChild("GemCharacter4")!=null)
							{
								request = stu.getChild("GemColor4");
								pcbdata.setJewelcolor4(request.getAttributeValue("Value"));
							}

							if(stu.getChild("GemCharacter5")!=null)
							{
								request = stu.getChild("GemColor5");
								pcbdata.setJewelcolor5(request.getAttributeValue("Value"));
							}

							if(stu.getChild("GemCharacter6")!=null)
							{
								request = stu.getChild("GemColor6");
								pcbdata.setJewelcolor6(request.getAttributeValue("Value"));
							}

							if(stu.getChild("GemCharacter7")!=null)
							{
								request = stu.getChild("GemColor7");
								pcbdata.setJewelcolor7(request.getAttributeValue("Value"));
							}

							Bean.getPcbmap().put(pcbdata.getId(), pcbdata);
						}
					}
					break;

				case "item":
					synchronized (list) {
						Bean.getItemmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							ItemData itemdata=new ItemData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							itemdata.setId(request.getAttributeValue("Value"));
							
							request = stu.getChild("ItemType");
							if(request!=null&&request.getAttributeValue("Value")!="")
								itemdata.setItemtype(request.getAttributeValue("Value"));
							
							request = stu.getChild("UseItAddNum");
							if(request!=null&&request.getAttributeValue("Value")!="")
								itemdata.setAddnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Lv");
							if(request!=null&&request.getAttributeValue("Value")!="")
								itemdata.setLv(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getItemmap().put(itemdata.getId(), itemdata);
						}
					}
					break;

				case "barrier":
					synchronized (list) {
						Bean.getBarriermap().clear();
						for(int i=0; i<list.size(); i++)
						{
							BarrierData barrierdata=new BarrierData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							barrierdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Probability");
							barrierdata.setProbability(Double.parseDouble(request.getAttributeValue("Value")));

							request = stu.getChild("AddPlayerExp");
							barrierdata.setAddplayerexp(request.getAttributeValue("Value"));

							request = stu.getChild("AddMechExp");
							barrierdata.setAddmechexp(request.getAttributeValue("Value"));

							request = stu.getChild("Material");
							barrierdata.setMaterial(request.getAttributeValue("Value"));

							request = stu.getChild("Star1Gold");
							barrierdata.setStar1gold(request.getAttributeValue("Value"));

							request = stu.getChild("Star2Gold");
							barrierdata.setStar2gold(request.getAttributeValue("Value"));

							request = stu.getChild("Star3Gold");
							barrierdata.setStar3gold(request.getAttributeValue("Value"));
							
							request = stu.getChild("FirstPassRewardId");
							barrierdata.setFirstpassrewardid(request.getAttributeValue("Value"));
							
							request = stu.getChild("FirstPassRewardNum");
							barrierdata.setFirstpassrewardnum(request.getAttributeValue("Value"));

							Bean.getBarriermap().put(barrierdata.getId(),barrierdata);
						}
					}
					break;

				case "storerank":
					synchronized (list) {
						Bean.getRankshopmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							RankShopData rankshopdata=new RankShopData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							rankshopdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("ItemId");
							rankshopdata.setItemid(request.getAttributeValue("Value"));

							request = stu.getChild("ItemType");
							rankshopdata.setItemtype(request.getAttributeValue("Value"));

							request = stu.getChild("ChipType");
							rankshopdata.setChiptype(request.getAttributeValue("Value"));

							request = stu.getChild("GemCharacter");
							rankshopdata.setJewelcharacter(request.getAttributeValue("Value"));

							request = stu.getChild("Color");
							rankshopdata.setColor(request.getAttributeValue("Value"));

							request = stu.getChild("Quantity");
							rankshopdata.setGoodsnum(request.getAttributeValue("Value"));

							request = stu.getChild("Integral");
							rankshopdata.setNeedpoints(request.getAttributeValue("Value"));

							request = stu.getChild("MinList");
							rankshopdata.setMinlist(request.getAttributeValue("Value"));

							request = stu.getChild("ExchangeNum");
							rankshopdata.setNumlimit(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Gold");
							rankshopdata.setNeedgold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetGold");
							rankshopdata.setGetgold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetExp");
							rankshopdata.setGetexp(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("NeedStatue");
							rankshopdata.setBullfightcup(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("LeagueGold");
							rankshopdata.setNeedstone("vip0",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip1");
							rankshopdata.setNeedstone("vip1",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip2");
							rankshopdata.setNeedstone("vip2",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip3");
							rankshopdata.setNeedstone("vip3",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip4");
							rankshopdata.setNeedstone("vip4",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip5");
							rankshopdata.setNeedstone("vip5",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip6");
							rankshopdata.setNeedstone("vip6",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip7");
							rankshopdata.setNeedstone("vip7",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip8");
							rankshopdata.setNeedstone("vip8",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip9");
							rankshopdata.setNeedstone("vip9",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip10");
							rankshopdata.setNeedstone("vip10",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip11");
							rankshopdata.setNeedstone("vip11",Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Vip12");
							rankshopdata.setNeedstone("vip12",Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getRankshopmap().put(rankshopdata.getId(),rankshopdata);
						}
					}
					break;

				case "store":
					synchronized (list) {
						Bean.getStoreshopmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							StoreData storedata=new StoreData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							storedata.setId(request.getAttributeValue("Value"));
							
							request = stu.getChild("ItemId");
							storedata.setItemid(request.getAttributeValue("Value"));

							request = stu.getChild("Quantity");
							storedata.setGoodsnum(request.getAttributeValue("Value"));

							request = stu.getChild("ExchangeNum");
							storedata.setNumlimit(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ShopType");
							storedata.setShoptype(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("ItemType");
							storedata.setItemtype(request.getAttributeValue("Value"));

							request = stu.getChild("NeedGold");
							storedata.setNeedgold(request.getAttributeValue("Value"));

							request = stu.getChild("MinLv");
							storedata.setMinlv(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxLv");
							storedata.setMaxlv(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("StartDate");
							storedata.setStartdate(request.getAttributeValue("Value"));
							
							request = stu.getChild("EndDate");
							storedata.setEnddate(request.getAttributeValue("Value"));

							Bean.getStoreshopmap().put(storedata.getId(),storedata);
						}
					}
					break;

				case "vip":
					synchronized (list) {
						Bean.getVipmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							VipData vipdata=new VipData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							vipdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("ConsumedRMB");
							vipdata.setConsumedrmb(request.getAttributeValue("Value"));

							request = stu.getChild("RewardTemplate1Type");
							vipdata.setRewardtemplate1type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate1Id");
							vipdata.setRewardtemplate1id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate1Num");
							vipdata.setRewardtemplate1num(request.getAttributeValue("Value"));

							request = stu.getChild("RewardTemplate2Type");
							vipdata.setRewardtemplate2type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate2Id");
							vipdata.setRewardtemplate2id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate2Num");
							vipdata.setRewardtemplate2num(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate3Type");
							vipdata.setRewardtemplate3type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate3Id");
							vipdata.setRewardtemplate3id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate3Num");
							vipdata.setRewardtemplate3num(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate4Type");
							vipdata.setRewardtemplate4type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate4Id");
							vipdata.setRewardtemplate4id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate4Num");
							vipdata.setRewardtemplate4num(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate5Type");
							vipdata.setRewardtemplate5type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate5Id");
							vipdata.setRewardtemplate5id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate5Num");
							vipdata.setRewardtemplate5num(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate6Type");
							vipdata.setRewardtemplate6type(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate6Id");
							vipdata.setRewardtemplate6id(request.getAttributeValue("Value"));
							
							request = stu.getChild("RewardTemplate6Num");
							vipdata.setRewardtemplate6num(request.getAttributeValue("Value"));

							Bean.getVipmap().put(vipdata.getId(), vipdata);
						}
					}
					break;

				case "reward":
					synchronized (list) {
						Bean.getViprewardmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							VipRewardData viprewarddata=new VipRewardData();
							Element stu = (Element)list.get(i);		

							Element request = stu.getChild("Id");
							viprewarddata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("RewardType");
							viprewarddata.setRewardtype(request.getAttributeValue("Value"));

							request = stu.getChild("RewardItemType");
							if(request!=null)
							{
								viprewarddata.setRewarditemtype(request.getAttributeValue("Value"));
							}

							request = stu.getChild("RewardItemId");
							if(request!=null)
							{
								viprewarddata.setRewarditemid(Integer.parseInt(request.getAttributeValue("Value")));
							}

							request = stu.getChild("RewardItemNum");
							if(request!=null)
							{
								viprewarddata.setRewarditemnum(Integer.parseInt(request.getAttributeValue("Value")));
							}
							Bean.getViprewardmap().put(viprewarddata.getId(), viprewarddata);
						}
					}
					break;

				case "maxbuy":
					synchronized (list) {
						Bean.getMaxbuymap().clear();
						for(int i=0; i<list.size(); i++)
						{
							MaxBuyData maxbuydata=new MaxBuyData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							maxbuydata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("MaxBuyVit");
							maxbuydata.setMaxbuyvit(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxBuyEnergy");
							maxbuydata.setMaxbuyenergy(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxBuyGold");
							maxbuydata.setMaxbuygold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxBuyChallenge");
							maxbuydata.setMaxbuychallenge(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxExchangePatch");
							maxbuydata.setMaxexchangepatch(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getMaxbuymap().put(maxbuydata.getId(), maxbuydata);
						}
					}
					break;

				case "recshop":
					synchronized (list) {
						Bean.getRecshopmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							RECShopData recshopdata=new RECShopData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							recshopdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("NeedRmb");
							recshopdata.setNeedrmb(Double.parseDouble(request.getAttributeValue("Value")));

							request = stu.getChild("Stone");
							recshopdata.setStone(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getRecshopmap().put(recshopdata.getId(), recshopdata);
						}
					}
					break;

				case "buystone":
					synchronized (list) {
						Bean.getBuystonemap().clear();
						for(int i=0; i<list.size(); i++)
						{
							BuyStoneData buystonedata=new BuyStoneData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							buystonedata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("BuyVitStone");
							buystonedata.setBuyvitstone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("BuyEnergyStone");
							buystonedata.setBuyenergystone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("BuyGoldStone");
							buystonedata.setBuygoldstone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("BuyBullFightStone");
							buystonedata.setBuybullfightstone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("BuyDeathStone");
							buystonedata.setBuydeathstone(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getBuystonemap().put(buystonedata.getId(), buystonedata);	
						}
					}
					break;

				case "sign":
					synchronized (list) {
						Bean.getSignmonthmap().clear();
						Bean.getSignweekmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							SignData signdata=new SignData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							signdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("WhichDay");
							signdata.setWhichday(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("SignType");
							signdata.setSigntype(request.getAttributeValue("Value"));

							request = stu.getChild("RewardType");
							signdata.setRewardtype(request.getAttributeValue("Value"));

							request = stu.getChild("RewardValue");
							signdata.setRewardvalue(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("RewardItemId");
							signdata.setRewarditemid(request.getAttributeValue("Value"));

							request = stu.getChild("DoubleNeedVipLv");
							signdata.setDoubleneedviplv(Integer.parseInt(request.getAttributeValue("Value")));

							if(signdata.getSigntype().equals("Week"))
								Bean.getSignweekmap().put(signdata.getId(), signdata);	
							else if(signdata.getSigntype().equals("Month"))
								Bean.getSignmonthmap().put(signdata.getId(), signdata);
						}
					}
					break;
				/*		
				case "dailytask":
					synchronized (list) {
						Bean.getDailytaskmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							DailytaskData dailytaskdata=new DailytaskData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							dailytaskdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Dot");
							dailytaskdata.setDot(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("TaskCount");
							dailytaskdata.setTaskcount(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("RewardStone");
							dailytaskdata.setRewardstone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("RewardGold");
							dailytaskdata.setRewardgold(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("HeroExp");
							dailytaskdata.setFighterexp(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("RewardExp");
							dailytaskdata.setRewardexp(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Type");
							dailytaskdata.setType(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getDailytaskmap().put(dailytaskdata.getId(), dailytaskdata);
						}
					}
					break;*/
				case "vippackage":
					synchronized (list) {
						Bean.getVippackagemap().clear();
						for(int i=0; i<list.size(); i++)
						{
							VipPackageData vippackagedata=new VipPackageData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							vippackagedata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("VipPackageGold");
							vippackagedata.setGold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GemCharacter");
							vippackagedata.setJewelcharacter(request.getAttributeValue("Value"));

							request = stu.getChild("GemQuantity");
							vippackagedata.setJewelnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ChipColor");
							vippackagedata.setChipcolor(request.getAttributeValue("Value"));

							request = stu.getChild("ChipQuantity");
							vippackagedata.setChipnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GuiItemId");
							vippackagedata.setItemid(request.getAttributeValue("Value"));

							request = stu.getChild("GuiQuantity");
							vippackagedata.setItemnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("VipPackageStone");
							vippackagedata.setNeedstone(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getVippackagemap().put(vippackagedata.getId(), vippackagedata);
						}
					}
					break;
				case "xxdd":
					synchronized (list) {
						Bean.getXxddmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							XxddData xxdddata=new XxddData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							xxdddata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("StarNum");
							xxdddata.setStarnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetExp");
							xxdddata.setExp(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetGold");
							xxdddata.setGold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetStore");
							xxdddata.setStone(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getXxddmap().put(xxdddata.getId(), xxdddata);
						}
					}
					break;
				case "jjfy":
					synchronized (list) {
						Bean.getJjfymap().clear();
						for(int i=0; i<list.size(); i++)
						{
							JjfyData jjfydata=new JjfyData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							jjfydata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Group");
							jjfydata.setRankdan(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetRank");
							jjfydata.setRank(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetGold");
							jjfydata.setGold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetStore");
							jjfydata.setStone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ItemId");
							jjfydata.setGoodsid(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ItemNum");
							jjfydata.setGoodsnum(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getJjfymap().put(jjfydata.getId(), jjfydata);
						}
					}
					break;

				case "addbuy":
					synchronized (list) {
						Bean.getAddbuymap().clear();
						for(int i=0; i<list.size(); i++)
						{
							AddbuyData addbuydata=new AddbuyData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							addbuydata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Diamons");
							addbuydata.setSpendstone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetGold");
							addbuydata.setGold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ChipId");
							addbuydata.setChipid(request.getAttributeValue("Value"));

							request = stu.getChild("GemId");
							addbuydata.setJewelid(request.getAttributeValue("Value"));

							request = stu.getChild("FighterId");
							addbuydata.setFighterid(request.getAttributeValue("Value"));

							request = stu.getChild("StartDate");
							addbuydata.setStartdate(request.getAttributeValue("Value"));

							request = stu.getChild("EndDate");
							addbuydata.setEnddate(request.getAttributeValue("Value"));

							request = stu.getChild("LjxfDesc");
							if(request!=null)
								addbuydata.setDesc(request.getAttributeValue("Value"));

							Bean.getAddbuymap().put(addbuydata.getId(), addbuydata);
						}
					}
					break;

				case "addrmb":
					synchronized (list) {
						Bean.getAddrmbmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							AddrmbData addrmbdata=new AddrmbData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							addrmbdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Rmb");
							addrmbdata.setNeedrmb(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetStone");
							addrmbdata.setStone(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("GetGold");
							addrmbdata.setGold(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ChipId");
							addrmbdata.setChipid(request.getAttributeValue("Value"));

							request = stu.getChild("GemId");
							addrmbdata.setJewelid(request.getAttributeValue("Value"));

							request = stu.getChild("StartDate");
							addrmbdata.setStartdate(request.getAttributeValue("Value"));

							request = stu.getChild("EndDate");
							addrmbdata.setEnddate(request.getAttributeValue("Value"));

							request = stu.getChild("LjczDesc");
							if(request!=null)
								addrmbdata.setDesc(request.getAttributeValue("Value"));

							Bean.getAddrmbmap().put(addrmbdata.getId(), addrmbdata);

						}
					}
					break;
				case "gun":
					synchronized (list) {
						Bean.getGunmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GunData gundata=new GunData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Num");
							gundata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Stake1");
							gundata.setStake1(request.getAttributeValue("Value"));

							request = stu.getChild("Stake2");
							gundata.setStake2(request.getAttributeValue("Value"));

							request = stu.getChild("Ring1");
							gundata.setRing1(request.getAttributeValue("Value"));

							request = stu.getChild("Ring2");
							gundata.setRing2(request.getAttributeValue("Value"));

							request = stu.getChild("Ring3");
							gundata.setRing3(request.getAttributeValue("Value"));

							request = stu.getChild("Ring4");
							gundata.setRing4(request.getAttributeValue("Value"));

							request = stu.getChild("Ring5");
							gundata.setRing5(request.getAttributeValue("Value"));

							request = stu.getChild("Ring6");
							gundata.setRing6(request.getAttributeValue("Value"));

							request = stu.getChild("Ring7");
							gundata.setRing7(request.getAttributeValue("Value"));

							request = stu.getChild("Ring8");
							gundata.setRing8(request.getAttributeValue("Value"));

							request = stu.getChild("Ring9");
							gundata.setRing9(request.getAttributeValue("Value"));

							request = stu.getChild("Ring10");
							gundata.setRing10(request.getAttributeValue("Value"));

							request = stu.getChild("StartDate");
							gundata.setRing10(request.getAttributeValue("Value"));

							request = stu.getChild("EndDate");
							gundata.setRing10(request.getAttributeValue("Value"));

							Bean.getGunmap().put(gundata.getId(), gundata);
						}
					}
					break;
				case "turn":
					synchronized (list) {
						Bean.getTurnmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							TurnData turndata=new TurnData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							turndata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("Type");
							turndata.setType(request.getAttributeValue("Value"));

							request = stu.getChild("GoodsId");
							turndata.setGoodsid(request.getAttributeValue("Value"));

							request = stu.getChild("GemCharacter");
							if(request!=null)
								turndata.setJewelcharacter(request.getAttributeValue("Value"));

							request = stu.getChild("ChipColor");
							if(request!=null)
								turndata.setChipcolor(request.getAttributeValue("Value"));

							request = stu.getChild("ProbabilityTurn");
							turndata.setProbability(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("AddTurn");
							turndata.setAddturn(Integer.parseInt(request.getAttributeValue("Value")));


							Bean.getTurnmap().put(turndata.getId(), turndata);
						}
					}
					break;
				case "turnnum":
					synchronized (list) {
						Bean.getTurnnummap().clear();
						for(int i=0; i<list.size(); i++)
						{
							TurnnumData turnnumdata=new TurnnumData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							turnnumdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("TurnType");
							turnnumdata.setTurntype(request.getAttributeValue("Value"));

							request = stu.getChild("TurnNum");
							turnnumdata.setNeedmoney(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getTurnnummap().put(turnnumdata.getId(), turnnumdata);
						}
					}
					break;
				case "mission":
					synchronized (list) {
						Bean.getMercenarytaskmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							MercenarytaskData tasktdata = new MercenarytaskData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							tasktdata.setId(request.getAttributeValue("Value").toString());

							request =  stu.getChild("MissionExp");
							tasktdata.setExp(request.getAttributeValue("Value").toString());

							request =  stu.getChild("MissionGold");
							tasktdata.setGold(request.getAttributeValue("Value").toString());

							request =  stu.getChild("ProbabilityGold");
							tasktdata.setGoldprob(request.getAttributeValue("Value").toString());

							request =  stu.getChild("MissionStone");
							tasktdata.setStone(request.getAttributeValue("Value").toString());

							request =  stu.getChild("GemId");
							tasktdata.setJewelid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("ProbabilityGem");
							tasktdata.setJewelprob(request.getAttributeValue("Value").toString());

							request =  stu.getChild("ChipId");
							tasktdata.setChipid(request.getAttributeValue("Value").toString());

							request =  stu.getChild("ProbabilityChip");
							tasktdata.setChipprob(request.getAttributeValue("Value").toString());

							request =  stu.getChild("ItemId");
							tasktdata.setGoodsid(request.getAttributeValue("Value").toString());

							Bean.getMercenarytaskmap().put(tasktdata.getId(), tasktdata);
						}
					}
					break;
				case "turntable":
					synchronized (list) {
						Bean.getTurnlvmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							TurnlvData turnlvdata = new TurnlvData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Lv");
							turnlvdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("AddressPercent");
							turnlvdata.setAddpro(Double.parseDouble(request.getAttributeValue("Value").toString()));

							request = stu.getChild("TurntableExp");
							turnlvdata.setLvlupexp(request.getAttributeValue("Value").toString());

							Bean.getTurnlvmap().put(turnlvdata.getId(),turnlvdata);
						}
					}
					break;
				case "gangpost":
					synchronized (list) {
						Bean.getGangpostmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GangpostData gangpostdata = new GangpostData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							gangpostdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("ArmyPay");
							gangpostdata.setArmypay(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("ArmyTurn");
							gangpostdata.setNeedcontribution(request.getAttributeValue("Value").toString());

							Bean.getGangpostmap().put(gangpostdata.getId(), gangpostdata);
						}
					}
					break;
				case "basegang":
					synchronized (list) {
						Bean.getGanglvupmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GanglvupData ganglvupdata = new GanglvupData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							ganglvupdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("GangExp");
							ganglvupdata.setGangexp(request.getAttributeValue("Value").toString());

							request = stu.getChild("GangCount");
							ganglvupdata.setGangcount(Integer.parseInt(request.getAttributeValue("Value").toString()));

							Bean.getGanglvupmap().put(ganglvupdata.getId(), ganglvupdata);
						}
					}
					break;
				case "trial":
					synchronized (list) {
						Bean.gettrialmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							TrialData trialdata = new TrialData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							trialdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetExp1");
							trialdata.getGetmap1().put("GetExp", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold1");
							trialdata.getGetmap1().put("GetGold", request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId1");
							trialdata.getGetmap1().put("ItemId", request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity1");
							trialdata.getGetmap1().put("Quantity", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetExp2");
							trialdata.getGetmap2().put("GetExp", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold2");
							trialdata.getGetmap2().put("GetGold", request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId2");
							trialdata.getGetmap2().put("ItemId", request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity2");
							trialdata.getGetmap2().put("Quantity", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetExp3");
							trialdata.getGetmap3().put("GetExp", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold3");
							trialdata.getGetmap3().put("GetGold", request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId3");
							trialdata.getGetmap3().put("ItemId", request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity3");
							trialdata.getGetmap3().put("Quantity", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetExp4");
							trialdata.getGetmap4().put("GetExp", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold4");
							trialdata.getGetmap4().put("GetGold", request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId4");
							trialdata.getGetmap4().put("ItemId", request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity4");
							trialdata.getGetmap4().put("Quantity", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetExp5");
							trialdata.getGetmap5().put("GetExp", request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold5");
							trialdata.getGetmap5().put("GetGold", request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId5");
							trialdata.getGetmap5().put("ItemId", request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity5");
							trialdata.getGetmap5().put("Quantity", request.getAttributeValue("Value").toString());

							Bean.gettrialmap().put(trialdata.getId(), trialdata);
						}
					}
					break;

				case "gangshop":
					synchronized (list) {
						Bean.getGangshopmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GangShopData gangshopdata = new GangShopData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							gangshopdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId");
							gangshopdata.setGoodsid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType");
							gangshopdata.setGoodstype(request.getAttributeValue("Value").toString());

							request = stu.getChild("ChipType");
							gangshopdata.setChiptype(request.getAttributeValue("Value").toString());

							request = stu.getChild("GemCharacter");
							gangshopdata.setJewelcharacter(request.getAttributeValue("Value").toString());

							request = stu.getChild("Color");
							gangshopdata.setColor(request.getAttributeValue("Value").toString());

							request = stu.getChild("Quantity");
							gangshopdata.setGoodsnum(request.getAttributeValue("Value").toString());

							request = stu.getChild("MinArmyPost");
							gangshopdata.setMinarmypost(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("ExchangeNum");
							gangshopdata.setExchangenum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("NeedTurn");
							gangshopdata.setNeedturn(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Gold");
							gangshopdata.setNeedgold(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("LeagueGold");
							gangshopdata.setNeedstone("vip0", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip1");
							gangshopdata.setNeedstone("vip1", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip2");
							gangshopdata.setNeedstone("vip2", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip3");
							gangshopdata.setNeedstone("vip3", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip4");
							gangshopdata.setNeedstone("vip4", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip5");
							gangshopdata.setNeedstone("vip5", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip6");
							gangshopdata.setNeedstone("vip6", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip7");
							gangshopdata.setNeedstone("vip7", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip8");
							gangshopdata.setNeedstone("vip8", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip9");
							gangshopdata.setNeedstone("vip9", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip10");
							gangshopdata.setNeedstone("vip10", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip11");
							gangshopdata.setNeedstone("vip11", Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Vip12");
							gangshopdata.setNeedstone("vip12", Integer.parseInt(request.getAttributeValue("Value").toString()));

							Bean.getGangshopmap().put(gangshopdata.getId(), gangshopdata);						
						}
					}
					break;

				case "holiday":
					synchronized (list) {
						Bean.getHolidaymap().clear();
						for(int i=0; i<list.size(); i++)
						{
							HolidayData holidaydata = new HolidayData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							holidaydata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("Holiday");
							holidaydata.setHolidayname(request.getAttributeValue("Value").toString());

							request = stu.getChild("Description");
							holidaydata.setDescription(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetGold");
							holidaydata.setGetgold(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetItem1Id");
							holidaydata.setGetitem1id(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetItem1Num");
							holidaydata.setGetitem1num(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetItem2Id");
							holidaydata.setGetitem2id(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetItem2Num");
							holidaydata.setGetitem2num(request.getAttributeValue("Value").toString());

							request = stu.getChild("GetStone");
							holidaydata.setGetstone(request.getAttributeValue("Value").toString());

							request = stu.getChild("GemCharacter");
							holidaydata.setJewelcharacter(request.getAttributeValue("Value").toString());

							request = stu.getChild("GemId");
							holidaydata.setJewelid(request.getAttributeValue("Value").toString());

							request = stu.getChild("GemItemId");
							holidaydata.setJewelitemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ChipColor");
							holidaydata.setChipcolor(request.getAttributeValue("Value").toString());

							request = stu.getChild("ChipId");
							holidaydata.setChipid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ChipItemId");
							holidaydata.setChipitemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId");
							holidaydata.setItemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum");
							holidaydata.setItemnum(request.getAttributeValue("Value").toString());

							request = stu.getChild("Vit");
							holidaydata.setVit(request.getAttributeValue("Value").toString());

							request = stu.getChild("Stone");
							holidaydata.setNeedstone(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("StartDate");
							holidaydata.setStartdate(request.getAttributeValue("Value").toString());

							request = stu.getChild("EndDate");
							holidaydata.setEnddate(request.getAttributeValue("Value").toString());

							request = stu.getChild("HolidayDesc");
							if(request!=null)
								holidaydata.setHolidaydesc(request.getAttributeValue("Value").toString());

							Bean.getHolidaymap().put(holidaydata.getId(), holidaydata);
						}
					}
					break;
				case "holidayinfo":
					synchronized (list) {
						Bean.getHolidayinfomap().clear();
						for(int i=0; i<list.size(); i++)
						{
							HolidayinfoData holidayinfodata = new HolidayinfoData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							holidayinfodata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("HolidayName");
							holidayinfodata.setHolidayname(request.getAttributeValue("Value").toString());

							request = stu.getChild("StartDate");
							holidayinfodata.setStartdate(request.getAttributeValue("Value").toString());

							request = stu.getChild("EndDate");
							holidayinfodata.setEnddate(request.getAttributeValue("Value").toString());

							request = stu.getChild("Desc");
							if(request!=null)
								holidayinfodata.setDesc(request.getAttributeValue("Value").toString());

							request = stu.getChild("OpenBase");
							holidayinfodata.setOpenbase(request.getAttributeValue("Value").toString());

							request = stu.getChild("BaseName");
							holidayinfodata.setBasename(request.getAttributeValue("Value").toString());

							Bean.getHolidayinfomap().put(holidayinfodata.getId(), holidayinfodata);
						}
					}
					break;
				case "dig":
					synchronized (list) {
						Bean.getDigmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							DigData digdata = new DigData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							digdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("UId");
							digdata.setUid(request.getAttributeValue("Value").toString());

							request = stu.getChild("DigId");
							digdata.setDigid(request.getAttributeValue("Value").toString());

							request = stu.getChild("DigNum");
							digdata.setDignum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("ItemType");
							digdata.setItemtype(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId");
							digdata.setItemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum");
							digdata.setItemnum(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemCurrency");
							digdata.setItemcurrency(request.getAttributeValue("Value").toString());

							request = stu.getChild("CGI");
							digdata.setCgi(request.getAttributeValue("Value").toString());

							request = stu.getChild("Description");
							digdata.setDesc(request.getAttributeValue("Value").toString());

							request = stu.getChild("MaxId");
							if(request!=null)
								digdata.setMaxid(request.getAttributeValue("Value").toString());

							Bean.getDigmap().put(digdata.getId(), digdata);
						}
					}
					break;

				case "wizard":
					synchronized (list) {
						Bean.getWizardmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							WizardData wizarddata = new WizardData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							wizarddata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("Use");
							wizarddata.setUse(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Type");
							wizarddata.setTypeid(request.getAttributeValue("Value").toString());

							request = stu.getChild("Description");
							wizarddata.setDesc(request.getAttributeValue("Value").toString());

							request = stu.getChild("TaskCount");
							wizarddata.setTaskcount(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("RewardCurrency");
							wizarddata.setRewardcurrency(request.getAttributeValue("Value").toString());

							request = stu.getChild("RewardGold");
							wizarddata.setRewardgold(request.getAttributeValue("Value").toString());

							request = stu.getChild("RewardItem");
							wizarddata.setRewarditemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("RewardItemNum");
							wizarddata.setRewarditemnum(request.getAttributeValue("Value").toString());

							Bean.getWizardmap().put(wizarddata.getId(), wizarddata);
						}
					}
					break;
				case "receive":
					synchronized (list) {
						Bean.getReceivemap().clear();
						for(int i=0; i<list.size(); i++)
						{
							ReceiveData receivedata = new ReceiveData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							receivedata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("SetStone");
							receivedata.setSetstone(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("GetCurrency");
							receivedata.setGetcurrency(Integer.parseInt(request.getAttributeValue("Value").toString()));

							Bean.getReceivemap().put(receivedata.getId(), receivedata);
						}
					}
					break;
				case "gangboss":
					synchronized (list) {
						Bean.getGangbossmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GangbossData gangbossdata = new GangbossData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							gangbossdata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("Span");
							gangbossdata.setSpan(Integer.parseInt(request.getAttributeValue("Value").toString()));

							Bean.getGangbossmap().put(gangbossdata.getId(), gangbossdata);
						}
					}
					break;
				case "gangbossaward":
					synchronized (list) {
						Bean.getGangbossawardmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							GangbossawardData gangbossawarddata = new GangbossawardData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							gangbossawarddata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("MaxLv");
							gangbossawarddata.setMaxlv(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Per15ItemId");
							gangbossawarddata.setPer15itemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per15ItemNum");
							gangbossawarddata.setPer15itemnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Per15GemCharacter");
							gangbossawarddata.setPer15jewelcharacter(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per30ItemId");
							gangbossawarddata.setPer30itemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per30ItemNum");
							gangbossawarddata.setPer30itemnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Per30GemCharacter");
							gangbossawarddata.setPer30jewelcharacter(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per60ItemId");
							gangbossawarddata.setPer60itemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per60ItemNum");
							gangbossawarddata.setPer60itemnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Per60ChipColor");
							gangbossawarddata.setPer60chipcolor(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per100ItemId");
							gangbossawarddata.setPer100itemid(request.getAttributeValue("Value").toString());

							request = stu.getChild("Per100ItemNum");
							gangbossawarddata.setPer100itemnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Per100ChipColor");
							gangbossawarddata.setPer100chipcolor(request.getAttributeValue("Value").toString());

							request = stu.getChild("F1Score");
							gangbossawarddata.setF1socore(request.getAttributeValue("Value").toString());

							request = stu.getChild("S2Score");
							gangbossawarddata.setF2socore(request.getAttributeValue("Value").toString());

							request = stu.getChild("T3Score");
							gangbossawarddata.setF3socore(request.getAttributeValue("Value").toString());

							request = stu.getChild("ElseScore");
							gangbossawarddata.setSocore(request.getAttributeValue("Value").toString());

							Bean.getGangbossawardmap().put(gangbossawarddata.getId(),gangbossawarddata);
						}
					}
					break;

				case "skin":
					synchronized (list) {
						Bean.getSkinmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							SkinData skindata = new SkinData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							skindata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("Type");
							skindata.setType(request.getAttributeValue("Value").toString());

							request = stu.getChild("FighterId");
							skindata.setFighterid(request.getAttributeValue("Value").toString());

							request = stu.getChild("DayticketNum");
							skindata.setDayticketnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("WeekticketNum");
							skindata.setWeekticketnum(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("LeagueStone");
							skindata.setNeedstone(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("OldStone");
							skindata.setOldstone(Integer.parseInt(request.getAttributeValue("Value").toString()));

							request = stu.getChild("Desc");
							if(request!=null)
								skindata.setDesc(request.getAttributeValue("Value").toString());

							Bean.getSkinmap().put(skindata.getId(), skindata);
						}
					}
					break;
				case "sevenday":
					synchronized (list) {
						Bean.getSevendaymap().clear();
						for(int i=0; i<list.size(); i++)
						{
							SevendayData sevendaydata = new SevendayData();

							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							sevendaydata.setId(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType1");
							sevendaydata.setItemtype1(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId1");
							sevendaydata.setItemid1(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum1");
							sevendaydata.setItemnum1(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType2");
							sevendaydata.setItemtype2(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId2");
							sevendaydata.setItemid2(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum2");
							sevendaydata.setItemnum2(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType3");
							sevendaydata.setItemtype3(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId3");
							sevendaydata.setItemid3(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum3");
							sevendaydata.setItemnum3(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType4");
							sevendaydata.setItemtype4(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId4");
							sevendaydata.setItemid4(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum4");
							sevendaydata.setItemnum4(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType5");
							sevendaydata.setItemtype5(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId5");
							sevendaydata.setItemid5(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum5");
							sevendaydata.setItemnum5(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType6");
							sevendaydata.setItemtype6(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId6");
							sevendaydata.setItemid6(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum6");
							sevendaydata.setItemnum6(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType7");
							sevendaydata.setItemtype7(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId7");
							sevendaydata.setItemid7(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum7");
							sevendaydata.setItemnum7(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType8");
							sevendaydata.setItemtype8(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId8");
							sevendaydata.setItemid8(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum8");
							sevendaydata.setItemnum8(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType9");
							sevendaydata.setItemtype9(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId9");
							sevendaydata.setItemid9(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum9");
							sevendaydata.setItemnum9(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemType10");
							sevendaydata.setItemtype10(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemId10");
							sevendaydata.setItemid10(request.getAttributeValue("Value").toString());

							request = stu.getChild("ItemNum10");
							sevendaydata.setItemnum10(request.getAttributeValue("Value").toString());

							Bean.getSevendaymap().put(sevendaydata.getId(), sevendaydata);
						}
					}
					break;

				case "lvtable":
					synchronized (list) {
						Bean.getLvtablemap().clear();
						for(int i=0; i<list.size(); i++)
						{
							LvtableData lvtabledata=new LvtableData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							lvtabledata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("coin");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setCoin(Long.parseLong(request.getAttributeValue("Value")));

							request = stu.getChild("PlayerExp");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setPlayerexp(Long.parseLong(request.getAttributeValue("Value")));

							request = stu.getChild("FighterExp");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setFighterexp(Long.parseLong(request.getAttributeValue("Value")));

							request = stu.getChild("EquipLv");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setEquiplv(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chip");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setChip(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("material");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setMaterial(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("PlayerExpBase");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setPlayerexpbase(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("FighterExpBase");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setFighterexpbase(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("GoldBase");
							if(request!=null&&request.getAttributeValue("Value")!="")
								lvtabledata.setGoldbase(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getLvtablemap().put(lvtabledata.getId(), lvtabledata);
						}
					}
					break;

				case "equip":
					synchronized (list) {
						Bean.getEquipmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							EquipData equipdata=new EquipData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							equipdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("FighterID");
							equipdata.setFighterid(request.getAttributeValue("Value"));

							request = stu.getChild("equipPos");
							equipdata.setEquippos(request.getAttributeValue("Value"));

							request = stu.getChild("material1");
							equipdata.setMaterial1(request.getAttributeValue("Value"));

							request = stu.getChild("material2");
							equipdata.setMaterial2(request.getAttributeValue("Value"));

							request = stu.getChild("material3");
							equipdata.setMaterial3(request.getAttributeValue("Value"));

							request = stu.getChild("material4");
							equipdata.setMaterial4(request.getAttributeValue("Value"));

							Bean.getEquipmap().put(equipdata.getId(), equipdata);
						}
					}
					break;

				case "tactics":
					synchronized (list) {
						Bean.getTacticsmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							TacticsData tacticsdata=new TacticsData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							tacticsdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("fighterLv");
							tacticsdata.setFighterlv(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("material");
							tacticsdata.setMaterial(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chance_2");
							tacticsdata.setChance_2(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chance_1");
							tacticsdata.setChance_1(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chance0");
							tacticsdata.setChance0(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chance1");
							tacticsdata.setChance1(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("chance2");
							tacticsdata.setChance2(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getTacticsmap().put(tacticsdata.getId(), tacticsdata);
						}
					}
					break;

				case "skilllv":
					synchronized (list) {
						Bean.getSkilllvmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							SkilllvData skilllvdata=new SkilllvData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							skilllvdata.setId(request.getAttributeValue("Value"));

							request = stu.getChild("FighterStar");
							skilllvdata.setFighterstar(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Pro1");
							skilllvdata.setPro1(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("Pro2");
							skilllvdata.setPro2(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("ProMax");
							skilllvdata.setPromax(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("NeedSkillNum");
							skilllvdata.setNeedskillnum(Integer.parseInt(request.getAttributeValue("Value")));

							request = stu.getChild("MaxValue");
							skilllvdata.setMaxvalue(Integer.parseInt(request.getAttributeValue("Value")));

							Bean.getSkilllvmap().put(skilllvdata.getId(), skilllvdata);
						}
					}
					break;

				case "MaterialPro":
					synchronized (list) {
						Bean.getMaterialpromap().clear();
						for(int i=0; i<list.size(); i++)
						{
							MaterialproData materialprodata=new MaterialproData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							materialprodata.setId(request.getAttributeValue("Value"));
							
							request = stu.getChild("PlayerLv");
							materialprodata.setPlayerlv(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("Material1Lv");
							materialprodata.setMaterial1lv(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("Material2Lv");
							materialprodata.setMaterial2lv(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("Material3Lv");
							materialprodata.setMaterial3lv(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("Material4Lv");
							materialprodata.setMaterial4lv(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("Material5Lv");
							materialprodata.setMaterial5lv(Integer.parseInt(request.getAttributeValue("Value")));
							
							Bean.getMaterialpromap().put(materialprodata.getId(), materialprodata);
						}
					}
					break;
					
				case "barrierreward":
					synchronized (list) {
						Bean.getBarrierrewardmap().clear();
						for(int i=0; i<list.size(); i++)
						{
							BarrierRewardData barrierRewardData=new BarrierRewardData();
							Element stu = (Element)list.get(i);

							Element request = stu.getChild("Id");
							barrierRewardData.setId(request.getAttributeValue("Value"));
							
							request = stu.getChild("startbarrierid");
							barrierRewardData.setStartbarrierid(request.getAttributeValue("Value"));
							
							request = stu.getChild("endbarrierid");
							barrierRewardData.setEndbarrierid(request.getAttributeValue("Value"));
							
							request = stu.getChild("needstar");
							barrierRewardData.setNeedstar(Integer.parseInt(request.getAttributeValue("Value")));
							
							request = stu.getChild("stone");
							barrierRewardData.setStone(request.getAttributeValue("Value"));
							
							Bean.getBarrierrewardmap().put(barrierRewardData.getId(), barrierRewardData);
						}
					}
					break;
					
				default:
					break;

				}
			}

			addTurnpro();

			Iterator<Entry<String, ChipData>> chipiterator=Bean.getChipmap().entrySet().iterator();
			synchronized (chipiterator) {
				Bean.getRafflechipone().clear();
				Bean.getRafflechipten().clear();
				while(chipiterator.hasNext())
				{
					Map.Entry<String,ChipData>  mapentry = chipiterator.next();
					String chipid=mapentry.getKey();
					ChipData chipdata=mapentry.getValue();
					String idalertcolor=chipdata.getIdAlertColor(EnumChipColor.Gold.toString());
					int loopnumone=chipdata.getProbabilityone();
					int loopnumten=chipdata.getProbabilityten();
					for(int i=0;i<loopnumone;i++)
					{
						if(chipid!=null)
						{
							Bean.getRafflechipone().add(chipid);
						}
					}

					for(int i=0;i<loopnumten;i++)
					{
						if(idalertcolor!=null)
						{
							Bean.getRafflechipten().add(idalertcolor);
						}
					}
				}
			}
			chipiterator=null;
			Iterator<Entry<String, JewelData>> jeweliterator=Bean.getJewelmap().entrySet().iterator();
			synchronized (jeweliterator) {
				Bean.getRafflejewelone().clear();
				Bean.getRafflejewelten().clear();
				while(jeweliterator.hasNext())
				{
					Map.Entry<String,JewelData>  mapentry = jeweliterator.next();
					String jewelid=mapentry.getKey();
					JewelData jeweldata=mapentry.getValue();
					String idalerttype=jeweldata.getIdAlterCharacter("Hexagon");
					int loopnumone=jeweldata.getProbabilityone();
					int loopnumten=jeweldata.getProbabilityten();
					for(int i=0;i<loopnumone;i++)
					{
						if(jewelid!=null)
						{
							Bean.getRafflejewelone().add(jewelid);
						}
					}
					for(int i=0;i<loopnumten;i++)
					{
						if(idalerttype!=null)
						{
							Bean.getRafflejewelten().add(idalerttype);
						}
					}
				}
			}
			jeweliterator=null;
			Iterator<Entry<String, FighterData>> fighteriterator=Bean.getFightermap().entrySet().iterator();
			synchronized (fighteriterator) {
				Bean.getRafflefighterone().clear();
				Bean.getRafflefighterten().clear();
				while(fighteriterator.hasNext())
				{
					Map.Entry<String,FighterData>  mapentry = fighteriterator.next();
					String fighterid=mapentry.getKey();
					FighterData fighterdata=mapentry.getValue();
					//int fighterstar=fighterdata.getDefaultstar();
					int loopnumone=fighterdata.getProbabilityone();
					int loopnumten=fighterdata.getProbabilityten();
					for(int i=0;i<loopnumone;i++)
					{
						if(fighterid!=null)
						{
							Bean.getRafflefighterone().add(fighterid);
						}
					}
					for(int i=0;i<loopnumten;i++)
					{
						if(fighterid!=null)
						{
							Bean.getRafflefighterten().add(fighterid);
						}
					}
				}
			}
			fighteriterator=null;
			Date nowdate=new Date();
			SimpleDateFormat dateFormat = Bean.getDateFormat();
			String time=dateFormat.format(nowdate.getTime());
			Bean.getTextArea().append("\r\n");
			Bean.getTextArea().append(time+"：模版数据读取完成");
			Bean.getTextArea().setCaretPosition(Bean.getTextArea().getText().length());
		}
		catch(Exception e)
		{
			Date nowdate=new Date();		
			SimpleDateFormat dateFormat = Bean.getDateFormat();
			String time=dateFormat.format(nowdate.getTime());
			Bean.getTextArea().append("\r\n");
			Bean.getTextArea().append(time+"："+this.getClass()+":"+e.getMessage());
			e.printStackTrace();
		}

	}
	private void addTurnpro(){
		int sum=0;
		Iterator<Entry<String, TurnData>> turniterator=Bean.getTurnmap().entrySet().iterator();
		synchronized (turniterator) {
			while(turniterator.hasNext())
			{
				Map.Entry<String,TurnData>  mapentry = turniterator.next();
				TurnData turndata=mapentry.getValue();
				int pro=turndata.getProbability();
				sum+=pro;
			}
			Bean.setTurnsum(sum);
		}
	}
}