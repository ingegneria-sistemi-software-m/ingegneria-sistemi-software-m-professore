package main.java.conway;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class GuiCmdTranslator {

	protected ActorBasic gameControl;
	
	public GuiCmdTranslator(ActorBasic gameControl) {
		this.gameControl = gameControl;
	}

	public void cvtToApplMessage( String msg ) {
		IApplMessage applMsg = null;
		if( msg.startsWith("cell") ) {
			String[] parts = msg.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			//La GUI comincia da (1,1)
			String cellCoords = "("+(x-1)+","+(+y-1)+")";
			applMsg = CommUtils.buildDispatch(
					gameControl.getName(), "cellstate", "cellstate"+cellCoords, gameControl.getName());

		}
		//Invio messaggi in forma di autodispatch
		if( msg.equals("start")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "startGame", "startGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("stop")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "stopGame", "stopGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("clear")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "clearGame", "clearGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("exit")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "exitGame", "exitGame(fromgui)", gameControl.getName());
		}
		gameControl.sendMsgToMyself(applMsg);
	}

}
