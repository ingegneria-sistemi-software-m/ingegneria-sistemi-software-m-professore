package demomqtt.usingConnBaseInAsynch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.mqtt.MqttConnectionBaseInAsynch;
import unibo.basicomm23.utils.CommUtils;

public class MqttConnBaseAsynchInReceiver implements MqttCallback{
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receiverasynchin";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
    private String prefix           = "       ";
    private MqttConnectionBaseInAsynch mqttConn;
    
    public MqttConnBaseAsynchInReceiver( ) {
    	mqttConn = new MqttConnectionBaseInAsynch(  MqttBroker, name, topic, this ) ; 
     	doJob();
    }
    
	public void doJob() {
		new Thread() {
			public void run() {		
			while(  goon ) {
				String message;
				try {
					CommUtils.outgreen(prefix + name + " | working");
					CommUtils.delay(500);
				} catch (Exception e) {
					e.printStackTrace();
				}  
 			}//while
			CommUtils.outmagenta(prefix + name + " | ENDS " );
			System.exit(0);
		}//run
		}.start();
 	}

	@Override
	public void connectionLost(Throwable cause) {
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String msg = message.toString();
		CommUtils.outmagenta(prefix + name + " | messageArrived " + msg + " on " + topic);	
		if( msg.equals("END") ) goon=false;
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}    

}
