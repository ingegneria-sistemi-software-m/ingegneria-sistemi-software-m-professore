%====================================================================================
% conwaycellsqak description   
%====================================================================================
dispatch( guicmd, guicmd(X) ).
dispatch( fromdisplay, fromdisplay(CMD) ).
dispatch( todisplay, todisplay(CELL,STATE) ).
event( allcellready, allcellready(X) ).
event( startthegame, startthegame(X) ).
dispatch( stopthecell, stopthecell(X) ).
dispatch( gameended, gameended(X) ).
dispatch( gamestopped, gamestopped(X) ).
dispatch( gamesuspend, gamesuspend(X) ).
dispatch( cellcreated, cellcreated(CELL,STATE) ).
dispatch( allcellcreated, allcellcreated(N) ).
dispatch( gameready, gameready(CELLNUM) ).
dispatch( cellends, cellends(CELL) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( nbconfig, nbconfig(N) ).
dispatch( allnbreceived, allnbreceived(N) ).
dispatch( cellready, cellready(CELL) ).
dispatch( allcellready, allcellready(X) ).
event( synch, synch(X) ).
event( clearCell, clearThecell(X) ).
event( curstate, curstate(NB,STATE) ).
request( addtogame, addtogame(NAME) ).
reply( addedtogame, addedtogame(CELLNAME,NROWS,NCOLS) ).  %%for addtogame
dispatch( start, start(V) ).
event( synch, synch(X) ).
event( clearCell, clearThecell(X) ).
event( allcellready, allcellready(X) ).
event( startthegame, startthegame(X) ).
%====================================================================================
context(ctxcells, "localhost",  "TCP", "8360").
 qactor( cell, ctxcells, "it.unibo.cell.Cell").
 static(cell).
  qactor( orchestrator, ctxcells, "it.unibo.orchestrator.Orchestrator").
 static(orchestrator).
