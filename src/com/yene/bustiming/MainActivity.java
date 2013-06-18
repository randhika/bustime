package com.yene.bustiming;

import com.yene.example.bustiming.R;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	 public final static String BUS_NO_MESSAGE = "com.yene.BUSNUMBER";
	 private boolean isConnected = false;
	 Context context;
	 State wifi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
			Log.d("isConnection",""+isConnected);
		}    isConnected = true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public void sendMessage (View view){
		Intent intent = new Intent(this, Direction.class);
	    EditText editText = (EditText) findViewById(R.id.busN);
	    String search_term = editText.getText().toString();
	    
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
		    
			CharSequence text = "Please Enter Bus Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }else if(!isConnected){
	    	CharSequence text = "Need Internet Connection";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    	
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	 	    startActivity(intent);
	    }
	}
	
	public void findNearByStop (View view){
		Intent intent = new Intent(this, Direction.class);
	    EditText editText = (EditText) findViewById(R.id.busN);
	    String search_term = editText.getText().toString();
	    
	    if(search_term.length()<1 || search_term.length()>5){
		    Context context = getApplicationContext();
			CharSequence text = "Searching for you Location";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }else{
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	 	    startActivity(intent);
	    }
	}

}
