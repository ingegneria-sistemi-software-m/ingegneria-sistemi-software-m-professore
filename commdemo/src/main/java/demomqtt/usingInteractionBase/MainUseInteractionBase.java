package demomqtt.usingInteractionBase;

public class MainUseInteractionBase {
	public static void main(String[] args) {
		 
		Sender sender     = new Sender();
		Receiver receiver = new Receiver();
	    receiver.doJob();
	    sender.doJob();
	}
}
