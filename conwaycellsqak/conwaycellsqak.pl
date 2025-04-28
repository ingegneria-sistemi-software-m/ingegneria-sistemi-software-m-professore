%====================================================================================
% conwaycellsqak description   
%====================================================================================
mqttBroker("192.168.1.68", "1883", "lifein").
event( startthegame, startthegame(X) ).
event( stopthecell, stopthecell(X) ).
event( synch, synch(X) ).
event( clearCell, clearCell(X) ).
event( curstate, curstate(NB,STATE) ).
event( kernel_rawmsg, kernel_rawmsg(ARG) ).
event( cellLifeName, cellLifeName(NAME) ).
event( exitCmd, exitCmd(ARG) ).
dispatch( nbconfig, nbconfig(N) ).
dispatch( allnbreceived, allnbreceived(N) ).
dispatch( cellready, cellready(CELL) ).
dispatch( allcellready, allcellready(X) ).
event( synch, synch(X) ).
request( addtogame, addtogame(NAME) ).
reply( addedtogame, addedtogame(CELLNAME,NROWS,NCOLS) ).  %%for addtogame
dispatch( cellcreated, cellcreated(CELL,STATE) ).
dispatch( allcellcreated, allcellcreated(N) ).
dispatch( gameready, gameready(CELLNUM) ).
dispatch( cellends, cellends(CELL) ).
event( allcellready, allcellready(X) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( allnbreceived, allnbreceived(N) ).
%====================================================================================
context(ctxcells, "localhost",  "TCP", "8360").
context(ctxmaster, "discoverable",  "TCP", "8260").
 qactor( gamemaster, ctxmaster, "external").
  qactor( cell, ctxcells, "it.unibo.cell.Cell").
 static(cell).
  qactor( perceiver, ctxcells, "it.unibo.perceiver.Perceiver").
 static(perceiver).
