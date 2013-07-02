package com.yene.bustiming;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.yene.example.bustiming.R;

public class DownloadFileTask extends AsyncTask<URL, Integer, Long> {
	private ArrayList<String> item = new ArrayList<String>();
	public JSONParser  gerUrl = new JSONParser();
	public MainActivity mainActivity; 
    private static final String TAG = "DownloadFilesTaskCalled";
    private String lat,lng;
    public DownloadFileTask(MainActivity mainActivity,String lat, String lng ){
    	this.mainActivity = mainActivity;
    	this.lat =lat;
    	this.lng =lng;
    }
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
   	
   	 item.remove(0);
   	 Set<String> setItems = new LinkedHashSet<String>(item);
   	 item.clear();
   	 item.addAll(setItems);
   	ArrayList<String> direction = new  ArrayList<String>();
   	 for(int index = 0 ; index < item.size(); index++){
   		 String []busDirection= item.get(index).split("\"",0);
   		 direction.add(busDirection[1]);
   		 Log.d(TAG, busDirection[1]);
   	 }
   	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity, R.layout.app_title, direction);
   	    ListView listview = (ListView) mainActivity.findViewById(R.id.listView1);
   	    listview.setAdapter(adapter);
    }

	@Override
	protected Long doInBackground(URL... params) {
		Log.d(TAG,"lat: "+lat+":::lng: "+lng);
		String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?Circle="+lat+","+lng+",250&ReturnList=StopCode1,StopPointName,StopPointIndicator";
		item = gerUrl.getJSONFromUrl(url);
		return null;
	}
}
