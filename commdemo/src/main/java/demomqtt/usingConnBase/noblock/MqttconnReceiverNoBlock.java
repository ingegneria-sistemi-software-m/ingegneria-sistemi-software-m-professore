package demomqtt.usingConnBase.noblock;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.mqtt.MqttConnectionBaseAsynch;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa MqttConnectionBase senza receive implicita in quanto implements MqttCallback
 * 
 * Un messaggio vorrebbe essere una richiesta di informazione, ma ...
 */

public class MqttconnReceiverNoBlock implements MqttCallback {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receivernoblock";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
    private MqttConnectionBaseAsynch mqttConn;

    //usiamo il costruttore che implica la receiver via callback
    public MqttconnReceiverNoBlock() {
    	mqttConn = new MqttConnectionBaseAsynch(  MqttBroker, name, topic,this  ) ;  //this ha la callback
    }
    
    public void doJob() {
    	new Thread() {
    		public void run() {
    	    	while( goon ) {
    		    	CommUtils.outgreen(name + " | doing something ... "   );
    		    	CommUtils.delay(1000);
    	    	}
    	    	System.exit(0);    			
    		}
    	}.start();
    }
  
	protected void elabMessage(String message) {
		CommUtils.outmagenta(name + " | messageArrived " + message + " on " + topic);	
			if( message.toString().equals("END") ) {
				goon = false;
				return;
			}
			try {
				IApplMessage msg =  new ApplMessage(  message.toString() ); //potrebbe dare Exception
//				CommUtils.outgreen(name + " |  msgid   = " + msgInput.msgId() );
//				CommUtils.outgreen(name + " |  msgtype = " + msgInput.msgType() );
//				CommUtils.outgreen(name + " |  emitter = " + msgInput.msgSender());
//				CommUtils.outgreen(name + " |  dest    = " + msgInput.msgReceiver());
//				CommUtils.outgreen(name + " |  content = " + msgInput.msgContent());

				if( msg.isRequest() ) {  	
					CommUtils.outred(name + " | vorrei rispondere alla richiesta, ma come? "  );
				}		 
				
			}catch( Exception e) {
				CommUtils.outred(name + "  message unknown "  );
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
