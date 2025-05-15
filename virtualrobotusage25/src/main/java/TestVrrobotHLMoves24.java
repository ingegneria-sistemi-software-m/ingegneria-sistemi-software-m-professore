package main.java;
/*
 * ==============================================================================
 * Muove il robot usando il supporto VrobotHLMoves24 
 * con owner = null
 * 
 * Il livello applicativo NON CONOSCE il cril
 * nè ha idea della connessione WS
 * 
 * Le informazioni emesse da VrobotHLMoves24 NON sono gestite
 * ma sono visibili ponendo ((VrobotHLMoves24)vrllsupport).setTrace(true);
 * ===============================================================================
*/ 
 
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
 

public class TestVrrobotHLMoves24 {
	 protected Interaction conn;
	 protected IVrobotMoves vrllsupport  ;
	 
    public TestVrrobotHLMoves24(String addr) {
        CommUtils.outblue("TestMovesUsingWs |  CREATING ..." + addr);
        init(addr);
    }

    protected void init(String addr){
        try {
        	conn = ConnectionFactory.createClientSupport(ProtocolType.ws,"localhost:8091","api/move");
        	CommUtils.outblue("TestMovesUsingWs |  init conn=" + conn);
        	//CREATE the vrllsupport
        	vrllsupport = new VrobotHLMoves24("localhost", null); //No ownwer qak
       	    ((VrobotHLMoves24)vrllsupport).setTrace(true);
        	//((WsConnection) conn).addObserver(this);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doBasicMoves() throws Exception {
    	vrllsupport.halt(   ) ;  
        CommUtils.delay(20);
//        CommUtils.waitTheUser("hit to turn");
		CommUtils.outblue("turnLeft (l) "  );		
       vrllsupport.move("l");
		 
		CommUtils.delay(500);
		
		CommUtils.outblue("turnRight (r) "  );
		vrllsupport.move("r");
		CommUtils.delay(500);

//		CommUtils.waitTheUser("hit to forward");
		
 		//The value of endmove depends on the position of the robot
		 
		CommUtils.outblue("moveForward (w)"  );
		vrllsupport.move("w");;
		CommUtils.delay(2600);
//		CommUtils.waitTheUser("hit to backwardcmd");
		CommUtils.outblue("moveBackward (s) "  );
    	vrllsupport.move("s");;
		CommUtils.delay(2600);
		CommUtils.outblue("step (p)  "  );
    	vrllsupport.move("p");;
		CommUtils.delay(1300);
		 //Give time to receive msgs from WEnv
    }


    public void doBoundaryBlind() throws Exception {
    	vrllsupport.halt(   ) ;
    	((VrobotHLMoves24)vrllsupport).setTrace(true); //mostra il 'pullulare' del basso livello
    	for( int i=1; i<=4; i++) {
    	   	CommUtils.outblue("moveForward " + i );
	     	vrllsupport.forward(2000);
	     	CommUtils.delay(2100);    //se invio prima della fine genero una notalloweed e una interruzione
	       	CommUtils.outblue("turnLeft " + i );
	       	vrllsupport.turnLeft(); 
 	       	CommUtils.delay(500); 
    	}

    } 
	/* 
	MAIN
	 */
	    public static void main(String[] args) throws Exception {
	        try{
	    		CommUtils.aboutThreads("Before start - ");
	    		TestVrrobotHLMoves24 appl = new TestVrrobotHLMoves24("localhost:8091");
  	            appl.doBasicMoves();
  	          
  	            CommUtils.waitTheUser("hit to execute doBoundaryBlind");
  	            appl.doBoundaryBlind();
	       		CommUtils.aboutThreads("At end - ");
	       		System.exit(0);
	        } catch( Exception ex ) {
	            CommUtils.outred("TestMovesUsingWs | main ERROR: " + ex.getMessage());
	        }
	    }


 
}
