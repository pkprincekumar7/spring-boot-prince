Before starting kafka server, add the following lines in kafka_2.12-1.0.0/config/server.properties file to provide access for message push to kafka topic from outside of linux machine:-
----------------------------------------------------------------------------------------------------------

listeners=PLAINTEXT://:9092
port=9092

advertised.listeners=PLAINTEXT://172.16.18.111:9092


Start zookeeper using following command:-
------------------------------------------

Linux:-
	bin/zookeeper-server-start.sh config/zookeeper.properties
	
Windows:-
	.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties


Start kafka server using following command:-
---------------------------------------------

Linux:-
	bin/kafka-server-start.sh config/server.properties
	
Windows:-
	.\bin\windows\kafka-server-start.bat .\config\server.properties


Create topic if not exist:-
----------------------------

Linux:-
	bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic MEDIUM_TOPIC
	
Windows
	.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic MEDIUM_TOPIC

(where topic name is MEDIUM_TOPIC)


Check the list of topics:-
---------------------------
Linux:-
	bin/kafka-topics.sh --list --zookeeper localhost:2181
	
Windows:-
	.\bin\windows\kafka-topics.bat --list --zookeeper localhost:2181


Run the following commands to build and start producer:-
---------------------------------------------------------

Linux:-
	export PATH=$PATH:/home/medium/softwares/apache-maven-3.5.2/bin
	
Windows:-
	set PATH=F:\Softwares\apache-maven-3.5.0\bin;%PATH%

mvn clean install package
mvn spring-boot:run

The producer will start on 8080 port


Hit the following url to send message to kafka topic (use "localhost" if client and server machines are same):-
----------------------------------------------------------------------------------------------------------------
Url:	http://<server_ip>:8080/producer/send/<topic_name>
Method:	POST
Sample Payload:-
{
	"name":"a"
}

e.g.,
Url:	http://localhost:8080/producer/send/MEDIUM_TOPIC
