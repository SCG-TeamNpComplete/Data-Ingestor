package com.scientificgateway.milestone1;

import java.net.URI;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import com.scientificgateway.helpers.URIutils;

@Path("/viewjobs")
public class ViewJobsPage {
	
	@GET
	public Response getHello(@Context ServletContext servletContext, @Context UriInfo uriInfo){
		
		System.out.println("in view jobs controller");
		URI contextPath = URIutils.getFullServletContextPath(servletContext, uriInfo);
	    UriBuilder uriBuilder = UriBuilder.fromUri(contextPath);
	    uriBuilder.path("jobs.jsp");
	    System.out.println(uriBuilder);
	    return Response.seeOther(uriBuilder.build()).build();
		
	}
	
	
}
