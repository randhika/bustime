package com.yene.bustiming;



import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class GPS {
	
	private static final String TAG = "GPS";
	private LocationManager locationManager;
	private String latituteField,longitudeField;
	 
	 public GPS(MainActivity mainactivity){
		 
		 
		// Acquire a reference to the system Location Manager
		 locationManager = (LocationManager) mainactivity.getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		 LocationListener locationListener = new LocationListener() {
		     public void onLocationChanged(Location location) {
		       // Called when a new location is found by the network location provider.
		    	
		       makeUseOfNewLocation(location);
		       MainActivity.getGpsCoor(getLatituteField(),getLongitudeField());
				
		     }

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub
				
			}
		 };
		 
		// Register the listener with the Location Manager to receive location updates
		 locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	 }
	 
	protected void makeUseOfNewLocation(Location location) {
		
		int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    setLatituteField(location.getLatitude()+"");
	    setLongitudeField(location.getLongitude()+"");
	    Log.d(TAG, String.valueOf(lat)+"lat");
	    Log.d(TAG, String.valueOf(lng)+"lng");
		
	}
	public String getLatituteField() {
		return latituteField;
	}


	public String getLongitudeField() {
		return longitudeField;
	}


	public void setLongitudeField(String longitudeField) {
		this.longitudeField = longitudeField;
	}


	public void setLatituteField(String latituteField) {
		this.latituteField = latituteField;
	}
	public void setTextView(TextView tv) {
		tv.setText(getLatituteField());
	}


}
