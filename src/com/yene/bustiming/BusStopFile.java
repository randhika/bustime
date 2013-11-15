package com.yene.bustiming;

import android.os.Parcel;
import android.os.Parcelable;



public class BusStopFile  implements Comparable<BusStopFile> , Parcelable { 

	public String stopId;
	public String stopName;
	String stopLat;
	String stopLng;
	public String bus;
	public String toward;
	private int mData;
	
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
		String busStopObject = "Name = "+stopName+",  StopId = "+stopId +", Lat = "+stopLat +", Lng = "+stopLng+ ",  Bus = "+bus+ ", Toward = "+toward;
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
	
	 public static final Parcelable.Creator<BusStopFile> CREATOR    = new Parcelable.Creator<BusStopFile>() {
		  public BusStopFile createFromParcel(Parcel in) {
			  return new BusStopFile(in);
		  }

		  public BusStopFile[] newArray(int size) {
			  	return new BusStopFile[size];
		  }
	 };
	 
	 private BusStopFile (Parcel in){
		 
		 stopId = in.readString();
		 
	 }
	  
	}
