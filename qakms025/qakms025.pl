%====================================================================================
% qakms025 description   
%====================================================================================
mqttBroker("localhost", "1883", "newsletter").
request( req0, req0(ARG) ).
reply( req0reply, req0reply(ARG) ).  %%for req0
event( ms0info, ms0info(ARG) ).
%====================================================================================
context(ctxqakms025, "localhost",  "TCP", "8919").
 qactor( ms0, ctxqakms025, "it.unibo.ms0.Ms0").
 static(ms0).
