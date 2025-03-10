package demomqtt.usingInteraction;

 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class Sender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private  String name       		= "sender";
	private String topic            = "unibo/conn1";
	private MqttInteraction  mqttConn;

	public Sender(String name) { 
		this.name = name;
		CommUtils.outblue(name + "  | CREATING"  );
		mqttConn = new MqttInteraction( MqttBroker, name, topic, false ); //NON attiva anche la ricezione
	}
	
	public void doJob() {
		new Thread() {
			public void run() {
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent(name, "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch(name, "cmd", "cmd("+name+",end)", "receiver" );
					IApplMessage msgRequest  = CommUtils.buildRequest(name, "info", "info("+name+",move)", "receiver" );
					
					CommUtils.outblue("sender | forward event "  ); 
					mqttConn.forward(  msgEvent     );
//					CommUtils.delay(500);
//					CommUtils.outblue("sender | forward dispatch "  ); 
//					mqttConn.forward(  msgDispatch  );

					CommUtils.delay(500);					
					CommUtils.outblue(name + " | SENDS request "  ); 							
					IApplMessage answer = mqttConn.request(  msgRequest );
					CommUtils.outblack(name + " | answer:"  + answer );					
                    //AGAIN   		
					CommUtils.outblue(name + " | SENDS request again "  ); 							
					answer = mqttConn.request(  msgRequest   );
					CommUtils.outblack(name + " | answer:"  + answer );	
 
 					mqttConn.forward(  msgDispatch );
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}	
	
}
