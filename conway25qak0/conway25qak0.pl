%====================================================================================
% conway25qak0 description   
%====================================================================================
mqttBroker("192.168.1.132", "1883", "conway0events").
dispatch( startGame, startGame(ARG) ).
dispatch( stopGame, stopGame(ARG) ).
dispatch( clearGame, clearGame(ARG) ).
dispatch( exitGame, exitGame(ARG) ).
dispatch( cellstate, cellstate(X,Y) ).
event( kernel_rawmsg, kernel_rawmsg(ARG) ).
%====================================================================================
context(ctxconway0, "localhost",  "TCP", "8920").
 qactor( conway0, ctxconway0, "it.unibo.conway0.Conway0").
 static(conway0).
  qactor( guicmdpereceiver, ctxconway0, "it.unibo.guicmdpereceiver.Guicmdpereceiver").
 static(guicmdpereceiver).
