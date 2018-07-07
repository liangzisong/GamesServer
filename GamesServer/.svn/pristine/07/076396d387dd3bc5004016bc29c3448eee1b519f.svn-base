package com.mangni.vegaplan.datatable;

public enum EnumComplainsType {
	suggest(1),			//建议
	complain(2),		//投诉
	report(3),			//举报
	bug(4);				//BUG
	private int value = 0;

	private EnumComplainsType(int value) {    //    必须是private的，否则编译错误
		this.value = value;
	}

	public static EnumComplainsType valueOf(int value) {    //    手写的从int到enum的转换函数
		switch (value) {
		case 1:
			return suggest;
		case 2:
			return complain;
		case 3:
			return report;
		case 4:
			return bug;
		default:
			return null;
		}
	}
	public int value() {
        return this.value;
    }

}
