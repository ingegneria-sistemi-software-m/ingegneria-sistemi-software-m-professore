package demows.client;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.Connection;
import unibo.basicomm23.utils.ConnectionFactory;


/*
 *
 */

public class WsServerDemoCallerConnection {
    private Interaction conn;
    
    public WsServerDemoCallerConnection() {
        try {     	
        	conn = ConnectionFactory.createClientSupport( ProtocolType.ws, "localhost:8887", "");
	        CommUtils.outyellow("WsServerDemoCallerConnection on 8887" );        
         } catch (Exception e) {
        	CommUtils.outred("WsServerDemoCallerConnection | ERROR:" +e.getMessage());
        }    	   	
    }

     
    public void doJob( ) {
        try {
//        	Connection c = (Connection) conn;
//        	c.trace      = true;
        	CommUtils.outmagenta("WsServerDemoCallerConnection | forward"  );
        	conn.forward("msg1");  //fire and forget
        	CommUtils.outmagenta("WsServerDemoCallerConnection | request"  );
        	String answer = conn.request("requestIngteraction_r1");  //blocking
        	CommUtils.outmagenta("WsServerDemoCallerConnection | answer:" + answer);
        } catch (Exception e) {
        	CommUtils.outred("WsServerDemoCallerConnection | ERROR:" +e.getMessage());
        }    	
    }
 
    public static void main(String[] args) {
    	WsServerDemoCallerConnection caller = new WsServerDemoCallerConnection();
    	caller.doJob(); 
    	CommUtils.outmagenta("WsServerDemoCallerConnection | BYE" );
    	System.exit(0);
    }

 
} 