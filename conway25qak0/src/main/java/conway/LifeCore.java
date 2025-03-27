package main.java.conway;

import conwayMqtt.Life;
import conwayMqtt.LifeController;
import unibo.basicomm23.utils.CommUtils;

public class LifeCore {
	
	public static void create() {
		CommUtils.aboutThreads("LifeCore | STARTS " ); 
        Life life           = new Life( 20,20 );
        LifeController cc   = new LifeController(life);   
		
	}

}
