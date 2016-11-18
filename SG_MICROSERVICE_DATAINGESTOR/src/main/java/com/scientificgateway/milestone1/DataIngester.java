package com.scientificgateway.milestone1;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.log4j.Logger;

import com.scientificgateway.BeanParams.DataIngesterParamHolder;
import com.scientificgateway.servicelayer.DataIngesterService;



@Path("/dataingester")
public class DataIngester {
	
	static{

		System.out.println("Registering Data Ingester Service");
		String endpointURI = "localhost:8080/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get";
	    //private final String endpointURI = "http://" + serverName + ":" + serverPort + "/catalog/resources/catalog";
	    //private final String endpointURI = "http://" + WildFlyUtil.getHostName() + ":" + WildFlyUtil.getHostPort() + "/catalog/resources/catalog";
	    String serviceName = "dataIngester";
		
	    int port = 8080;

		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();
		try {
			
			ServiceInstance serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(endpointURI)).address("localhost").port(port).name(serviceName).build();
			ServiceDiscoveryBuilder.builder(Void.class).basePath("load-balancing-example-dataIngester").client(curatorFramework)
			.thisInstance(serviceInstance).build().start();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
	//DataIngesterInit dataIngester;
	public static Logger log = Logger.getLogger(DataIngester.class.getName());
	private DataIngesterService DIservice;

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String returnURL(@BeanParam DataIngesterParamHolder dip) throws IOException, URISyntaxException {
		System.out.println("in /get");
		log.info("entered Data Ingester");
		DIservice = new DataIngesterService();
		String url = DIservice.returnResponseFile(dip.getStation(), dip.getDate(), dip.getHours(), dip.getMinutes(),
				dip.getSeconds());
		log.info("retrieved URL");

		DIservice.sendURL(url);

		log.info("called sendURL() method");
		return url;

	}
	
	
	

}
