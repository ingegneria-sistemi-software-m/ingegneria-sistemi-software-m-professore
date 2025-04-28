@echo off

 
::  set comando=tuo_comando_docker

:: Apri le finestre e esegui il comando in ciascuna
start cmd  "docker run -it   --name conwaycell1 -p8361:8360/tcp -p8361:8360/udp conwaycellsqak:1.0"

:: start cmd /c "docker run -it --rm --name conwaycell2 -p8362:8360/tcp -p8362:8360/udp conwaycellsqak:1.0"
:: start cmd /c "docker run -it --rm --name conwaycell3 -p8363:8360/tcp -p8363:8360/udp conwaycellsqak:1.0"
:: start cmd /c "docker run -it --rm --name conwaycell4 -p8364:8360/tcp -p8364:8360/udp conwaycellsqak:1.0"
