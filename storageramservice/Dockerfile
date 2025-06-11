FROM eclipse-temurin:17.0.5_8-jre-focal as builder
##FROM openjdk:12.0.2
## ADD extracts the tar
ADD ./build/distributions/storageramservice-1.0.tar /
WORKDIR /storageramservice-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "storageramservice"]


## 1) gradlew distTar
## 2) docker build -t storageramservice:1.0 .


## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name storageramservice -p8110:8110/tcp -p8110:8110/udp  --privileged storageramservice:1.0  /bin/bash
