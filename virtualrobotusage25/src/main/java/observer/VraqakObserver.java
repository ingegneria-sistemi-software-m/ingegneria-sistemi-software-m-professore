package main.java.observer;

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

public class VraqakObserver {

	protected IApplMessage cmdl        = CommUtils.buildDispatch("vrqakobs", "move", "move(l)", "vrqak");
	protected IApplMessage cmdr        = CommUtils.buildDispatch("vrqakobs", "move", "move(r)", "vrqak");
	protected IApplMessage reqForward  = CommUtils.buildRequest("vrqakobs", "cmd", "cmd(w,800)", "vrqak");
	protected IApplMessage reqBack     = CommUtils.buildRequest("vrqakobs", "cmd", "cmd(s,800)", "vrqak");
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8125;
        ProtocolType protocol = ProtocolType.coap;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr+":"+port, "ctxvrqak/vrqak");
        
        new Thread() {
        	public void run() {
        		addObservation(conn);
        	}
        }.start();
        
        try {
        	CommUtils.outblue("callerCoap cmd:" + cmdl);
        	conn.forward(cmdl);

        	IApplMessage answer = conn.request(reqForward);
        	CommUtils.outmagenta("callerCoap ANSWER:" + answer);

        	answer = conn.request(reqBack);
        	CommUtils.outmagenta("callerCoap ANSWER:" + answer);

        	CommUtils.outblue("callerCoap cmd:" + cmdr);
        	conn.forward(cmdr);

		} catch (Exception e) {
 			CommUtils.outred("callerCoap ERROR:" + e.getMessage() );
		}
        

	}
	
	protected void addObservation(Interaction conn) {
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
		
//		CommUtils.delay(5000);
//		relation.proactiveCancel();
//		CommUtils.outblue("ActorObserver | ENDS"   );
// 		System.exit(0);
	}
	

	 public static void main( String[] args ){
		 new VraqakObserver().doJob();
	 }

}
