package demomqtt.usingInteractionBase;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.mqtt.MqttInteractionBase;
import unibo.basicomm23.utils.CommUtils;

/*
 * Il sender attiva una connessione senza ricezione
 * in quanto la request attende la risposta su una topic diversa
 */
public class Sender {
	private final String MqttBroker = "tcp://localhost:1883";//"tcp://broker.hivemq.com"; //
    private final String name       = "sender";
	private String topic            = "unibo/conn";
	private MqttInteractionBase mqttConn;

	public Sender() {
		CommUtils.outblue(name + "  | CREATING"  );
		boolean sendonly=true	;
		//mqttConn = new MqttInteractionBase(MqttBroker, name, topic, sendonly); //NON attiva anche la ricezione
		mqttConn = new MqttInteractionBase(MqttBroker, name, topic); // attiva anche la ricezione
	}
	
	public Sender(MqttInteractionBase mqttConn ) {
		CommUtils.outblue(name + "  | CREATING WITH mqttConn"  );
		this.mqttConn = mqttConn;
	}
	
	public void doJob() {
		new Thread() {
			public void run() {
				try {
					IApplMessage msgEvent    = CommUtils.buildEvent("sender", "alarm", "alarm(fire)" );
					IApplMessage msgDispatch = CommUtils.buildDispatch("sender", "cmd", "cmd(move)", "receiver" );
					IApplMessage msgRequest  = CommUtils.buildRequest("sender", "info", "info(move)", "receiver" );
					
					CommUtils.delay(500);
					
					CommUtils.outblue("sender | forward event "  ); 
					mqttConn.forward(  msgEvent.toString()    );
					
					CommUtils.delay(500);
					CommUtils.outblue("sender | forward dispatch "  ); 
					mqttConn.forward(  msgDispatch.toString() );
					
					/*
					 * request
					 */					
					CommUtils.outblue("sender | doing request "  ); 
					String answer = mqttConn.request(  msgRequest.toString()  );
					CommUtils.outblack("sender | answer:"  + answer );	
 
					CommUtils.outblue("sender | wait for message"  ); 
					String rrr = mqttConn.receiveMsg();
					CommUtils.outblue("sender | receives " + rrr  ); 
					
					CommUtils.delay(500);
					CommUtils.outblue("sender | forward ENDS "  );		
					mqttConn.forward(  "END" );
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
	protected void elabTheAnwer(String request, String reply) {
		if( reply.equals(request)) {
			elabTheAnwer(request, reply);
		}else {
			CommUtils.outgreen("elabTheAnwer");
		}
	}

}
