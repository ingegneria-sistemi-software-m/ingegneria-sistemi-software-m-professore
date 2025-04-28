package main.java;

import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;


/*

 */
public class CallerMqtt {

	protected MqttInteraction conn;
	
	
	protected void connect() {
		CommUtils.outblue("callermqtt connect"   );
        String brokerAddr     = "tcp://localhost:1883";
        conn =  new MqttInteraction("callermqtt",brokerAddr, "guin","lifein");	
        CommUtils.outblue("callermqtt connected "  + conn );
	}
	
	protected void initSomeCell() {
	    try {
	    	CommUtils.outgreen("callermqtt initSomeCell"   );
	        conn.forward("clear");       
 
//	        CommUtils.delay(500);
//	    	conn.forward("cell-1-1");

	    	CommUtils.delay(500);
	    	conn.forward("cell-1-2");

//	    	CommUtils.delay(500);
//	    	conn.forward("cell-1-3");

	    	CommUtils.delay(500);
	    	conn.forward("cell-2-2");

	    	CommUtils.delay(500);
	    	conn.forward("cell-3-2");

 
		} catch (Exception e) {
				CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}	
	}

	protected void work() {
        try {
	    	CommUtils.delay(1000);
	    	
        	CommUtils.outgreen("callermqtt send start"   );
        	conn.forward("start");
        	
        	CommUtils.delay(3000);
        	
           	CommUtils.outgreen("callermqtt send stop"   );
       	    conn.forward("stop");
       	    
       	    conn.forward("exit");
        	
		} catch (Exception e) {
 			CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}
		
	}
	public void doJob() {
		connect();
        initSomeCell();
        CommUtils.delay(1000);
        work();
    	System.exit(0);
	}

	 public static void main( String[] args ){
		 new CallerMqtt().doJob();
	 }
} 
