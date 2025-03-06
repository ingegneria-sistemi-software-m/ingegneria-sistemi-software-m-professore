package demomqtt.usingSupport;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttSupport;
import unibo.basicomm23.utils.CommUtils;


/*
 * Opera come sender di messaggi
 */
public class Sender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
	private MqttSupport mqttConn = new MqttSupport();
	private String name;
	private String receiverIn = "receiverIn";
	private int dt            = 700;

	public Sender(String name) {
		this.name = name;
		mqttConn.connectToBroker(name,MqttBroker );
	}
	
	public void doJob() {
		new Thread() {
			public void run() {	
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
					IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
					CommUtils.outblue(name + "  | SENDS "  ); 
					mqttConn.publish( receiverIn, msgEvent.toString()    );
					CommUtils.delay(dt);
					CommUtils.outblue(name + "  | SENDS "  ); 
					mqttConn.publish(  receiverIn, msgDispatch.toString() );
					CommUtils.delay(dt);
					CommUtils.outblue(name + "  | SENDS "  ); 
					mqttConn.publish(  receiverIn, msgRequest.toString()  );
					CommUtils.delay(dt);
					CommUtils.outblue(name + "  | SENDS "  ); 
					mqttConn.publish(  receiverIn, "work done" );
					CommUtils.delay(dt);
					CommUtils.outblue(name + "  | SENDS "  ); 
					CommUtils.outblue(name + "  | ENDS "  );		
					mqttConn.publish(  receiverIn, "END" );				
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}	
}
