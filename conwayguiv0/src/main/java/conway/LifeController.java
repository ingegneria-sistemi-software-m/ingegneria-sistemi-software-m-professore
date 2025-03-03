package conway;
import java.util.concurrent.TimeUnit;
import conway.devices.ConwayOutput;
import unibo.basicomm23.utils.CommUtils;
import unibo.disi.conwaygui.ws.WSConwayguiLifeLocal;

public class LifeController {
    private int generationTime = 500;
    private  Life life;
    public   IOutDev outdev; 

    //Aggiunte
 	protected boolean running = false;
    protected int epoch = 0;

    public LifeController(Life game){  
        this.life = game;
        outdev = new ConwayOutput( WSConwayguiLifeLocal.getInstance() );
     }
  
    protected void play() { //CHANGED since the game must be user-controlled
			new Thread() {
			public void run() {			
				while( running ) {
					try {
						TimeUnit.MILLISECONDS.sleep(generationTime);
						life.computeNextGen();
						
						//Come si riduce il traffico di rete?
						//Troppi messaggi con questo metodo.   						
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
				}
			}
			}.start();
    }


    /*
     * reset grid and nextGrid in life
     * by updating the display  only to make a cell dead
     * in order to avoid rows x cols x nclients messages
     * 
     * called by elabMsg
     */
	public void resetAndDisplayGrids(   ) {
		Grid grid     = life.getGrid();
		Grid nextGrid = life.getNextGrid();
		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
//		int cellState = 0;
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
	
	
	public void displayGrid(   ) {
		Grid grid     = life.getGrid();
 		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
//		int cellState = 0;
		CommUtils.outmagenta("LifeController displayGrid " + rows + " " + cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				Cell cell = grid.getCell(i,j);
				outdev.displayCell(cell); 
 			}			
		}
	}
	

	public void elabMsg(String message) {
		CommUtils.outblack("LifeController | elabMsg:" + message);
		if( message.equals("start")) {
			if( running ) return;   //start sent while running
			running = true;
			play();
		}else if( message.equals("stop")) {
			running = false;
		}else if( message.equals("exit")) {
			System.exit(0);
		}else if( message.equals("clear")) {		
			epoch = 0;
			resetAndDisplayGrids(  ); //per ridurre il numero di msgs emessi
		}
		else if( message.startsWith("cell")) {
			String[] parts = message.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			Cell c = life.getCell(x-1, y-1);  //La GUI comincia da (1,1)
			c.switchCellState( );   
			outdev.displayCell(c);
			
		}
	}
}
