package com.yene.bustiming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;
 
public class JSONParser {
 
    private static final String TAG = "JSON PARSER";
	static InputStream is = null;
    static JSONObject jObj = null;
    ArrayList<BusStopObject> collectionBusStop = new  ArrayList<BusStopObject>();
    // constructor
    public JSONParser() {
    	 Log.d(TAG,"Get data from web has strated");
    }
 
    public ArrayList<BusStopObject> getJSONFromUrl(String url) {
 
        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();          
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            
            String line,urlForBusNumber, busNumber = null;
            
            while ((line = reader.readLine()) != null) {
            	BusStopObject busStopObject = new BusStopObject();
	            String []split = line.split(",");
	            if(split.length>3){
		           String busStopID = split[2];
		            //Log.d(TAG,line+": first request 1");
		            //Log.d(TAG,busStopID);
		           
		            String stopID = busStopID.replace("\"", "");
		            
		            urlForBusNumber = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?StopCode1="+stopID+"&ReturnList=lineId";
		            Log.d(TAG,stopID);  
		            busNumber = getListOfBusStop(urlForBusNumber);
		            String busStop,toward;
		            busStop = split[3].replace("\"", "");
		            toward = split[1].replace("\"", "");
		            busStopObject.setstopName(busStop);
		            busStopObject.setTowards(toward);
		            busStopObject.setbusNumber(busNumber);
		            if(busNumber.length()>1){
		            collectionBusStop.add(busStopObject);
		            }
		            //Log.d(TAG,stopID);
		            //Log.d(TAG,busNumber+"busNumber");
	            }
            }
            is.close();
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // return JSON String
        return collectionBusStop;
 
    }
    public String getListOfBusStop(String url) {
    	 ArrayList<String> collectionBusNumber = new ArrayList<String>();
        // Making HTTP request
    	 String listOfbus;
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
 
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();          
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
           
            String line = null;
            while ((line = reader.readLine()) != null) {
            	Log.d(TAG,line+": request 2");
	            String []split = line.split(",");
	            listOfbus = split[1];
	            String eachBus = listOfbus.replace("\"", "").replace("]", "");
	            collectionBusNumber.add(eachBus);
	            sb.append(eachBus);
	            //Log.d(TAG,listOfbus+":request 3");
	            //Log.d(TAG,line+"line");
            }
            //Log.d(TAG,sb.toString()+"busNumber");
            is.close();
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // return JSON String
        collectionBusNumber.remove(0);
        return removeDuplicate(collectionBusNumber);
 
    }
    
    public  String removeDuplicate(ArrayList<String> arlList)
    {
    	StringBuilder sb = new StringBuilder();
	     HashSet<String> h = new HashSet<String>(arlList);
	     arlList.clear();
	     arlList.addAll(h);
	     for(String item: arlList){
	    	 sb.append(item+"  ");
	     }
      return sb.toString();
    }
}
