package com.yene.custom_list;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yene.bustiming.BusStopObject;
import com.yene.bustiming.R;
import com.yene.bustiming.R.id;
import com.yene.bustiming.R.layout;
public class CustomListAdapter extends BaseAdapter {
	 
    private static final String TAG = "CUSTOMLISTADAPTER";

	private ArrayList<BusStopObject> listData;
 
    private LayoutInflater layoutInflater;
 
    public CustomListAdapter(Context context, ArrayList<BusStopObject> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        
    }
 
    @Override
    public int getCount() {
        return listData.size();
    }
 
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.singel_stop, null);
            holder = new ViewHolder();
            
            holder.toward 		= (TextView) convertView.findViewById(R.id.destinationName);
            holder.busNumber 	= (TextView) convertView.findViewById(R.id.busNumber);
            holder.countDown 	= (TextView) convertView.findViewById(R.id.date);
            holder.busReg 		= (TextView) convertView.findViewById(R.id.busRegNo);
            
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.toward.setText(listData.get(position).getTowards());
        holder.busNumber.setText(listData.get(position).getbusNumber());
        holder.countDown.setText(listData.get(position).getcountDown());
        holder.busReg.setText(listData.get(position).getLineID());
 
        return convertView;
    }
 
    public class ViewHolder {
        TextView toward;
        TextView busNumber;
        TextView countDown;
        TextView busReg;
    }
 
}