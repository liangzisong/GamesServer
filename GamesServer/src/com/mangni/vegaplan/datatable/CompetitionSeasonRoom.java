package com.mangni.vegaplan.datatable;

import java.util.Calendar;

public class CompetitionSeasonRoom {
	private String roomid,player1id,player2id;
	private long time;
	public CompetitionSeasonRoom(String roomid,String player1id,String player2id){
		this.roomid=roomid;
		this.player1id=player1id;
		this.player2id=player2id;
		this.time=Calendar.getInstance().getTimeInMillis();
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public String getPlayer1id() {
		return player1id;
	}
	public void setPlayer1id(String player1id) {
		this.player1id = player1id;
	}
	public String getPlayer2id() {
		return player2id;
	}
	public void setPlayer2id(String player2id) {
		this.player2id = player2id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
}
