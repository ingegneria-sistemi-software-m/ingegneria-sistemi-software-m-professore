%====================================================================================
% virtualrobotusage25 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxvrusage25, "localhost",  "TCP", "8720").
 qactor( mapbuilder, ctxvrusage25, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
