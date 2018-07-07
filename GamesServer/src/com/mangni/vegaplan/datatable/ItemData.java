package com.mangni.vegaplan.datatable;

public class ItemData {
	private String id,itemtype;
	private int lv,addnum;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	public String getIdbyTypeAndLv(String itemtype,int lv){
		if(itemtype.equals(this.getItemtype())&&lv==this.lv){
			return this.id;
		}else{
			return null;
		}
	}
	public int getAddnum() {
		return addnum;
	}
	public void setAddnum(int addnum) {
		this.addnum = addnum;
	}
}
