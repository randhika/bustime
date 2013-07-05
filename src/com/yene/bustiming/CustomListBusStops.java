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
            holder.stopName = (TextView) convertView.findViewById(R.id.stopName);
            holder.busNumber = (TextView) convertView.findViewById(R.id.busNumber);
            holder.toward = (TextView) convertView.findViewById(R.id.toward);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        
        holder.stopName.setText(""+busStopList.get(position).getstopName());
        holder.busNumber.setText(busStopList.get(position).getbusNumber());
        holder.toward.setText(busStopList.get(position).getTowards());
 
        return convertView;
    }
 
    public class ViewHolder {
        TextView stopName;
        TextView busNumber;
        TextView toward;
    }
 
}