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

public class CallerCoap  {
	
	protected IApplMessage req0 = CommUtils.buildRequest("callercoap", "req0", "req0(time)", "ms0");
 	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8919;
        ProtocolType protocol = ProtocolType.coap;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr+":"+port, "ctxqakms025/ms0");
        
         
        try {
        	CommUtils.outblue("callerCoap REQUEST:" + req0);
        	IApplMessage answer = conn.request(req0);
        	CommUtils.outmagenta("callerCoap ANSWER:" + answer);

		} catch (Exception e) {
 			CommUtils.outred("callerCoap ERROR:" + e.getMessage() );
		}
        
        addObservation(conn);

	}
	
	protected void addObservation(Interaction conn) {
		//CoapClient client = new CoapClient("coap://localhost:8919/ctxqakms025/ms0" );  
		
		CoapConnection coapConn = (CoapConnection)conn;
		CoapClient client = coapConn.getClient();
		
	    CommUtils.outblue("callerCoap addObservation client"  );
		CoapObserveRelation relation = client.observe(
				new CoapHandler() {
					@Override public void onLoad(CoapResponse response) {
						String content = response.getResponseText();
						CommUtils.outgreen("ActorObserver | value=" + content );
					}					
					@Override public void onError() {
						CommUtils.outred("OBSERVING FAILED  ");
					}
				});	
		
		CommUtils.delay(3000);
		relation.proactiveCancel();
		CommUtils.outblue("ActorObserver | ENDS"   );
		System.exit(0);
	}
	

	 public static void main( String[] args ){
		 new CallerCoap().doJob();
	 }
}
