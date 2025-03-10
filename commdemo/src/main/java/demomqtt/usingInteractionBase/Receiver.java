package demomqtt.usingInteractionBase;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteractionBase;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 * Attende un messaggio cone receive bloccante e 
 * invia una risposta in caso di request
 */
public class Receiver {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receiver";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
	private MqttInteractionBase mqttConn;

	public Receiver() {
		CommUtils.outcyan(name + "  | CREATING"  );
		mqttConn = new MqttInteractionBase(MqttBroker, name, topic, false);
	}
	public Receiver(MqttInteractionBase mqttConn) {
		CommUtils.outcyan(name + "  | CREATING with mqttconn"  );
		this.mqttConn = mqttConn;
	}

	public void doJob() {
		new Thread() {
			public void run() {	
				try {
					while(  goon ) {
						String message = mqttConn.receiveMsg(); //Blocking		
						//CommUtils.outmagenta(name + " |  Received:" + message );
						elabMessage( message );
					}//while
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}
    
	protected void elabMessage(String message) {
		try {
			if( message.toString().equals("END") ) goon = false; 
			CommUtils.outmagenta(name + " | elabMessage " + message); 
			if( message.toString().contains("request") ) { //Faremo meglio in seguito ...
				CommUtils.outmagenta(name + " | rispondo alla richiesta usando una topic specificata dal supporto"  );
				mqttConn.reply("answer_to_"+message);
			}
			
			
//			IApplMessage msgInput = new ApplMessage(message.toString());
//			CommUtils.outmagenta(name + " | elabMessage an IApplMessage = " + msgInput); 		
//			CommUtils.outgreen(name + " |  msgid   = " + msgInput.msgId() );
//			CommUtils.outgreen(name + " |  msgtype = " + msgInput.msgType() );
//			CommUtils.outgreen(name + " |  emitter = " + msgInput.msgSender());
//			CommUtils.outgreen(name + " |  dest    = " + msgInput.msgReceiver());
//			CommUtils.outgreen(name + " |  content = " + msgInput.msgContent());
		}catch(Exception e) {
			CommUtils.outmagenta(name + " |  elabMessage just a String= " + message); 
			if( message.equals("END") ) goon = false;
		}		
	}

}
