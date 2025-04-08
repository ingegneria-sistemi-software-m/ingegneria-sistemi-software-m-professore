package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;


/*
 * La comunicazione avviene  a livello QakActor
 */
public class CallerLifeActorMqttHighlevel {

	protected IApplMessage cmdstart = CommUtils.buildDispatch("callertcp", "startGame", "startGame(ok)", "conway0");
	protected IApplMessage cmdstop  = CommUtils.buildDispatch("callertcp", "stopGame", "stopGame", "conway0");
	
	public void doJob() {
        String brokerAddr       = "tcp://localhost:1883";
        ProtocolType protocol = ProtocolType.mqtt;
        MqttInteraction conn = 
        		new MqttInteraction("callermqtt",brokerAddr, "caller","unibo/qak/conway0");
        
        try {
        	CommUtils.outblue("callermqtt start"  );
       	 	conn.forward(cmdstart);
       	 	CommUtils.delay(8000);
        	CommUtils.outblue("callermqtt  stop"  );
      	    conn.forward(cmdstop);
        	
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerLifeActorMqttHighlevel().doJob();
	 }
} 
