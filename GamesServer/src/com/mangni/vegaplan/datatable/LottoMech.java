package com.mangni.vegaplan.datatable;

import java.util.HashMap;
import java.util.List;

public class LottoMech {
	private String id,MechLv,Patch,SingleOdds,Odds1,Odds2,Odds3,Odds4,Odds5,Odds6,Odds7,Odds8,Odds9,Odds10,TemplateId;
	private List<String> TemplateIdList = null;
	private HashMap<Integer,List<String>> ratemap = null;

	public String getIdAlterOdd(String odd)
	{
		String[] oddarray = odd.split("#");
		String rate = oddarray[0];
		String oddtype = oddarray[1];

		switch (oddtype) {
			case "SingleOdds":
				if(this.SingleOdds.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds1":
				if(this.Odds1.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds2":
				if(this.Odds2.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds3":
				if(this.Odds3.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds4":
				if(this.Odds4.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds5":
				if(this.Odds5.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds6":
				if(this.Odds6.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds7":
				if(this.Odds7.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds8":
				if(this.Odds8.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds9":
				if(this.Odds9.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			case "Odds10":
				if(this.Odds10.equals(rate))
				{
					return id;
				}
				else
				{
					return null;
				}
			default:
				return null;
			}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMechLv() {
		return MechLv;
	}

	public void setMechLv(String mechLv) {
		MechLv = mechLv;
	}

	public String getPatch() {
		return Patch;
	}

	public void setPatch(String patch) {
		Patch = patch;
	}

	public String getSingleOdds() {
		return SingleOdds;
	}

	public void setSingleOdds(String singleOdds) {
		SingleOdds = singleOdds;
	}

	public String getOdds1() {
		return Odds1;
	}

	public void setOdds1(String odds1) {
		Odds1 = odds1;
	}

	public String getOdds2() {
		return Odds2;
	}

	public void setOdds2(String odds2) {
		Odds2 = odds2;
	}

	public String getOdds3() {
		return Odds3;
	}

	public void setOdds3(String odds3) {
		Odds3 = odds3;
	}

	public String getOdds4() {
		return Odds4;
	}

	public void setOdds4(String odds4) {
		Odds4 = odds4;
	}

	public String getOdds5() {
		return Odds5;
	}

	public void setOdds5(String odds5) {
		Odds5 = odds5;
	}

	public String getOdds6() {
		return Odds6;
	}

	public void setOdds6(String odds6) {
		Odds6 = odds6;
	}

	public String getOdds7() {
		return Odds7;
	}

	public void setOdds7(String odds7) {
		Odds7 = odds7;
	}

	public String getOdds8() {
		return Odds8;
	}

	public void setOdds8(String odds8) {
		Odds8 = odds8;
	}

	public String getOdds9() {
		return Odds9;
	}

	public void setOdds9(String odds9) {
		Odds9 = odds9;
	}

	public String getOdds10() {
		return Odds10;
	}

	public void setOdds10(String odds10) {
		Odds10 = odds10;
	}

	public String getTemplateId() {
		return TemplateId;
	}

	public void setTemplateId(String templateId) {
		TemplateId = templateId;
	}

	public List<String> getTemplateIdList() {
		return TemplateIdList;
	}

	public void setTemplateIdList(List<String> templateIdList) {
		TemplateIdList = templateIdList;
	}

	public HashMap<Integer, List<String>> getRatemap() {
		return ratemap;
	}

	public void setRatemap(HashMap<Integer, List<String>> ratemap) {
		this.ratemap = ratemap;
	}
	
	
}
