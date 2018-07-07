package com.mangni.vegaplan.datatable;

public class DailytaskData {
	private String id,name,desc;
	private int dot=0,taskcount=0,rewardstone=0,rewardgold=0,rewardexp=0,fighterexp,type=0;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public int getDot()
	{
		return dot;
	}
	public void setDot(int dot)
	{
		this.dot=dot;
	}
	
	public int getTaskcount()
	{
		return taskcount;
	}
	public void setTaskcount(int taskcount)
	{
		this.taskcount=taskcount;
	}
	
	public int getRewardstone()
	{
		return rewardstone;
	}
	public void setRewardstone(int rewardstone)
	{
		this.rewardstone=rewardstone;
	}
	
	public int getRewardgold()
	{
		return rewardgold;
	}
	public void setRewardgold(int rewardgold)
	{
		this.rewardgold=rewardgold;
	}
	
	public int getRewardexp()
	{
		return rewardexp;
	}
	public void setRewardexp(int rewardexp)
	{
		this.rewardexp=rewardexp;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getFighterexp() {
		return fighterexp;
	}
	public void setFighterexp(int fighterexp) {
		this.fighterexp = fighterexp;
	}
}
