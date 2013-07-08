package com.yene.bustiming;

public class MyBusStopObject {
	
	String id,direction,toward,time;
	public MyBusStopObject (String id, String direction, String toward ){
		
		this.id=id;
		this.direction = direction;
		this.toward= toward;
		
		
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
	public String getToward() {
		return toward;
	}
	public void setToward(String toward) {
		this.toward = toward;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

}
