%====================================================================================
% gamelifeactors description   
%====================================================================================
dispatch( guicmd, guicmd(X) ).
dispatch( fromdisplay, fromdisplay(CMD) ).
dispatch( todisplay, todisplay(CELL,STATE) ).
event( startthegame, startthegame(X) ).
dispatch( stopthecell, stopthecell(X) ).
dispatch( gameended, gameended(X) ).
dispatch( gamestopped, gamestopped(X) ).
dispatch( gamesuspend, gamesuspend(X) ).
dispatch( cellcreated, cellcreated(CELL,STATE) ).
dispatch( allcellcreated, allcellcreated(N) ).
dispatch( gameready, gameready(CELLNUM) ).
dispatch( cellends, cellends(CELL) ).
dispatch( switchstate, switchstate(X) ).
dispatch( nbconfig, nbconfig(N) ).
dispatch( allnbreceived, allnbreceived(N) ).
dispatch( cellready, cellready(CELL) ).
dispatch( allcellready, allcellready(X) ).
event( synch, synch(X) ).
event( clearCell, clearThecell(X) ).
event( curstate, curstate(NB,STATE) ).
%====================================================================================
context(ctxconwayactors, "localhost",  "TCP", "8360").
 qactor( griddisplay, ctxconwayactors, "it.unibo.griddisplay.Griddisplay").
 static(griddisplay).
  qactor( gridcreator, ctxconwayactors, "it.unibo.gridcreator.Gridcreator").
 static(gridcreator).
  qactor( gamelifehelper, ctxconwayactors, "it.unibo.gamelifehelper.Gamelifehelper").
 static(gamelifehelper).
  qactor( gamelife, ctxconwayactors, "it.unibo.gamelife.Gamelife").
 static(gamelife).
  qactor( cell, ctxconwayactors, "it.unibo.cell.Cell").
dynamic(cell). %%Oct2023 
