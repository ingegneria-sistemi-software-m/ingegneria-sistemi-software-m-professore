package demomqtt.usingConnBaseInSynch;

 
public class MainMqttConnBaseSynch {

	public static void main(String[] args) {
		MqttConnBaseSynchReceiver receiver = new MqttConnBaseSynchReceiver();
		MqttConnBaseSynchInSender sender   = new MqttConnBaseSynchInSender();
 		receiver.doJob();
		sender.doJob();
 	}
}
