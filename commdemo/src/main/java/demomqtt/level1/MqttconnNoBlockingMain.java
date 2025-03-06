package demomqtt.level1;

 

public class MqttconnNoBlockingMain {

	public static void main(String[] args) {
		MqttconnReceiverNoBlocking receiver = new MqttconnReceiverNoBlocking("receiver");
 		MqttconnSenderString sender         = new MqttconnSenderString();
		sender.doJob();
	}
}
