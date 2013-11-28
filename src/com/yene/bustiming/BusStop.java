package com.yene.bustiming;


import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import java.util.Locale;


import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yene.bustiming.R;
import com.yene.custom_list.CustomListAdapter;
import com.yene.db.DatabaseHandler;
import com.yene.view.MapView;



public class BusStop extends ListActivity {
	 public  final static String BUS_MESSAGE = "com.yene.BUSNUMBER";
	 public  static final String BUS_DIRECTION = "com.yene.BUSDIRECTION";
     public  SingelBusStop  gerUrl = new SingelBusStop();
     private String showMenu = "";
     private ProgressDialog dialog;
     private DatabaseHandler db ;
     private ArrayList<String> item = new ArrayList<String>();
     private String busStopID,busStopName,direction,busList,toward;
     private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db 				= new DatabaseHandler(this);
		dialog 			= new ProgressDialog(this);
		context 		= getApplicationContext();
		Intent intent 	= getIntent();
		busStopID 		= intent.getStringExtra(MapView.BUS_NO_MESSAGE);
		showMenu		= intent.getStringExtra(FavouritStop.KLASS);
		
		dialog.setMessage("Please wait loading...");
		dialog.show();
		
		new DownloadFilesTask().execute();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if(showMenu == null){
			getMenuInflater().inflate(R.menu.activity_main, menu);
		}
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.save_stop:
	        	 Toast.makeText(this,"Save Bus Stop , Toward = "+toward , Toast.LENGTH_LONG).show();
	        	 db.addFavouritStop(busStopID,busStopName,toward,busList);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	public void openBusStopList (String direction, String busNumber){
		
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
			return "0";
		}
		return ""+diffMinutes;
	}
	
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
		private static final String TAG = "DownloadFilesTask";		
		
		
		protected void onProgressUpdate(Integer... progress) {
			
			 Log.d(TAG, "Show Message");
			 System.out.print(TAG);
	     }
	     protected void onPostExecute(Long result) {
	    	dialog.dismiss();     
	    	item.remove(0);
	    	ArrayList<BusStopObject> timeArrivle = new  ArrayList<BusStopObject>();
	    	
	    	 for(int index = 0 ; index < item.size(); index++){
	    		 String []eachBusStop	= item.get(index).split(",");
	    		 Log.e("New Arrray",item.get(index));
	    		 if(eachBusStop.length == 7){
	    		 
	    			 busStopName 			= eachBusStop[1].replace("\"", "");
	    			 String busNumber 		= eachBusStop[2].replace("\"", "");	 
	    			 String toward 			= eachBusStop[3].replace("\"", "");
	    			 String busID 	 		= eachBusStop[4].replace("\"", "");
	    			 String busReg     		= eachBusStop[5].replace("\"", "");	
	    			 String arrivTime 		= eachBusStop[6].replace("]", "");
	    			 String countDown 		= convertDate( Long.parseLong(arrivTime, 10));
	    			 Log.e("New Data",""+busStopName+"-"+busNumber+"-"+toward+"-"+toward+"-"+busID+"-"+busReg+"-"+countDown);
	    			 BusStopObject busStopObj = new BusStopObject(toward,  busNumber , countDown,  busStopName ,  busReg );
	    			 timeArrivle.add(busStopObj);
	    		 }
	    		 if(eachBusStop.length == 8){
		    		 
	    			 busStopName 			= eachBusStop[1].replace("\"", "");
	    			 String busNumber 		= eachBusStop[2].replace("\"", "");	 
	    			 String toward 			= eachBusStop[3].replace("\"", "");
	    			 toward                += eachBusStop[4].replace("\"", "");
	    			 String busID 	 		= eachBusStop[5].replace("\"", "");
	    			 String busReg     		= eachBusStop[6].replace("\"", "");	
	    			 String arrivTime 		= eachBusStop[7].replace("]", "");
	    			 String countDown 		= convertDate( Long.parseLong(arrivTime, 10));
	    			 //Log.e("New Data",""+busStopName+"-"+busNumber+"-"+toward+"-"+toward+"-"+busID+"-"+busReg+"-"+countDown);
	    			 BusStopObject busStopObj = new BusStopObject(toward,  busNumber , countDown,  busStopName ,  busReg );
	    			 timeArrivle.add(busStopObj);
	    		 }
	 	         
	    		
	    		 
	    	 }
	    	 Collections.sort(timeArrivle);  
	    	 setListAdapter(new CustomListAdapter(BusStop.this, timeArrivle));
	    	 setTitle(busStopName);
	     }

		@Override
		protected Long doInBackground(URL... params) {
			//String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?StopCode1="+busStopID;
			String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?StopCode1="+busStopID+"&"+
						"ReturnList=VehicleID,RegistrationNumber,DestinationName,EstimatedTime,LineID,StopPointName";
			Log.e("Json",busStopID);
			item = gerUrl.getJSONFromUrl(url);
			return null;
		}
	
	}
	

} 
