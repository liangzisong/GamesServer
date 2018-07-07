package com.mangni.vegaplan.datatable;

public enum EnumGoodsTypes {
	GOLD(1),
	STONE(2),
	VIT(3),
	ENERGY(4),
	PLAYEREXP(5),
	JEWEL(6),
	CHIP(7),
	GOODS(8),
	FIGHTER(9),
	HOLIDAYGOLD(10),
	SKIN(11),
	BUYSHOPNUM(12),
	DAILYTASK(13),
	HOLIDAYTASK(14),
	EXPPOOL(15),
	FIGHTEREXP(16),
	ACTPOWER(17),
	ENDURANCE(18),
	CONTRIBUTION(19);
	private int value = 0;

	private EnumGoodsTypes(int value) {    //    必须是private的，否则编译错误
		this.value = value;
	}

	public int value() {
		return this.value;
	}
	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

	public static EnumGoodsTypes valueOf(int value) {    //    手写的从int到enum的转换函数
		switch (value) {
		case 1:
			return GOLD;
		case 2:
			return STONE;
		case 3:
			return VIT;
		case 4:
			return ENERGY;
		case 5:
			return PLAYEREXP;
		case 6:
			return JEWEL;
		case 7:
			return CHIP;
		case 8:
			return GOODS;
		case 9:
			return FIGHTER;
		case 10:
			return HOLIDAYGOLD;
		case 11:
			return SKIN;
		case 12:
			return BUYSHOPNUM;
		case 13:
			return DAILYTASK;
		case 14:
			return HOLIDAYTASK;
		case 15:
			return EXPPOOL;
		case 16:
			return FIGHTEREXP;
		case 17:
			return ACTPOWER;
		case 18:
			return ENDURANCE;
		case 19:
			return CONTRIBUTION;
		default:
			return null;
		}
	}

	public static boolean isCanValue(String value){
		try{
			EnumGoodsTypes.valueOf(value);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
