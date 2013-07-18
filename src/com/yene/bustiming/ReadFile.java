package com.yene.bustiming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;



 
public class ReadFile  {	
	ArrayList <BusStopFile> busCollection = new ArrayList<BusStopFile>();
	ArrayList <BusStopFile> nearby = new ArrayList<BusStopFile>();
	static int count =0;
	ReadFile() {
	      System.out.println("-------------------: ");
	      System.out.println("Object BufferedReaderExample: ");
	   }
	
	public ArrayList<BusStopFile> eachStop(){
		
		return nearby;
	}
	

	
	public double distance(double userlat, double userlng, double stopLat, double stopLng){
		double R = 6371;
		double lat1,lat2,lng1,lng2;
		
		lat1  = userlat;
		lng1  = userlng;
		lat2  = stopLat;
		lng2  = stopLng;
		
		double dLat =Math.toRadians(lat2-lat1);
		double dLan =Math.toRadians(lng2-lng1);
		double lat3 = Math.toRadians(lat1);
		double lat4 = Math.toRadians(lat2);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLan/2) * Math.sin(dLan/2) * Math.cos(lat3) * Math.cos(lat4); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = R*c;
		return d;
	}
	
	public  boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	public  void readName(InputStream  in){
		BufferedReader br;
		System.out.println("-------------------: ");
	    System.out.println("Object InputStream: ");
		
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String Id,stopname,lat,lng,bus,toward;
				String []name = sCurrentLine.split(";");
							
					//printList(name);
					if(name.length == 6 && isInteger(name[2])){
						//printList(name);	
						stopname 	= name[0];
						toward 		= name[1];
						Id 			= name[2];
						lat 		= name[3];
						lng 		= name[4];
						bus 		= name[5];						
						busCollection.add(new BusStopFile( Id, stopname,lat,lng,bus,toward ));
				}
				count++;
			}
			 System.out.println("Total list "+ count);	
			 System.out.println("Total bus stop object "+ busCollection.size());	
				
		} catch (IOException e) {
			e.printStackTrace();
		} 
		//findBusStop((float) 51.51709899999999,(float) -0.146084);
	}
	
	public void printList( String []name ){
		
		System.out.print("Lenght: "+ name.length+
				"\n  0=: "+ name[0]+
				"\n  1: "+ name[1]+
				"\n  2: "+ name[2]+
				"\n  3: "+ name[3]+
				"\n  4: "+ name[4]+						
				"\n  6: "+ name[5]+"\n");
		
	}
	
	public ArrayList<BusStopFile> findBusStop(double nearLat, double nearLng){
		System.out.println("-------------------: ");
		System.out.println("lat = "+nearLat  +" lng = " +nearLng );
		int counts = 0;
		
		for(BusStopFile item :busCollection ){
			
			double lat ,lng;
			lat 		= Double.parseDouble(""+item.stopLat);
			lng 		= Double.parseDouble(""+item.stopLng);
			double d 	= distance(nearLat,nearLng,lat,lng);
			//System.out.println("Distnace ="+d );
			if(d < 0.5){				//System.out.print("\n\n["+item.toString()+"]");
				nearby.add(item);
				counts++;
				}
			}
		System.out.print("\nNumber of near stop: "+counts);
		return nearby;	
	}
	
}