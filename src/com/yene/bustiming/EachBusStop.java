package com.yene.bustiming;

import android.os.Parcel;
import android.os.Parcelable;



public class EachBusStop  implements Comparable<EachBusStop> , Parcelable { 

	public String stopId;
	public String stopName;
	String stopLat;
	String stopLng;
	public String bus;
	public String toward;
	//private int mData;
	private String distance;
	
	public EachBusStop(String id, String name , String lat ,String lng, String bus,String toward){
		this.stopId 	= id;
		this.stopName 	= name;
		this.stopLat 	= lat;
		this.stopLng 	= lng;
		this.bus		= bus;
		this.toward 	= toward;
	}



	public String getDistance() {
		return distance;
	}



	public void setDistance(String distance) {
		this.distance = distance;
	}



	@Override
	public String toString() {
		String busStopObject = "Name = "+stopName+",  StopId = "+stopId +", Lat = "+stopLat +", Lng = "+stopLng+ ",  Bus = "+bus+ ", Toward = "+toward;
		return busStopObject;
	}

	@Override
	public int hashCode() {		
		return stopId.hashCode() ^ stopName.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(!( obj instanceof EachBusStop)) return false;
		EachBusStop bus =(EachBusStop) obj;
		
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
	public int compareTo(EachBusStop o) {
		int countDown = Integer.parseInt(o.getStopId());
		int thisCount = Integer.parseInt(this.getStopId());
		return thisCount-countDown;
		
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(stopId);
		dest.writeString(stopName);
		dest.writeString(stopLat);
		dest.writeString(stopLng);
		dest.writeString(bus);
		dest.writeString(toward);
	}
	
	 public static final Parcelable.Creator<EachBusStop> CREATOR    = new Parcelable.Creator<EachBusStop>() {
		  public EachBusStop createFromParcel(Parcel in) {
			  return new EachBusStop(in);
		  }

		  public EachBusStop[] newArray(int size) {
			  	return new EachBusStop[size];
		  }
	 };
	 
	 private EachBusStop (Parcel in){
		 
		 stopId = in.readString();
		 
	 }
	  
	}
