package main.resources.map;

import java.util.ArrayList;
import java.util.List;
import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.GoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import unibo.basicomm23.utils.CommUtils;

/*
 * Usa aima.core
 */

public class PlanAima {

    private RobotState robotState;
    private RoomMap  map ;
    private RobotMapper mapper;
    private BreadthFirstSearch search;
    private GoalTest curGoal              = new Functions();
    private	boolean currentGoalApplicable = true;

    public PlanAima(RobotMapper mapper) {
    	this.mapper = mapper;
        robotState = mapper.getRobotState(); //new RobotState(0, 0, RobotState.Direction.DOWN);
        search     = new BreadthFirstSearch(new GraphSearch());
        map        = mapper.getMap();
        CommUtils.outmagenta("PlanAima initAI done " + map.getDimX() + "," + map.getDimY() );
    	
    }
    
 
    
    public void setGoal( Integer x, Integer y) {
        try {
            CommUtils.outyellow("PlanAlma | setGoal x=" +x + " y=" + y + " while:" + mapper.robotPosInfo());
            if( map.isObstacle(x,y) ) {
            	CommUtils.outred("PlanAlma | ATTEMPT TO GO INTO AN OBSTACLE ");
                currentGoalApplicable = false;
                 return;
            }else currentGoalApplicable = true;

            map.put(x, y, new Box(false, true, false));  //set dirty
            //pg 67
            curGoal = new GoalTest() {
                @Override
                public boolean isGoalState(Object state) {
                    RobotState robotState =  (RobotState)state;
                    return robotState.getX() == x && robotState.getY() == y;
                }
            };
            //showMap();
        } catch (Exception e ) {
            //e.printStackTrace();
        	CommUtils.outred("setGoal ERROR " + e.getMessage());
        }
    }
	
    
    public List<Action> planForGoal(  String x ,  String y) throws Exception {
        Integer vx = Integer.parseInt(x);
        Integer vy = Integer.parseInt(y);
        setGoal(vx,vy);
        return doPlan();
    }

 
    public Integer getPosX(){ return mapper.getPosX(); }
    public Integer getPosY(){ return mapper.getPosY(); }

    public List<Action> planForNextDirty( ) throws Exception {
        List<Action> actions = new ArrayList<Action>();
         
        int dimX = map.getDimX( );
        int dimY = map.getDimY( );
        CommUtils.outyellow( "... planForNextDirty dimX="+ dimX + " dimY=" + dimY );
        //showCurrentRobotState();
        for( int i = 0; i<=dimX-1 ; i++ ){
            for( int j= 0; j<=dimY-1;j++ ){
                if( map.isDirty(i,j)  ){
                	CommUtils.outyellow( "... planForNextDirty "+ i + "," + j + " curpos= " + getPosX() + "," + getPosY() );
                    setGoal( i,j ) ;
                    actions = doPlan();
                    return actions;
                }
            }
        }
        return actions;
    }
    
    public String planForNextDirtyCompact( ) throws Exception {
    	return planForNextDirty( ).toString().replace("[","").replace("]","").replace(",","").replace(" ","");
    }
     
    
    
    public List<Action> doPlan() throws Exception {
        CommUtils.outyellow("PlanAlma doPlannnnnnnnnnnnnnnnnnnnnnnnnnnnnn robotState x=" + mapper.getRobotState().getX() + " " + mapper.getRobotState().getY());
    	
    	List<Action> actions;
        if( ! currentGoalApplicable ){
        	CommUtils.outred("PlanAlma | doPlan cannot go into an obstacle");
            actions = new ArrayList<Action>();  //TOCHECK
            return actions;		//empty list
        }

        SearchAgent searchAgent;
        Problem problem = new Problem(mapper.getRobotState(),//robotState,
                new Functions(), new Functions(), curGoal, new Functions());
        //println("PlanAlma doPlan newProblem (A) search " );
        searchAgent = new SearchAgent(problem, search);
        actions     = searchAgent.getActions();

        //println("PlanAlma doPlan actions="+actions);

        if (actions == null || actions.isEmpty()) {
            CommUtils.outred("PlanAlma doPlan NO MOVES !!! " + actions  );
            //if (! RoomMap.getRoomMap().isClean()) RoomMap.getRoomMap().setObstacles();  //NO MAY24
            return new ArrayList<Action>();
        } else if (actions.get(0).isNoOp() ) {
        	CommUtils.outyellow("PlanAlma | doPlan NoOp");
            return new ArrayList<Action>();
        }
         
        return actions;
    }
    
    public String doPlanCompact() throws Exception {
    	String p = doPlan().toString().replace("[","").replace("]","").replace(",","").replace(" ","");
    	return p;
    }
    
}
