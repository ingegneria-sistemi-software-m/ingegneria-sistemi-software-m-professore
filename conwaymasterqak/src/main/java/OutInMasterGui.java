package main.java;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;
 
public class OutInMasterGui  {
    private final String name = "OutInMqtt";
 	protected MqttConnection mqttConn;
  	protected ActorBasic owner; 
  	protected String topicout;
 	 
/*
 * owner è già connesso a mqttBroker "localhost" : 1883 eventTopic "lifein"
 *

 */
	

 	
 	public OutInMasterGui(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		mqttConn = owner.getMqtt().getMqttConn();
 		CommUtils.outcyan(name + " | CREATED  topic=" +  topicout);
// 		connectMqttInOut();
 		//connectMqttOut();
	}

 	
	public void display(String msg) {		    
		try {
			CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( mqttConn != null ) {
				//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				//mqttConn.forward(msg); //msg is not a IApplMessage
				mqttConn.publish("guiin", msg);
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}

/*
 * For cmd from the the gui u
 */
	protected void activateReceive( ActorBasic owner, Interaction mqttConn) {
		CommUtils.outgreen(owner.getName() + " outin | activateReceive   "  );
		new Thread() {
			public void run() {
				try {
				while ( mqttConn != null ) {
					String msg = mqttConn.receiveMsg();
					CommUtils.outgreen(owner.getName() + " outin | receives: " + msg);
					if( mqttConn == null || msg == null || msg.equals("exit")) {
 						System.exit(0);
					}
				}	
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}.start();		
	}
//	protected void activateReceive( ) {
//		CommUtils.outgreen(owner.getName() + " outin | activateReceive  " + topicout  );
//		new Thread() {
//			public void run() {
//				try {
//				while ( mqttConn != null ) {
//					String msg = mqttConn.receiveMsg();
//					//CommUtils.outgreen(owner.getName() + " outin | receives: " + msg);
//					fromRawDataToApplMessage(owner,msg);
//				}	
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}.start();		
//	}
//
//	public void fromRawDataToApplMessage(ActorBasic owner, String message) { 
//		//CommUtils.outmagenta("fromRawDataToApplMessage  master | message:" + message);
//		if( message.equals("start")) {
//			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "start", "start(init)", owner.getName());			
//			owner.autoMsg(cmdmsg, null);
//			//owner.forward("start", "start(init)", "gridcreator", null); //TRICK
//		}else if( message.equals("stop")) {
//			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "stop", "stop(ok)", owner.getName());	
//			owner.autoMsg(cmdmsg, null);
// 		}else if( message.equals("exit")) {
//				System.exit(0);
//		}
//	}
	
 

}
