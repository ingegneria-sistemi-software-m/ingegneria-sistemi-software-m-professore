package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

 
/*
 * La comunicazione avviene  a livello QakActor
 */
public class CallerRaspactorMqtt {

	protected IApplMessage turnOn   = CommUtils.buildDispatch("callertcp", "turnOn",  "turnOn(ok)",  "raspactor");
	protected IApplMessage turnOff  = CommUtils.buildDispatch("callertcp", "turnOff", "turnOff(ok)", "raspactor");
	
	public void doJob() {
		CommUtils.outblue("callermqtt STARTS"  );
        String brokerAddr       = "tcp://192.168.137.1:1883"; //"tcp://192.168.1.68:1883"; //"tcp://test.mosquitto.org:1883"; //"tcp://broker.hivemq.com:1883"; //
        ProtocolType protocol   = ProtocolType.mqtt;
        MqttInteraction conn = 
        		new MqttInteraction("callermqtt",brokerAddr, "ledout","unibo/qak/raspactor");
        addObservation( conn );
        
        try {
        	
        	CommUtils.outblue("callermqtt turnOn"  );
       	 	conn.forward(turnOn);
       	 	CommUtils.delay(3000);
        	CommUtils.outblue("callermqtt  turnOff"  );
      	    conn.forward(turnOff);
        	
       	    CommUtils.delay(3000);
       	    CommUtils.outmagenta("callermqtt | ENDS"   );
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}
	}
	
	protected void addObservation(Interaction conn) {
		new Thread() {
			public void run() {
				try {
					while(true) {
						String m = conn.receiveMsg();
						CommUtils.outmagenta("mqtt observed:" + m);
					}
				} catch (Exception e) {
					CommUtils.outred("callermqtt addObservation ERROR:" + e.getMessage() );
				}
				
			}
		}.start();
	}

	 public static void main( String[] args ){
		 new CallerRaspactorMqtt().doJob();
	 }
} 
