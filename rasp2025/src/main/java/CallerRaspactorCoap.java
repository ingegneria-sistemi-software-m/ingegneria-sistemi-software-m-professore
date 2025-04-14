package main.java;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;

import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerRaspactorCoap  {
	
	protected IApplMessage turnOn   = CommUtils.buildDispatch("callertcp", "turnOn", "turnOn(ok)", "raspactor");
	protected IApplMessage turnOff  = CommUtils.buildDispatch("callertcp", "turnOff", "turnOff(ok)", "raspactor");
 	protected CoapObserveRelation relation;
	
	
	public void doJob() {
        String hostAddr       = "192.168.137.2";
        int port              = 8080;
        ProtocolType protocol = ProtocolType.coap;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr+":"+port, "ctxrasp/raspactor");
        
         
        try {
        	new Thread() {
        		public void run() {
        			addObservation(conn);
        		}
        	}.start();
        	
        	
        	CommUtils.outblue("callercoap  :" + turnOn);
	       	conn.forward(turnOn);
	        CommUtils.delay(3000);
        	CommUtils.outblue("callercoap  :" + turnOff);
      	    conn.forward(turnOff);

      	    CommUtils.delay(3000);
    		relation.proactiveCancel();
    		CommUtils.outmagenta("callercoap | ENDS"   );
    		System.exit(0);
 
		} catch (Exception e) {
 			CommUtils.outred("callercoap ERROR:" + e.getMessage() );
		}
        
    

	}
	
	protected void addObservation(Interaction conn) {
		//CoapClient client = new CoapClient("coap://192.168.1.248:8920/ctxledalone/led" );  
		
		CoapConnection coapConn = (CoapConnection)conn;
		CoapClient client = coapConn.getClient();
		
	    CommUtils.outblue("callerCoap addObservation client"  );
		relation = client.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
						CommUtils.outgreen("ActorObserver | value=" + content );
					}					
					@Override public void onError() {
						CommUtils.outred("OBSERVING FAILED  ");
					}
				});	
		
	}
	

	 public static void main( String[] args ){
		 new CallerRaspactorCoap().doJob();
	 }
}
