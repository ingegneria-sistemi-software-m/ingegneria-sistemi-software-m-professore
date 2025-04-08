package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerLifeActorTcp {
	
	protected IApplMessage cmdstart = CommUtils.buildEvent("callertcp", "startthegame", "startthegame(ok)" );
	protected IApplMessage synch  = CommUtils.buildEvent("callertcp", "synch", "synch(ok)" );
	protected IApplMessage cmdstop = CommUtils.buildEvent("callertcp", "stopthecell", "stopthecell(ok)" );
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8360;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        try {
        	CommUtils.outblue("callertcp  :" + cmdstart);
        	 conn.forward(cmdstart);
        	 for( int i = 1; i<= 4; i++) {
	        	 CommUtils.delay(1000);  //ALLA CIECA per fine epoch
	         	 CommUtils.outblue("callertcp  :" + synch);
	       	     conn.forward(synch);
        	 }
        	 CommUtils.delay(1000); //SE ASSENTE L?EVENTO E' PERSO
         	 CommUtils.outblue("callertcp  :" + cmdstop);
       	     conn.forward(cmdstop);
         	
       	      
       	     CommUtils.outblue("callertcp BYE"  );
        	//System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerLifeActorTcp().doJob();
	 }
}
