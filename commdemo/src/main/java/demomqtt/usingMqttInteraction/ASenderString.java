package demomqtt.usingMqttInteraction;
 

import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class ASenderString {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com";
    private  String name;
	private MqttInteraction  mqttConn;
 	
	public ASenderString(String name) { 
		this.name = name;
		mqttConn = new MqttInteraction(name,  MqttBroker, "senderstringIn", "receiverstringIn");
		CommUtils.outblue(name + "  | CREATED"  );
		
		new Thread() {
		public void run() {
			observe();
		}
		}.start();
		
		
		
//		new Thread() {
//			public void run() {
//				doJob();
//			}
//		}.start();
	}
	
	protected void observe() {
		try {
			CommUtils.outred(name + " | observing ..."    );	
			String msg = mqttConn.receiveMsg();
			CommUtils.outred(name + " | msg:"  + msg );			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public  void doJob() {
		try {
//			CommUtils.outblue(name + " | SENDS alarm "  ); 							
//			mqttConn.forward(  "alarm" );
//
//			CommUtils.delay(1000);
//			CommUtils.outblue(name + " | SENDS  hello1"   ); 							
//			mqttConn.forward(  "hello1" );
//	
			CommUtils.delay(1000);
			CommUtils.outblue(name + " | SENDS request"  ); 							
			String answer = mqttConn.request(  "request" );    
			CommUtils.outblack(name + " | get answer="  + answer );					
//
//			//AGAIN   		
//			CommUtils.outblue(name + " | SENDS AGAIN the same request"  ); 							
//			answer = mqttConn.request( "request"   );
//			CommUtils.outblack(name + " | answer:"  + answer );	
//
				
				
			CommUtils.delay(1000);
			CommUtils.outblue(name + " | SENDS END "   ); 							
			mqttConn.forward(  "END" );

			CommUtils.outblue(name + " | BYE "  ); 	

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//work

 

	public static void main(String[] args) { 		 
		new ASenderString("senderstring");   
  
 	}

}
