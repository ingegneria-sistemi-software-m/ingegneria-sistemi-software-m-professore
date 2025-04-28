@echo off
 
for /L %%N in (1,1,8) do (
    echo Avviando container con N=%%N...
    docker run -d --rm --name cwaycell_b%%N -p837%%N:8360/tcp  --network cargo-network --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" conwaycellsqak:1.0   
) 
