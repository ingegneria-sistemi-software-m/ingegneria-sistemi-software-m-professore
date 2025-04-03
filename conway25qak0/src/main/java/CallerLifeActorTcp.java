package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerLifeActorTcp {
	
	protected IApplMessage cmdstart = CommUtils.buildDispatch("callertcp", "startGame", "startGame(ok)", "conway0");
	protected IApplMessage cmdstop  = CommUtils.buildDispatch("callertcp", "stopGame", "stopGame(ok)", "conway0");
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8920;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        try {
        	CommUtils.outblue("callertcp  :" + cmdstart);
        	 conn.forward(cmdstart);
        	 CommUtils.delay(8000);
         	CommUtils.outblue("callertcp  :" + cmdstop);
       	    conn.forward(cmdstop);
         	
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerLifeActorTcp().doJob();
	 }
}
