package main.resources.map;

import java.io.FileInputStream;
/*
 * Realizza posizionamenti del robot 
 * nella mappa intesa come immagine mentale  
 */
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import unibo.basicomm23.utils.CommUtils;
 


public class RobotMapper implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private RobotState robotState =  new RobotState(0, 0, RobotState.Direction.DOWN);
	private RoomMap    map        =  RoomMap.getRoomMap();
	
    public Integer getPosX(){ return robotState.getX(); }
    public Integer getPosY(){ return robotState.getY(); }
    public RobotState.Direction getDir(){ return robotState.getDirection(); }

    public void moveRobotInTheMap(){
        //curPos = new Pair(robotState.getX(),robotState.getY());
        map.put(robotState.getX(), robotState.getY(), new Box(false, false, true));
        //MODIFCO RobotState
    }

    public RoomMap getMap() {
    	return map;
    }
    
    public RobotState getRobotState() {
    	return robotState;
    }
    
    protected RobotState newstate(RobotState curState, String action) {
    	//BIsogna modicare la posizione
    	//RobotState state   = (RobotState) curState;
    	RobotState result;
		switch(action ) {
			case "w":   result = curState.forward(); break;
			case "s":  result = curState.backward(); break;
			case "l":  result = curState.turnLeft(); break;
			case "r": result = curState.turnRight(); break;
			default: throw new IllegalArgumentException("Not a valid RobotAction");
		}	
		return result;  	
    }
    
    public void doMove(String move, String msg) {
    	doMove(move);
    	if( msg.length() > 0 ) CommUtils.outblack(msg);
    }
    
    public void doMove(String move) {
        Integer x   = getPosX();
        Integer y   = getPosY();
        RoomMap map = RoomMap.getRoomMap(); 
        CommUtils.outmagenta("RobotMApper | doMove move="+move + " x=" + x + " y=" + y + " dir=" + robotState.getDirection());
        try {
        	
        if(move.equals( "rightDir" ) ){ CommUtils.outmagenta("RobotMApper uuuu"); map.put(x, y, new Box(true, false, false)); doMove("s");}
        if(move.equals( "leftDir"  ) ){ map.put(x - 1, y, new Box(true, false, false)); }
        if(move.equals( "upDir"    ) ){ map.put(x, y - 1, new Box(true, false, false)); }
        if(move.equals( "downDir"  ) ){ map.put(x, y + 1, new Box(true, false, false)); }

        CommUtils.outblack("-------------------");
        showMentalMap();
        CommUtils.outblack("-------------------");

        
            switch (move) {
                case "w" : {
                    map.cleanCell(x,y);
                    robotState = newstate(robotState,"w"); //(RobotState) new Functions().result(robotState, RobotAction.wAction) ;
                     moveRobotInTheMap();
                    return;
                }
                case "s": {
                    robotState = newstate(robotState,"s"); // (RobotState) new Functions().result(robotState, RobotAction.sAction) ;
                     moveRobotInTheMap();
                    return;
                }
                case "a"  : {
                    robotState = newstate(robotState,"l"); //(RobotState) new Functions().result(robotState, RobotAction.lAction);
                      moveRobotInTheMap();
                    return;
                }
                case "l" : {
                    robotState = newstate(robotState,"l"); // (RobotState) new Functions().result(robotState, RobotAction.lAction) ;
                      moveRobotInTheMap();
                    return;
                }
                case "d" : {
                    robotState = newstate(robotState,"r"); //(RobotState) new Functions().result(robotState, RobotAction.rAction) ;
                      moveRobotInTheMap();
                    return;
                }
                case "r" : {
                    robotState = newstate(robotState,"r"); //(RobotState) new Functions().result(robotState, RobotAction.rAction) ;
                     moveRobotInTheMap(); 
                    return;
                }
                 //Box(boolean isObstacle, boolean isDirty, boolean isRobot)
//                case "rightDir" : { map.put(x + 1, y, new Box(true, false, false));return;}
//                case "leftDir"  : { map.put(x - 1, y, new Box(true, false, false));return;}
//                case "upDir"    : { map.put(x, y - 1, new Box(true, false, false));return;}
//                case "downDir"  : { map.put(x, y + 1, new Box(true, false, false));return;}
                
                
            }//when
//            CommUtils.outblack("-------------------");
//            showMentalMap();
//            CommUtils.outblack("-------------------");
        } catch (Exception e ) {
            CommUtils.outred("RobotMapper | doMove:" + move + " ERROR:" + e.getMessage());
        }
    }
    
    public String robotPosInfo(){
        return "RobotPos=("+ robotState.getX() + "," + robotState.getY() + ") direction="+ robotState.getDirection() ;
   }
    
    public void  updateMapObstacleOnCurrentDirection(   ){
    	CommUtils.outyellow("updateMapObstacleOnCurrentDirection " + robotState.getDirection());
		doMove( getDirection() );
	} 

    public String getDirection(){
        RobotState.Direction direction = getDir();
        switch( direction ){
            case  UP    : return "upDir";
            case  RIGHT : return "rightDir";
            case  LEFT  : return "leftDir";
            case  DOWN  : return "downDir";
            default : return "unknownDir";
        }
    }
    
    public String getRep() {
    	return map.toString();
    }
    
    public void showMentalMap() {
    	CommUtils.outcyan(getRep());
    	CommUtils.outred("x="+robotState.getX()+ " y="+robotState.getY() );
    }
    
    public void showCurrentRobotState(){
        CommUtils.outblue("| ===================================================");
        showMentalMap();
        CommUtils.outcyan(robotPosInfo());
        CommUtils.outblue(" =================================================== |");
    }

	
    public  void saveRoomMap(  String fname   ) throws Exception {
    	CommUtils.outmagenta("saveRoomMap in "+ fname );
        PrintWriter pw = new PrintWriter( new FileWriter(fname+".txt") );
        pw.print( map );
        pw.close();

        ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream(fname+".bin") );
        os.writeObject( map );
        os.flush();
        os.close();
    }
    
    public void loadRoomMap( String fname ){   
        try{
        	CommUtils.outyellow("loadRoomMap  from "+fname);
            ObjectInputStream inps = new ObjectInputStream(new FileInputStream(fname+".bin"));
            CommUtils.outyellow("loadRoomMap  inps= "+ inps);
            map  = (RoomMap)inps.readObject();
            CommUtils.outyellow("loadRoomMap DONE from "+fname);
            RoomMap.setRoomMap( map );
            inps.close();
        }catch(Exception e){
        	CommUtils.outred("loadRoomMap FAILURE "+ e.getMessage());
        }
    }

   
    public void doPathOnMap(String planrep) {
 	   String Path = planrep.replace("[","").replace("]","").replace(",","").replace(" ","");
 	   CommUtils.outblue("doPathOnMap " + Path);
 	   while( Path.length() > 0 )  {
 		   String curMove =  ""+Path.charAt(0);
 		   //CommUtils.outblue("doPathOnMap curMove=" + curMove + " Path=" + Path);
 		   doMove( curMove );
 		   Path = Path.substring(1,Path.length());	     
 	   }	   
    }
    
    
    public Integer getMapDimX( ) { return RoomMap.getRoomMap().getDimX(); }
    public Integer getMapDimY( ) { return RoomMap.getRoomMap().getDimY(); }
 
    public void nextDirty( )  {
//        List<Action> actions = new ArrayList<Action>();
//        RoomMap rmap = RoomMap.getRoomMap();
        int dimX = getMapDimX( );
        int dimY = getMapDimY( );
        //println( "... planForNextDirty dimX="+ dimX + " dimY=" + dimY );
        //showCurrentRobotState();
        for( int i = 0; i<=dimX-1 ; i++ ){
            for( int j= 0; j<=dimY-1;j++ ){
                if( map.isDirty(i,j)  ){
                	CommUtils.outgreen( "nextDirty "+ i + "," + j + " curpos= " + getPosX() + "," + getPosY() );
//                    setGoal( i,j ) ;
//                    actions = doPlan();
//                    return actions;
                }
            }
        }
//        return actions;
    }
 


}
