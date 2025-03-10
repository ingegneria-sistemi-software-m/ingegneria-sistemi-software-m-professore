package demomqtt.usingMqttConnection25;

public class MainUseMqttConnection25 {
	public static void main(String[] args) { 
 
		Agent2Sender agent2     = new Agent2Sender("agent2sender"); 
		Agent1Receiver agent1   = new Agent1Receiver("agent1receiver");
 		agent1.doJob();
 		agent2.doJob();
	}

}
