package demomqtt.usingMqttInteraction;
 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class ASender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com";
    private  String name;
	private MqttInteraction  mqttConn;
	private boolean receiverStarted = false;
	
	
	public ASender(String name) { 
		this.name = name;
		mqttConn = new MqttInteraction(name,  MqttBroker, "senderIn", "receiverIn");
		CommUtils.outblue(name + "  | CREATED"  );
		new Thread() {
			public void run() {
				doJob();
			}
		}.start();
	}
	
	/*
	 * Se il sender parte prima del receiver, la request non viene percepita
	 * Ppichè la receive è bloccante, il sender si ferma.
	 * CI VORREBBE un request con timeut .... 
	 */
	
	protected void informTheUser() {
		new Thread() {
			public void run() {
				int i = 0;
				while( !  receiverStarted && i++<5){
					CommUtils.outcyan(name + " |  receiver not started ..." + i );
					CommUtils.delay(1000);
				}	
//				if( ! receiverStarted ) CommUtils.outred(name + " SORRY: receiver not started"  );
			}
		}.start();		
	}
	
	protected boolean receiverStarted() {
		try {
//			informTheUser();   //OPZIONALE 
			CommUtils.outcyan("Waits for start"  );
			IApplMessage message = mqttConn.receive();
			CommUtils.outcyan(name + " perceives:" + message.toString());						
 			if( message.msgContent().contains("receiverStarted")) receiverStarted=true;
 			return true;
		} catch (Exception e) {
			return false;
		} 		
	}
	public 
	void doJob() {
		if( receiverStarted() ) work();
//		if( receiverStarted() ) workWithStrings();  
		else CommUtils.outred(name + " SORRY: receiver not started"  );
	}
		
		protected void work() {
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent(name, "alarm", "alarm(fire)" );
					IApplMessage endDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",endofwork)", "agent1" );
					IApplMessage msgDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",do)", "agent1" );
					IApplMessage msgRequest  = CommUtils.buildRequest(name, "info", "info("+name+",move)", "agent1" );
					
					CommUtils.outblue(name + " | SENDS event alarm "  ); 							
 					mqttConn.forward(  msgEvent );

// 					CommUtils.delay(1000);
//					CommUtils.outblue(name + " | SENDS  " + msgDispatch ); 							
// 					mqttConn.forward(  msgDispatch );
			
 					CommUtils.delay(1000);
					CommUtils.outblue(name + " | SENDS request"  ); 							
					IApplMessage answer = mqttConn.request(  msgRequest ); //Bloccante
					CommUtils.outblack(name + " | answer:"  + answer );					
  
//					//AGAIN   		
//					CommUtils.outblue(name + " | SENDS AGAIN the same request"  ); 							
//					answer = mqttConn.request(  msgRequest   );
//					CommUtils.outblack(name + " | answer:"  + answer );	
// 
 					
 					
 					CommUtils.delay(1000);
					CommUtils.outblue(name + " | SENDS  " + endDispatch ); 							
 					mqttConn.forward(  endDispatch );

 					CommUtils.delay(3000);
 					CommUtils.outblue(name + " | BYE "  ); 	
					//System.exit(0);  //LO fa il receiver
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//work

		protected void workWithStrings() {
			try {
//				IApplMessage msgEvent    = CommUtils.buildEvent(name, "alarm", "alarm(fire)" );
//				IApplMessage endDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",endofwork)", "agent1" );
//				IApplMessage msgDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",do)", "agent1" );
//				IApplMessage msgRequest  = CommUtils.buildRequest(name, "info", "info("+name+",move)", "agent1" );
				
				CommUtils.outblue(name + " | SENDS event alarm "  ); 							
				mqttConn.forward(  "alarm" );

				CommUtils.delay(1000);
				CommUtils.outblue(name + " | SENDS  "   ); 							
				mqttConn.forward(  "hello1" );
		
				CommUtils.delay(1000);
				CommUtils.outblue(name + " | SENDS request"  ); 							
				String answer = mqttConn.request(  "givetime" ); //Bloccante
				CommUtils.outblack(name + " | answer:"  + answer );					
//
//				//AGAIN   		
				CommUtils.outblue(name + " | SENDS AGAIN the same request"  ); 							
				answer = mqttConn.request( "givetime"   );
				CommUtils.outblack(name + " | answer:"  + answer );	
//
					
					
				CommUtils.delay(1000);
				CommUtils.outblue(name + " | SENDS  "   ); 							
				mqttConn.forward(  "endofwork" );

				CommUtils.outblue(name + " | BYE "  ); 	

				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//work


//	public void activateReceive() {
//		new Thread() {
//			public void run() {	
//				try {
//					while(  true ) {
//						CommUtils.outcyan(name + "  | RECEIVING on ... "  );
//						IApplMessage message = mqttConn.receive(); //Blocking	!!!!	
//						CommUtils.outcyan(name + " perceives:" + message.toString());						
//						if( message.msgContent().contains("receiverStarted")) receiverStarted=true;
//					}//while
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//		}//run
//		}.start();
// 	}

	public static void main(String[] args) { 		 
		ASender agent2     = new ASender("agent2sender");   
 		//agent2.doJob();   
 	}

}
