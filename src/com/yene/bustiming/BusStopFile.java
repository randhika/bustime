package com.yene.bustiming;



public class BusStopFile  implements Comparable<BusStopFile>{ 

	String stopId,stopName,stopLat,stopLng,bus,toward;
	
	public BusStopFile(String id, String name , String lat ,String lng, String bus,String toward){
		this.stopId 	= id;
		this.stopName 	= name;
		this.stopLat 	= lat;
		this.stopLng 	= lng;
		this.bus		= bus;
		this.toward 	= toward;
	}

	@Override
	public String toString() {
		String busStopObject = "Name = "+stopName+",  StopId = "+stopId +", Lat = "+stopLat +", Lng = "+stopLng+ ",  Bus = "+bus;
		return busStopObject;
	}

	@Override
	public int hashCode() {		
		return stopId.hashCode() ^ stopName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!( obj instanceof BusStopFile)) return false;
		BusStopFile bus =(BusStopFile) obj;
		
		return bus.stopId.equals(this.stopId) && bus.bus.equals(this.bus);
	}

	public String getStopId() {
		return stopId;
	}

	public void setStopId(String stopIdc) {
		stopId = stopIdc;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopLat() {
		return stopLat;
	}

	public void setStopLat(String stopLat) {
		this.stopLat = stopLat;
	}

	public String getStopLng() {
		return stopLng;
	}

	public void setStopLng(String stopLng) {
		this.stopLng = stopLng;
	}

	@Override
	public int compareTo(BusStopFile o) {
		int countDown = Integer.parseInt(o.getStopId());
		int thisCount = Integer.parseInt(this.getStopId());
		return thisCount-countDown;
		
	}
}
