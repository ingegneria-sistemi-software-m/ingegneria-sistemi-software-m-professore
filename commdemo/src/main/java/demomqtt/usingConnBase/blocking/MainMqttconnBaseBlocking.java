package demomqtt.usingConnBase.blocking;

 
public class MainMqttconnBaseBlocking {

	public static void main(String[] args) {
		MqttconnReceiverBlocking receiver = new MqttconnReceiverBlocking();
		MqttconnSender sender             = new MqttconnSender();
 		receiver.doJob();
		sender.doJob();
 	}
}
