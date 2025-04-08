package main.java;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;
 
public class OutForGui  {


    private final String name = "OutForGui";
 	protected MqttConnection  mqttConn;
  	protected ActorBasic owner; 
  	protected String topicout = "guiin";
 	
/*
 */
 	
 	public OutForGui(ActorBasic owner  ) {
 		this.owner    = owner;
   		connectMqttOut();

	}


	protected void connectMqttOut() {  
		try {
 			//mqttConn = new MqttConnectionBaseOut( "tcp://192.168.1.132:1883", owner.getName()+"_out", topicout );
 			mqttConn = owner.getMqtt().getMqttConn(); 		
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}

  	protected boolean chcekBrokerConnection() {
  		while( ! mqttConn.isConnected() ) {
	  			CommUtils.outcyan(name + " | waiting for mqttConn ... "  );
	  			CommUtils.delay(200);
	  	}
	  	//CommUtils.outcyan(name + " | mqtt connection done " + mqttConn); 
	  	return true;
	}
	
	public void display(String msg) {		    
		try {
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( chcekBrokerConnection() ) {
//				CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				mqttConn.publish(topicout,msg); //send(msg);
			}
		} catch (Exception e) {
 			CommUtils.outred(name +" | display ERROR: " + e.getMessage());
		}		
	}


 

 	
 

}
