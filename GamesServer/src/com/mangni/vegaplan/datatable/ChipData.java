package com.mangni.vegaplan.datatable;

public class ChipData {
	private String id,chiptype,chipcolor;
	private int probabilityone=0,probabilityten=0,chipprice=0;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public String getChiptype()
	{
		return chiptype;
	}
	public void setChiptype(String chiptype)
	{
		this.chiptype=chiptype;
	}
	
	public String getChipcolor()
	{
		return chipcolor;
	}
	public void setChipcolor(String chipcolor)
	{
		this.chipcolor=chipcolor;
	}
	
	public int getChipprice()
	{
		return chipprice;
	}
	public void setChipprice(int chipprice)
	{
		this.chipprice=chipprice;
	}
	
	public int getProbabilityone()
	{
		return probabilityone;
	}
	public void setProbabilityone(int probabilityone)
	{
		this.probabilityone=probabilityone;
	}
	
	public int getProbabilityten()
	{
		return probabilityten;
	}
	public void setProbabilityten(int probabilityten)
	{
		this.probabilityten=probabilityten;
	}
	
	public String getIdAlertColor(String chipcolor)
	{
		if(this.chipcolor.equals(chipcolor))
		{
			return id;
		}else
		{
			return null;
		}
	}
	
	public String getIdAlertChiptype(String chiptype)
	{
		if(this.chiptype.equals(chiptype))
		{
			return id;
		}else
		{
			return null;
		}
	}
}
