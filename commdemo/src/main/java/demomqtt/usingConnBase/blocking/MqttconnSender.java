package demomqtt.usingConnBase.blocking;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.utils.CommUtils;

/*
  * Il sender attiva anche la ricezione  e
 * attende di ricevere il messaggio trasmesso 
 * prima di inviare il messaggio succesivo
 */
public class MqttconnSender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sender";
	private String topic            = "unibo/conn";
    private MqttConnectionBase mqttConn;

    public MqttconnSender() {
    	mqttConn = new MqttConnectionBase(  MqttBroker, name, topic, null) ; //disaabilitazione receive implicita
    	//mqttConn = new MqttConnectionBase(  MqttBroker, name, topic) ; //abilitazione receive implicita
    }
    
  
	
	public void doJob() {
		new Thread() {
			public void run() {
				IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
				IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
				IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
				CommUtils.outblue("sender | SENDS " +  msgEvent); 
				mqttConn.send(  msgEvent.toString()    );
//				String msgsent = mqttConn.receive();
//				CommUtils.outblue("sender | msgsent=" + msgsent ); 
				CommUtils.outblue("sender | SENDS " +  msgDispatch); 
				CommUtils.delay(100);
				mqttConn.send(  msgDispatch.toString() );
//				msgsent = mqttConn.receive();
//				CommUtils.outblue("sender | msgsent=" + msgsent ); 
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS " +  msgRequest); 
				mqttConn.send(  msgRequest.toString()  );
//				msgsent = mqttConn.receive();
//				CommUtils.outblue("sender | msgsent=" + msgsent ); 
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS work done"  ); 
				mqttConn.send(  "work done" );
				CommUtils.delay(100);
				CommUtils.outblue("sender | ENDS "  );		
				mqttConn.send(  "END" );
			}
		}.start();
	}
    
}
