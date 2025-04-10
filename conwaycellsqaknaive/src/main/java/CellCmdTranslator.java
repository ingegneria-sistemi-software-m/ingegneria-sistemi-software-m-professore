package main.java;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class CellCmdTranslator {

	protected ActorBasic owner;
	
	public CellCmdTranslator(ActorBasic owner) {
		this.owner = owner;
	}

	public void cvtToApplMessage( String msg ) {
		IApplMessage applMsg = null;
		if( msg.startsWith("cell") ) {
			String[] parts = msg.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			//La GUI comincia da (1,1)
			String cellCoords = "("+(y-1)+","+(+x-1)+")";
			applMsg = CommUtils.buildDispatch(
					owner.getName(), "changeCellState", "changeCellState"+cellCoords, owner.getName());
			owner.sendMsgToMyself(applMsg);
		}
		
		// NON RISPONDE ALLA GUI MA a CallerLifeActorTcp
		/*
		//Invio messaggi in forma di autodispatch
		if( msg.equals("start")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "startcmd", "startcmd(fromgui)", owner.getName());
		}
		if( msg.equals("stop")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "stopcmd", "stopcmd(fromgui)", owner.getName());
		}
		if( msg.equals("clear")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "clearcmd", "clearcmd(fromgui)", owner.getName());
		}
		if( msg.equals("exit")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "exitcmd", "exitcmd(fromgui)", owner.getName());
		}
		*/
		
	}

}
