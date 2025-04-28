%====================================================================================
% conwaymasterqak description   
%====================================================================================
mqttBroker("192.168.1.68", "1883", "lifein").
dispatch( guicmd, guicmd(X) ).
dispatch( fromdisplay, fromdisplay(CMD) ).
dispatch( todisplay, todisplay(CELL,STATE) ).
event( startthegame, startthegame(X) ).
dispatch( epochDone, epochDone(N) ).
dispatch( start, start(V) ).
dispatch( stop, stop(V) ).
dispatch( stopthecell, stopthecell(X) ).
dispatch( gameended, gameended(X) ).
dispatch( gamestopped, gamestopped(X) ).
dispatch( gamesuspend, gamesuspend(X) ).
dispatch( cellcreated, cellcreated(CELL,STATE) ).
dispatch( allcellcreated, allcellcreated(N) ).
dispatch( cellends, cellends(CELL) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( nbconfig, nbconfig(N) ).
dispatch( allnbreceived, allnbreceived(N) ).
dispatch( cellready, cellready(CELL) ).
dispatch( allcellready, allcellready(X) ).
event( synch, synch(X) ).
event( clearCell, clearThecell(X) ).
event( allcellready, allcellready(X) ).
event( curstate, curstate(NB,STATE) ).
request( addtogame, addtogame(NAME) ).
reply( addedtogame, addedtogame(CELLNAME,NROWS,NCOLS) ).  %%for addtogame
%====================================================================================
context(ctxmaster, "localhost",  "TCP", "8260").
 qactor( gamemaster, ctxmaster, "it.unibo.gamemaster.Gamemaster").
 static(gamemaster).
