package com.mangni.vegaplan.datatable;


public class StoreData {
	private String id,itemid,itemtype,goodsnum,needgold,startdate,enddate;
	private int shoptype,numlimit=0,minlv,maxlv;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getGoodsnum() {
		return goodsnum;
	}
	public void setGoodsnum(String goodsnum) {
		this.goodsnum = goodsnum;
	}
	public int getShoptype() {
		return shoptype;
	}
	public void setShoptype(int shoptype) {
		this.shoptype = shoptype;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public int getNumlimit() {
		return numlimit;
	}
	public void setNumlimit(int numlimit) {
		this.numlimit = numlimit;
	}
	public String getNeedgold() {
		return needgold;
	}
	public void setNeedgold(String needgold) {
		this.needgold = needgold;
	}
	public int getMinlv() {
		return minlv;
	}
	public void setMinlv(int minlv) {
		this.minlv = minlv;
	}
	public int getMaxlv() {
		return maxlv;
	}
	public void setMaxlv(int maxlv) {
		this.maxlv = maxlv;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	
}
