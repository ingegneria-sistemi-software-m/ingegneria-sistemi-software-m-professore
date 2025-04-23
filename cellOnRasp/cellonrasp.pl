%====================================================================================
% cellonrasp description   
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
context(ctxcellonrasp, "localhost",  "TCP", "8075").
 qactor( cell_1_1, ctxcellonrasp, "it.unibo.cell_1_1.Cell_1_1").
 static(cell_1_1).
  qactor( perceiver, ctxcellonrasp, "it.unibo.perceiver.Perceiver").
 static(perceiver).
