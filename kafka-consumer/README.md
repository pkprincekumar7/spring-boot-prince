Before starting kafka server, add the following lines in kafka_2.12-1.0.0/config/server.properties file to provide access for message push to kafka topic from outside of linux machine:-
----------------------------------------------------------------------------------------------------------

listeners=PLAINTEXT://:9092
port=9092

advertised.listeners=PLAINTEXT://172.16.18.111:9092


Run the following commands to build and start consumer:-
---------------------------------------------------------

Linux:-
	export PATH=$PATH:/home/medium/softwares/apache-maven-3.5.2/bin
	
Windows:-
	set PATH=F:\Softwares\apache-maven-3.5.0\bin;%PATH%

mvn clean install package
mvn spring-boot:run
