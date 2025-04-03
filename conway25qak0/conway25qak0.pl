%====================================================================================
% conway25qak0 description   
%====================================================================================
dispatch( startGame, startGame(ARG) ).
dispatch( stopGame, stopGame(ARG) ).
dispatch( clearGame, clearGame(ARG) ).
dispatch( exitGame, exitGame(ARG) ).
dispatch( cellstate, cellstate(ARG) ).
%====================================================================================
context(ctxconway0, "localhost",  "TCP", "8920").
 qactor( conway0, ctxconway0, "it.unibo.conway0.Conway0").
 static(conway0).
