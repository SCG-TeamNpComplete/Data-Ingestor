package com.scientificgateway.milestone1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

@Path("/DataIngester")
public class DataIngesterInit extends HttpServlet {
	
	
	@Override
	public void init() throws ServletException {
		String ip=null;
    	try
    	{
	    	URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(
							whatismyip.openStream()));
	
			ip = in.readLine(); //you get the IP as a String
			System.out.println(ip);
    	}
    	catch (Exception exception)
    	{
    		exception.printStackTrace();
    	}
		System.out.println("Registering Data Ingester Service");
		String endpointURI = "http://"+ip+":8085/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get";
		// private final String endpointURI = "http://" + serverName + ":" +
		// serverPort + "/catalog/resources/catalog";
		// private final String endpointURI = "http://" +
		// WildFlyUtil.getHostName() + ":" + WildFlyUtil.getHostPort() +
		// "/catalog/resources/catalog";
		String serviceName = "dataIngester";

		int port = 8080;

		/*CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
				new RetryNTimes(5, 1000));*/
		
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("ec2-35-161-48-143.us-west-2.compute.amazonaws.com:2181", new RetryNTimes(5, 1000));
		//curatorFramework.start();
		curatorFramework.start();
		try {

			ServiceInstance serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(endpointURI))
					.address("localhost").port(port).name(serviceName).build();
			ServiceDiscoveryBuilder.builder(Void.class).basePath("load-balancing-example")
					.client(curatorFramework).thisInstance(serviceInstance).build().start();

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

	@GET
	@Path("/init")
	public String initZookeeper() {

		System.out.println("Registering Data Ingester Service");
		String endpointURI = "localhost:8080/SG_MICROSERVICE_DATAINGESTOR/webapi/dataingester/get";
		// private final String endpointURI = "http://" + serverName + ":" +
		// serverPort + "/catalog/resources/catalog";
		// private final String endpointURI = "http://" +
		// WildFlyUtil.getHostName() + ":" + WildFlyUtil.getHostPort() +
		// "/catalog/resources/catalog";
		String serviceName = "dataIngester";

		int port = 8080;

		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();
		try {

			ServiceInstance serviceInstance = ServiceInstance.builder().uriSpec(new UriSpec(endpointURI))
					.address("localhost").port(port).name(serviceName).build();
			ServiceDiscoveryBuilder.builder(Void.class).basePath("load-balancing-example")
					.client(curatorFramework).thisInstance(serviceInstance).build().start();
			
			return "done registering DataingesterInit";

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Not-registered-inDataIngester"; 
	}
}
