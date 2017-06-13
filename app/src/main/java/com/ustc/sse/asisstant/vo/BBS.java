package com.ustc.sse.asisstant.vo;

import java.sql.Timestamp;

public class BBS {
	private int id;
	private String author;
	private String title ;
	private int lightBBS;
	private int reponseBBS;
	private Timestamp time;
	
	
	public int getId(){
		return this.id;
	}
	public void setID(int id){
		this.id = id;
	}
	public String getAuthor(){
		return this.author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public Timestamp getTime(){
		return this.time;
	}
	public void setTime(Timestamp time){
		this.time = time;
	}
	public int getLightBBS(){
		return this.lightBBS;
	}
	public void setLightBBS(int lightBBS){
		this.lightBBS = lightBBS;
	}
	public int getReponseBBS(){
		return reponseBBS;
	}
	public void setReponseBBS(int reponseBBS){
		this.reponseBBS = reponseBBS;
	}
}
