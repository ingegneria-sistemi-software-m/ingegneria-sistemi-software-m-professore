%====================================================================================
% eurekaexample description   
%====================================================================================
dispatch( m1, m1(X) ).
request( r1, r1(X) ).
reply( r1reply, r1rely(X) ).  %%for r1
event( alarm, alarm(X) ).
%====================================================================================
context(ctxeurekademousage, "localhost",  "TCP", "8445").
context(ctxeureka, "discoverable",  "TCP", "0").
 qactor( aservice, ctxeureka, "external").
  qactor( aserviceusage, ctxeurekademousage, "it.unibo.aserviceusage.Aserviceusage").
 static(aserviceusage).
