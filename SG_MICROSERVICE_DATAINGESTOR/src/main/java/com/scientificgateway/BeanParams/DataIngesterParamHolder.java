package com.scientificgateway.BeanParams;

import javax.ws.rs.QueryParam;

public class DataIngesterParamHolder {

	private @QueryParam("station") String station;
	private @QueryParam("date") String date;
	private @QueryParam("hours") String hours;
	private @QueryParam("minutes") String minutes;
	private @QueryParam("seconds") String seconds;
	
	
	
	public String getSeconds() {
		return seconds;
	}
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHours() {
		return hours;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	public String getMinutes() {
		return minutes;
	}
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
	
}
