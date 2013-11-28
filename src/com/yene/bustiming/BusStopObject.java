package com.yene.bustiming;

public class BusStopObject  implements Comparable<BusStopObject>{

	    private String busNumber;
	    private String countDown;
	    private String stopName;
	    private String towards;
	    private String lineID;
	    
	    public BusStopObject(String towards, String busNumber ,String countDown, String stopName , String lineID){
	    	this.busNumber 	= 	busNumber;
	    	this.towards 	= 	towards;
	    	this.countDown	= 	countDown;
	    	this.stopName 	=	stopName;
	    	this.lineID 	=	lineID;
	    }
	    
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
	        return "[ busNumber=" + busNumber + " , countDown=" + 
	                countDown + " , stopName=" + stopName +
	                " , towards ="+towards+
	                "]";
	    }

		@Override
		public int compareTo(BusStopObject another) {
			int countDown = Integer.parseInt(another.getcountDown());
			int thisCount = Integer.parseInt(this.countDown);
			return thisCount-countDown;
		}


}
