package demomqtt.usingMqttInteraction;

import unibo.basicomm23.utils.CommUtils;

public class MainUseMqttInteraction {
	public static void main(String[] args) { 
 
		new ASender("asender"); 
		new AnotherSender("anothersender");
		new AReceiver("areceiver");
	}

}
