package com.mangni.vegaplan.datatable;

public class MaxBuyData {
	private String id;
	private int maxbuyvit=0,maxbuyenergy=0,maxbuygold=0,maxbuychallenge=0,maxexchangepatch=0;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public void setMaxbuyvit(int maxbuyvit)
	{
		this.maxbuyvit=maxbuyvit;
	}
	public int getMaxbuyvit()
	{
		return maxbuyvit;
	}
	
	public void setMaxbuyenergy(int maxbuyenergy)
	{
		this.maxbuyenergy=maxbuyenergy;
	}
	public int getMaxbuyenergy()
	{
		return maxbuyenergy;
	}
	
	public void setMaxbuygold(int maxbuygold)
	{
		this.maxbuygold=maxbuygold;
	}
	public int getMaxbuygold()
	{
		return maxbuygold;
	}
	
	public void setMaxbuychallenge(int maxbuychallenge)
	{
		this.maxbuychallenge=maxbuychallenge;
	}
	public int getMaxbuychallenge()
	{
		return maxbuychallenge;
	}
	
	public void setMaxexchangepatch(int maxexchangepatch)
	{
		this.maxexchangepatch=maxexchangepatch;
	}
	public int getMaxexchangepatch()
	{
		return maxexchangepatch;
	}
}
