package demomqtt.usingConnBase.blocking;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.mqtt.MqttConnectionCallbackForReceive;
import unibo.basicomm23.utils.CommUtils;

/*
 *  Usa MqttConnectionBase con callback di receive implicita
 *  per eseguire receive bloccante sulla topic "unibo/conn"
 */
public class MqttconnReceiverBlocking {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receiver";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
    private MqttConnectionBase mqttConn;

    public MqttconnReceiverBlocking() {
    	//usiamo il costruttore che implica la creazione di una MqttConnectionCallbackForReceive
    	mqttConn = new MqttConnectionBase(  MqttBroker, name, topic) ;  
    } 
	
	public void doJob() {
		new Thread() {
			public void run() {		
			while(  goon ) {
				String message = mqttConn.receive(); //Blocking			
				elabMessage( message );
			}//while
			System.exit(0);
		}//run
		}.start();
 	}
    
	protected void elabMessage(String message) {
		try {
			if( message.toString().equals("END") ) goon = false; 
			CommUtils.outmagenta(name + " | elabMessage " + message); 
			if( message.toString().contains("request") ) { //Faremo meglio in seguito ...
				CommUtils.outmagenta(name + " | vorrei rispondere alla richiesta, ma come?  "  );
				//Forse potrei usare una topic per la risposta ..
			}
			
			
//			IApplMessage msgInput = new ApplMessage(message.toString());
//			CommUtils.outmagenta(name + " | elabMessage an IApplMessage = " + msgInput); 		
//			CommUtils.outgreen(name + " |  msgid   = " + msgInput.msgId() );
//			CommUtils.outgreen(name + " |  msgtype = " + msgInput.msgType() );
//			CommUtils.outgreen(name + " |  emitter = " + msgInput.msgSender());
//			CommUtils.outgreen(name + " |  dest    = " + msgInput.msgReceiver());
//			CommUtils.outgreen(name + " |  content = " + msgInput.msgContent());
		}catch(Exception e) {
			CommUtils.outmagenta(name + " |  elabMessage just a String=" + message); 
			if( message.equals("END") ) goon = false;
		}		
	}
}
