package main.java;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;
 
public class OutInCellGui  {

    private final String name = "OutInMqtt";
    protected MqttConnection mqttConn;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 * Dispositivo di basso livello per la visualizzazione dello stato
 * di una cella su GUI
 * 
 * Si ricorda che l'input viene gestito dal perceiver che
 * gestisce gli eventi kernel_rawmsg 
 */
 	public OutInCellGui(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		mqttConn = owner.getMqtt().getMqttConn();
 	}
 
	
	public void display(String msg) {		    
		try {
			if( mqttConn != null ) {
				CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				mqttConn.publish(topicout, msg);
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}


}
