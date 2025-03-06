package demomqtt.level1;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa oMqttConnectiont per trasmettere informazioni 
 */
public class MqttconnSenderString {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sender";
	private String topic            = "unibo/conn";
    private MqttConnection mqttConn;

    public MqttconnSenderString() {
    	connectMqtt( ); 
    }
    
	protected void connectMqtt( ) {  
		try {
			mqttConn = new MqttConnection(   );  
			mqttConn.connect(name, MqttBroker);
 		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}    
	
	public void doJob() {
		new Thread() {
			public void run() {
				CommUtils.outblue("sender | STARTS "  ); 
				mqttConn.publish( topic,  "hello1" );
				mqttConn.publish( topic,  "hello2" );
				mqttConn.publish( topic,  "hello2" );
				CommUtils.outblue("sender | ENDS "  );		
			}
		}.start();
	}
    
}
