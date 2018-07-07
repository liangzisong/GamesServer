package com.mangni.vegaplan.datatable;

public class FighterData {
	private String id,positionid,ranceid,skillid1,skillid2,skillid3;
	private int probabilityone=0,probabilityten=0,fighterpatch=0,defaultstar=0;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public String getSkillid1()
	{
		return skillid1;
	}
	public void setSkillid1(String skillid1)
	{
		this.skillid1=skillid1;
	}
	
	public String getSkillid2()
	{
		return skillid2;
	}
	public void setSkillid2(String skillid2)
	{
		this.skillid2=skillid2;
	}
	
	public String getSkillid3()
	{
		return skillid3;
	}
	public void setSkillid3(String skillid3)
	{
		this.skillid3=skillid3;
	}
	
	public int getProbabilityone() 
	{
		return probabilityone;
	}
	public void setProbabilityone(int probabilityone) 
	{
		this.probabilityone = probabilityone;
	}
	
	public int getProbabilityten() 
	{
		return probabilityten;
	}
	public void setProbabilityten(int probabilityten) 
	{
		this.probabilityten = probabilityten;
	}
	
	public int getFighterpatch()
	{
		return fighterpatch;
	}
	public void setFighterpatch(int fighterpatch) 
	{
		this.fighterpatch = fighterpatch;
	}
	
	public int getDefaultstar()
	{
		return defaultstar;
	}
	public void setDefaultstar(int defaultstar) 
	{
		this.defaultstar = defaultstar;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
	public String getRanceid() {
		return ranceid;
	}
	public void setRanceid(String ranceid) {
		this.ranceid = ranceid;
	}
	
}