package demomqtt.usingMqttConnection25;

import unibo.basicomm23.utils.CommUtils;

public class MainUseMqttConnection25 {
	public static void main(String[] args) { 
 
		Agent2Sender agent2     = new Agent2Sender("agent2sender"); 
		Agent1Receiver agent1   = new Agent1Receiver("agent1receiver");
 		agent1.doJob();
 		CommUtils.delay(500);
 		agent2.doJob();
	}

}
