%====================================================================================
% virtualrobotusage25 description   
%====================================================================================
dispatch( stepdone, stepdone(X) ). %step ok
dispatch( stepfailed, stepfailed(X) ). %step ko
event( sonardata, sonar(DISTANCE) ). %emesso dal SONAR
dispatch( vrinfo, vrinfo(A,B) ). %inviato dal supportp
event( vrinfo, vrinfo(A,B) ). %emesso dal supportp
event( obstacle, obstacle(X) ). %emesso dal supportp
%====================================================================================
context(ctxvrusage25, "localhost",  "TCP", "8120").
 qactor( vrbasicmoves, ctxvrusage25, "it.unibo.vrbasicmoves.Vrbasicmoves").
 static(vrbasicmoves).
  qactor( perceiver, ctxvrusage25, "it.unibo.perceiver.Perceiver").
 static(perceiver).
