package main.java;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerLifeActorTcp {

	protected IApplMessage cmdclear = CommUtils.buildEvent("callertcp",    "clearCell",       "clearCell(ok)" );
	protected IApplMessage change00 = CommUtils.buildDispatch("callertcp", "changeCellState", "changeCellState(0,0)", "cell_0_0" );
	protected IApplMessage change01 = CommUtils.buildDispatch("callertcp", "changeCellState", "changeCellState(0,1)", "cell_0_1" );
	protected IApplMessage change10 = CommUtils.buildDispatch("callertcp", "changeCellState", "changeCellState(1,0)", "cell_1_0" );
	protected IApplMessage change11 = CommUtils.buildDispatch("callertcp", "changeCellState", "changeCellState(1,1)", "cell_1_1" );

	protected IApplMessage cmdstart = CommUtils.buildEvent("callertcp", "startthegame", "startthegame(ok)" );
	protected IApplMessage synch  = CommUtils.buildEvent("callertcp", "synch", "synch(ok)" );
	protected IApplMessage cmdstop = CommUtils.buildEvent("callertcp", "stopthecell", "stopthecell(ok)" );
	
	protected Interaction conn;
	
	protected void initSomeCell() {
	    try {
	        conn.forward(cmdclear);       
 
//	        CommUtils.delay(500);
//	    	conn.forward(change00);

	    	CommUtils.delay(500);
	    	conn.forward(change01);

//	    	CommUtils.delay(500);
//	    	conn.forward(change10);
	    	
	    	CommUtils.delay(500);
	    	conn.forward(change11);
		} catch (Exception e) {
				CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}	
	}
	
	protected void controlTheGame() { //Emit synch USEED FOR conwaycellsnqakaive
        try {
          	CommUtils.outblue("callertcp  :" + cmdstart);
        	 conn.forward(cmdstart);
        	 for( int i = 1; i<= 4; i++) {
	        	 CommUtils.delay(1000);  //ALLA CIECA per fine epoch
	         	 CommUtils.outblue("callertcp  :" + synch);
	       	     conn.forward(synch);
        	 }
        	 CommUtils.delay(1000); //SE ASSENTE L?EVENTO E' PERSO
         	 CommUtils.outblue("callertcp  :" + cmdstop);
       	     conn.forward(cmdstop);
         	
        	 
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
		
	}
	
	protected void startStopGame() { //No synch USEED FOR conwaycellsnqakcoreog
        try {
          	CommUtils.outblue("callertcp  :" + cmdstart);
        	 conn.forward(cmdstart);
         	 CommUtils.delay(5000);  
         	 CommUtils.outblue("callertcp  :" + cmdstop);
       	     conn.forward(cmdstop);
		} catch (Exception e) {
 			CommUtils.outred("callertcp ERROR:" + e.getMessage() );
		}
		
	}
	
	public void doJob() {
        String hostAddr       = "localhost";
        int port              = 8360;
        ProtocolType protocol = ProtocolType.tcp;

        conn = ConnectionFactory.createClientSupport(protocol, hostAddr, ""+port);
        
        //FASE INIT PER conwaycellsnqakcoreog.qaktt
         initSomeCell();
         CommUtils.delay(2000);
        
         controlTheGame();
         
//         startStopGame();
  	     CommUtils.delay(3000);
  	     CommUtils.outblue("callertcp BYE"  );
        
  	}

	 public static void main( String[] args ){
		 new CallerLifeActorTcp().doJob();
	 }
}
