package com.scientificgateway.milestone1;

import java.io.IOException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.scientificgateway.BeanParams.DataIngesterParamHolder;
import com.scientificgateway.servicelayer.DataIngesterService;

@Path("/dataingester")
public class DataIngester {
	public static Logger log = Logger.getLogger(DataIngester.class.getName());
	private DataIngesterService DIservice;

	@GET
	@Path("/get")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String returnURL(@BeanParam DataIngesterParamHolder dip) throws IOException {

		log.info("entered Data Ingester");
		DIservice = new DataIngesterService();
		String url = DIservice.returnResponseFile(dip.getStation(), dip.getDate(), dip.getHours(), dip.getMinutes(),
				dip.getSeconds());
		log.info("retrieved URL");

		DIservice.sendURL(url);

		log.info("Storm Detector Notified !!!");
		return url;

	}

	/*
	 * @GET //@POST
	 * 
	 * @Path("/get1")
	 * 
	 * @Produces("application/xml") public String generateKML() throws
	 * ParseException {
	 * 
	 * String dateString ="2015-06-06"; String locationCode ="KABX"; Date date =
	 * new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
	 * //System.out.println(date);
	 * 
	 * String filevalue
	 * ="http://noaa-nexrad-level2.s3.amazonaws.com/2015/03/03/KABX/KABX20150303_001050_V06.gz";
	 * 
	 * 
	 * String result = "@Produces(\"application/xml\")" + "\n\n" +
	 * "Output:\n\n Example KML, we can add tags here " + "\n\n" +
	 * "\n\n URL for file is " +filevalue+ "\n\n" +
	 * " use this for download \n\n";
	 * 
	 * return "<KMLResponseFromStormDetector>" + "<year>" + date + "</year>" +
	 * "<location>" + locationCode + "</location>"+ "<filename>" + result +
	 * "</filename>" + "</KMLResponseFromStormDetector>"; }
	 */

}
