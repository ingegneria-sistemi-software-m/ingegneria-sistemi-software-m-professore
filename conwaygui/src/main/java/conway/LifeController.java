package conway;
import java.util.concurrent.TimeUnit;
import conway.devices.WSIoDev;
import unibo.basicomm23.utils.CommUtils;

/*
 * LifeController di conwaygui. 
 * Definisce funzioni di controllo del gioco:
 * 
 * - play
 * - displayGrid
 * - resetAndDisplayGrids
 * - swithCellState
 * - startTheGame
 * - stopTheGame
 * - clearTheGame
 * - exitTheGame
 * 
 * Queste funzioni sono invocate da WSioDev
 */

public class LifeController {
    private int generationTime = 500;
    private  Life life;
    public   IOutDev outdev; 

    //Aggiunte
 	protected boolean running = false;
    protected int epoch = 0;

    
    public LifeController(Life game){  
        this.life = game;
        outdev    = WSIoDev.getInstance(); 
        CommUtils.outyellow("LifeController CREATED ... "  + outdev);
     }
 
/*
 * Funzioni di controllo del gioco
 */
    
	public void swithCellState(int x, int y) {
		Cell c = life.getCell(x, y); 
		c.switchCellState( );   
		//CommUtils.outblue("LifeControllers withCellState "  + outdev);
		outdev.displayCell(c);
	}
	
	public void startTheGame() {
		if( running ) return;   //start sent while running
		running = true;
		play();		
	}
	
	public void stopTheGame() {
		running = false;
	}

	public void clearTheGame() {
 		stopTheGame();
 		CommUtils.delay(500);   //prima fermo e poi ...
		epoch = 0;
		resetAndDisplayGrids(  );   
		outdev.display("clear");   // ... pulisco la GUI
		//resetAndDisplayGridsOptimized(  );  
	}
	
	public void exitTheGame() {
		System.exit(0);
	}
	
    protected void play() {  
			new Thread() {
			public void run() {			
				outdev.display("game started"); 
				while( running ) {
					try {
						TimeUnit.MILLISECONDS.sleep(generationTime);
						life.computeNextGen();
						
						//Come si riduce il traffico di rete?
						//Troppi messaggi con questo metodo?   						
						displayGrid(   );

						CommUtils.outblue("---------Epoch ---- "+epoch++ );
						boolean gridEmpty  = life.gridEmpty();
						boolean gridStable = life.gridStable();
						if( gridEmpty || gridStable ) {
				    		running = false;
				    		String reason = gridStable ? "stable" : "empty";
				    		outdev.display("grid GAME ENDED after " + epoch + 
				    				" Epochs since empty=" + gridEmpty + " stable="+ gridStable);
				    		epoch = 0;
				    	}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}//while
				outdev.display("game stopped"); 
			}
			}.start();
    }


	public void displayGrid(   ) {
		Grid grid     = life.getGrid();
 		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = grid.getCell(i,j);
				outdev.displayCell(cell); 
 			}			
		}
	}
	

	public void resetAndDisplayGrids(   ) {
		life.resetGrids();
		displayGrid();
	}
	
	
    /*
     * reset grid and nextGrid in life
     * by updating the display  only to make a cell dead
     * in order to avoid rows x cols x nclients messages
     * 
     * called by clearTheGame
     */
	public void resetAndDisplayGridsOptimized(   ) {		
		Grid grid     = life.getGrid();
		Grid nextGrid = life.getNextGrid();
		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
		CommUtils.outmagenta("LifeController resetAndDisplayGrids " + rows + " " + cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				Cell cell = grid.getCell(i,j);
				//OTTIMIZZAZIONE
				if( cell.getState() ) {
					cell.setState(false);
					outdev.displayCell(cell);  
				}
				if( nextGrid.getCell(i, j).getState()) {
					nextGrid.getCell(i, j).setState(false);
				}
			}			
		}
	}
	
}
