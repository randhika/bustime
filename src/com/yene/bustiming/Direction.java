package com.yene.bustiming;


import java.net.URL;
import java.util.ArrayList;

import com.yene.view.MapView;
import com.yene.view.MapViewEachBus;


import android.app.ListActivity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;




public class Direction extends FragmentActivity {
	 private static final String TAG = "DownloadFilesTask";
	 public final static String BUS_MESSAGE = "com.yene.BUSNUMBER";
	 public static final String BUS_DIRECTION = "com.yene.BUSDIRECTION";
	 public static final String LINEID ="EACHBUSONROAD";
     public SingelBusStop  gerData = new SingelBusStop();
     private ArrayList<String> item = new ArrayList<String>();
     private Intent intent ;
     private String busNumber;
     private EditText mEdit;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_direction);
		Log.e(TAG,"Started");
		 intent 	= new Intent(this, MapViewEachBus.class);
		 mEdit   	= (EditText)findViewById(R.id.searchTerm);
	}
	
	public void searchBus (View v){
		busNumber = mEdit.getText().toString();
		new DownloadFilesTask().execute();
		Log.e(TAG,busNumber);
		
	}


	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	
	    

		protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	    	
	    
	    	 intent.putStringArrayListExtra(LINEID, item);
	    	 startActivity(intent);
	     }

		@Override
		protected Long doInBackground(URL... params) {
			String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?LineID="+busNumber+
						"&ReturnList=StopCode1,VehicleID,RegistrationNumber,DestinationName,EstimatedTime,LineID,Latitude,Longitude";
			item = gerData.getJSONFromUrl(url);
			 Log.e(TAG,""+item.size());
			return null;
		}
	 }
	 
} 
