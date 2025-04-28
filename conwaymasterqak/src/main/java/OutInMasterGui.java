package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
 
import unibo.basicomm23.utils.CommUtils;
 
public class OutInMasterGui  {

    private static OutInMasterGui mysingleton;
    private final String name = "OutInMqtt";
 	protected MqttInteraction mqttConn;
    protected MqttConnectionBaseOut mqttConnbase;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 * owner è già connesso a mqttBroker "localhost" : 1883 eventTopic "lifein"
 *
 * owner potrebbe stabilire con topicout una ULTERIORE connessione MqttInteraction 
 * 
 * per semplificare, owner usa mqttConnbase verso topicout essendo già
 * statbilita una subscribe alla eventTopic "lifein" che  comporta la invocazione del 
 * metodo messageArrived di owner il quale emette un evento di nome kernel_rawmsg 
 * quando giunge un messaggio (String) non-IApplMessage  
 * 
 * owner deve gestire la rispostaa  kernel_rawmsg (che viene imesso SOLO sua coda)
 * facendo override del metodo fromRawDataToApplMessage
 */
	
//	public static OutInMqttForactor activateSingleton(ActorBasic owner, String  topicout) {
//        if( mysingleton == null ) {
//        	mysingleton = new OutInMqttForactor(owner, topicout);
//        }
//        return mysingleton;
//    }
 	
 	public OutInMasterGui(ActorBasic owner, String  topicout ) {
 		this.owner    = owner;
 		this.topicout = topicout;
 		connectMqttInOut();
 		//connectMqttOut();
	}

	protected void connectMqttOut() { //SI veda ProductServiceCallerMqtt.java
		try {
			//UNA CONESSIONE di owner su topicIn ESISTE GIA per via di mqttBroker "localhost" : 1883 eventTopic "..."
			String broker = System.getenv("MQTTBROKER_URL");
			if( broker == null ) broker = "tcp://localhost:1883";
			else {
				CommUtils.outgreen(owner.getName() + " | connectMqttInOut in docker to " + broker ); 	
			}					
			mqttConnbase = new MqttConnectionBaseOut(broker, owner.getName()+"_out", topicout ); //sendonly
			CommUtils.outgreen(name + " | connectMqtt to " + topicout + " for " + owner.getName()); 
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}
	protected void connectMqttInOut() {  
		try {
			//UNA CONESSIONE di owner.getName() ESISTE GIA per via di mqttBroker "localhost" : 1883 eventTopic "..."
			//mqttConn SI AGGIUNGE E BISOGNA ELIMNARE eventTopic "lifein"
			String broker = System.getenv("MQTTBROKER_URL");
			if( broker == null ) broker = "tcp://localhost:1883";
			else {
				CommUtils.outgreen(owner.getName() + " | connectMqttInOut in docker to " + broker ); 	
			}					
			mqttConn = new MqttInteraction( owner.getName()+"_outin", broker, "lifein", topicout );
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
				mqttConnbase.send( msg );
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
	protected void activateReceive( ActorBasic owner, Interaction mqttConn) {
		CommUtils.outgreen(owner.getName() + " outin | activateReceive   "  );
		new Thread() {
			public void run() {
				try {
				while ( mqttConn != null ) {
					String msg = mqttConn.receiveMsg();
					//CommUtils.outgreen(owner.getName() + " outin | receives: " + msg);
					fromRawDataToApplMessage(owner,msg);
				}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}
	protected void activateReceive( ) {
		CommUtils.outgreen(owner.getName() + " outin | activateReceive  " + topicout  );
		new Thread() {
			public void run() {
				try {
				while ( mqttConn != null ) {
					String msg = mqttConn.receiveMsg();
					//CommUtils.outgreen(owner.getName() + " outin | receives: " + msg);
					fromRawDataToApplMessage(owner,msg);
				}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}

	public void fromRawDataToApplMessage(ActorBasic owner, String message) { 
		//CommUtils.outmagenta("fromRawDataToApplMessage  master | message:" + message);
		if( message.equals("start")) {
			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "start", "start(init)", owner.getName());			
			owner.autoMsg(cmdmsg, null);
			//owner.forward("start", "start(init)", "gridcreator", null); //TRICK
		}else if( message.equals("stop")) {
			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "stop", "stop(ok)", owner.getName());	
			owner.autoMsg(cmdmsg, null);
 		}else if( message.equals("exit")) {
				System.exit(0);
		}
// 		else if( message.startsWith("cell")) {
//			String[] parts = message.split("-");
//			int y = Integer.parseInt(parts[1])-1;  //La GUI parte da (1,1)
//			int x = Integer.parseInt(parts[2])-1;
//			//CommUtils.outblue("fromRawDataToApplMessage | cell:" + x + "," +y);
//			String cmd = "changeCellState(X,Y)".replace("X", ""+x).replace("Y", ""+y);
//			IApplMessage cmdmsg = MsgUtil.buildDispatch("gui", "changeCellState", cmd, owner.getName());
//			//CommUtils.outblue("fromRawDataToApplMessage | automsg:" + cmdmsg);
//			owner.autoMsg(cmdmsg, null);
//		}
	}
	
 

}
