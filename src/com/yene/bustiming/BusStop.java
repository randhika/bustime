package com.yene.bustiming;


import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.Locale;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.yene.example.bustiming.R;

public class BusStop extends ListActivity {
	 public final static String BUS_MESSAGE = "com.yene.BUSNUMBER";
	 public static final String BUS_DIRECTION = "com.yene.BUSDIRECTION";
     public JSONParser  gerUrl = new JSONParser();
     ArrayList<String> item = new ArrayList<String>();
     String busStopID;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.select_direction);
		Context context = getApplicationContext();
		Intent intent = getIntent();
		int duration = Toast.LENGTH_SHORT;
		busStopID = intent.getStringExtra(MainActivity.BUS_NO_MESSAGE);
		Toast toast = Toast.makeText(context, busStopID, duration);
		toast.show();
		new DownloadFilesTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	protected void onListItemClick(CustomListAdapter l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	    //openBusStopList(item,busNumber);
	  }
	
	public void openBusStopList (String direction, String busNumber){
		Intent intent = new Intent(this, BusStopList.class);
	   
	    String search_term = direction+","+busNumber;
	    
	    Context context = getApplicationContext();
		CharSequence text = search_term;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	    intent.putExtra(BUS_DIRECTION, search_term);
	 	startActivity(intent);
	}
	
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
		
		private static final String TAG = "DownloadFilesTask";
		
		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }
		
		
		private String convertDate( Long str )
		{
			
			Date countDown = new Date(str);
			Date currentTime = new Date();
			
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
			String dateStop = DATE_FORMAT.format(countDown);
			String  dateStart = DATE_FORMAT.format(currentTime);
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss",Locale.ENGLISH);
	 
			Date d1 = null;
			Date d2 = null;
			Long diffMinutes =null;
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(dateStop);
	 
				//in milliseconds
				long diff = d2.getTime() - d1.getTime();
				 	 diffMinutes = diff / (60 * 1000) % 60;
	 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(diffMinutes == 0){
				return "Due";
			}
	 
			return ""+diffMinutes +"(min)  ";
			 
		
			 
		}

	     protected void onPostExecute(Long result) {
	    	
	    	item.remove(0);
	    	ArrayList<BusStopObject> timeArrivle = new  ArrayList<BusStopObject>();
	    	
	    	 for(int index = 0 ; index < item.size(); index++){
	    		 BusStopObject busStopObj = new BusStopObject();
	    		 Log.d(TAG+"Before spilt", item.get(index));
	    		 String []busDirection= item.get(index).split(",",0);
	    		 Log.d(TAG+"last element", busDirection[3]);
	    		 //convertDate( Long.parseLong(arrivTime, 10));
	    		 
	    		 busStopObj.setstopName(busDirection[1].replace("\"", ""));
	 	         busStopObj.setbusNumber(busDirection[2].replace("\"", ""));
	 	         
	 	         String arrivTime = busDirection[3].replace("]", "");
	 	         busStopObj.setcountDown(convertDate( Long.parseLong(arrivTime, 10)));
	    		 Log.d(TAG,arrivTime);
	    		 timeArrivle.add(busStopObj);
	    	 }
	    	   
			    setListAdapter(new CustomListAdapter(BusStop.this, timeArrivle));
			 
	     }

		@Override
		protected Long doInBackground(URL... params) {
			
			String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?StopCode1="+busStopID;
			//item = gerUrl.getJSONFromUrl(url);
			//ConnectionDetector cd;
			//cd = new ConnectionDetector(BusStop.this);
			//cd.showAlertDialog(BusStop.this, "Number of Bus At this Bus Stop", ""+item.size(), false);
			return null;
		}
	
	}
} 
