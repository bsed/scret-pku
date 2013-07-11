package model;

import java.sql.Date;

public class Node {
	private int id;
	private String os;
	private int pv;
	private int vv;
	private String date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getVv() {
		return vv;
	}
	public void setVv(int vv) {
		this.vv = vv;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
