package com.yene.bustiming;


import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.yene.example.bustiming.R;

public class Direction extends ListActivity {
	 public final static String BUS_MESSAGE = "com.yene.BUSNUMBER";
	 public static final String BUS_DIRECTION = "com.yene.BUSDIRECTION";
     public JSONParser  gerUrl = new JSONParser();
     ArrayList<String> item = new ArrayList<String>();
     String busNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.select_direction);
		Context context = getApplicationContext();
		Intent intent = getIntent();
		int duration = Toast.LENGTH_SHORT;
		busNumber = intent.getStringExtra(MainActivity.BUS_NO_MESSAGE);
		Toast toast = Toast.makeText(context, busNumber, duration);
		toast.show();
	
		
		    new DownloadFilesTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	    openBusStopList(item,busNumber);
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
	    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Direction.this,R.layout.select_direction, R.id.label, direction);
			    setListAdapter(adapter);
	     }

		@Override
		protected Long doInBackground(URL... params) {
			String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?LineName="+busNumber+"&ReturnList=DestinationText";
			item = gerUrl.getJSONFromUrl(url);
			return null;
		}
	 }
	 
} 
