package com.yene.bustiming;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import com.yene.example.bustiming.R;

import android.net.NetworkInfo.State;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;

import android.content.Context;

import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 public final static String BUS_NO_MESSAGE = "com.yene.BUSNUMBER";
	 private static final String TAG = "MAIN ACTIVITY";
	 private boolean isConnected = false;
	 private GPS gps;
	 private Context context;
	 private State wifi;
	 private ConnectionDetector cd;
	 private static TextView latituteField;
	 private static TextView longitudeField;
	 ArrayList<String> item = new ArrayList<String>();
	 public JSONParser  gerUrl = new JSONParser();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cd = new ConnectionDetector(context);
		latituteField = (TextView) findViewById(R.id.TextView02);
	    longitudeField = (TextView) findViewById(R.id.TextView04);
		gps = new GPS(MainActivity.this);
		
		Toast.makeText(this, "gps.getLatituteField() " + gps.getLatituteField(),Toast.LENGTH_SHORT).show();
		
		isConnected = cd.isConnectingToInternet();
		new DownloadFilesTask().execute();
	
	}
	public  static void getGpsCoor(String lat , String lng){
		
		latituteField.setText(lat);
		longitudeField.setText(lng);
		Log.d(TAG,"mainactivity");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void sendMessage (View view){
		Intent intent = new Intent(this, BusStop.class);
	    EditText editText = (EditText) findViewById(R.id.busN);
	    String search_term = editText.getText().toString();
	    
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
		    
			CharSequence text = "Please Enter Bus Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }else if(!isConnected){	
			cd.showAlertDialog(MainActivity.this, "No Connection", "Please enable your internet connection.", false);
	    	
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	 	    startActivity(intent);
	    }
	}

	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	
	     private static final String TAG = "DownloadFilesTask";

		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	    	
	    	 item.remove(0);
	    	 Set<String> setItems = new LinkedHashSet<String>(item);
	    	 item.clear();
	    	 item.addAll(setItems);
	    	ArrayList<String> direction = new  ArrayList<String>();
	    	 for(int index = 0 ; index < item.size(); index++){
	    		 String []busDirection= item.get(index).split("\"",0);
	    		 direction.add(busDirection[1]);
	    		 Log.d(TAG, busDirection[1]);
	    	 }
	    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.app_title, direction);
	    	    ListView listview = (ListView) findViewById(R.id.listView1);
	    	    listview.setAdapter(adapter);
	     }

		@Override
		protected Long doInBackground(URL... params) {
			String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?Circle=51.5288624,-0.098334,250&ReturnList=StopCode1,StopPointName,StopPointIndicator";
			item = gerUrl.getJSONFromUrl(url);
			return null;
		}
	 }
	


}
