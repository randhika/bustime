package com.yene.bustiming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
 
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
    static String json = "";
    ArrayList<String> result;
 
    // constructor
    public JSONParser() {
    	 Log.d(TAG,"Get data from web has strated");
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
            StringBuilder sb = new StringBuilder();
            result = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                result.add(line);
                Log.d(TAG,line);
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
 
       
 
        // return JSON String
        return result;
 
    }
}
