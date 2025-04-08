package main.java;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;


/*
 * La comunicazione avviene a basso livello, non a livello QakActor
 */
public class CallerLifeActorMqttLowlevel {

	
	public void doJob() {
        String brokerAddr       = "tcp://192.168.1.132:1883";  //con localhost non va
        ProtocolType protocol = ProtocolType.mqtt;
        MqttInteraction conn = 
        		new MqttInteraction("callermqtt",brokerAddr, "caller","lifein");
        
        try {
        	CommUtils.outblue("callermqtt start"  );
       	 conn.forward("start");
       	 CommUtils.delay(8000);
        	CommUtils.outblue("callermqtt  stop"  );
      	    conn.forward("stop");
        	
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerLifeActorMqttLowlevel().doJob();
	 }
} 
