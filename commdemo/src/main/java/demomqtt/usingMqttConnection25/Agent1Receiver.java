package demomqtt.usingMqttConnection25;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection25;  
import unibo.basicomm23.utils.CommUtils;

public class Agent1Receiver {
    private String name ;
	private final String MqttBroker = "tcp://192.168.1.132:1883"; //"tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
	private boolean goon            = true;
	private IApplMessage msgEvent   ;
	private MqttConnection25 mqttConn;

	public Agent1Receiver(String name) {
		this.name = name;
		CommUtils.outcyan(name + "  | CREATING"  );
		mqttConn = new MqttConnection25( name, MqttBroker, "agent1In", "agent2In");
		msgEvent = CommUtils.buildEvent(name, "alarm", "alarm(fire)" );
	}
	public void doJob() {
		
		IApplMessage notifyReceiverStarted = CommUtils.buildEvent(name, "info", "receiverStarted");
		try {
			mqttConn.forward( notifyReceiverStarted );
		} catch (Exception e) {
 			e.printStackTrace();
		} 
		
		new Thread() {
			public void run() {	
				try {
					while(  goon ) {
						CommUtils.outgreen(name + "  | RECEIVING ... "  );
						IApplMessage message = mqttConn.receive(); //Blocking		
						CommUtils.outmagenta(name + " |  Received:" + message );						
						if( message != null ) elabMessage( message );
						
					}//while
					CommUtils.delay(2000);
					CommUtils.outmagenta(name + " |  BYE"   );
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}
    
	protected void elabMessage(IApplMessage message) {
		try {
		    if( message.msgContent().contains("alarm") ) {
		    	CommUtils.outblue(name + " | elab alarm "  ); 
		    }
			if (message.msgType().equals("request")) {
				CommUtils.outblue(name + " |  REPLY TO " + message); 
				//reply id deve essere quello della richiesta 
				IApplMessage replyMsg = 
						CommUtils.buildReply(name, message.msgId(), 
								"answer_to_"+message.msgSender()+"_"+message.msgId(), message.msgSender());
				mqttConn.reply(replyMsg);
				
				//Dopo la risposta emette un evebto di allarme che dovrebbe essere perceito da agent2
				CommUtils.outblue(name + " |  forward event to test ... " + msgEvent);
				mqttConn.forward( msgEvent );  //invia su agent2In
			}
			else if( message.msgContent().contains("end") ) {
	            CommUtils.outblue(name + " |  END" );
	            goon = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) { 		 
		Agent1Receiver agent1     = new Agent1Receiver("agent1receiver");  
 		agent1.doJob();   
 	}
	
}
