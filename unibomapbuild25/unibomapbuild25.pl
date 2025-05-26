%====================================================================================
% unibomapbuild25 description   
%====================================================================================
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
dispatch( goon, goon(X) ).
%====================================================================================
context(ctxmapbuild, "localhost",  "TCP", "8032").
 qactor( mapbuilder, ctxmapbuild, "it.unibo.mapbuilder.Mapbuilder").
 static(mapbuilder).
