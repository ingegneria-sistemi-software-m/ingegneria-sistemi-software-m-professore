%====================================================================================
% sonar description   
%====================================================================================
dispatch( simulatorstart, simulator(V) ).
event( sonarRobot, sonar(V) ).
event( obstacle, obstacle(V) ).
event( sonardata, distance(V) ).
%====================================================================================
context(ctxsonar, "localhost",  "TCP", "8068").
context(ctxradargui, "127.0.0.1",  "TCP", "8038").
 qactor( sonarsimulator, ctxsonar, "rx.sonarSimulator").
 static(sonarsimulator).
  qactor( datalogger, ctxsonar, "rx.dataLogger").
 static(datalogger).
  qactor( datacleaner, ctxsonar, "rx.dataCleaner").
 static(datacleaner).
  qactor( distancefilter, ctxsonar, "rx.distanceFilter").
 static(distancefilter).
  qactor( radargui, ctxradargui, "external").
  qactor( sonar, ctxsonar, "it.unibo.sonar.Sonar").
 static(sonar).
