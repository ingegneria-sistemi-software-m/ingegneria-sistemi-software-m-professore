%====================================================================================
% virtualrobotusage25 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
<<<<<<< HEAD
event( sonardata, sonar(DISTANCE) ).
event( vrinfo, vrinfo(A,B) ).
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxvrusage25, "localhost",  "TCP", "8120").
 qactor( vrboundary, ctxvrusage25, "it.unibo.vrboundary.Vrboundary").
 static(vrboundary).
=======
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxvrusage25, "localhost",  "TCP", "8720").
 qactor( mapbuilder, ctxvrusage25, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
>>>>>>> f1315657568d70d86448a8abf7caa83dd0448658
