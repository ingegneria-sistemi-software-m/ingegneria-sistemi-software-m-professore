package main.resources;
 
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class UserControl {
	
	protected IApplMessage goonMsg = CommUtils.buildDispatch("user","goon","goon(ok)",  "mapbuilder");

 

	public void doJob() {
        String hostAddr       = "localhost"; 
        int port              = 8032;
        ProtocolType protocol = ProtocolType.tcp;

        Interaction conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        try {
        	CommUtils.outblue("caller  :" + goonMsg);
        	 conn.forward(goonMsg);
        }catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
	}
	
	
	public static void doBeeps() {
		java.awt.Toolkit.getDefaultToolkit().beep();
		CommUtils.delay(1500);
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
	public static void doBeep() {
		java.awt.Toolkit.getDefaultToolkit().beep();
	}
	
 
	
	public static void main(String[] args) {
		UserControl appl = new UserControl();
		appl.doJob();
	}
	
}
