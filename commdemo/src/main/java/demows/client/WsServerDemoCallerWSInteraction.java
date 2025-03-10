package demows.client;

import java.util.Observable;
//import javax.websocket.ClientEndpoint;
import unibo.basicomm23.interfaces.IObserver;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;

/*
 *
 */

public class WsServerDemoCallerWSInteraction implements IObserver{
    private Interaction conn;
    
    public WsServerDemoCallerWSInteraction() {
        try {     	
        	conn = ConnectionFactory.createClientSupport( ProtocolType.ws, "localhost:8887", "");
	        CommUtils.outyellow("WsServerDemoCallerWSInteraction on 8887" );        
	        ((WsConnection) conn).addObserver(this);
        } catch (Exception e) {
        	CommUtils.outred("WsServerDemoCallerWSInteraction | ERROR:" +e.getMessage());
        }    	   	
    }

     
    public void doJob( ) {
        try {
        	conn.forward("msg1");
        	String answer = conn.request("requestIngteraction_r1");
        	CommUtils.outmagenta("WsServerDemoCallerWSInteraction | answer:" + answer);
        } catch (Exception e) {
        	CommUtils.outred("WsServerDemoCallerWSInteraction | ERROR:" +e.getMessage());
        }    	
    }
    
	@Override 
	public void update(Observable o, Object arg) {
		//CommUtils.outyellow("WsServerDemoCallerWSInteraction | riceve da observale: " + o + " la info:" + arg);		
		update(arg.toString() );
	}


	@Override
	public void update(String message) {
		CommUtils.outgreen("WsServerDemoCallerWSInteraction | oseerva: " + message);
	}
 

    public static void main(String[] args) {
    	WsServerDemoCallerWSInteraction caller = new WsServerDemoCallerWSInteraction();
    	caller.doJob(); 
     	CommUtils.delay(10000); //To chcek broadcasted messages
    	CommUtils.outmagenta("WsServerDemoCallerWSInteraction | BYE" );
    	System.exit(0);
    }

 
} 