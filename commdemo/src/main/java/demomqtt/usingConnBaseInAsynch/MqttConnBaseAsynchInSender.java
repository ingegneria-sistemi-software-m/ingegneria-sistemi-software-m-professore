package demomqtt.usingConnBaseInAsynch;

import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;

public class MqttConnBaseAsynchInSender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sendersynchin";
	private String topic            = "unibo/conn";
    private MqttConnectionBaseOut mqttConn;

	public MqttConnBaseAsynchInSender() {
		mqttConn = new MqttConnectionBaseOut(  MqttBroker, name, topic ) ; 
		doJob();
	}
	
	public void doJob() {
		CommUtils.outblue(name + " | SENDS hello1  "  ); 
		try {
			mqttConn.send(  "hello1"   );
			CommUtils.delay(1000);
			CommUtils.outblue(name + " | SENDS request " ); 
			mqttConn.send( "request"  );
			CommUtils.delay(1000);
			CommUtils.outblue(name + " | SENDS ENDS "  );		
			mqttConn.send(  "END" );
		} catch (Exception e) {
 			e.printStackTrace();
		}

	}
}
