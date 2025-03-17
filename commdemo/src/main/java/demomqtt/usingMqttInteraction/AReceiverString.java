package demomqtt.usingMqttInteraction;
 
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;

public class AReceiverString {
    private String name ;
	private final String MqttBroker = "tcp://localhost:1883"; //"tcp://broker.hivemq.com"; //

	private MqttInteraction mqttConn;

	public AReceiverString(String name) {
		this.name = name;
		mqttConn = new MqttInteraction( name, MqttBroker, "receiverstringIn", "senderstringIn");
		CommUtils.outmagenta("        " + name + "  | CREATED"  );
		
		CommUtils.delay(2000);
		try {
			CommUtils.outmagenta("        " + name + "  | emits	"  );
			mqttConn.forward("receiverStarts");
		} catch (Exception e) {
 			e.printStackTrace();
		}
		
//		doJob();
		
	}
	public void doJob() {
//		CommUtils.outmagenta("        " +name + "  | doJob ... "  );
		
		
 		new Thread() {
			public void run() {	
				try {
					while(  true ) {
 						CommUtils.outgreen("        " +name + "  | RECEIVING ... "  );
						String message = mqttConn.receiveMsg(); //Blocking
						CommUtils.outmagenta("        " +name + " |  Received:" + message );						
						if( message != null ) {
							if( message.contains("request")) {
								CommUtils.outmagenta("        " +name + " |  replies"  );
								mqttConn.reply("answerTo"+message);
//								mqttConn.forward("info");
							}
							else if ( message.equals("END"))  break;
						}						
					}//while
					CommUtils.outmagenta("        " +name + " |  BYE"   );
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}//run
		}.start();
 	}
    
 	
	


	public static void main(String[] args) { 		 
		 new AReceiverString("receiverstring");  
  	}
	
}
