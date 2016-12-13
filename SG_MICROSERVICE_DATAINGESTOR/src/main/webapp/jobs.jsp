<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>

<button onclick="viewJobs()">View Jobs</button>
<br>
<table class="table table-responsive">
     <thead>
         <tr>
             <th>JOB_NAME</th>
             <th>RAM</th>
             <th>CPU</th>
             <th>DISK</th>
             <th>GIF_1</th>
             <th>GIF_2</th>
             <th>STATUS</th>
         </tr>
     </thead>
     <tbody id="jobsTable">
     
     
     
     
     

</tbody>
</table>
<br><br>

<form action="/SG_MICROSERVICE_STROMCLUSTERING/gateway/resubmit" method="get">
	<input type="text" value ="JOB NAME" name="jobName">
	<input type=submit name="Re-submit">
</form>

<script >

	function viewJobs(){
		
		var ajax_call = function() {
			$("tr").remove(".res");
			$.ajax({
				type : "GET",
				url : "http://ec2-35-161-48-143.us-west-2.compute.amazonaws.com:8888/SG_MICROSERVICE_STROMCLUSTERING/gateway/getjobdetails",
				dataType : "json",
				error : function(xhr, err) {
					alert("readyState: " + xhr.readyState + "\nstatus: "
					+ xhr.status);
					alert("responseText: " + xhr.responseText + " " + err);
					console.log(xhr.responseText);
				},
				success : function(response) {
		                 console.log('result length --');
		                 console.log(response);
		                 console.log("hello   "+response[0].cpu);
						 
		                 for (var i=0;i<response.length;i++){
		                	 var obj = response[i];
		                	 var gif1_link = obj.gif1.replace("file","download");
		                	 var gif2_link = obj.gif2.replace("file","download");
		                	 $('<tr class="res"><td>'+obj.jobName+'</td><td>'+obj.ram+'</td><td>'+obj.cpu+'</td><td>'+obj.disk+'</td><td><a target="_blank" id="gif1" href="http://'+gif1_link+'">'+obj.gif1+'</a><img height="100" width="100" src="http://'+gif1_link+'" class="loading" /></td><td><a id="gif2" href="http://'+gif2_link+'">'+obj.gif2+'</a><img height="100" width="100" src="http://'+gif2_link+'" class="loading" /></td><td>'+obj.status+'</td>').appendTo('#jobsTable');
		                 }
		                 
		              }
			});
			
			};
			var interval = 1000 * 10;
			setInterval(ajax_call, interval); 
		
	}

</script>

<script>
$("#paypalLink").click(function(){
    $(".loading").show();
});

</script>


</body>
</html>