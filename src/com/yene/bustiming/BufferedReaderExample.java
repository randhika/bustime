package com.yene.bustiming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

 
public class BufferedReaderExample  {	
	ArrayList <BusStopFile> busCollection = new ArrayList<BusStopFile>();
	ArrayList <BusStopFile> badBusCollection = new ArrayList<BusStopFile>();
	ArrayList <BusStopFile> eachBusStop = new ArrayList<BusStopFile>();
	static int count =0;
	
	BufferedReaderExample() {
	      System.out.println("-------------------: ");
	      System.out.println("Object Start: ");
	   }
	
	public ArrayList<BusStopFile> eachStop(){
		
		return eachBusStop;
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
	public  void readName(String fileName, InputStream  in){
		BufferedReader br;
		
		
		try {
			
			br = new BufferedReader(new InputStreamReader(in));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String Id,stopname,lat,lng,bus;
				String [] location;
				String []name = sCurrentLine.split("\"");
				if(name.length>5){
				//System.out.print(name[5]+" : "+name[3]+" : "+name[1]+"\n");
				}
				if(name.length<8 && isInteger(name[1]) && name[4].length()>4){
					location = name[4].split(",");
					Id= name[1];
					stopname = name[3];
					lat = location[1];
					lng = location[2];
					bus = name[5];
					busCollection.add(new BusStopFile( Id, stopname,lat,lng,bus ));
				}else{
					Id= name[2].replace("\"", "");
					stopname = name[1];
					lat = name[3];
					lng = name[4].replace("]", "");
					bus = "";
					
					badBusCollection.add(new BusStopFile( Id, stopname,lat,lng,bus ));
				}
				count++;
			}
			 System.out.println("Total list "+ count);	
			 System.out.println("Total bus stop object "+ busCollection.size());	
			 System.out.println("Total bus stop object "+ busCollection.get(10000).toString());	
			 System.out.println("Total bus stop object "+ busCollection.get(10001).toString());		
		} catch (IOException e) {
			e.printStackTrace();
		} 
		findBusStop((float) 51.51709899999999,(float) -0.146084);
	}
	
	 public ArrayList <BusStopFile> removeDuplicate(ArrayList <BusStopFile> arlList){
		 
		System.out.print("\nNumber of near stop before: "+arlList.size());	
		Set<BusStopFile> h = new HashSet<BusStopFile>(arlList);
		arlList.clear();
		System.out.print("\nNumber of near stop clean: "+arlList.size());	
		System.out.print("\nNumber of near in H: "+h.size());	
		arlList.addAll(h);
		System.out.print("\nNumber of near stop After: "+arlList.size());	
	   return arlList;
	  }
	 
	 public void groupBusNumber(ArrayList <BusStopFile> busStop){
		 String busNumber ="";
		 for (int i= 0; i< busStop.size(); i++){
			 int index = i;
			
			 index++;
			 if(index < busStop.size()){
			 	if(busStop.get(i).stopId.equals(busStop.get(index).stopId)) {
			 		busNumber += "  "+busStop.get(i).bus;
			 		
			 		}else if(busNumber.length()<1){
			 			eachBusStop.add(new BusStopFile( busStop.get(index).stopId, busStop.get(index).stopName,busStop.get(index).stopLat,busStop.get(index).stopLng, busStop.get(i).bus ));
			 			busNumber = "";	
			 		}else if(busNumber.length()>1){
			 			eachBusStop.add(new BusStopFile( busStop.get(index).stopId, busStop.get(index).stopName,busStop.get(index).stopLat,busStop.get(index).stopLng, busNumber));
			 			busNumber = "";	
			 		}
			 
		 	}
		 }
			
		 }
	 
	
	public void findBusStop(double nearLat, double nearLng){
		System.out.println("-------------------: ");
		int counts = 0;
		
		removeDuplicate(busCollection);
		Collections.sort(busCollection);
		groupBusNumber(busCollection);
		removeDuplicate(eachBusStop);
		Collections.sort(eachBusStop);
		for(BusStopFile item :eachBusStop ){
			//System.out.print("\n\n["+item.toString()+"]");
			double lat ,lng;
			lat = Double.parseDouble(""+item.stopLat);
			lng = Double.parseDouble(""+item.stopLng);
			double d = distance(nearLat,nearLng,lat,lng);
			
			if(d < 0.5){
				//System.out.print("\n\n["+item.toString()+"]");
				counts++;
				}
			}
		System.out.print("\nNumber of near stop: "+counts);	
	}
	
}