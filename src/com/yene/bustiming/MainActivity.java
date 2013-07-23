package com.yene.bustiming;

import java.io.InputStream;
import java.util.ArrayList;

import com.yene.bustiming.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


import android.app.ListActivity;
import android.app.ProgressDialog;
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
	 private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context 			= getApplicationContext();
		cd 					= new ConnectionDetector(context);
		bf					= new ReadFile();
		Criteria criteria 	= new Criteria();
		dialog 				= new ProgressDialog(this);
		
		isConnected 		= cd.isConnectingToInternet();
		InputStream is 		= this.getResources().openRawResource(R.drawable.busstop);
		locationManager 	= (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		provider 			= locationManager.getBestProvider(criteria, true);
		Location location 	= locationManager.getLastKnownLocation(provider);
		
		dialog.setMessage("Please wait loading...");
		dialog.show();
		bf.readName(is);
		 
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
		
	    }
		
	}
	public void getGpsCoor(double lat , double lng){
		bsf.clear();
		bsf = bf.findBusStop( lat,lng);
		setListAdapter(new CustomListBusStops(this, bsf));
		dialog.dismiss();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reload, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
	    switch (item.getItemId()) {
	        case R.id.mapview:
	        	Toast.makeText(this,"Loading Map...", Toast.LENGTH_LONG).show();
	        	 Intent intent = new Intent(this, MapView.class);
	        	 startActivity(intent);
	        	 return true;
	        case R.id.mystop:
	        	Toast.makeText(this,"Loading  favourit...", Toast.LENGTH_LONG).show();
	        	Intent intent2 = new Intent(this, FavouritStop.class);
	        	startActivity(intent2);
	        	return true;
	        case R.id.search:
	        	Toast.makeText(this,"Loading  favourit...", Toast.LENGTH_LONG).show();
	        	cd.showAlertDialog(MainActivity.this, "Coming Soon...", "Search via BUS, POSTCODE , BUS STOP ID.", false);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	 /* Request updates at startup */
	 @Override
	 protected void onResume() {
	   super.onResume();
	   locationManager.requestLocationUpdates(provider, 5000, 10, this);
	 }
	 
	public void sendMessage (String search_term, String toward,String bus){
		Intent intent = new Intent(this, BusStop.class);
	    //EditText editText = (EditText) findViewById(R.id.busN);
	    //String search_term = editText.getText().toString();
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
			
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
		 final int position = getListView().getPositionForView((RelativeLayout)v.getParent());
		 String stopID = bf.eachStop().get(position).stopId;
		 String toward = bf.eachStop().get(position).toward;
		 String bus = bf.eachStop().get(position).bus;
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
