package com.yene.bustiming;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBusStopObject {
	
	String id,direction,time;
	public MyBusStopObject (String id, String direction ){
		
		this.id=id;
		this.direction = direction;
		
		this.time = getTime();
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String getTime() {
		DateFormat  dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 	//get current date time with Date()
	 	Date date = new Date();
		return  dateFormat.format(date);
	}
	public void setTime(String time) {
		this.time = time;
	}

}
