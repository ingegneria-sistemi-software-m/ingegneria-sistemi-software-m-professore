#### FROM eclipse-temurin:17.0.5_8-jre-focal as builder
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/conwaymasterqak-1.0.tar /
WORKDIR /conwaymasterqak-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs
RUN apt-get update && apt-get install -y nano

CMD ["bash", "conwaymasterqak"]


## 1) gradlew distTar
## 2) docker rmi conwaymasterqak:1.0    docker build -t conwaymasterqak:1.0 .
## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name conwaymasterqak -p8361:8360/tcp -p8361:8360/udp  --privileged conwaymasterqak:1.0  /bin/bash
## docker run -it --rm --name conwaymasterqak -p8260:8260/tcp -p8260:8260/udp --network cargo-network --env "MQTTBROKER_URL=tcp://mosquitto:1883"  conwaymasterqak:1.0   
