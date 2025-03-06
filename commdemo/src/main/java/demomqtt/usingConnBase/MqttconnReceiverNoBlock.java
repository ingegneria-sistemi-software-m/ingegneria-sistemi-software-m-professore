package demomqtt.usingConnBase;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 *  
 */
public class MqttconnReceiverNoBlock implements MqttCallback {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receivernoblock";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
    private MqttConnectionBase mqttConn;

    //usiamo il costruttore che implica la receiver via callback
    public MqttconnReceiverNoBlock() {
    	mqttConn = new MqttConnectionBase(  MqttBroker, name, topic, this) ;  
    }
    
    public void doJob() {
    	new Thread() {
    		public void run() {
    	    	while( goon ) {
    		    	CommUtils.outgreen(name + " | doing something ... "   );
    		    	CommUtils.delay(50);
    	    	}
    	    	System.exit(0);    			
    		}
    	}.start();
    }
  
	protected void elabMessage(String message) {
		try {
			IApplMessage msgInput = new ApplMessage(message.toString());
			CommUtils.outmagenta(name + " | elabMessage an IApplMessage = " + msgInput); 		
//			CommUtils.outgreen(name + " |  msgid   = " + msgInput.msgId() );
//			CommUtils.outgreen(name + " |  msgtype = " + msgInput.msgType() );
//			CommUtils.outgreen(name + " |  emitter = " + msgInput.msgSender());
//			CommUtils.outgreen(name + " |  dest    = " + msgInput.msgReceiver());
//			CommUtils.outgreen(name + " |  content = " + msgInput.msgContent());
		}catch(Exception e) {
			CommUtils.outmagenta(name + " | elabMessage just a String= " + message); 
			if( message.equals("END") ) goon=false;
		}		
	}


	@Override
	public void connectionLost(Throwable cause) {
 	}
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		elabMessage(message.toString());		
	}
 	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
 	}
    
}
