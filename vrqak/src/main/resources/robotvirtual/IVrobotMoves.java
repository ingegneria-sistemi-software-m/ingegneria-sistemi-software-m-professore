package main.resources.robotvirtual;

public interface IVrobotMoves {
	//Ispirate da VirtualRobot23
    public void turnLeft() throws Exception;
    public void turnRight() throws Exception;
    public void forward( int time ) throws Exception;
    public void backward( int time ) throws Exception;
    public void halt() throws Exception;

    //Nuove operazioni
    public boolean step(long time) throws Exception;
    public void stepAsynch(int time) throws Exception;
    
    //Aril (Abstract Robot Interaction Lanaguage)
    //cmd=w | s | l | r | h"
    public void move(String cmd ) throws Exception;
}
