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

public class CallerLifeActorCoap  {
	
	protected IApplMessage cmdstart = CommUtils.buildDispatch("callertcp", "startGame", "startGame(ok)", "conway0");
	protected IApplMessage cmdstop  = CommUtils.buildDispatch("callertcp", "stopGame", "stopGame(ok)", "conway0");
 	protected CoapObserveRelation relation;
	
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8920;
        ProtocolType protocol = ProtocolType.coap;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr+":"+port, "ctxconway0/conway0");
        
         
        try {
        	new Thread() {
        		public void run() {
        			addObservation(conn);
        		}
        	}.start();
        	
        	
        	CommUtils.outblue("callercoap  :" + cmdstart);
	       	conn.forward(cmdstart);
	        CommUtils.delay(8000);
        	CommUtils.outblue("callercoap  :" + cmdstop);
      	    conn.forward(cmdstop);

      	    
    		relation.proactiveCancel();
    		CommUtils.outmagenta("ActorObserver | ENDS"   );
    		System.exit(0);
 
		} catch (Exception e) {
 			CommUtils.outred("callercoap ERROR:" + e.getMessage() );
		}
        
    

	}
	
	protected void addObservation(Interaction conn) {
		//CoapClient client = new CoapClient("coap://localhost:8920/ctxconway0/conway0" );  
		
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
		 new CallerLifeActorCoap().doJob();
	 }
}
