package com.yene.fragment;

import java.util.ArrayList;

import com.yene.bustiming.BusStopFile;
import com.yene.bustiming.R;
import com.yene.bustiming.R.layout;
import com.yene.bustiming.R.string;
import com.yene.helper.ReadFile;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public  class ListView extends Fragment implements LocationListener {

    public static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<BusStopFile> bsf 		= new ArrayList<BusStopFile>();
    private ReadFile bf = new ReadFile();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_direction, container, false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.busNumber)).setText("List View");
        bsf.clear();
		bsf = bf.findBusStop(51.529121,-0.0986832);
		Log.e("bsf", ""+bsf.size());
		
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
