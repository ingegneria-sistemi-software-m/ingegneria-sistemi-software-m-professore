%====================================================================================
% conwayactorsqak description   
%====================================================================================
mqttBroker("192.168.1.132", "1883", "lifeevents").
dispatch( startcmd, startcmd(X) ).
dispatch( stopcmd, stopcmd(X) ).
dispatch( clearcmd, clearcmd(X) ).
event( startthegame, startthegame(X) ).
event( stopthecell, stopthecell(X) ).
event( synch, synch(X) ).
event( clearCell, clearCell(X) ).
event( curstate, curstate(NB,STATE) ).
event( kernel_rawmsg, kernel_rawmsg(ARG) ).
dispatch( activateMaster, activateMaster(NCELLS) ).
dispatch( epochDone, epochDone(N) ).
dispatch( start, start(V) ).
dispatch( stop, stop(V) ).
dispatch( clear, clear(V) ).
dispatch( gridEmpty, gridEmpty(X) ).
dispatch( cellcreated, cellcreated(CELL,STATE) ).
dispatch( allcellcreated, allcellcreated(N) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( allnbreceived, allnbreceived(N) ).
dispatch( cellready, cellready(CELL,STATE) ).
dispatch( allcellready, allcellready(X) ).
request( addtogame, addtogame(NAME) ).
reply( addedtogame, addedtogame(CELLNAME,NROWS,NCOLS) ).  %%for addtogame
%====================================================================================
context(ctxcells, "localhost",  "TCP", "8360").
 qactor( player, ctxcells, "it.unibo.player.Player").
dynamic(player). %%Oct2023 
  qactor( gamebuilder, ctxcells, "it.unibo.gamebuilder.Gamebuilder").
 static(gamebuilder).
  qactor( gamecontroller, ctxcells, "it.unibo.gamecontroller.Gamecontroller").
 static(gamecontroller).
  qactor( gamemaster, ctxcells, "it.unibo.gamemaster.Gamemaster").
 static(gamemaster).
  qactor( cell, ctxcells, "it.unibo.cell.Cell").
dynamic(cell). %%Oct2023 
