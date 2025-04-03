package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * La comunicazione avviene a basso livello, non a livello QakActor
 */
public class CallerLifeActorMqtt {

//	protected IApplMessage cmdstart = CommUtils.buildDispatch("callertcp", "start", "start(ok)", "conway0");
//	protected IApplMessage cmdstop  = CommUtils.buildDispatch("callertcp", "stop", "stop(ok)", "conway0");
	
	public void doJob() {
        String brokerAddr       = "tcp://localhost:1883";
//        int port              = 8919;
        ProtocolType protocol = ProtocolType.mqtt;
        MqttInteraction conn = 
        		new MqttInteraction("callermqtt",brokerAddr, "guin","lifein");
        
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
		 new CallerLifeActorMqtt().doJob();
	 }
} 
