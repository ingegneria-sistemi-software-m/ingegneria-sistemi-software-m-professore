%====================================================================================
% bwbscrbt description   
%====================================================================================
request( engage, engage(OWNER,STEPTIME) ).
reply( engagedone, engagedone(ARG) ).  %%for engage
reply( engagerefused, engagerefused(ARG) ).  %%for engage
dispatch( disengage, disengage(ARG) ).
dispatch( cmd, cmd(MOVE) ). %MOVE = a|d|l|r|h   
request( cmd, cmd(MOVE,T) ). %MOVE = w|s|p (stepSynch)
reply( cmddone, cmddone(R) ).  %%for cmd
reply( cmdfailed, cmdfailed(T,CAUSE) ).  %%for cmd
request( step, step(TIME) ).
reply( stepdone, stepdone(V) ).  %%for step
reply( stepfailed, stepfailed(DURATION,CAUSE) ).  %%for step
request( doplan, doplan(PATH,STEPTIME) ).
reply( doplandone, doplandone(ARG) ).  %%for doplan
reply( doplanfailed, doplanfailed(ARG) ).  %%for doplan
request( moverobot, moverobot(TARGETX,TARGETY) ).
reply( moverobotdone, moverobotok(ARG) ).  %%for moverobot
reply( moverobotfailed, moverobotfailed(PLANDONE,PLANTODO) ).  %%for moverobot
request( getrobotstate, getrobotstate(ARG) ).
reply( robotstate, robotstate(POS,DIR) ).  %%for getrobotstate
request( getenvmap, getenvmap(X) ).
reply( envmap, envmap(MAP) ).  %%for getenvmap
dispatch( setrobotstate, setpos(X,Y,D) ).
dispatch( setdirection, dir(D) ).
event( sonardata, sonar(DISTANCE) ).
event( obstacle, obstacle(X) ).
dispatch( brdata, changed(Y) ).
dispatch( coapUpdate, changed(Y) ).
dispatch( pause, pause(X) ).
dispatch( goon, goon(N) ).
dispatch( showmap, showmap(X) ).
%====================================================================================
context(ctxbwbscrbt, "localhost",  "TCP", "8720").
context(ctxbasicrobot, "discoverable",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( robotpos, ctxbasicrobot, "external").
  qactor( bwbrcore, ctxbwbscrbt, "it.unibo.bwbrcore.Bwbrcore").
 static(bwbrcore).
  qactor( mapviewer, ctxbwbscrbt, "it.unibo.mapviewer.Mapviewer").
 static(mapviewer).
