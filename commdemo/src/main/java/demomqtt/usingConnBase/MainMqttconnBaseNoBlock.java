package demomqtt.usingConnBase;

 

public class MainMqttconnBaseNoBlock {

	public static void main(String[] args) {
		MqttconnReceiverNoBlock receiver =  new MqttconnReceiverNoBlock();
		MqttconnSender sender            =  new MqttconnSender();
		receiver.doJob();
		sender.doJob();
 	}
}
