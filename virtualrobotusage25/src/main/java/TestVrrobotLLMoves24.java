package main.java;
/*
 * ===========================================================================
 * Muove il robot usando il supporto VrobotLLMoves24 
 * con owner = null
 * 
 * Il livello applicativo NON CONOSCE il cril
 * nè ha idea della connessione WS
 * 
 * Le informazioni emesse da VrobotLLMoves24 NON sono gestite
 * ma sono visibili ponendo ((VrobotHLMoves24)vrllsupport).setTrace(true);
 * ===========================================================================
*/ 
 
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
 

public class TestVrrobotLLMoves24 {
	 protected Interaction conn;
	 protected IVrobotLLMoves vrllsupport  ;
	 
    public TestVrrobotLLMoves24(String addr) {
        CommUtils.outblue("TestMovesUsingWs |  CREATING ..." + addr);
        init(addr);
    }

    protected void init(String addr){
        try {
        	conn = ConnectionFactory.createClientSupport(ProtocolType.ws,"localhost:8091","api/move");
        	CommUtils.outblue("TestMovesUsingWs |  init conn=" + conn);
        	//CREATE the vrllsupport
        	vrllsupport = new VrobotLLMoves24("localhost", null); //No ownwer qak
//        	((VrobotLLMoves24)vrllsupport).setTrace(true);
        	//((WsConnection) conn).addObserver(this);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doBasicMoves() throws Exception {
    	vrllsupport.halt(   ) ;  
        CommUtils.delay(20);
//        CommUtils.waitTheUser("hit to turn");
        vrllsupport.turnLeft();
		 
		CommUtils.outblue("turnLeft  "  );		
		CommUtils.delay(500);
		
		vrllsupport.turnRight();
		CommUtils.outblue("turnRight  "  );
		CommUtils.delay(500);

//		CommUtils.waitTheUser("hit to forward");
		
 		//The value of endmove depends on the position of the robot
		 
		vrllsupport.forward(1000);
		CommUtils.outblue("moveForward "  );
		CommUtils.delay(1300);
//		CommUtils.waitTheUser("hit to backwardcmd");
    	vrllsupport.backward(1000);
		CommUtils.outblue("moveBackward  "  );
		CommUtils.delay(1300);
		 //Give time to receive msgs from WEnv
    }


    public void doBoundaryBlind() throws Exception {
    	vrllsupport.halt(   ) ;
    	((VrobotLLMoves24)vrllsupport).setTrace(true); //mostra il 'pullulare' del basso livello
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
	    		TestVrrobotLLMoves24 appl = new TestVrrobotLLMoves24("localhost:8091");
// 	            appl.doBasicMoves();
  	            appl.doBoundaryBlind();
	       		CommUtils.aboutThreads("At end - ");
	       		System.exit(0);
	        } catch( Exception ex ) {
	            CommUtils.outred("TestMovesUsingWs | main ERROR: " + ex.getMessage());
	        }
	    }


 
}
