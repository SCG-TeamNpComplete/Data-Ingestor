echo 'starting installation process'
cd '/home/ec2-user/docker'
sudo docker login -e="kedar.gn20@gmail.com" -u="kedargn" -p="npcomplete"   #TODO : hide password
sudo docker pull kedargn/dataingestor
sudo docker run -d -p 8085:8080 --name dataingestor $(sudo docker images | grep kedargn/dataingestor | awk '{print $3}') >> ./log.txt
