package com.mangni.vegaplan.datatable;

public class BarrierRewardData {
	private String id,startbarrierid,endbarrierid,stone;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartbarrierid() {
		return startbarrierid;
	}
	public void setStartbarrierid(String startbarrierid) {
		this.startbarrierid = startbarrierid;
	}
	public String getEndbarrierid() {
		return endbarrierid;
	}
	public void setEndbarrierid(String endbarrierid) {
		this.endbarrierid = endbarrierid;
	}
	public String getStone() {
		return stone;
	}
	public void setStone(String stone) {
		this.stone = stone;
	}
	public int getNeedstar() {
		return needstar;
	}
	public void setNeedstar(int needstar) {
		this.needstar = needstar;
	}
	private int needstar;
}
