## ----------------------------------------------------------
## Dockerfile for unibo.basicrobot24
## ----------------------------------------------------------

FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## EXPOSE 8720
## ADD extracts the tar
ADD ./build/distributions/bwbscrbt-1.0.tar /
WORKDIR /bwbscrbt-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./

## RUN apt-get update -y
## RUN apt-get install -y wiringpi
## RUN sudo apt-get install -y python

CMD ["bash", "bwbscrbt"]

 
## 1) gradlew distTar
## 2) docker build -t bwbscrbt:1.0 .
 

# docker-compose -f bwbscrbt.yaml  up