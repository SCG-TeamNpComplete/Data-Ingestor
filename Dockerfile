FROM tomcat:8-jre8
ADD	SG_MICROSERVICE_DATAINGESTOR/target/SG_MICROSERVICE_DATAINGESTOR.war /usr/local/tomcat/webapps
EXPOSE 8085
