package com.yene.bustiming;


import java.net.URL;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yene.example.bustiming.R;

public class Direction extends ListActivity {
	 public final static String BUS_MESSAGE = "com.yene.BUSNUMBER";
     public JSONParser  gerUrl = new JSONParser();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.select_direction);
		Context context = getApplicationContext();
		Intent intent = getIntent();
		int duration = Toast.LENGTH_SHORT;
		String text = intent.getStringExtra(MainActivity.BUS_NO_MESSAGE);
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		String[] values = new String[] { 	"Android", "iPhone", "WindowsMobile",
		        							"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		        							"Linux", "OS/2" };
		    // Use your own layout
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.select_direction, R.id.label, values);
		    setListAdapter(adapter);
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
	  }
	
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	     protected Long doInBackground(URL... urls) {
	         int count = urls.length;
	         long totalSize = 0;
	         for (int i = 0; i < count; i++) {
	             totalSize += gerUrl.getJSONFromUrl("").length();
	             publishProgress((int) ((i / (float) count) * 100));
	             // Escape early if cancel() is called
	             if (isCancelled()) break;
	         }
	         return totalSize;
	     }

	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	         //showDialog("Downloaded " + result + " bytes");
	     }
	 }
	 
} 
