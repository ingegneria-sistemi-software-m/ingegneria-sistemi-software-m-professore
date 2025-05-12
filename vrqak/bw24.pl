%====================================================================================
% bw24 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
event( sonardata, sonar(DISTANCE) ).
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxbw24, "localhost",  "TCP", "8120").
 qactor( bw24core, ctxbw24, "it.unibo.bw24core.Bw24core").
 static(bw24core).
