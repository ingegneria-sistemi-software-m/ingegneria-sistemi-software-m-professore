package demomqtt.usingConnBaseOut;

import demomqtt.usingConnBaseInSynch.MqttConnBaseSynchReceiver;

public class MainMqttConnBaseOut {

	public static void main(String[] args) {
		Receiver receiver = new Receiver("receiver");
		MqttConnBaseOutSender sender           = new MqttConnBaseOutSender();
 		receiver.doJob();
		sender.doJob();
 	}
}
