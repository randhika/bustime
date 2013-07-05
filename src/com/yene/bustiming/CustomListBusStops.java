package com.yene.bustiming;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yene.example.bustiming.R;
public class CustomListBusStops extends BaseAdapter {
	 
    private static final String TAG = "CUSTOMLISTADAPTER";
	private ArrayList<BusStopObject> busStopList;
    private LayoutInflater layoutInflater;
 
    public CustomListBusStops(Context context, ArrayList<BusStopObject> busStopList) {
        this.busStopList = busStopList;
        layoutInflater = LayoutInflater.from(context);
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
            holder.headlineView = (TextView) convertView.findViewById(R.id.stopName);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.busNumber);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.toward);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.headlineView.setText(""+busStopList.get(position).getstopName());
        holder.reporterNameView.setText(busStopList.get(position).getbusNumber());
        holder.reportedDateView.setText(busStopList.get(position).getTowards());
 
        return convertView;
    }
 
    public class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }
 
}