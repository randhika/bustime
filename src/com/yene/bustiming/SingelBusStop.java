package com.yene.bustiming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

 
import android.util.Log;
 
public class SingelBusStop {
 
    private static final String TAG = "SINGLE BUS STOP";
	static InputStream is = null;
    ArrayList<String> result;
    
 
    // constructor
    public SingelBusStop() {
    	 Log.e(TAG,"Get data from web has strated");
    }
 
    public ArrayList<String> getJSONFromUrl(String url) {
 
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
            result = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
            is.close();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
       
 
        // return JSON String
        return result;
 
    }
    
    public void sendGet(String url) throws Exception {
    	 
    	HttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet("http://www.vogella.com");
    	HttpResponse response = client.execute(request);

    	// Get the response
    	BufferedReader rd = new BufferedReader
    	  (new InputStreamReader(response.getEntity().getContent()));
    	    
    	String line = "";
    	while ((line = rd.readLine()) != null) {
    	  Log.e(TAG,line);
    	} 
 
	}
}

