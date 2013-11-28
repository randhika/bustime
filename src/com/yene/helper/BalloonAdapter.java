package com.yene.helper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.yene.bustiming.R;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class BalloonAdapter implements InfoWindowAdapter {
        LayoutInflater inflater = null;
        private TextView textViewTitle,distance,busNumber;

        public BalloonAdapter(LayoutInflater inflater) {
                this.inflater = inflater;
        }

        @Override
        public View getInfoWindow(Marker marker) {
                View v = inflater.inflate(R.layout.balloon, null);
                Log.e("888888","88888888"+marker.getId());
                if (marker != null && marker.getSnippet()!= null) {
               	 String title = marker.getSnippet();
        		 String current_pick[] = title.split(":");
        		 String bus = current_pick[0].replace("{id","");
        		 String stopId = current_pick[1].replace("}","").trim();
        		 String toward = marker.getTitle();
        		 
        		 //Log.e("bus",bus);
        		 //Log.e("StopId",stopId);
        		 //Log.e("title",marker.getTitle());
        		 
        		 String towardNear 		= marker.getTitle();
        		 String towardNears[]	= towardNear.split("Near:");
                        textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
                        
                        busNumber = (TextView) v.findViewById(R.id.busNo);
                        textViewTitle.setText(towardNears[0]);
                       
                        busNumber.setText("Bus Nos: "+ bus);
                }else{
                	return null;
                }
                return (v);
        }

        @Override
        public View getInfoContents(Marker marker) {
        	Log.e("888888","9999999");
        	 View v = inflater.inflate(R.layout.balloon, null);
             if (marker != null) {
                     textViewTitle = (TextView) v.findViewById(R.id.textViewTitle);
                     textViewTitle.setText(marker.getTitle());
             }
             return null;
        }
}