package main.java.conway;

 

import conwayMqtt.Cell;
import conwayMqtt.Grid;
import conwayMqtt.Life;
import main.java.conway.devices.OutInMqttForActor;
import unibo.basicomm23.utils.CommUtils;

public class LifeUsageHelper {
	private static LifeUsageHelper helperObj;
	private Life life;
	private OutInMqttForActor outdev;
	
	private int epoch = 0;
	
	public static LifeUsageHelper getInstance() {
		return helperObj;
 	}
	
	public LifeUsageHelper( Life life, OutInMqttForActor outdev ) {
		this.life   = life;
		this.outdev = outdev;
		helperObj   = this;
	}
	
	public void swithCellState(int x, int y) {
		Cell c = life.getCell(x, y); 
		c.switchCellState( );   
		//CommUtils.outblue("LifeControllers withCellState "  + outdev);
		outdev.displayCell(c);
	}

	public void displayGrid(   ) {
		Grid grid     = life.getGrid();
 		int rows = grid.getRowsNum();
		int cols = grid.getColsNum();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = grid.getCell(i,j);
				outdev.displayCell( cell ); 
 			}			
		}
	}

	public void resetAndDisplayGrids(   ) {
		life.resetGrids();
		displayGrid();
		epoch = 0;
	}
	
	
	public boolean fireEpoch() {
			life.computeNextGen();
			displayGrid(   );

			CommUtils.outblue("helper | ---------Epoch ---- "+epoch++ );
			boolean gridEmpty  = life.gridEmpty();
			boolean gridStable = life.gridStable();
			if( gridEmpty || gridStable ) {
//	    		running = false;
	    		String reason = gridStable ? "stable" : "empty";
	    		String outInfo = "lfctrl: GAME ENDED after " + epoch + 
	    				" Epochs since empty=" + gridEmpty + " stable="+ gridStable;
	    		CommUtils.outblack("helper | " + outInfo);
	    		outdev.display(outInfo);
	    		epoch = 0;
	    		return false;
	    	}else return true;
	}
}
