package com.mangni.vegaplan.datatable;

import java.util.concurrent.Future;

public class Znx_GanggoalData {
	private String id;
	private long hp,maxhp;
	private Future<?> future;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getHp() {
		return hp;
	}
	public synchronized boolean addHp(long hp) {
		if(this.hp<=0){
			this.hp += hp;
			return true;
		}
		this.hp += hp;
		if(this.hp<=0){
			return false;
		}else{
			return true;
		}
	}
	public Future<?> getFuture() {
		return future;
	}
	public void setFuture(Future<?> future) {
		this.future = future;
	}
	public long getMaxhp() {
		return maxhp;
	}
	public void setMaxhp(long maxhp) {
		this.maxhp = maxhp;
	}
}
