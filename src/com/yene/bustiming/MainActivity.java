package com.yene.bustiming;

import com.yene.example.bustiming.R;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 public final static String BUS_NO_MESSAGE = "com.yene.BUSNUMBER";
	 private static final String TAG = "MAIN ACTIVITY";
	 private boolean isConnected = false;
	 private GPS gps;
	 private Context context;
	 private ConnectionDetector cd;
	 private static TextView latituteField;
	 private static TextView longitudeField;
	 private DownloadFileTask downloadfile;
	
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
		
	}
	public void getGpsCoor(String lat , String lng){
		latituteField.setText(lat);
		longitudeField.setText(lng);
		Log.d(TAG,"mainactivity");
		downloadfile = new DownloadFileTask(this,lat,lng);
		downloadfile.execute();
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
}
