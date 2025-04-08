package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.mqtt.MqttInteraction;

import unibo.basicomm23.utils.CommUtils;
 
public class OutInCellGui  {

//    private static OutInCellGui mysingleton;
    private final String name = "OutInMqtt";
 	protected MqttInteraction mqttConn;
    protected MqttConnectionBaseOut mqttConnOut;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 
 */
 	
 	public OutInCellGui(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		connectMqttInOut();
 		//connectMqttOut();
	}

//	protected void connectMqttOut() { //SI veda ProductServiceCallerMqtt.java
//		try {
//			//UNA CONESSIONE di owner su topicIn ESISTE GIA per via di mqttBroker "localhost" : 1883 eventTopic "..."
//			mqttConnOut = new MqttConnectionBaseOut(
//					"tcp://localhost:1883", owner.getName()+"_out", topicout ); //sendonly
//			CommUtils.outblack(name + " | connectMqtt to " + topicout + " for " + owner.getName()); 
//		} catch (Exception e) {
// 			e.printStackTrace();
//		}	
//	}
	protected void connectMqttInOut() {  
		try {
			//UNA CONESSIONE di owner.getName() ESISTE GIA per via di mqttBroker "localhost" : 1883 eventTopic "..."
			//mqttConn SI AGGIUNGE E BISOGNA ELIMNARE eventTopic "lifein"
			mqttConn = new MqttInteraction( owner.getName()+"_outin", "tcp://localhost:1883", "lifein", topicout );
			//LA GUI non sa nulla della celle e invia meg solo sulla topic lifein
			activateReceive();		
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}


	
	public void display(String msg) {		    
		try {
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( mqttConn != null ) {
				//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				mqttConn.forward(msg);
			}
			else try{
				CommUtils.outgreen(name + " | display to GUI forward:" + msg);  
				mqttConnOut.send( msg );
			}catch(Exception e) {
				CommUtils.outred("display error" + e.getMessage());
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}

/*
 * For communication with the gui using MqttInteraction
 */
//	protected void activateReceive( ActorBasic owner, Interaction mqttConn) {
////		CommUtils.outblack(owner.getName() + " outin | activateReceive on " + mqttConn );
//		new Thread() {
//			public void run() {
//				try {
//				while ( mqttConn != null ) {
//					String msg = mqttConn.receiveMsg();
//					//CommUtils.outblack(owner.getName() + " outin | receives: " + msg);
//					fromRawDataToApplMessage(owner,msg);
//				}	
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();		
//	}
	protected void activateReceive( ) {
//		CommUtils.outblack(owner.getName() + " outin | activateReceive topicout= " + topicout + " on:"+mqttConn );
		new Thread() {
			public void run() {
				try {
				while ( mqttConn != null ) {
					String msg = mqttConn.receiveMsg();
//					CommUtils.outblack(owner.getName() + " outin | receives: " + msg);
					fromRawDataToApplMessage(owner,msg);
				}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}

	public void fromRawDataToApplMessage(ActorBasic owner, String message) { 
		//CommUtils.outblue("fromRawDataToApplMessage  cell | message:" + message);
		if( message.equals("start") || message.equals("stop") || message.equals("exit")) { //IGNORE
		}else if( message.startsWith("cell")) {
			String[] parts = message.split("-");
			int y = Integer.parseInt(parts[1])-1;  //La GUI parte da (1,1)
			int x = Integer.parseInt(parts[2])-1;
			//CommUtils.outblue("fromRawDataToApplMessage | cell:" + x + "," +y);
			String cmd = "changeCellState(X,Y)".replace("X", ""+x).replace("Y", ""+y);
			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "changeCellState", cmd, owner.getName());
			//CommUtils.outblue("fromRawDataToApplMessage | automsg:" + cmdmsg);
			owner.autoMsg(cmdmsg, null);
		}
	}
	
 

}
