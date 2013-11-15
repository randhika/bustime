package com.yene.bustiming;

import java.util.ArrayList;

import com.yene.db.DatabaseHandler;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class FavouritStop extends ListActivity {
	public final static String BUS_NO_MESSAGE 	= "com.yene.BUSNUMBER";
	public final static String TOWARD 			= "com.yene.TOWARD";
	public final static String BUSLIST 			= "com.yene.BUSSTOP";
	private Context context;
	private DatabaseHandler db ;
	private ConnectionDetector cd;
	private ArrayList<BusStopFile> myFavourit 	= new ArrayList<BusStopFile>();
	private boolean isConnected = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context 	= getApplicationContext();
		cd 			= new ConnectionDetector(context);
		isConnected = cd.isConnectingToInternet();
		db 			= new  DatabaseHandler (this);
		myFavourit 	= db.getAllFavourStop();
		setListAdapter(new MyFavouritStop(this, myFavourit));
	}
	
	public void addFavourit (View v){
		final int position = getListView().getPositionForView((RelativeLayout)v.getParent());
		Toast.makeText(getBaseContext(), "position:" + position, Toast.LENGTH_LONG).show();
		String stopID 	= myFavourit.get(position).stopId;
		String toward 	= myFavourit.get(position).toward;
		String bus 	= myFavourit.get(position).bus;
		sendMessage(stopID,toward,bus);
	}
	
	public void sendMessage (String search_term, String toward,String bus){
		Intent intent = new Intent(this, BusStop.class);
	    if(search_term.length()<1 || search_term.length()>5 && isConnected ){
			
	    }else if(!isConnected){	
			cd.showAlertDialog(FavouritStop.this, "No Connection", "Please enable your internet connection.", false);
	    }else if(isConnected){
	    	 intent.putExtra(BUS_NO_MESSAGE, search_term);
	    	 intent.putExtra(TOWARD, toward);
	    	 intent.putExtra(BUSLIST, bus);
	 	     startActivity(intent);
	    }
	}
	
    public void removeFavourit( View v){
    	final int position = getListView().getPositionForView((RelativeLayout)v.getParent());
    	String favStop = myFavourit.get(position).stopId;
		myFavourit.remove(position);
		db.deleteFavourit(favStop);
		setListAdapter(new MyFavouritStop(this, myFavourit));
    }

}
