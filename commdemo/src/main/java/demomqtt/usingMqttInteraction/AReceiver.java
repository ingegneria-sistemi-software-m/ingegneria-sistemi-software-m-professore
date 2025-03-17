package demomqtt.usingMqttInteraction;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class AReceiver {
    private String name ;
	private final String MqttBroker = "tcp://localhost:1883"; //"tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
	private boolean goon            = true;
	private MqttInteraction mqttConn;

	public AReceiver(String name) {
		this.name = name;

		mqttConn = new MqttInteraction( name, MqttBroker, "receiverIn", "senderIn");
		CommUtils.outmagenta("        " + name + "  | CREATED"  );
		new Thread() {
			public void run() {
				doJob();
			}
		}.start();
	}
	public void doJob() {
//		CommUtils.outmagenta("        " +name + "  | doJob ... "  );
		IApplMessage notifyReceiverStarted = CommUtils.buildEvent(name, "info", "receiverStarted");
		try {
//			CommUtils.delay(2000);
			CommUtils.outmagenta("        " + name + "  | emits " +  notifyReceiverStarted + " on " + mqttConn);
			mqttConn.forward( notifyReceiverStarted );
		} catch (Exception e) {
 			e.printStackTrace();
		} 
		
		new Thread() {
			public void run() {	
				try {
					while(  goon ) {
//						CommUtils.outgreen("        " +name + "  | RECEIVING ... "  );
//						IApplMessage message = mqttConn.receive(); //Blocking	
						String message = mqttConn.receiveMsg(); //Blocking
						CommUtils.outmagenta("        " +name + " |  Received:" + message );						
						if( message != null ) elabMessage( message );
						
					}//while
//					CommUtils.delay(2000);
					CommUtils.outmagenta("        " +name + " |  BYE"   );
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}
    
	protected void elabMessage(String inputmessage) {
		try {
			
			IApplMessage message = new ApplMessage(inputmessage);
			
		    if( message.isEvent() ) {
		    	CommUtils.outblue("        " +name + " | elab event " + message ); 
		    }
		    else if (message.isRequest()) {
				CommUtils.outblue("        " +name + " |  elab request " + message); 
				//reply id deve essere quello della richiesta 
				IApplMessage replyMsg = 
						CommUtils.buildReply(name, message.msgId(), 
								"answer_to_"+message.msgSender()+"_"+message.msgId(), message.msgSender());
				mqttConn.reply(replyMsg);
				
				//Dopo la risposta emette un evebto di allarme che dovrebbe essere percepito dal sender
//				CommUtils.outblue("        " +name + " |  forward event to test ... " + msgEvent);
//				mqttConn.forward( msgEvent );  //invia su agent2In
			}
			else if( message.isDispatch()  ) {
				CommUtils.outblue("        " +name + " |  elab dispatch "  + message);
	            if( message.msgContent().contains("endofwork") ) {
 	            	goon = false;
	            }	            
			}
		} catch (Exception e) {
			CommUtils.outblue("        " +name + " | elab bare string " + inputmessage );
			if( inputmessage.equals("endofwork") ) goon = false;
			else elabARequestString( inputmessage );
			//e.printStackTrace();
		}
		
	}
	
	protected void elabARequestString(String msg) {
		if( msg.equals("givetime"))
			try {
				mqttConn.reply("REPLY about_the_time ");
			} catch (Exception e) {
 				e.printStackTrace();
			}
	}
	
	


	public static void main(String[] args) { 		 
		AReceiver agent1     = new AReceiver("agent1receiver");  
 		//agent1.doJob();   
 	}
	
}
