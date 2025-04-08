package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;

/*
 * Used by gamecontroller
 */
public class OutInForGui  {

//    private static OutInMasterGui mysingleton;
    private final String name = "OutInfForGui";
 	protected MqttConnection  mqttConn;
//    protected MqttConnectionBaseOut mqttConnOut;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 * Usato da gamecontroller
 */
 	
 	public OutInForGui(ActorBasic owner  ) {
 		this.owner    = owner;
 		this.topicout = "guiin";
 		connectMqttInOut();

	}

  	protected boolean chcekBrokerConnection() {
  		while( ! mqttConn.isConnected() ) {
	  			CommUtils.outcyan(name + " | waiting for mqttConn ... "  );
	  			CommUtils.delay(1000);
	  	}
	  	//CommUtils.outcyan(name + " | mqtt connection done " + mqttConn); 
	  	return true;
	}
  	
	protected void connectMqttInOut() {  
		try {
 			//mqttConn = new MqttInteraction( owner.getName()+"_outin", "tcp://192.168.1.132:1883", "lifein", topicout );
 			
 			mqttConn = owner.getMqtt().getMqttConn();
 			
//			if(chcekBrokerConnection())  CommUtils.outblue(name + " " +  owner.getName() + " | mqtt connection done " + mqttConn); 
 			if(chcekBrokerConnection()) mqttConn.subscribe("lifein",owner);
 			
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}


	
	public void display(String msg) {		    
		try {
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( chcekBrokerConnection() ) {
				CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				//mqttConn.forward(msg);
				mqttConn.publish("guiin",msg); 
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}



}
