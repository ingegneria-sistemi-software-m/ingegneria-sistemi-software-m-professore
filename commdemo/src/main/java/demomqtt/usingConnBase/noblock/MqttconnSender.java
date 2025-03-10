package demomqtt.usingConnBase.noblock;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBase;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa la send di MqttConnectionBase senza receive implicita.
 * 
 * Un messaggio vorrebbe essere una richiesta di informazione, ma ...
 */
public class MqttconnSender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sender";
	private String topic            = "unibo/conn/answer";  //usata dal reciver per la risposta
    private MqttConnectionBase mqttConn;

    public MqttconnSender() {
    	mqttConn = new MqttConnectionBase(  MqttBroker, name, topic, null) ; //disaabilitazione receive implicita
    	//mqttConn = new MqttConnectionBase(  MqttBroker, name, answertopic) ; //abilitazione receive implicita
    }
    
  
	
	public void doJob() {
		new Thread() {
			public void run() {
				IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
				IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
				IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
				CommUtils.outblue("sender | SENDS " + msgEvent ); 
				mqttConn.send(  msgEvent.toString()    );
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS " + msgDispatch ); 
				mqttConn.send(  msgDispatch.toString() );
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS " + msgRequest ); 
				mqttConn.send(  msgRequest.toString()  );
				CommUtils.delay(100);
				CommUtils.outblue("sender | SENDS END "  ); 
				mqttConn.send(  "END" );
 			}
		}.start();
	}
    
}
