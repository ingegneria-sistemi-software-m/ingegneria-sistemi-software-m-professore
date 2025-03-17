package demomqtt.usingConnBaseInSynch;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttConnectionBaseInSynch;
import unibo.basicomm23.mqtt.MqttConnectionBaseOut;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa:
 *     MqttConnectionBaseOut per eseguire un thread che fa send sulla topic "unibo/conn"
 *     MqttConnectionBaseInSynch per eseguire un thread che fa receive sulla topic "unibo/conn"
 */
public class MqttConnBaseSynchIOAutoAgent {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sendersynchin";
	private String topic            = "unibo/conn";
    private MqttConnectionBaseOut mqttConnOut;
    private MqttConnectionBaseInSynch mqttConnIn;
	private boolean goon            = true;
    private String prefix           = "       ";

    public MqttConnBaseSynchIOAutoAgent() {
      	mqttConnOut = new MqttConnectionBaseOut(  MqttBroker, name+"_out", topic ) ;  
      	mqttConnIn  = new MqttConnectionBaseInSynch(  MqttBroker, name+"_in", topic ) ;  
      	doJob();
    }
 	
	public void doJob() {
		receiveInfob();
		emitInfo();
 	}
	
	public void emitInfo() {
		new Thread() {
			public void run() {
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
					IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
					CommUtils.outblue(name + " | SENDS event " +  msgEvent); 
					mqttConnOut.send(  msgEvent.toString()    );
 
					CommUtils.outblue(name + " | SENDS dispatch " +  msgDispatch); 
					CommUtils.delay(300);
					mqttConnOut.send(  msgDispatch.toString() );
 
					CommUtils.delay(300);
					CommUtils.outblue(name + " | SENDS request " +  msgRequest); 
					mqttConnOut.send(  msgRequest.toString()  );
 
					CommUtils.outblue(name + " | ENDS "  );		
					mqttConnOut.send(  "end_of_work" );
				}catch(Exception e) {
					
				}
			
			}
		}.start();
		
	}

	public void receiveInfob() {
		new Thread() {
			public void run() {		
			while(  goon ) {
				String message;
				try {
					message = mqttConnIn.receive(); //bloccante
					CommUtils.outmagenta(prefix + name + " | receives " + message + " on " + topic);
					if(message.contains("end_of_work")) {
						goon = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
 			}//while
			CommUtils.outmagenta(prefix + name + " | ENDS " );
			System.exit(0);
		}//run
		}.start();
 	}
	
	public static void main(String[] args) {
		new MqttConnBaseSynchIOAutoAgent();
	}
}
