%====================================================================================
% conwaycellsnqakaive description   
%====================================================================================
mqttBroker("192.168.1.132", "1883", "lifein").
event( startthegame, startthegame(X) ).
event( stopthecell, stopthecell(X) ).
event( synch, synch(X) ).
event( clearCell, clearCell(X) ).
event( curstate, curstate(NB,STATE) ).
event( kernel_rawmsg, kernel_rawmsg(ARG) ).
event( alarm, alarm(ARG) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( allnbreceived, allnbreceived(N) ).
%====================================================================================
context(ctxcells, "localhost",  "TCP", "8360").
 qactor( cellbuilder, ctxcells, "it.unibo.cellbuilder.Cellbuilder").
 static(cellbuilder).
  qactor( cell, ctxcells, "it.unibo.cell.Cell").
dynamic(cell). %%Oct2023 
  qactor( perceiveronpc, ctxcells, "it.unibo.perceiveronpc.Perceiveronpc").
 static(perceiveronpc).
