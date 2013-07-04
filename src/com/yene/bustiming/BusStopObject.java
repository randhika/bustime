package com.yene.bustiming;

public class BusStopObject {

	    private String busNumber;
	    private String countDown;
	    private String stopName;
	    private String towards;
	    private String lineID;
	    
	    public String getLineID() {
			return lineID;
		}

		public void setLineID(String lineID) {
			this.lineID = lineID;
		}

		public String getbusNumber() {
	        return busNumber;
	    }
	 
	    public void setbusNumber(String busNumber) {
	        this.busNumber = busNumber;
	    }
	 
	    public String getcountDown() {
	        return countDown;
	    }
	 
	    public void setcountDown(String countDown) {
	        this.countDown = countDown;
	    }
	 
	    public String getstopName() {
	        return stopName;
	    }
	 
	    public void setstopName(String stopName) {
	        this.stopName = stopName;
	    }
	
		public String getTowards() {
			return towards;
		}

		public void setTowards(String towards) {
			this.towards = towards;
		}
		 
	    @Override
	    public String toString() {
	        return "[ busNumber=" + busNumber + ", reporter Name=" + 
	                countDown + " , stopName=" + stopName + "]";
	    }


}
