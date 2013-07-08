package com.yene.bustiming;

import java.net.URL;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;
public class RequestBusStop extends AsyncTask<URL, Integer, Long> {
	private ArrayList<BusStopObject> busStopObjects= new ArrayList<BusStopObject>();
	public JSONParser  gerUrl = new JSONParser();
	public MainActivity mainActivity; 
    private static final String TAG = "RequestBusStop";
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
   	 	mainActivity.setListAdapter(new CustomListBusStops(mainActivity, busStopObjects));
   	}

	@Override
	protected Long doInBackground(URL... params) {
		Log.d(TAG,"lat: "+lat+":::lng: "+lng);
		String url = "http://countdown.api.tfl.gov.uk/interfaces/ura/instant_V1?Circle="+lat+","+lng+",500&ReturnList=StopCode1,StopPointName,StopPointIndicator,Towards";
		busStopObjects = gerUrl.getJSONFromUrl(url);
		
		for(BusStopObject item : busStopObjects){
			Log.d(TAG,item.toString());
		}
		
		return null;
	}
}
