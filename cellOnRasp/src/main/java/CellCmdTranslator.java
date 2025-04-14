package main.java;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class CellCmdTranslator {

	protected ActorBasic owner;
	protected IApplMessage cmdstart = CommUtils.buildEvent("callertcp", "startthegame", "startthegame(ok)" );
	protected IApplMessage cmdstop  = CommUtils.buildEvent("callertcp", "stopthecell", "stopthecell(ok)" );
	protected IApplMessage cmdclear = CommUtils.buildEvent("callertcp",    "clearCell",       "clearCell(ok)" );

	
	public CellCmdTranslator(ActorBasic owner) {
		this.owner = owner;
	}

	public void cvtToApplMessage( String msg ) {
		CommUtils.outyellow("cvtToApplMessage " +  owner.getName() + " " + msg);
		IApplMessage applMsg = null;
		if( msg.startsWith("cell") ) {
			String[] parts = msg.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			//La GUI comincia da (1,1)
			String cellCoords = "("+(y-1)+","+(+x-1)+")";
			applMsg = CommUtils.buildDispatch(
					owner.getName(), "changeCellState", "changeCellState"+cellCoords, owner.getName());
		}
		
		//  RISPONDE ANCHE ALLA GUI  grazie a  kernel_rawmsg
 
		//Invio messaggi in forma di autodispatch
		else if( msg.equals("start")) {
			applMsg =  cmdstart;
		}
		else if( msg.equals("stop")) {
			applMsg = cmdstop;
		}
		else if( msg.equals("clear")) {
			applMsg = cmdclear;
		}
//		if( msg.equals("exit")) {
//			applMsg = CommUtils.buildDispatch(
//				owner.getName(), "exitcmd", "exitcmd(fromgui)", owner.getName());
//		}
		if( applMsg != null ) owner.sendMsgToMyself(applMsg);
		
		
	}

}
