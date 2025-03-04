package conway;

 

public class MainConway  {

    public static void main(String[] args) {
    //sysUtil.aboutThreads("MainConway | STARTS " ); //Richiede dipendenze
    	//configureTheSystem
        Life life           = new Life( 3,3 );
        LifeController cc   = new LifeController(life);   
         
        //the system is started by the GUI
        
        
    }

}