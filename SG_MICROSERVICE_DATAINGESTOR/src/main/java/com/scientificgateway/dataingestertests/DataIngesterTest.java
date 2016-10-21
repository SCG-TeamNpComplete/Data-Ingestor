package com.scientificgateway.dataingestertests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.scientificgateway.milestone1.DataIngester;
import com.scientificgateway.servicelayer.DataIngesterService;

import junit.framework.Assert;

public class DataIngesterTest {
	
	private DataIngesterService ds;
	
	@Before
	public void returnRequiredObjects(){
		ds=new DataIngesterService();
	}
	
	
	@Test
	public void testReturnUrl() {
		try {
			Assert.assertEquals("https://noaa-nexrad-level2.s3.amazonaws.com/1996/06/06/hello/hello19960606_001958.gz", ds.returnResponseFile("hello", "06/06/1996", "00", "19", "58"));
			System.out.println(ds.returnResponseFile("hello", "06/06/1996", "00", "19", "58"));
		} catch (IOException e) {
			DataIngester.log.error("could not test testReturnUrl(). ");
		}
		
	}
	
}
