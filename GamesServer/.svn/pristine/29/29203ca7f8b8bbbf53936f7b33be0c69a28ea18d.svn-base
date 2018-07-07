package com.mangni.vegaplan.datatable;

public enum EnumJewelCharacter {
	Triangle(1),               //三角形
    Quadrilateral(2),          //四边形
    Pentagon(3),               //五边形
    Hexagon(4),                //六边形
    Circle(5);                 //圆
    
    private int value = 0;

    private EnumJewelCharacter(int value) {    //    必须是private的，否则编译错误
        this.value = value;
    }

    public static EnumJewelCharacter valueOf(int value) {    //    手写的从int到enum的转换函数
        switch (value) {
        case 1:
            return Triangle;
		case 2:
            return Quadrilateral;
        case 3:
            return Pentagon;
        case 4:
            return Hexagon;
        case 5:
            return Circle;
        default:
            return null;
        }
    }

    public static int getJewelEnumCharacter(String character)
	{
		int icharacter=0;
		switch(character)
		{
			case "Triangle":
				icharacter=EnumJewelCharacter.Triangle.value();
				break;
			case "Quadrilateral":
				icharacter=EnumJewelCharacter.Quadrilateral.value();
				break;
			case "Pentagon":
				icharacter=EnumJewelCharacter.Pentagon.value();
				break;
			case "Hexagon":
				icharacter=EnumJewelCharacter.Hexagon.value();
				break;
			case "Circle":
				icharacter=EnumJewelCharacter.Circle.value();
			default:
				icharacter=0;
		}
		return icharacter;
	}
    
    public static int getJewelComposePro(String value)
    {
    	switch (value) {
        case "Triangle":
            return 100;
        case "Quadrilateral":
            return 60;
        case "Pentagon":
            return 50;
        case "Hexagon":
            return 40;
        case "Circle":
            return 0;
        default:
            return 0;
        }
    }
    
    public int value() {
        return this.value;
    }
}
