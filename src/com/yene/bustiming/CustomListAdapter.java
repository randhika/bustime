package com.yene.bustiming;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yene.example.bustiming.R;
public class CustomListAdapter extends BaseAdapter {
	 
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
            convertView = layoutInflater.inflate(R.layout.select_direction, null);
            holder = new ViewHolder();
            holder.headlineView = (TextView) convertView.findViewById(R.id.stopName);
            holder.reporterNameView = (TextView) convertView.findViewById(R.id.busNumber);
            holder.reportedDateView = (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        holder.headlineView.setText(""+listData.get(position).getstopName());
        holder.reporterNameView.setText(listData.get(position).getbusNumber());
        holder.reportedDateView.setText(listData.get(position).getcountDown());
 
        return convertView;
    }
 
    public class ViewHolder {
        TextView headlineView;
        TextView reporterNameView;
        TextView reportedDateView;
    }
 
}