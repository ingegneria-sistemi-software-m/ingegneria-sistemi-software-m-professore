package demomqtt.usingConnBaseInSynch;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBaseInSynch;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.CommUtils;

/*
 
 */
public class MqttConnBaseSynchReceiver  {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "receiversynchin";
	private String topic            = "unibo/conn";
	private boolean goon            = true;
    private String prefix           = "       ";
    private MqttConnectionBaseInSynch mqttConn;

    public MqttConnBaseSynchReceiver() {
    	//Il costruttore che implica la creazione di una MqttConnectionCallbackForReceive
    	mqttConn = new MqttConnectionBaseInSynch(  MqttBroker, name, topic ) ;  
    } 
	
	public void doJob() {
		new Thread() {
			public void run() {		
			while(  goon ) {
				String message;
				try {
					message = mqttConn.receive();
					elabMessage( message.toString() );
				} catch (Exception e) {
					e.printStackTrace();
				} //bloccante
 			}//while
			CommUtils.outmagenta(prefix + name + " | ENDS " );
			System.exit(0);
		}//run
		}.start();
 	}
    
	protected void elabMessage(String message) {
		CommUtils.outmagenta(prefix + name + " | elabMessage " + message + " on " + topic);
		try {

				IApplMessage msg =  new ApplMessage(  message ); //potrebbe dare Exception
//				CommUtils.outgreen(name + " |  msgid   = " + msgInput.msgId() );
//				CommUtils.outgreen(name + " |  msgtype = " + msgInput.msgType() );
//				CommUtils.outgreen(name + " |  emitter = " + msgInput.msgSender());
//				CommUtils.outgreen(name + " |  dest    = " + msgInput.msgReceiver());
//				CommUtils.outgreen(name + " |  content = " + msgInput.msgContent());

				if( msg.isDispatch() && msg.msgId().equals("end")) {
					goon = false;
					return;
				}

				
				if( msg.isRequest() ) {  	
					CommUtils.outgreen(prefix + name + " | vorrei rispondre alla richiesta del caller:"  + msg.msgSender() );
//					IApplMessage reply = CommUtils.buildReply(name, "answTo"+msg.msgId(), "answerTo_"+ msg.msgContent(), msg.msgSender());
//					mqttConn.send( reply.toString() );
//					mqttConn.reply( msg,"answerTo_"+ msg.msgContent());
				}		 		
			
		}catch(Exception e) {
			CommUtils.outcyan(prefix + name + " | elabMessage just a String=" + message); 
			if( message.equals("END") ) goon = false;
		}		
	}

}
