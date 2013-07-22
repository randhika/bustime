package com.yene.bustiming;

import java.io.InputStream;
import java.util.ArrayList;

import com.yene.bustiming.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.RelativeLayout;

import android.widget.Toast;



public class MainActivity extends ListActivity implements LocationListener{
	 public final static String BUS_NO_MESSAGE 	= "com.yene.BUSNUMBER";
	 public final static String TOWARD 			= "com.yene.TOWARD";
	 public final static String BUSLIST 		= "com.yene.BUSSTOP";
	 private static final String TAG 			= "MAIN-ACTIVITY";
	 private boolean isConnected 				= false;
	 private ArrayList<BusStopFile> bsf 		= new ArrayList<BusStopFile>();
	 private Context context;
	 private ConnectionDetector cd;
	 private ReadFile bf;
	 private LocationManager locationManager;
	 private String provider;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = getApplicationContext();
		super.onCreate(savedInstanceState);
		cd = new ConnectionDetector(context);
		isConnected = cd.isConnectingToInternet();

		bf= new ReadFile();
		InputStream is = this.getResources().openRawResource(R.drawable.busstop);

		bf.readName(is);
		
		 // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, true);
	    Location location = locationManager.getLastKnownLocation(provider);
	    
	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
		
	    }
		
	}
	public void getGpsCoor(double lat , double lng){
		Log.d(TAG,"mainactivity");
		bsf = bf.findBusStop( lat,lng);
		setListAdapter(new CustomListBusStops(this, bsf));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reload, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(this,"Loading Map...", Toast.LENGTH_LONG).show();
	    switch (item.getItemId()) {
	        case R.id.mapview:
	        	 Intent intent = new Intent(this, MapView.class);
	        	 startActivity(intent);
	            return true;
	        case R.id.mystop:
	        	Toast.makeText(this,"Loading my favourit...", Toast.LENGTH_LONG).show();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	 /* Request updates at startup */
	 @Override
	 protected void onResume() {
	   super.onResume();
	   locationManager.requestLocationUpdates(provider, 400, 1, this);
	 }


	public void sendMessage (String search_term, String toward,String bus){
		Intent intent = new Intent(this, BusStop.class);
	    //EditText editText = (EditText) findViewById(R.id.busN);
	    //String search_term = editText.getText().toString();
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
			CharSequence text = "Please Enter Bus Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }else if(!isConnected){	
			cd.showAlertDialog(MainActivity.this, "No Connection", "Please enable your internet connection.", false);
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	    	 intent.putExtra(TOWARD, toward);
	    	 intent.putExtra(BUSLIST, bus);
	 	     startActivity(intent);
	    }
	}
	
	public void addFavourit (View v){
		//Toast.makeText(getBaseContext(), "position:", Toast.LENGTH_LONG).show();
		 final int position = getListView().getPositionForView((RelativeLayout)v.getParent());
		 String stopID = bf.eachStop().get(position).stopId;
		 String toward = bf.eachStop().get(position).toward;
		 String bus = bf.eachStop().get(position).bus;
		 Toast.makeText(getBaseContext(), position+ "= position:"+bf.eachStop().get(position).stopId, Toast.LENGTH_LONG).show();
		 sendMessage(stopID,toward,bus);
	}
	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
	    
	    getGpsCoor(lat,lng);
	    System.out.print("\n Location Change lat ="+lat);
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
