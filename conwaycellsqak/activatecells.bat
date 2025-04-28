@echo off
for /L %%N in (1,1,9) do (
    echo Avviando container con N=%%N...
    docker run -d --name cwaycell_a%%N -p836%%N:8360/tcp  --network cargo-network --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" conwaycellsqak:1.0   
) 
for /L %%N in (1,1,9) do (
    echo Avviando container con N=%%N...
    docker run -d --name cwaycell_b%%N -p837%%N:8360/tcp  --network cargo-network --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" conwaycellsqak:1.0   
) 
for /L %%N in (1,1,7) do (
    echo Avviando container con N=%%N...
    docker run -d --name cwaycell_c%%N -p838%%N:8360/tcp  --network cargo-network --env "MQTTBROKER_URL=tcp://192.168.1.132:1883" conwaycellsqak:1.0   
) 