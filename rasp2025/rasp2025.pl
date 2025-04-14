%====================================================================================
% rasp2025 description   
%====================================================================================
mqttBroker("192.168.137.1", "1883", "ledalone/events").
dispatch( turnOn, turnOn(X) ).
dispatch( turnOff, turnOff(X) ).
event( ledchangedevent, ledchangedevent(V) ).
event( ledstatelocalevent, ledstatelocalevent(V) ).
event( ledvaluestream, ledvaluestream(V) ).
event( ledinfoevent, ledinfoevent(V) ).
%====================================================================================
context(ctxrasp, "localhost",  "TCP", "8080").
 qactor( raspactor, ctxrasp, "it.unibo.raspactor.Raspactor").
 static(raspactor).
  qactor( ledobserver, ctxrasp, "it.unibo.ledobserver.Ledobserver").
 static(ledobserver).
  qactor( ledperceiver, ctxrasp, "it.unibo.ledperceiver.Ledperceiver").
 static(ledperceiver).
  qactor( ledreactor, ctxrasp, "it.unibo.ledreactor.Ledreactor").
 static(ledreactor).
tracing.
