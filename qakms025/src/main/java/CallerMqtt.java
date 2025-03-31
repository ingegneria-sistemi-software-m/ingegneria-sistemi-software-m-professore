package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * La topicIn del CallerMqtt è la stessa topic (newsletter) su cui
 * il servizio (che ha topicIn=unibo/qak/ms0) 
 * usa come topicOut per gli eventi  
 */
public class CallerMqtt {

	protected IApplMessage req0 = CommUtils.buildRequest("callermqtt", "req0", "req0( newsletter )", "ms0");
	
	public void doJob() {
        String hostAddr       = "tcp://localhost:1883";
        int port              = 8919;
        ProtocolType protocol = ProtocolType.mqtt;

        Interaction conn = ConnectionFactory.createClientSupport(
        		protocol, hostAddr,  "callermqtt-newsletter-unibo/qak/ms0");
        
        try {
        	CommUtils.outblue("callermqtt REQUEST:" + req0);
        	IApplMessage answer = conn.request(req0);
        	CommUtils.outblue("callermqtt ANSWER:" + answer);
        	
        	//Dopo la risposta, il servizio fa una publish su newsletter
        	//ed emette un evento, avendo come topicOut per gli eventi ancora newsletter
        	IApplMessage msgIn = conn.receive();
        	CommUtils.outmagenta("callermqtt RECEIVES 1:" + msgIn);
        	msgIn = conn.receive();
        	CommUtils.outmagenta("callermqtt RECEIVES 2:" + msgIn);
        	
        	System.exit(0);
		} catch (Exception e) {
 			CommUtils.outred("callermqtt ERROR:" + e.getMessage() );
		}
	}

	 public static void main( String[] args ){
		 new CallerMqtt().doJob();
	 }
} 
