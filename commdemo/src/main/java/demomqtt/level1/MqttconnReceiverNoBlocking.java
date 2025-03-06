package demomqtt.level1;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.AbstractMqttConnCallback;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa oMqttConnectiont per trasmettere informazioni 
 */
public class MqttconnReceiverNoBlocking extends AbstractMqttConnCallback{
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receivernoblock";
	private String topic            = "unibo/conn";
    private MqttConnection mqttConn;

    public MqttconnReceiverNoBlocking(String name) {
    	super(name);
    	connectMqtt( ); 
    }
    
	protected void connectMqtt( ) {  
		try {
			mqttConn = new MqttConnection(   );  
			mqttConn.connect(name, MqttBroker);
			mqttConn.subscribe( topic, this   );
 		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}    

	@Override
	protected void elabArrivedNessage(String topic, MqttMessage msgInput) {
		CommUtils.outblue("receiver | perceives;" + msgInput); 		
	}

	@Override
	public void elaborate(IApplMessage message, Interaction conn) {
		CommUtils.outmagenta("AbstractMqttConnectionCallback | elaborate  " + message  );
	}

}
