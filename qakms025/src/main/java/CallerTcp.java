package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerTcp {
	
	protected IApplMessage req0 = CommUtils.buildRequest("callertcp", "req0", "req0(time)", "ms0");
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8919;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        try {
        	CommUtils.outblue("callertcp REQUEST:" + req0);
        	IApplMessage answer = conn.request(req0);
        	CommUtils.outmagenta("callertcp ANSWER:" + answer);

//          Tenta di ricevere informazioni dal servizio      
        	/*
        	CommUtils.outcyan("callertcp receiveMsg ... " );
         	String m = conn.receiveMsg();
         	CommUtils.outmagenta("callertcp m:" + m);
        	*/
        	
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerTcp().doJob();
	 }
}
