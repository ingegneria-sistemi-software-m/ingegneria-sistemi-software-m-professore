%====================================================================================
% unibologger description   
%====================================================================================
mqttBroker("192.168.1.132", "1883", "logkb").
event( unibolog, unibolog(X) ).
event( unibologprolog, unibologprolog(SOURCE,CATEG,CONTENT) ).
%====================================================================================
context(ctxunibologger, "localhost",  "TCP", "8260").
 qactor( unibologkb, ctxunibologger, "it.unibo.unibologkb.Unibologkb").
 static(unibologkb).
