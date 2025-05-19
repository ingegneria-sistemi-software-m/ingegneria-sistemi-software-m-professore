%====================================================================================
% unibomapbuild25 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ).
dispatch( stepfailed, stepfailed(X) ).
dispatch( vrinfo, vrinfo(A,B) ).
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8720").
 qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
