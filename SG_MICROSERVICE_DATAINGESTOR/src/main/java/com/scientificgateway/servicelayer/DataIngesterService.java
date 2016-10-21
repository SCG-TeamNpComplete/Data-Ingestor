package com.scientificgateway.servicelayer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.*;

import org.glassfish.jersey.client.ClientConfig;

import com.scientificgateway.helpers.DownloadGZFile;
import com.scientificgateway.helpers.UNZIPfile;
import com.scientificgateway.milestone1.DataIngester;

public class DataIngesterService {

	public String returnResponseFile(String station, String date, String hours, String minutes, String seconds)
			throws IOException {

		/*
		 * DownloadGZFile dzip = new DownloadGZFile(); UNZIPfile unzip = new
		 * UNZIPfile();
		 */

		System.out.println(station);
		StringTokenizer inputstation = new StringTokenizer(station, "-");
		String stationcode = inputstation.nextToken();
		System.out.println(stationcode);

		StringTokenizer inputdate = new StringTokenizer(date, "/");
		String day = inputdate.nextToken();
		String month = inputdate.nextToken();
		String year = inputdate.nextToken();

		List<String> l = new LinkedList<>();

		while (true) {

			String filename = stationcode + year + month + day + "_" + hours + minutes + seconds + ".gz";
			String url = "https://noaa-nexrad-level2.s3.amazonaws.com/" + year + "/" + month + "/" + day + "/"
					+ stationcode + "/" + filename;

			System.out.println(url);
			return url;
		}

		/*
		 * String downloadedFile = dzip.downloadFile(url); if
		 * (downloadedFile.equals("xxx")) {
		 * System.out.println("testing other URLs"); l =
		 * convertMinutesSecondsHoursDays(hours, minutes, seconds, day, month,
		 * year); year = l.get(0); month = l.get(1); day = l.get(2); hours =
		 * l.get(3); minutes = l.get(4); seconds = l.get(5); continue;
		 * 
		 * } else {
		 * 
		 * unzip.unzip(downloadedFile, "hello.txt");
		 * System.out.println("file created"); return url; } }
		 */

	}

	public String sendURL(String url) {
		
		DataIngester.log.info("inside sendURL() method");
		
		ClientConfig clientConfig = new ClientConfig();

		Client client = ClientBuilder.newClient(clientConfig);

		// System.out.println("Client client1 ");

		WebTarget target = client.target("http://ec2-54-69-92-137.us-west-2.compute.amazonaws.com:9999/SG_MICROSERVICE_STORMDETECTOR/gateway/StormDetection")
				.path("send");
		
		
		
		// System.out.println("dataingestor");
		System.out.println("in send url    " + url);

		// Response response = target.request().post(Entity.entity(url,
		// "application/xml"), Response.class);
		// System.out.println(response.toString());
		String responsefrom;
		responsefrom = target.request().post(Entity.entity(url, "application/xml"), String.class);
		
		DataIngester.log.info("notified Storm Detector ");
		
		System.out.println();
		System.out.println(responsefrom);
		return url;

	}

	public List<String> convertMinutesSecondsHoursDays(String hours, String minutes, String seconds, String day,
			String month, String year) {

		List<String> li = new LinkedList<>();

		int mins = Integer.parseInt(minutes);
		int secs = Integer.parseInt(seconds);
		int hour = Integer.parseInt(hours);
		int days = Integer.parseInt(day);
		int months = Integer.parseInt(month);
		int years = Integer.parseInt(year);

		secs += 1;

		if (secs == 60) {
			secs = 00;
			mins += 1;
		}
		if (mins == 60) {
			mins = 00;
			hour += 1;
		}
		if (hour == 24) {
			hour = 00;
			days += 1;
		}
		if (days == 31) {
			days = 01;
			months += 1;
		}
		if (months == 12) {
			months = 01;
			years += 1;
		}

		String hh = String.valueOf(hour);
		String mm = String.valueOf(mins);
		String ss = String.valueOf(secs);
		String d = String.valueOf(day);
		String m = String.valueOf(months);
		String y = String.valueOf(years);

		if (hh.length() < 2) {
			hh = "0" + hh;
		}
		if (ss.length() < 2) {
			ss = "0" + ss;
		}
		if (mm.length() < 2) {
			mm = "0" + mm;
		}
		if (d.length() < 2) {
			d = "0" + d;
		}
		if (m.length() < 2) {
			m = "0" + m;
		}

		li.add(y);
		li.add(m);
		li.add(d);
		li.add(hh);
		li.add(mm);
		li.add(ss);
		return li;
	}
}
