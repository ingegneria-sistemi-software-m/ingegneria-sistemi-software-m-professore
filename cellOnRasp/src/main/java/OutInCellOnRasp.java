package main.java;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*
 * Used by 
 */
public class OutInCellOnRasp  {

    private final String name = "OutInCellOnRasp";
 	protected MqttConnection  mqttConn;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/* 
 
 */
 	
 	public OutInCellOnRasp(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		connectMqttInOut();
	}

 
	protected void connectMqttInOut() {  
		try {
			
			mqttConn = owner.getMqtt().getMqttConn();		
			chcekBrokerConnection();
// 		    if(chcekBrokerConnection()) mqttConn.subscribe("lifein",owner); //genera kernel_rawmsg
			
	
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}

  	protected boolean chcekBrokerConnection() {
  		while( ! mqttConn.isConnected() ) {
	  			CommUtils.outcyan(name + " | waiting for mqttConn ... "  );
	  			CommUtils.delay(200);
	  	}
	  	CommUtils.outcyan(name + " | mqtt connection done " + owner.getName()); 
	  	return true;
	}
	
	public void display(String msg) {		    
		try {
//			CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( mqttConn != null ) {
				//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				//mqttConn.forward(msg);
				mqttConn.publish("guiin",msg);
			}
 
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}

 
 
}
