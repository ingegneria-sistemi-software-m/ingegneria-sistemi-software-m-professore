package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;
 
public class OutForGui  {

//    private static OutInMasterGui mysingleton;
    private final String name = "OutInfForGui";
 	protected MqttConnectionBaseOut mqttConn;
    //protected MqttConnectionBaseOut mqttConnOut;
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
 			mqttConn = new MqttConnectionBaseOut( "tcp://localhost:1883", owner.getName()+"_out", topicout );
 			 		
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}


	
	public void display(String msg) {		    
		try {
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( mqttConn != null ) {
				CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				mqttConn.send(msg);
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}


 

 	
 

}
