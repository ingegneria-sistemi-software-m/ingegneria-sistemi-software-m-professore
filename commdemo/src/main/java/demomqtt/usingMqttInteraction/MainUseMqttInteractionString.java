package demomqtt.usingMqttInteraction;

import unibo.basicomm23.utils.CommUtils;

public class MainUseMqttInteractionString {
	public static void main(String[] args) { 
 
		new ASenderString("asenderstring"); 
		new AnotherSenderString("anothersenderstring");
		new AReceiverString("areceiverstring");
		//CommUtils.delay(1000);
	}

}
