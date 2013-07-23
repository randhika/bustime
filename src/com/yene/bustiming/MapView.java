package com.yene.bustiming;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yene.bustiming.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;



 
public class MapView  extends FragmentActivity  implements LocationListener ,OnInfoWindowClickListener, InfoWindowAdapter{
    private static final String TAG = "MapView";
    public final static String BUS_NO_MESSAGE = "com.yene.BUSNUMBER";
    public final static String TOWARD 			= "com.yene.TOWARD";
	public final static String BUSLIST 		= "com.yene.BUSSTOP";
	/**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private ReadFile bf;
    private LocationManager locationManager;
	private String provider;
	private boolean isConnected = false;
	private ConnectionDetector cd;
	private Context context;
	private ProgressDialog dialog;
	
	private ArrayList<BusStopFile> busStopLocation = new ArrayList<BusStopFile> ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        context = getApplicationContext();
        cd = new ConnectionDetector(context);
		isConnected = cd.isConnectingToInternet();
		
		dialog = new ProgressDialog(this);
		dialog.setMessage("Please wait loading...");
		dialog.show();
		
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

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not have been
     * completely destroyed during this process (it is likely that it would only be stopped or
     * paused), {@link #onCreate(Bundle)} may not be called again so we should call this method in
     * {@link #onResume()} to guarantee that it will be called.
     */
    
    public void getGpsCoor(double lat , double lng){
		Log.d(TAG,"MapView");
		busStopLocation = bf.findBusStop( lat,lng);
		setUpMapIfNeeded();
	}
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null && busStopLocation != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	
    	for(BusStopFile item : busStopLocation){
    		Double lat = Double.parseDouble(item.getStopLat());
    		Double lng = Double.parseDouble(item.getStopLng());
    		String toward = item.toward;
    		mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("Toward :"+toward).snippet(item.bus).icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
    	}
    	
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setInfoWindowAdapter(new MapView());
        mMap.setOnInfoWindowClickListener(this);
        dialog.dismiss();  
    }

	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		getGpsCoor(lat,lng);
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
		mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)));
		mMap.addCircle(new CircleOptions().center(new LatLng(lat, lng)).radius(550).strokeColor(0x40336699).fillColor(0x20336699).strokeWidth(4));
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

	@Override
	public void onInfoWindowClick(Marker marker) {
		 String mID =  marker.getId().substring(1);
		 int index 	=  Integer.parseInt(mID);
		 String stopId  = busStopLocation.get(index).stopId;
		 String toward 	= busStopLocation.get(index).toward;
		 String bus 	= busStopLocation.get(index).bus;
		 sendMessage(stopId,toward,bus);
		
	}

	@Override
	public View getInfoContents(Marker marker) {
		//Toast.makeText(getBaseContext(),"Info Window clicked@" + marker.getId(), Toast.LENGTH_SHORT).show();
		//System.out.print("Info Window clicked@" + marker.getId());	  
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		// TODO Auto-generated method stub
		//System.out.print("Info Window clicked@" + marker.getId());	  
		return null;
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
			cd.showAlertDialog(MapView.this, "No Connection", "Please enable your internet connection.", false);
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	    	 intent.putExtra(TOWARD, toward);
	    	 intent.putExtra(BUSLIST, bus);
	 	     startActivity(intent);
	    }
	}

	
}


