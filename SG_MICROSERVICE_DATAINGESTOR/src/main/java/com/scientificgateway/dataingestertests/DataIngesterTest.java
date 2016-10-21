package com.scientificgateway.dataingestertests;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.Before;
import org.junit.Test;

import com.scientificgateway.milestone1.DataIngester;
import com.scientificgateway.servicelayer.DataIngesterService;

import junit.framework.Assert;

public class DataIngesterTest {

	private DataIngesterService ds;
	private ClientConfig clientConfig;
	private Client client;
	private WebTarget target;
	private Response response;

	@Before
	public void returnRequiredObjects() {
		ds = new DataIngesterService();
		clientConfig = new ClientConfig();
		client = ClientBuilder.newClient(clientConfig);
		target = client
				.target("http://ec2-35-160-231-198.us-west-2.compute.amazonaws.com:8080/SG_MICROSERVICE_DATAINGESTOR/webapi")
				.path("dataingester").path("get");

	}

	@Test
	public void testReturnResponseFile() {
		try {
			Assert.assertEquals("https://noaa-nexrad-level2.s3.amazonaws.com/1996/06/06/hello/hello19960606_001958.gz",
					ds.returnResponseFile("hello", "06/06/1996", "00", "19", "58"));
			System.out.println(ds.returnResponseFile("hello", "06/06/1996", "00", "19", "58"));
		} catch (IOException e) {
			DataIngester.log.error("could not test testReturnUrl(). ");
		}

	}

	@Test
	public void testReturnURL() {

		WebTarget targetwithQueryParams = target.queryParam("station", "hello").queryParam("date", "06/06/1996")
				.queryParam("hours", "00").queryParam("minutes", "19").queryParam("seconds", "58");

		Invocation.Builder invocationBuilder = targetwithQueryParams.request(MediaType.TEXT_PLAIN_TYPE);
		invocationBuilder.header("test-header", "true");
		response = invocationBuilder.get();
		
		
		
		Assert.assertEquals("https://noaa-nexrad-level2.s3.amazonaws.com/1996/06/06/hello/hello19960606_001958.gz",
				response.readEntity(String.class));

	}

}
