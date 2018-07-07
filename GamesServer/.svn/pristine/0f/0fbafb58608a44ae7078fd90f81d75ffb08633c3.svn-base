package com.mangni.vegaplan.datatable;
public enum EnumChipColor {           
     Green(0),                  //绿
     Blue(1),                   //蓝
     Purple(2),                 //紫
     Gold(3),                   //金
     Red(4);					//红
    private int value = 0;

    private EnumChipColor(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }

    public static EnumChipColor valueOf(int value) {    //    手写的从int到enum的转换函数
        switch (value) {
        case 0:
            return Green;
        case 1:
            return Blue;
        case 2:
            return Purple;
        case 3:
            return Gold;
        case 4:
            return Red;
        default:
            return null;
        }
    }
    
    public static int getChipEnumColor(String color)
    {
    	int icolor=4;
		switch(color)
		{
			case "Green":
				icolor=EnumChipColor.Green.value();
				break;
			case "Blue":
				icolor=EnumChipColor.Blue.value();
				break;
			case "Purple":
				icolor=EnumChipColor.Purple.value();
				break;
			case "Gold":
				icolor=EnumChipColor.Gold.value();
				break;
			case "Red":
				icolor=4;
				break;
			default:
				icolor=4;
				break;
		}
		return icolor;
	}
    

    public int value() {
        return this.value;
    }
    
    public String getStrengthenGoods(String color)
    {
    	String goodsid="0";
    	switch(color)
    	{
		case "Purple":
			goodsid="4";
			break;
		case "Gold":
			goodsid="3";
			break;
		case "Red":
			goodsid="2";
			break;
		default:
			break;
    	}
    	return goodsid;
    }
    
    public static String getStrengthenGoods(int color)
    {
    	String goodsid="0";
    	switch(color)
    	{
		case 2:
			goodsid="4";
			break;
		case 3:
			goodsid="3";
			break;
		case 4:
			goodsid="2";
			break;
		default:
			break;
    	}
    	return goodsid;
    }
    
    public static int getChipComposePro(int color)
    {
    	switch (color) {
        case 0:
            return 100;
        case 1:
            return 60;
        case 2:
            return 50;
        case 3:
            return 40;
        case 4:
            return 0;
        default:
            return 0;
        }
    }
}
