#### FROM eclipse-temurin:17.0.5_8-jre-focal as builder
FROM openjdk:12.0.2
## ADD extracts the tar
ADD ./build/distributions/cargoproduct-1.0.tar /
WORKDIR /cargoproduct-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs

CMD ["bash", "cargoproduct"]


## 1) gradlew distTar
## 2) docker rmi cargo:1.0    docker build -t cargo:1.0 .
## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name cargo -p6001:6001/tcp -p6001:6001/udp  --privileged cargo:1.0  /bin/bash
