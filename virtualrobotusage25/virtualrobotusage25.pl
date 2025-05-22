%====================================================================================
% virtualrobotusage25 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
event( sonardata, sonar(DISTANCE) ).
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxvrusage25, "localhost",  "TCP", "8120").
 qactor( vrboundary, ctxvrusage25, "it.unibo.vrboundary.Vrboundary").
 static(vrboundary).
