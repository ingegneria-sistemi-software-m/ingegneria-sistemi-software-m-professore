#### Dockerfile in project conwaygui
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/conway0-1.0.tar /
WORKDIR /conway0-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "conway25qak0"]
 

## 1) gradlew distTar

## docker rmi conway0:1.0   	 
## docker  build -t conway0:1.0 .    
## docker run -it --rm --name conway0 -p8920:8920 --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" conway0:1.0  /bin/bash
## docker run -it --rm --name conway0 -p8920:8920 --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" --env "HOST_IP=192.168.1.132" conway0:1.0  /bin/bash
 

## Register the image on DockerHub
## docker tag conway0:1.0 natbodocker/conway0:1.0
## type docker_password.txt | docker login --username natbodocker --password-stdin
## docker login --username natbodocker
## docker push natbodocker/conway0:1.0

 