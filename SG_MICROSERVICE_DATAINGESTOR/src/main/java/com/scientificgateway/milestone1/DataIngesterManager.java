package com.scientificgateway.milestone1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;
import org.apache.curator.x.discovery.UriSpec;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.glassfish.jersey.client.ClientConfig;

import com.scientificgateway.servicelayer.DataIngesterService;

@Path("/DataIngesterManager")
public class DataIngesterManager {
	private static String availableIpAddress = null;

	public DataIngesterManager() {

		List<String> ipaddresses = new LinkedList<>();
		ipaddresses.add("ec2-35-161-48-143.us-west-2.compute.amazonaws.com");
		ipaddresses.add("ec2-35-160-137-157.us-west-2.compute.amazonaws.com");
		ipaddresses.add("ec2-52-15-90-97.us-east-2.compute.amazonaws.com");

		URI uri = null;
		String response = null;
		int count = 0;
		for (String ip : ipaddresses) {
			count++;
			try {
				if (InetAddress.getByName(ip).isReachable(3000)) {
					availableIpAddress = ip;
					break;
				}
			} catch (IOException e) {
				System.out.println(e.toString());
			}
		}
		if (count == ipaddresses.size() && availableIpAddress == null) {
			try {
				throw new Exception();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		// TODO Auto-generated constructor stub
	}

	private DataIngesterService dataingesterservice;

	@GET
	@Path("/delegate")
	@Produces("text/plain")
	public String getDataIngesterReplica(@QueryParam("station") String station, @QueryParam("date") String date,
			@QueryParam("hours") String hours, @QueryParam("minutes") String minutes,
			@QueryParam("seconds") String seconds) {

		System.out.println(station);
		System.out.println(station.replaceAll("\\s+", ""));

		String stationMain = station.replaceAll("\\s+", "");

		System.out.println("in delegate manager-dataingester");
		/*
		 * CuratorFramework curatorFramework =
		 * CuratorFrameworkFactory.newClient("localhost:2181", new
		 * RetryNTimes(5, 1000));
		 */
		/*
		 * CuratorFramework curatorFramework =
		 * CuratorFrameworkFactory.newClient(
		 * "ec2-35-161-48-143.us-west-2.compute.amazonaws.com:2181", new
		 * RetryNTimes(5, 1000)); curatorFramework.start();
		 */
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(availableIpAddress + ":2181",
				new RetryNTimes(5, 1000));
		curatorFramework.start();

		System.out.println("curator start----in delegate manager-dataingester");

		ServiceDiscovery<Void> serviceDiscovery = ServiceDiscoveryBuilder.builder(Void.class)
				.basePath("load-balancing-example").client(curatorFramework).build();
		try {
			serviceDiscovery.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServiceProvider<Void> serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("dataIngester")
				.build();
		try {
			serviceProvider.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ServiceProvider serviceProvider;
		ServiceInstance instance;
		// InstanceProvider instanceProvider;

		System.out.println("before try");
		try {
			List<ServiceInstance<Void>> instances = (List<ServiceInstance<Void>>) serviceProvider.getAllInstances();
			if (instances.size() == 0) {
				System.out.println("list empty");
				return null;
			}

			int thisIndex = DataIngesterService.getIndex();
			DataIngesterService.setIndex(thisIndex + 1);

			System.out.println("thisIndex" + thisIndex);
			System.out.println(instances.get(thisIndex % instances.size()));

			String address = instances.get(thisIndex % instances.size()).getId();
			UriSpec address1 = instances.get(thisIndex % instances.size()).getUriSpec();
			String url = address1.build();
			System.out.println(url);

			System.out.println("hello");

			URIBuilder builder = new URIBuilder();
			builder.setPath(url).setParameter("station", stationMain).setParameter("date", date)
					.setParameter("hours", hours).setParameter("minutes", minutes).setParameter("seconds", seconds);
			URI uri = builder.build();
			
			
			
			
			
			
			System.out.println(uri);
			HttpGet httpget = new HttpGet(uri);
			ClientConfig clientConfig = new ClientConfig();
			Client client = ClientBuilder.newClient(clientConfig);
			String response = client.target(uri).request().get(String.class);
			System.out.println(response);
			return response;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally{
			curatorFramework.close();	
		}

		return "dataIngester - nothing";

	}

}
