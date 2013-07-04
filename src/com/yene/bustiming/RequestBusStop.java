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
	private ArrayList<String> item = new ArrayList<String>();
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
   	 
   	ArrayList<BusStopObject> busStopData = new  ArrayList<BusStopObject>();
   	
   	ArrayList<String> direction = new  ArrayList<String>();
   	 for(int index = 0 ; index < item.size(); index++){
   		BusStopObject busStopObj = new BusStopObject();
   		
   		 String []busDirection= item.get(index).split("\"",0);
   		 busStopObj.setTowards(busDirection[5]);
   		 busStopObj.setstopName(busDirection[1].replace("\"", ""));
   		 busStopObj.setbusNumber("[23,24,5,4]");
   		 direction.add(busDirection[5]);
   		 Log.d(TAG, busDirection[5]);
   		 
   		busStopData.add(busStopObj);
   	 }
   	    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(mainActivity, R.layout.app_title, direction);
   	    //ListView listview = (ListView) mainActivity.findViewById(R.id.listView1);
   	    //listview.setAdapter(adapter);
   	    mainActivity.setListAdapter(new CustomListBusStops(mainActivity, busStopData));
    }

	@Override
	protected Long doInBackground(URL... params) {
		Log.d(TAG,"lat: "+lat+":::lng: "+lng);
		String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?Circle="+lat+","+lng+",250&ReturnList=StopCode1,StopPointName,StopPointIndicator,Towards";
		item = gerUrl.getJSONFromUrl(url);
		return null;
	}
}
