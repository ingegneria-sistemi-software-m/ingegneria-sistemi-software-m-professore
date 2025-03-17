package demomqtt.usingMqttInteraction;
 

import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class AnotherSenderString {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com";
    private  String name;
	private MqttInteraction  mqttConn;
 	
	public AnotherSenderString(String name) { 
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

		
		
		//Osserva 
//		new Thread() {
//			public void run() {
//				try {
//				CommUtils.outred(name + " | RECEIVING ------------------ "  ); 	
//				while( true ) {
//					observe();
////					String msg = mqttConn.receiveMsg();
////					CommUtils.outred(name + " | msg:"  + msg );	
//				}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}			
//		     }//run
//		}.start();
	}
	
	protected void observe() {
		try {
			CommUtils.outcyan(name + " | observing ..."    );	
			String msg = mqttConn.receiveMsg();
			CommUtils.outcyan(name + " | msg:"  + msg );			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
 
	public  void doJob() {
		try {
//			CommUtils.outblue(name + " | SENDS anotheralarm "  ); 							
//			mqttConn.forward(  "anotheralarm" );
//
//			
//			CommUtils.delay(1000);
//			CommUtils.outred(name + " | SENDS anotherrequest"  ); 							
//			String answer = mqttConn.request(  "anotherrequest" );    
//			CommUtils.outred(name + " | get answer="  + answer );					
			
//			CommUtils.outred(name + " | RECEIVING ------------------ "  ); 	
//			while( true ) {
//				String msg = mqttConn.receiveMsg();
//				CommUtils.outred(name + " | msg:"  + msg );	
//			}

 			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//work

 

	public static void main(String[] args) { 		 
		new AnotherSenderString("anothersenderstring");   
  
 	}

}
