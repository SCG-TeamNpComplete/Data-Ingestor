echo 'Installing SG_MICROSERVICE_DATAINGESTOR' >> /var/log/tomcat.log
cd '/home/ec2-user/dataingestor-microservice/SG_MICROSERVICE_DATAINGESTOR'
sudo mvn -e clean install >> /var/log/tomcat.log
sudo cp target/*.war /usr/local/tomcat7/apache-tomcat-7.0.72/webapps/ >> /var/log/tomcat.log
sudo cd  /usr/local/tomcat7/apache-tomcat-7.0.72
pwd >> /var/log/tomcat.log
sudo sh ./bin/startup.sh >> /var/log/tomcat.log 2>&1 &
