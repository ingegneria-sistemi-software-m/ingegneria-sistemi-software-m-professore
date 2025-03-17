package demomqtt.conway;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
 
/*
 * Gestore del Game che opera senza alcun controllo di ownershio
 */
public class LifeCallerMQTT {

    private Interaction conn;
    private boolean goon = true;
    
    public LifeCallerMQTT() {
        try {     	
        	conn = ConnectionFactory.createClientSupport(
        			ProtocolType.mqtt, "tcp://localhost:1883", "callerMqtt-guiin-lifein");
//        	conn = new MqttInteraction( "callerMqtt", "tcp://localhost:1883", "guiin", "lifein");
	        CommUtils.outyellow("LifeCallerMQTT CREATED" );        
        } catch (Exception e) {
        	CommUtils.outred("LifeCallerMQTT | ERROR:" +e.getMessage());
        }    	   	
    }
     
    public void workWithGame( ) {
        try {
        	activateApplObserver();  //per ricevere info dalla appl
        	
//        	conn.forward("clear");
        	
        	conn.forward("cell-1-2");
        	conn.forward("cell-2-2");
        	conn.forward("cell-3-2");
        	
            CommUtils.outblue("LifeCallerMQTT | forward start" );
            conn.forward("start");            
            CommUtils.delay(10000);            
            CommUtils.outblue("LifeCallerMQTT | forward stop" );
            conn.forward("stop");    

            conn.close();
            
        } catch (Exception e) {
        	CommUtils.outred("LifeCallerMQTT | ERROR:" +e.getMessage());
        }    	
    }
    
    protected void activateApplObserver() {
    	new Thread() {
    		public void run() {
            	CommUtils.outgreen("lifeobserver | RECEIVE PHASE"  );
            	while( goon ) { 
					try {
						String info = conn.receiveMsg();
						if( ! info.startsWith("cell("))
							CommUtils.outgreen("lifeobserver | receives:" + info);
					} catch (Exception e) {
						CommUtils.outred("lifeobserver | ERROR:" + e.getMessage());
					}
             	}
            	CommUtils.outgreen("lifeobserver | out of loop" );
    		}
    	}.start();
    }
 

    public static void main(String[] args) {
    	LifeCallerMQTT caller = new LifeCallerMQTT();
    	caller.workWithGame(); 
//     	CommUtils.delay(20000); //To chcek broadcasted messages
    	CommUtils.outmagenta("LifeCallerMQTT | BYE" );
     	System.exit(0);
    }     
 

}
