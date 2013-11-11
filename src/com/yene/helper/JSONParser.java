package com.yene.helper;

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

import com.yene.bustiming.BusStopObject;
 
import android.util.Log;
 
public class JSONParser {
 
    private static final String TAG = "JSON PARSER";
	static InputStream is = null;
    static JSONObject jObj = null;
    ArrayList<BusStopObject> collectionBusStop = new  ArrayList<BusStopObject>();
    static ArrayList<String> collectionBusID = new  ArrayList<String>();
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
            
            String line,urlForBusNumber, busNumbers = null;
            collectionBusID.clear();
            while ((line = reader.readLine()) != null) {
            	BusStopObject busStopObject = new BusStopObject("","","");
            	Log.d(TAG,line);
	            String []split = line.split(",");
	            if(split.length>3 && split[3] != null && split.length < 6 ){
	            	String busStopID = split[2];
		            //Log.d(TAG,busStopID);
		            String stopID = busStopID.replace("\"", "");
		            collectionBusID.add(stopID);
		            urlForBusNumber = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?StopCode1="+stopID+"&ReturnList=lineId";
		           		            busNumbers = getListOfBusNumbers(urlForBusNumber);
		            String busStop,toward;
		            busStop = split[3].replace("\"", "");
		            toward = split[1].replace("\"", "");
		            busStopObject.setstopName(busStop);
		            busStopObject.setTowards(toward);
		            busStopObject.setbusNumber(busNumbers);
		            collectionBusStop.add(busStopObject);
		          
	            }
            }
            is.close();
            
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        // return JSON String
        return collectionBusStop;
 
    }
    public static ArrayList<String> getCollectionBusID() {
		return collectionBusID;
	}

	public String getListOfBusNumbers(String url) {
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
	            String []split = line.split(",");
	            if(split.length<3){
		            listOfbus = split[1];
		            String eachBus = listOfbus.replace("\"", "").replace("]", "");
		            collectionBusNumber.add(eachBus);
		            sb.append(eachBus);
	            }
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
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
