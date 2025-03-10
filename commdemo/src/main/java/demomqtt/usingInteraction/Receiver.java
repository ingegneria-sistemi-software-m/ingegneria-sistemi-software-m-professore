package demomqtt.usingInteraction;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class Receiver {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receiver";
	private String topic            = "unibo/conn1";
	private boolean goon            = true;
	private MqttInteraction mqttConn;

	public Receiver() {
		CommUtils.outcyan(name + "  | CREATING"  );
		mqttConn = new MqttInteraction( MqttBroker, name, topic,true );
	}
	public void doJob() {
		new Thread() {
			public void run() {	
				try {
					while(  goon ) {
						IApplMessage message = mqttConn.receive(); //Blocking		
						//CommUtils.outmagenta(name + " |  Received:" + message );
						elabMessage( message );
						
						if (message.msgType().equals("request")) {
							CommUtils.outmagenta(name + " |  REPLY TO " + message); 
							//reply id deve essere quello della richiesta 
							IApplMessage replyMsg = 
									CommUtils.buildReply(name, message.msgId(), 
											"answer_to_"+message.msgSender()+"_"+message.msgId(), message.msgSender());
							mqttConn.reply(replyMsg);
						}
						else if( message.msgContent().contains("end") ) {
                            CommUtils.outmagenta(name + " |  END" );
                            goon = false;
						}
					}//while
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}
    
	protected void elabMessage(IApplMessage message) {
		
	}
	
	
}
