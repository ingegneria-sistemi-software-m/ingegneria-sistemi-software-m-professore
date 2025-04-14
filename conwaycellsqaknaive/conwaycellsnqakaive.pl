%====================================================================================
% conwaycellsnqakaive description   
%====================================================================================
mqttBroker("test.mosquitto.org", "1883", "lifeevents").
event( startthegame, startthegame(X) ).
event( stopthecell, stopthecell(X) ).
event( synch, synch(X) ).
event( clearCell, clearCell(X) ).
event( curstate, curstate(NB,STATE) ).
event( kernel_rawmsg, kernel_rawmsg(ARG) ).
dispatch( changeCellState, changeCellState(X,Y) ).
dispatch( allnbreceived, allnbreceived(N) ).
%====================================================================================
context(ctxcells, "localhost",  "TCP", "8360").
context(ctxcellonrasp, "192.168.137.2",  "TCP", "8075").
 qactor( cellonrasp, ctxcellonrasp, "external").
  qactor( cellbuilder, ctxcells, "it.unibo.cellbuilder.Cellbuilder").
 static(cellbuilder).
  qactor( cell, ctxcells, "it.unibo.cell.Cell").
dynamic(cell). %%Oct2023 
