package demomqtt.usingInteractionBase;

//import it.unibo.kactor.MsgUtil;
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
		mqttConn = new MqttInteractionBase(MqttBroker, name, topic, true); //NON attiva anche la ricezione
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
					
//					CommUtils.outblue("sender | SENDS "  ); 
//					
//					String requestMsg = "work";
//					String answer = mqttConn.request(  requestMsg );
//					CommUtils.outblack("sender | answer:"  + answer );
					
					//String reply = mqttConn.receiveMsg();
					//elabTheAnwer(requestMsg, reply);
					//CommUtils.outmagenta("sender | reply:"  + reply );
					
					CommUtils.outblue("sender | forward event "  ); 
					mqttConn.forward(  msgEvent.toString()    );
					CommUtils.delay(500);
					CommUtils.outblue("sender | forward dispatch "  ); 
					mqttConn.forward(  msgDispatch.toString() );
//					CommUtils.delay(500);
 					
					CommUtils.outblue("sender | doing request "  ); 
					String answer = mqttConn.request(  msgRequest.toString()  );
					CommUtils.outblack("sender | answer:"  + answer );	
 
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
