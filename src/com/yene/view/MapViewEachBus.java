package com.yene.view;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import com.yene.helper.*;



import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yene.bustiming.BusStop;
import com.yene.bustiming.Direction;
import com.yene.bustiming.EachBusStop;
import com.yene.bustiming.ConnectionDetector;
import com.yene.bustiming.FavouritStop;
import com.yene.bustiming.R;

import com.yene.fragment.AppSectionsPagerAdapter;
import com.yene.helper.ReadFile;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



 
public class MapViewEachBus  extends FragmentActivity  implements LocationListener ,OnInfoWindowClickListener,ActionBar.TabListener
, GooglePlayServicesClient.ConnectionCallbacks,     GooglePlayServicesClient.OnConnectionFailedListener{
    private static final String TAG 			= "MapView";
    public final static String BUS_NO_MESSAGE 	= "com.yene.BUSNUMBER";
    public final static String TOWARD 			= "com.yene.TOWARD";
	public final static String BUSLIST 			= "com.yene.BUSSTOP";
	private double lat,lng;
	/**
     * Note that this may be null if the Google Play services APK is not available.
     */
    private GoogleMap mMap;
    private MarkerOptions markerBusStop, user;
    private ReadFile bf;
    private LocationManager locationManager;
	private String provider;
	private boolean isConnected = false;
	private ConnectionDetector cd;
	private Context context;
	private ProgressDialog dialog;
	private ArrayList<Marker> marker = new ArrayList <Marker> ();
	private ArrayList<EachBusStop> busStopLocation = new ArrayList<EachBusStop> ();
	private Criteria criteria;
	private AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> item = new ArrayList<String>();
  
    
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;

    // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
    
    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;
    
    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;
    
    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_other);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
        Intent intent 	= getIntent();
        item = intent.getStringArrayListExtra(Direction.LINEID);
        
        final ActionBar actionBar = getActionBar();
        

        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
       // actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(actionBar.newTab().setText(mAppSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
        }
        
        context 	= getApplicationContext();
        
		dialog 		= new ProgressDialog(this);
		
		dialog.setMessage("Please wait loading...");
		dialog.show();
		
        
		 // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    criteria = new Criteria();
	    provider 		  = locationManager.getBestProvider(criteria, true);	  
	    locationManager.requestLocationUpdates(provider, 15000, 100, this);
	    Location location = locationManager.getLastKnownLocation(provider);
	    
	    lat = location.getLatitude();
	    lng = location.getLongitude();
	    Log.e("getLatitude"+ location.getLatitude(),"getLongitude"+ location.getLongitude());
	    if(location.hasAltitude()){
	    	onLocationChanged(location);
	    }
	    new DownloadFilesTask().execute();
	    
	    // Create a new global location parameters object
        mLocationRequest = LocationRequest.create();

        /*
         * Set the update interval
         */
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;

        // Open Shared Preferences
        mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);
	    
    }
    
    /*
     * Called when the Activity is restarted, even before it becomes visible.
     */
    @Override
    public void onStart() {

        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

        // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }

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
    			
		setUpMapIfNeeded();
	}

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
    	Log.d(TAG,"MapView setUpMapIfNeeded : "+busStopLocation.size());
    		
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentByTag("map")).getMap();
            mMap.setInfoWindowAdapter(new BalloonAdapter(getLayoutInflater()));
            Log.e("MAP","not NUll");
        }
         // Check if we were successful in obtaining the map.
        if (mMap != null && busStopLocation != null) {
          //Log.d(TAG,"MapView setUpMapIfNeeded : NOT NULL");
              setUpMap();
          }else{
            	//Log.d(TAG,"MapView NULL : BUS NULL");
          		}
        
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
    	Log.d(TAG,"MapView setUpMap : " + busStopLocation.size());
    	markerBusStop 	= new MarkerOptions();
    	
    	item.remove(0);
   	 for(int index = 0 ; index < item.size(); index++){
   		 	String []eachBusStop	= item.get(index).split(",");
   		 	Double lat 		= Double.parseDouble(eachBusStop[2]);
   		 	Double lng 		= Double.parseDouble(eachBusStop[3]);
   		 	String stopId 	= eachBusStop[1].replace("\"", "");
   		 	String busNo	= eachBusStop[4].replace("\"","");
   		 	mMap.addMarker(markerBusStop.position(new LatLng(lat, lng)).title("Toward: "+eachBusStop[5]).snippet(busNo +"{id: "+stopId+"}").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));


   		 	Log.e(TAG+stopId,stopId);
   		 
	 }
    	
        mMap.setTrafficEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
       // mMap.setInfoWindowAdapter(new MapView());
        mMap.setOnInfoWindowClickListener(this);
        dialog.dismiss();  
    }

	@Override
	public void onLocationChanged(Location location) {
		
		//if(mMap != null){mMap.clear();}
		user = new MarkerOptions();		
		lat = location.getLatitude();
		lng = location.getLongitude();
		
		if (mMap != null && busStopLocation.size() > 0) {
			
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15));
			mMap.addMarker(user.position(new LatLng(lat, lng)));
			mMap.addCircle(new CircleOptions().center(new LatLng(lat, lng)).radius(550).strokeColor(0x40336699).fillColor(0x20336699).strokeWidth(4));
			getGpsCoor(lat,lng);
		 }
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
		 String title = marker.getSnippet();
		 String current_pick[] = title.split(":");
		 String bus = current_pick[0].replace("{id","");
		 String stopId = current_pick[1].replace("}","").trim();
		 String toward = marker.getTitle();
		 
		 //Log.e("bus",bus);
		 //Log.e("StopId",stopId);
		 //Log.e("title",marker.getTitle());
		 sendMessage(stopId,toward,bus);
		
	}


	
	public void sendMessage (String search_term, String toward,String bus){
		Intent intent = new Intent(this, BusStop.class);
		cd 			= new ConnectionDetector(context);
		isConnected = cd.isConnectingToInternet();		
	    //EditText editText = (EditText) findViewById(R.id.busN);
	    //String search_term = editText.getText().toString();
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
			CharSequence text = "Please Enter Bus Number";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
	    }else if(!isConnected){	
			cd.showAlertDialog(MapViewEachBus.this, "No Connection", "Please enable your internet connection.", false);
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	    	 intent.putExtra(TOWARD, toward);
	    	 intent.putExtra(BUSLIST, bus);
	 	     startActivity(intent);
	    }
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
	       
	        case R.id.mystop:
	        	Toast.makeText(this,"Loading  favourit...", Toast.LENGTH_LONG).show();
	        	final Intent intent2 = new Intent(this, FavouritStop.class);
	        	startActivity(intent2);
	        	return true;
	        case R.id.search:
	        	cd.showAlertDialog(MapViewEachBus.this, "Coming Soon...", "Search via BUS, POSTCODE , BUS STOP ID.", false);
	        	return true;
	        case R.id.reload:
	        	Toast.makeText(this,"Loading Current Location...", Toast.LENGTH_LONG).show();
	        	
	        	 if (servicesConnected()) {	        		
	        		 final Location currentLocation = mLocationClient.getLastLocation();
	        		 final String lng=LocationUtils.getLatLng(this, currentLocation);
	        		 Log.e("lng", lng);
	        		 onLocationChanged(currentLocation);
	        	 }
	        
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
		private static final String TAG = "DownloadFilesTask";		
		
		
		protected void onProgressUpdate(Integer... progress) {
			 Log.d(TAG, "Show Message");
			 //System.out.print(TAG);
	     }
	     protected void onPostExecute(Long result) {
	    	 
	    	getGpsCoor(lat,lng);
	    	mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 11));
	    	
	      	user = new MarkerOptions();	
			mMap.addMarker(user.position(new LatLng(lat, lng)));
			mMap.addCircle(new CircleOptions().center(new LatLng(lat, lng)).radius(550).strokeColor(0x40336699).fillColor(0x20336699).strokeWidth(4));
	    	Log.d(TAG,"onPostExecute");
	     }

		@Override
		protected Long doInBackground(URL... params) {
			bf= new ReadFile();
			InputStream is = MapViewEachBus.this.getResources().openRawResource(R.drawable.busstop);
			bf.readName(is);
			return null;
		}
	
	}
	
	  /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =    GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(LocationUtils.APPTAG, getString(R.string.play_services_available));

            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), LocationUtils.APPTAG);
            }
            return false;
        }
    }
    /**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		   mViewPager.setCurrentItem(tab.getPosition());
		
	}



	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	
}


