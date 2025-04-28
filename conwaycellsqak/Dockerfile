#### FROM eclipse-temurin:17.0.5_8-jre-focal as builder
FROM eclipse-temurin:17.0.5_8-jre-focal as builder
## ADD extracts the tar
ADD ./build/distributions/conwaycellsqak-1.0.tar /
WORKDIR /conwaycellsqak-1.0/bin
COPY ./*.pl ./
COPY ./*.json ./
COPY ./*.bin ./
COPY ./*.txt ./
RUN mkdir logs
RUN apt-get update && apt-get install -y nano

CMD ["bash", "conwaycellsqak"]


## 1) gradlew distTar
## 2) docker rmi conwaycellsqak:1.0   
##  docker build -t conwaycellsqak:1.0 .
## OCCORRE APRIRE UDP PER PERMETTERE COAP
## docker run -it --rm --name conwaycell -p8361:8360/tcp -p8361:8360/udp  --privileged conwaycellsqak:1.0  /bin/bash
