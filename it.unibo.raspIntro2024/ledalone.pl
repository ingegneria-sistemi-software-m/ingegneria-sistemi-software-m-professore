%====================================================================================
% ledalone description   
%====================================================================================
mqttBroker("192.168.1.132", "1883", "ledalone/events").
dispatch( turnOn, turnOn(X) ).
dispatch( turnOff, turnOff(X) ).
event( ledchanged, ledchanged(V) ).
%====================================================================================
context(ctxledalone, "localhost",  "TCP", "8080").
 qactor( led, ctxledalone, "it.unibo.led.Led").
 static(led).
