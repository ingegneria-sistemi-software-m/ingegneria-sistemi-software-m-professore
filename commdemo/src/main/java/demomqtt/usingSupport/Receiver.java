package demomqtt.usingSupport;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.mqtt.MqttSupport;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa MqttSupport.subscribe implementando MqttCallback
 * La ricezione di un messaggio sulla topic receiverIn
 * avviene nel metodo di callback messageArrived
 * mentre il receiver opera entro un Thread
 * 
 * Si noti il problema della richiesta
 */
public class Receiver implements MqttCallback{
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
	private MqttSupport mqttSupport = new MqttSupport();
	private String name;
	private boolean goon = true;
	private String receiverIn = "receiverIn";
	
	public Receiver(String name) {
		this.name = name;
		mqttSupport.connectToBroker(name,MqttBroker );
		mqttSupport.subscribe ( receiverIn, this );
		CommUtils.outcyan(name + " | CONSTRUCTED"  );
	}
	
	public void doJob() {
		new Thread() {
			public void run() {
				while( goon ) {
					CommUtils.outyellow(name + " | working ... Messages are perceived in messageArrived called by the support"  );	
					CommUtils.delay(250);
				}
				CommUtils.outcyan(name + " |  ENDS"  );		
				System.exit(0);
		}
		}.start();
	}

	@Override
	public void connectionLost(Throwable cause) {
		CommUtils.outred(name + " | connectionLost " + cause.getMessage() );
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		CommUtils.outmagenta(name + " | messageArrived " + message + " on " + topic);	
		if( message.toString().contains("request") ) { //Faremo meglio in seguito ...
			CommUtils.outmagenta(name + " | vorrei rispondere alla richiesta, ma come? "  );
		}
		if( message.toString().equals("END") ) goon = false;
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		CommUtils.outyellow(name + " | deliveryComplete " + token.getMessageId() );		
	}
}
