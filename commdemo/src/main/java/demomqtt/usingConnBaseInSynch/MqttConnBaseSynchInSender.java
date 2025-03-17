package demomqtt.usingConnBaseInSynch;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa MqttConnectionBaseOut per eseguire send sulla topic "unibo/conn"
 */
public class MqttConnBaseSynchInSender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sendersynchin";
	private String topic            = "unibo/conn";
    private MqttConnectionBaseOut mqttConn;

    public MqttConnBaseSynchInSender() {
      	mqttConn = new MqttConnectionBaseOut(  MqttBroker, name, topic ) ;  
    }
 	
	public void doJob() {
		new Thread() {
			public void run() {
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
					IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
					CommUtils.outblue(name + " | SENDS event " +  msgEvent); 
					mqttConn.send(  msgEvent.toString()    );
 
					CommUtils.outblue(name + " | SENDS dispatch " +  msgDispatch); 
					CommUtils.delay(300);
					mqttConn.send(  msgDispatch.toString() );
 
					CommUtils.delay(300);
					CommUtils.outblue(name + " | SENDS request " +  msgRequest); 
					mqttConn.send(  msgRequest.toString()  );
 
					CommUtils.outblue(name + " | ENDS "  );		
					mqttConn.send(  "END" );
				}catch(Exception e) {
					
				}
			
			}
		}.start();
	}
    
}
