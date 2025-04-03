package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

/*
 * La topicIn del CallerMqtt è la stessa topic (newsletter) su cui
 * il servizio (che ha topicIn=unibo/qak/ms0) fa emissione di eventi via MQTT
 */
public class CallerMqtt {

	protected IApplMessage req0 = CommUtils.buildRequest("callermqtt", "req0", "req0( newsletter )", "ms0");
	
	public void doJob() {
        String brokerAddr     = "tcp://localhost:1883";
        int port              = 8919;
        ProtocolType protocol = ProtocolType.mqtt;

//        Interaction conn = ConnectionFactory.createClientSupport(
//        		protocol, hostAddr,  "newsletter-unibo/qak/ms0");
        
        MqttInteraction conn = 
        		new MqttInteraction("callermqtt",brokerAddr, "newsletter","unibo/qak/ms0");

        try {
        	CommUtils.outblue("callermqtt REQUEST:" + req0);
        	IApplMessage answer = conn.request(req0);
        	CommUtils.outgreen("callermqtt ANSWER:" + answer);
        	
        	//
        	IApplMessage msgIn = conn.receive();
        	CommUtils.outgreen("callermqtt RECEIVES 1:" + msgIn);
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
