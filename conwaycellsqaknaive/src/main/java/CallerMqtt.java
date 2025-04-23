package main.java;

import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;


/*

 */
public class CallerMqtt {

	protected MqttInteraction conn;
	
	
	protected void connect() {
		CommUtils.outblue("callermqtt connectt"   );
        String brokerAddr     = "tcp://192.168.1.132:1883";
        conn =  new MqttInteraction("callermqtt",brokerAddr, "guin","lifein");	
        CommUtils.outblue("callermqtt connected "  + conn );
	}
	
	protected void initSomeCell() {
	    try {
	    	CommUtils.outblue("callermqtt initSomeCell"   );
	        conn.forward("clear");       
 
//	        CommUtils.delay(500);
//	    	conn.forward("cell-1-1");

	    	CommUtils.delay(500);
	    	conn.forward("cell-1-2");

	    	CommUtils.delay(500);
	    	conn.forward("cell-2-1");
	    	
//	    	CommUtils.delay(500);
//	    	conn.forward("cell-2-2");
		} catch (Exception e) {
				CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}	
	}

	protected void work() {
        try {
        	CommUtils.outblue("callermqtt send start"   );
        	conn.forward("start");
        	
        	CommUtils.delay(3000);
        	
           	CommUtils.outblue("callermqtt send stop"   );
       	    conn.forward("stop");
        	
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
