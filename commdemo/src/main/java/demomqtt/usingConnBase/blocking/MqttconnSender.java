package demomqtt.usingConnBase.blocking;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa MqttConnectionBase per eseguire send sulla topic "unibo/conn"
 */
public class MqttconnSender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sender";
	private String topic            = "unibo/conn";
    private MqttConnectionBase mqttConn;

    public MqttconnSender() {
    	//mqttConn = new MqttConnectionBase(  MqttBroker, name, topic, null) ; //disaabilitazione receive implicita
    	mqttConn = new MqttConnectionBase(  MqttBroker, name, topic) ; //abilitazione receive implicita
    }
 	
	public void doJob() {
		new Thread() {
			public void run() {
				IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
				IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
				IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
				CommUtils.outblue("sender | SENDS event " +  msgEvent); 
				mqttConn.send(  msgEvent.toString()    );
//				String msgsent = mqttConn.receive();
//				CommUtils.outblue("sender | msgsent=" + msgsent ); 
				CommUtils.outblue("sender | SENDS dispatch " +  msgDispatch); 
				CommUtils.delay(100);
				mqttConn.send(  msgDispatch.toString() );
// 				String answer = mqttConn.receive();
//				CommUtils.outblue("sender | answer=" + answer ); 
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS request " +  msgRequest); 
				mqttConn.send(  msgRequest.toString()  );
 				String answer = mqttConn.receive();
 				CommUtils.outblue("sender | answer=" + answer ); 
				CommUtils.delay(100);
				CommUtils.outblue("sender | ENDS "  );		
				mqttConn.send(  "END" );
			}
		}.start();
	}
    
}
