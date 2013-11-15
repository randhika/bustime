package com.yene.fragment;

import java.io.InputStream;
import java.util.ArrayList;

import com.yene.bustiming.BusStopFile;
import com.yene.bustiming.R;
import com.yene.custom_list.CustomListBusStops;
import com.yene.helper.ReadFile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public  class ListView extends Fragment implements LocationListener {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<BusStopFile> bsf 		= new ArrayList<BusStopFile>();
    private ReadFile bf = new ReadFile();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	
    	
        View rootView1 = inflater.inflate(R.layout.select_direction, container, false);
        Bundle args = getArguments();
       
        bsf.clear();
        InputStream is = ListView.this.getResources().openRawResource(R.drawable.busstop);
		bf.readName(is);
		bsf = bf.findBusStop(51.529121,-0.0986832);
		Log.e("Before bsf", ""+bsf.size());
	
		CustomListBusStops listview = new CustomListBusStops(this,bsf);
		View rootView = listview.getView(1, rootView1, container);
		
		
        return rootView;
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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
