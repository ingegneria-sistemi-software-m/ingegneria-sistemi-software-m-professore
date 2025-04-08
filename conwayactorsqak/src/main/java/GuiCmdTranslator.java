package main.java;

import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class GuiCmdTranslator {

	protected ActorBasic owner;
	
	public GuiCmdTranslator(ActorBasic owner) {
		this.owner = owner;
	}

	public void cvtToApplMessage( String msg ) {
		IApplMessage applMsg = null;
		//CommUtils.outblue("cvtToApplMessage .... "+msg);
		/*//SOLO PER cell
		if( msg.startsWith("cell") ) {
			String[] parts = msg.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			//La GUI comincia da (1,1)
			String cellCoords = "("+(x-1)+","+(+y-1)+")";
			applMsg = CommUtils.buildDispatch(
					owner.getName(), "cellstate", "cellstate"+cellCoords, owner.getName());

		}
		*/
		//Invio messaggi in forma di autodispatch
		if( msg.equals("start")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "startcmd", "startcmd(fromgui)", owner.getName());
		}
		else if( msg.equals("stop")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "stopcmd", "stopcmd(fromgui)", owner.getName());
		}
		else if( msg.equals("clear")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "clearcmd", "clearcmd(fromgui)", owner.getName());
		}
		else if( msg.equals("exit")) {
			applMsg = CommUtils.buildDispatch(
				owner.getName(), "exitcmd", "exitcmd(fromgui)", owner.getName());
		}
		//CommUtils.outblue( owner.getName() + " sendMsgToMyself ........ " + applMsg);
		if( applMsg != null)  owner.sendMsgToMyself(applMsg);
	}

}
