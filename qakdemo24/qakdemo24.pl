%====================================================================================
% qakdemo24 description   
%====================================================================================
dispatch( info, info(SOURCE,TERM) ).
%====================================================================================
context(ctxobs, "localhost",  "TCP", "8004").
 qactor( observer, ctxobs, "it.unibo.observer.Observer").
 static(observer).
  qactor( worker, ctxobs, "it.unibo.worker.Worker").
 static(worker).
