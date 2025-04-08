package main.java;
import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.MsgUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;
 
public class OutInForGui  {

//    private static OutInMasterGui mysingleton;
    private final String name = "OutInfForGui";
 	protected MqttInteraction mqttConn;
    protected MqttConnectionBaseOut mqttConnOut;
  	protected ActorBasic owner; 
  	protected String topicout;
 	
/*
 */
 	
 	public OutInForGui(ActorBasic owner  ) {
 		this.owner    = owner;
 		this.topicout = "guiin";
 		connectMqttInOut();

	}


	protected void connectMqttInOut() {  
		try {
 			mqttConn = new MqttInteraction( owner.getName()+"_outin", "tcp://localhost:1883", "lifein", topicout );
 			activateReceive();		
		} catch (Exception e) {
 			e.printStackTrace();
		}	
	}


	
	public void display(String msg) {		    
		try {
			//CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
			if( mqttConn != null ) {
				CommUtils.outcyan(name + " | display to GUI " + msg + " topic=" +  topicout);
				mqttConn.forward(msg);
			}
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}


	protected void activateReceive( ) {
		CommUtils.outgreen(owner.getName() + " OutInfForGui | activateReceive  " + topicout + " on:" + mqttConn );
		new Thread() {
			public void run() {
				try {
				while ( mqttConn != null ) {
					String msg = mqttConn.receiveMsg();
					//CommUtils.outgreen(owner.getName() + " OutInfForGui | receives: " + msg);
					fromRawDataToApplMessage(owner,msg);
				}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}

	public void fromRawDataToApplMessage(ActorBasic owner, String message) { 
		//CommUtils.outmagenta("fromRawDataToApplMessage    | message:" + message);
		if( message.equals("start")) {
			IApplMessage cmdmsg = MsgUtil.buildEvent("gui", "startcmd", "startcmd(init)" );			
			owner.autoMsg(cmdmsg, null);
		}else if( message.equals("stop")) {
			IApplMessage cmdmsg = MsgUtil.buildEvent("gui", "stopcmd", "stopcmd(ok)" );	
			owner.autoMsg(cmdmsg, null);
 		}else if( message.equals("clear")) {
 			CommUtils.outmagenta("fromRawDataToApplMessage   | emit clear for "  + owner.getName());
			IApplMessage cmdmsg = MsgUtil.buildEvent("gui", "clearcmd", "clearcmd(ok)" );	
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
//			IApplMessage cmdmsg = MsgUtil.buildEvent("gui", "changeCellState", cmd );  //ALLE CELLE
//			//CommUtils.outblue("fromRawDataToApplMessage | automsg:" + cmdmsg);
//			owner.autoMsg(cmdmsg, null);
//		}
	}
	
 

}
