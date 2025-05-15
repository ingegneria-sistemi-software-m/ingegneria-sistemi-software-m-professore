/**
 * TestMovesUsingWs
 ===============================================================
 * Technology-dependent application
 * TODO. eliminate the communication details from this level
 ===============================================================
 */

package main.java.basicusage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.ApplAbstractObserver;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;
import unibo.basicomm23.ws.WsConnection;

/*
 * ================================================================
 * Muove il robot inviando messaggi cril sulla connessioneWS
 * ================================================================
*/

 

 
public class TestMovesUsingWs extends ApplAbstractObserver{
     private  JSONParser simpleparser = new JSONParser();
	 private  String turnrightcmd  = "{\"robotmove\":\"turnRight\"    , \"time\": \"300\"}";
	 private  String turnleftcmd   = "{\"robotmove\":\"turnLeft\"     , \"time\": \"300\"}";
	 private  String forwardcmd    = "{\"robotmove\":\"moveForward\"  , \"time\": \"1200\"}";
	 private  String backwardcmd   = "{\"robotmove\":\"moveBackward\" , \"time\": \"1300\"}";
	 private  String haltcmd       = "{\"robotmove\":\"alarm\" ,        \"time\":  \"10\"}";

	 private  String forwardlongcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"3000\"}";


	 long startTime ;
	 
	 protected Interaction conn;
	 
    public TestMovesUsingWs(String addr) {
            CommUtils.outblue("TestMovesUsingWs |  CREATING ..." + addr);
            init(addr);
    }

    protected void init(String addr){
        try {
        	conn = ConnectionFactory.createClientSupport(ProtocolType.ws,"localhost:8091","api/move");
        	CommUtils.outblue("TestMovesUsingWs |  init conn=" + conn);
        	((WsConnection) conn).addObserver(this);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    /*
     * Since ApplAbstractObserver
     */

	@Override
	public void update(String message) {
        long duration = System.currentTimeMillis() - startTime;
        try {
            //{"collision":"true ","move":"..."} or {"sonarName":"sonar2","distance":19,"axis":"x"}
        	CommUtils.outmagenta("TestMovesUsingWs | onMessage:" + message + " duration="+duration);
            JSONObject jsonObj = (JSONObject) simpleparser.parse(message);
            //CommUtils.outblue("TestMovesUsingWs | jsonObj:" + jsonObj);
            if (jsonObj.get("endmove") != null ) {
                boolean endmove = jsonObj.get("endmove").toString().equals("true");
                String  move    = (String) jsonObj.get("move") ;
                //CommUtils.outgreen("TestMovesUsingWs | endmove:" + endmove + " move="+move);
            } else if (jsonObj.get("collision") != null ) {
                String move   = (String) jsonObj.get("collision");
                String target = (String) jsonObj.get("target");
                //halt();
                //senza halt il msg {"endmove":"false","move":"moveForward-collision"} arriva dopo 3000
             } else if (jsonObj.get("sonarName") != null ) { //JUST TO SHOW ...
                String sonarName = (String) jsonObj.get("sonarName") ;
                String distance  = jsonObj.get("distance").toString();
            }
        } catch (Exception e) {
        	CommUtils.outred("onMessage " + message + " " +e.getMessage());
        }
    }

    protected void callWS(String msg )   {
        CommUtils.outcyan("TestMovesUsingWs | callWS " + msg);
        if( ! msg.contains("alarm")) startTime = System.currentTimeMillis() ;
        try {
			conn.forward(msg);
		} catch (Exception e) {
 			e.printStackTrace();
		}    
    }
    protected void halt(){
        callWS( haltcmd );CommUtils.delay(30);
    }
/*
 * -------------------------------------------------
   BUSINESS LOGIC
 * -------------------------------------------------
 */
    public void doForward() {
		String forwardcmd   = "{\"robotmove\":\"moveForward\",\"time\": \"1000\"}";
		//ATTENDE COMANDO DA UTENTE
		CommUtils.waitTheUser("doForward (WS): PUT ROBOT in HOME  and hit (doing forward 1000)");
		startTime = System.currentTimeMillis();
		callWS(  forwardcmd  );
		CommUtils.waitTheUser("Hit to terminate doForward");
		//Per vedere il msg di stato collision e endmove, che potrei vedere anche su NaiveGui
	}
    
    public void  doCollision() {
    	CommUtils.waitTheUser("doCollision (WS): PUT ROBOT near a wall and hit (forward 3000)");
        //halt(); //To remove pending notallowed
        String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"3000\"}";
        startTime = System.currentTimeMillis();
        callWS(  forwardcmd  );
        CommUtils.waitTheUser("Hit to terminate doCollision");
        //Per vedere il msg di stato collision e endmove
    }

    public void doNotAllowed() {
        CommUtils.waitTheUser("doNotAllowed (WS): PUT ROBOT in HOME and hit (forward 1200 and turnLeft after 400)");
        String forwardcmd   = "{\"robotmove\":\"moveForward\", \"time\":\"1200\"}";
        startTime = System.currentTimeMillis();
        callWS(  forwardcmd  );
        CommUtils.outblue("doNotAllowed (WS): moveForward msg sent"  );
        CommUtils.delay(400);
        CommUtils.outblue("doNotAllowed (WS): Now call turnLeft"  );
        callWS(  turnleftcmd  );
        CommUtils.waitTheUser("doHalt (WS): Hit to terminate doNotAllowed");
    }

    public void doHalt() {
        CommUtils.waitTheUser("doHalt (WS): PUT ROBOT in HOME and hit (forward 3000 and alarm after 1000)");
        String forwardcmd   = "{\"robotmove\":\"moveForward\", \"time\":\"3000\"}";
        callWS(  forwardcmd  );
        CommUtils.outblue("doHalt (WS): moveForward msg sent"  );
        CommUtils.delay(1000);
        callWS(  haltcmd  );
        CommUtils.waitTheUser("doHalt (WS): Hit to terminate doHalt");
    }

    public void doBasicMoves() {
        callWS(  haltcmd ) ; //halt asynch non manda enmove
        CommUtils.delay(20);
     CommUtils.waitTheUser("hit to turn");
 	
		callWS(  turnleftcmd ) ;
		CommUtils.outblue("turnLeft msg sent"  );		
		CommUtils.delay(500);
		
		callWS(  turnrightcmd ) ;
		CommUtils.outblue("turnRight msg sent"  );
		CommUtils.delay(500);

	CommUtils.waitTheUser("hit to forward");
//		//Now the value of endmove depends on the position of the robot
		callWS(  forwardcmd  );
		CommUtils.outblue("moveForward msg sent"  );
		CommUtils.delay(1300);
    CommUtils.waitTheUser("hit to backwardcmd");
		callWS(  backwardcmd );
		CommUtils.outblue("moveBackward msg sent"  );
		CommUtils.delay(1300);
		 //Give time to receive msgs from WEnv
}

/*
MAIN
 */
    public static void main(String[] args) {
        try{
    		CommUtils.aboutThreads("Before start - ");
            TestMovesUsingWs appl = new TestMovesUsingWs("localhost:8091");
            appl.doBasicMoves();
//            appl.doForward();
//            appl.doCollision();
//            appl.doNotAllowed();
//            appl.doHalt();
       		CommUtils.aboutThreads("At end - ");
       		System.exit(0);
        } catch( Exception ex ) {
            CommUtils.outred("TestMovesUsingWs | main ERROR: " + ex.getMessage());
        }
    }


}

