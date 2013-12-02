package com.yene.view;

import java.util.ArrayList;

import com.yene.bustiming.ConnectionDetector;
import com.google.android.gms.ads.*;

import com.yene.bustiming.Direction;
import com.yene.bustiming.FavouritStop;
import com.yene.bustiming.R;
import com.yene.bustiming.SingelBusStop;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.*;
public class LandingClass extends FragmentActivity {
	private AdView adView;
	private Intent mapView ;
	private Intent favourit ;
	private Intent searchBus ;
	private ConnectionDetector cd;
	private boolean isConnected = false;
	private ArrayList<String> joke = new ArrayList<String>();
	
	private Context context;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-7154649041385691/7938725706");
	    adView.setAdSize(AdSize.BANNER);
	    
		super.onCreate(arg0);
		setContentView(R.layout.landing_view);
		context 	= getApplicationContext();
		
		mapView 	= new Intent(this, MapView.class);
		searchBus 	= new Intent(this, Direction.class);
		favourit 	= new Intent(this, FavouritStop.class);
		
		   // Lookup your LinearLayout assuming it's been given
	    // the attribute android:id="@+id/mainLayout".
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adsView);

	    // Add the adView to it.
	    layout.addView(adView);

	    // Initiate a generic request.
	    AdRequest adRequest = new AdRequest.Builder().build();

	    // Load the adView with the ad request.
	    adView.loadAd(adRequest);
	    
	 
	    
	    
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	public void searchBus (View v){
		cd 			= new ConnectionDetector(context);
		isConnected = cd.isConnectingToInternet();
		if(isConnected){
			startActivity(searchBus);
		}else{
			cd.showAlertDialog(this, "No Connection", "Please enable your internet connection.", false);
		}
		
		cd = null;
	}
	
	public void mapView (View v){
		startActivity(mapView);
		
	}
	public void favourit (View v){
		startActivity(favourit);
	}
	
	

}
