package demomqtt.usingMqttConnection25;
 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection25;
import unibo.basicomm23.utils.CommUtils;

public class Agent2Sender {
	private final String MqttBroker = "tcp://192.168.1.132:1883";//"tcp://192.168.1.132:1883"; //"tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private  String name;
	private MqttConnection25  mqttConn;
	private boolean receiverStarted = false;
	
	public Agent2Sender(String name) { 
		this.name = name;
		CommUtils.outblue(name + "  | CREATING"  );
		mqttConn = new MqttConnection25(name,  MqttBroker, "agent2In", "agent1In");
	}
	
	/*
	 * Se il sender parte prima del receiver, la request non viene percepita
	 * Ppichè la receive è bloccante, il sender si ferma.
	 * CI VORREBBE un request con timeut .... 
	 */
	public void doJob() {
		activateReceive();  
		int i = 0;
		while( !  receiverStarted && i++<5){
			CommUtils.outcyan("please, activate the receiver ..." + i );
			CommUtils.delay(1000);
		}
		//Comincio comunque, sperando che il reciver sia partito prima ...
		new Thread() {
			public void run() {
				
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent(name, "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",end)", "agent1" );
					IApplMessage msgRequest  = CommUtils.buildRequest(name, "info", "info("+name+",move)", "agent1" );
					
					CommUtils.outblue(name + " | SENDS event alarm "  ); 							
 					mqttConn.forward(  msgEvent );
			
 					CommUtils.delay(1000);
					CommUtils.outblue(name + " | SENDS request"  ); 							
					IApplMessage answer = mqttConn.request(  msgRequest ); //Bloccante
					CommUtils.outblack(name + " | answer:"  + answer );					
  
					//AGAIN   		
					CommUtils.outblue(name + " | SENDS AGAIN request"  ); 							
					answer = mqttConn.request(  msgRequest   );
					CommUtils.outblack(name + " | answer:"  + answer );	
// 
					CommUtils.outblue(name + " | SENDS cmd to end "  ); 							
 					mqttConn.forward(  msgDispatch );
 					
 					CommUtils.outblue(name + " | BYE "  ); 	
					System.exit(0);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}	

	public void activateReceive() {
		new Thread() {
			public void run() {	
				try {
					while(  true ) {
						CommUtils.outgreen(name + "  | RECEIVING ... "  );
						IApplMessage message = mqttConn.receive(); //Blocking	!!!!	
						CommUtils.outmagenta(name + " perceives:" + message.toString());						
						if( message.msgContent().contains("receiverStarted")) receiverStarted=true;
					}//while
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}

	public static void main(String[] args) { 		 
		Agent2Sender agent2     = new Agent2Sender("agent2sender");   
 		agent2.doJob();   
 	}

}
