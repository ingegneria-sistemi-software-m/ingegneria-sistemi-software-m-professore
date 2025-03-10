package demomqtt.usingInteraction;

public class MainUseInteraction {
	public static void main(String[] args) { 
		Sender sender1     = new Sender("s1");
		Sender sender2     = new Sender("s2");
		Receiver receiver = new Receiver();
	    receiver.doJob();
	    sender1.doJob();
//	    sender2.doJob();
	}

}
