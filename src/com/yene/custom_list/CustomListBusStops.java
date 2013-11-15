package com.yene.custom_list;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yene.bustiming.BusStopFile;
import com.yene.bustiming.R;
import com.yene.bustiming.R.id;
import com.yene.bustiming.R.layout;
public class CustomListBusStops extends BaseAdapter {
	 
    private static final String TAG = "CUSTOMLISTADAPTER";
	private ArrayList<BusStopFile> busStopList;
    private LayoutInflater layoutInflater;
 
    public CustomListBusStops(Context context, ArrayList<BusStopFile> busStopList) {
        this.busStopList = busStopList;
        layoutInflater = LayoutInflater.from(context);
    }
    public CustomListBusStops(com.yene.fragment.ListView listView, ArrayList<BusStopFile> busStopList) {
		// TODO Auto-generated constructor stub
    	 this.busStopList = busStopList;
         layoutInflater = LayoutInflater.from(listView.getActivity());
	}

	
	@Override
    public int getCount() {
        return busStopList.size();
    }
    @Override
    public Object getItem(int position) {
        return busStopList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.select_direction, null);
            holder = new ViewHolder();
            holder.stopName = (TextView) convertView.findViewById(R.id.stopName);
            holder.busNumber = (TextView) convertView.findViewById(R.id.busNumber);
            holder.toward = (TextView) convertView.findViewById(R.id.toward);
            holder.addFavourit = (Button) convertView.findViewById(R.id.button1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        if(busStopList.isEmpty()){
        	holder.stopName.setText(busStopList.get(position).toward);
        	holder.busNumber.setText(busStopList.get(position).bus.trim());
        	holder.toward.setText(busStopList.get(position).stopName);
        }
        return convertView;
    }
 
    public class ViewHolder {
        TextView stopName;
        TextView busNumber;
        TextView toward;
        Button addFavourit;
    }
 
}