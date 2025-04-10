package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*
 * Used by 
 */
public class OutInCellGui  {

//    private static OutInCellGui mysingleton;
    private final String name = "OutInCellGui";
 	protected MqttConnection  mqttConn;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 
 */
 	
 	public OutInCellGui(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		connectMqttInOut();
	}

 
	protected void connectMqttInOut() {  
		try {
			//UNA CONESSIONE di owner.getName() ESISTE GIA per via di mqttBroker "localhost" : 1883 eventTopic "..."
			//mqttConn SI AGGIUNGE E BISOGNA ELIMNARE eventTopic "lifein"
//			mqttConn = new MqttInteraction( owner.getName()+"_outin", "tcp://192.168.1.132:1883", "lifein", topicout );
			
			mqttConn = owner.getMqtt().getMqttConn();			
 		    if(chcekBrokerConnection()) mqttConn.subscribe("lifein",owner); //genera kernel_rawmsg
//			if(chcekBrokerConnection())  CommUtils.outgreen(name + " " +  owner.getName() + " | mqtt connection done " + mqttConn); 
			
	
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
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
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
