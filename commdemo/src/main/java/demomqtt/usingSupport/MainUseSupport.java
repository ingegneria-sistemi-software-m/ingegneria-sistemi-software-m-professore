package demomqtt.usingSupport;

 

public class MainUseSupport {

	public static void main(String[] args) { 
		Sender agent2     = new Sender("sender");
		Receiver agent1   = new Receiver("receiver");   
		agent1.doJob();
		agent2.doJob();
	}
}
