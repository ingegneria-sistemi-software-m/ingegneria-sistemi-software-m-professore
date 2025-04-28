package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerServiceqakTcp {
	
	protected IApplMessage m1   = CommUtils.buildDispatch("callertcp", "m1",  "m1(ok)",  "aservice");
	protected IApplMessage r1   = CommUtils.buildRequest("callertcp", "r1",   "r1(ok)",  "aservice");
	
	public void doJob() {
        String hostAddr       = "localhost"; 
        int port              = 8443;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        try {
        	CommUtils.outblue("callertcp  :" + m1);
        	 conn.forward(m1);
       	    CommUtils.delay(1000);
        	CommUtils.outblue("callertcp  :" + r1);
       	    IApplMessage answer = conn.request(r1);
       	    CommUtils.outblue("callertcp  answer:" + answer);
      	    CommUtils.delay(3000);
      	    CommUtils.outmagenta("callertcp | ENDS"   );
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerServiceqakTcp().doJob();
	 }
}
