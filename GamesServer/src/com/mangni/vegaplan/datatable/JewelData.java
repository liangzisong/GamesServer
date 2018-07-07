package com.mangni.vegaplan.datatable;

public class JewelData {
	private String id,jewelcolor,jewelcharacter,jewelatt1;
	private int probabilityone=0,probabilityten=0,jewelprice=0;
	public String getIdAlterCharacter(String jewelcharacter)
	{
		if(this.jewelcharacter.equals(jewelcharacter))
		{
			return id;
		}
		else
		{
			return null;
		}
	}
	public String getIdAlterJewelatt1(String jewelatt1)
	{
		if(this.jewelatt1.equals(jewelatt1))
		{
			return id;
		}
		else
		{
			return null;
		}
	}
	public String getIdAlterJewelcolor(String jewelcolor)
	{
		if(this.jewelcolor.equals(jewelcolor))
		{
			return id;
		}
		else
		{
			return null;
		}
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public String getJewelcolor()
	{
		return jewelcolor;
	}
	public void setJewelcolor(String jewelcolor)
	{
		this.jewelcolor=jewelcolor;
	}
	
	public String getJewelcharacter()
	{
		return jewelcharacter;
	}
	public void setJewelcharacter(String jewelcharacter)
	{
		this.jewelcharacter=jewelcharacter;
	}
	
	public int getJewelprice()
	{
		return jewelprice;
	}
	public void setJewelprice(int jewelprice)
	{
		this.jewelprice=jewelprice;
	}
	
	public String getJewelatt1()
	{
		return jewelatt1;
	}
	public void setJewelatt1(String jewelatt1)
	{
		this.jewelatt1=jewelatt1;
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
}
