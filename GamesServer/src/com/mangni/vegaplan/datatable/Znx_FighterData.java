package com.mangni.vegaplan.datatable;

import java.util.List;

public class Znx_FighterData {
	private String id,playerid,fighterid;
	private int fighterlv=0,fighterstar=0,tacticslv=0,tactics1=0,tactics2=0,tactics3=0,tactics4=0,equipallstar=0,equip1lv=0,equip1star=0,
			equip2lv=0,equip2star=0,equip3lv=0,equip3star=0,equip4lv=0,equip4star=0,fighterskill1lv,fighterskill1exp,fighterskill2lv,
			fighterskill2exp,fighterskill3lv,fighterskill3exp,fighterskill4lv,fighterskill4exp;
	private long fighterexp=0;
	private List<List<Integer>> tacticslist;
	
	public int getFighterskill1lv() {
		return fighterskill1lv;
	}
	public void setFighterskill1lv(int fighterskill1lv) {
		this.fighterskill1lv = fighterskill1lv;
	}
	public int getFighterskill1exp() {
		return fighterskill1exp;
	}
	public void setFighterskill1exp(int fighterskill1exp) {
		this.fighterskill1exp = fighterskill1exp;
	}
	public int getFighterskill2lv() {
		return fighterskill2lv;
	}
	public void setFighterskill2lv(int fighterskill2lv) {
		this.fighterskill2lv = fighterskill2lv;
	}
	public int getFighterskill2exp() {
		return fighterskill2exp;
	}
	public void setFighterskill2exp(int fighterskill2exp) {
		this.fighterskill2exp = fighterskill2exp;
	}
	public int getFighterskill3lv() {
		return fighterskill3lv;
	}
	public void setFighterskill3lv(int fighterskill3lv) {
		this.fighterskill3lv = fighterskill3lv;
	}
	public int getFighterskill3exp() {
		return fighterskill3exp;
	}
	public void setFighterskill3exp(int fighterskill3exp) {
		this.fighterskill3exp = fighterskill3exp;
	}
	public int getFighterskill4lv() {
		return fighterskill4lv;
	}
	public void setFighterskill4lv(int fighterskill4lv) {
		this.fighterskill4lv = fighterskill4lv;
	}
	public int getFighterskill4exp() {
		return fighterskill4exp;
	}
	public void setFighterskill4exp(int fighterskill4exp) {
		this.fighterskill4exp = fighterskill4exp;
	}
	public int getEquipallstar() {
		return equipallstar;
	}
	public void setEquipallstar(int equipallstar) {
		this.equipallstar = equipallstar;
	}
	public int getEquip1lv() {
		return equip1lv;
	}
	public void setEquip1lv(int equip1lv) {
		this.equip1lv = equip1lv;
	}
	public int getEquip1star() {
		return equip1star;
	}
	public void setEquip1star(int equip1star) {
		this.equip1star = equip1star;
	}
	public int getEquip2lv() {
		return equip2lv;
	}
	public void setEquip2lv(int equip2lv) {
		this.equip2lv = equip2lv;
	}
	public int getEquip2star() {
		return equip2star;
	}
	public void setEquip2star(int equip2star) {
		this.equip2star = equip2star;
	}
	public int getEquip3lv() {
		return equip3lv;
	}
	public void setEquip3lv(int equip3lv) {
		this.equip3lv = equip3lv;
	}
	public int getEquip3star() {
		return equip3star;
	}
	public void setEquip3star(int equip3star) {
		this.equip3star = equip3star;
	}
	public int getEquip4lv() {
		return equip4lv;
	}
	public void setEquip4lv(int equip4lv) {
		this.equip4lv = equip4lv;
	}
	public int getEquip4star() {
		return equip4star;
	}
	public void setEquip4star(int equip4star) {
		this.equip4star = equip4star;
	}
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id=id;
	}
	
	public String getPlayerid()
	{
		return playerid;
	}
	public void setPlayerid(String playerid)
	{
		this.playerid=playerid;
	}
	
	public String getFighterid() {
		return fighterid;
	}
	public void setFighterid(String fighterid) {
		this.fighterid = fighterid;
	}
	
	public long getFighterexp()
	{
		return fighterexp;
	}
	public void setFighterexp(long fighterexp)
	{
		this.fighterexp=fighterexp;
	}
	
	public int getFighterlv()
	{
		return fighterlv;
	}
	public void lvlupFighterlv(int fighterlvup)
	{
		this.fighterlv+=fighterlvup;
	}
	public void setFighterlv(int fighterlvup)
	{
		this.fighterlv=fighterlvup;
	}
	public int getFighterstar()
	{
		return fighterstar;
	}
	public void LvlupFighterstar(int fighterstar)
	{
		this.fighterstar+=fighterstar;
	}
	
	public int getTacticslv() {
		return tacticslv;
	}
	public void setTacticslv(int tacticslv) {
		this.tacticslv = tacticslv;
	}
	public int getTactics1() {
		return tactics1;
	}
	public void setTactics1(int tactics1) {
		this.tactics1 = tactics1;
	}
	public int getTactics2() {
		return tactics2;
	}
	public void setTactics2(int tactics2) {
		this.tactics2 = tactics2;
	}
	public int getTactics3() {
		return tactics3;
	}
	public void setTactics3(int tactics3) {
		this.tactics3 = tactics3;
	}
	public int getTactics4() {
		return tactics4;
	}
	public void setTactics4(int tactics4) {
		this.tactics4 = tactics4;
	}
	public String getIdAlertplayerid(String playerid)
	{
		if(this.playerid.equals(playerid))
		{
			return this.id;
		}
		else
		{
			return null;
		}
	}
	public List<List<Integer>> getTacticslist() {
		return tacticslist;
	}
	public void setTacticslist(List<List<Integer>> tacticslist) {
		this.tacticslist = tacticslist;
	}
	
}