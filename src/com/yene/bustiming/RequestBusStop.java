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

public class RequestBusStop extends AsyncTask<URL, Integer, Long> {
	private ArrayList<BusStopObject> item = new ArrayList<BusStopObject>();
	public JSONParser  gerUrl = new JSONParser();
	public MainActivity mainActivity; 
    private static final String TAG = "DownloadFilesTaskCalled";
    private String lat,lng;
    public RequestBusStop(MainActivity mainActivity,String lat, String lng ){
    	this.mainActivity = mainActivity;
    	this.lat =lat;
    	this.lng =lng;
    }
	protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
   	
   	 item.remove(0);
   	 mainActivity.setListAdapter(new CustomListBusStops(mainActivity, item));
    }

	@Override
	protected Long doInBackground(URL... params) {
		Log.d(TAG,"lat: "+lat+":::lng: "+lng);
		String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?Circle="+lat+","+lng+",250&ReturnList=StopCode1,StopPointName,StopPointIndicator,Towards";
		item = gerUrl.getJSONFromUrl(url);
		return null;
	}
}
