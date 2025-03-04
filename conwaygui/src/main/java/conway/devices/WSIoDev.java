package conway.devices; 

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import conway.Cell;
import conway.IOutDev;
import conway.LifeController;
import jakarta.websocket.server.ServerEndpoint;
import unibo.basicomm23.utils.CommUtils;
import unibo.disi.conwaygui.controller.ConwayGuiControllerLifeLocal;


/*
 * Handler di websocket che funge da dispositivo di I/O
 */


//@Component //Ci pensa WebSocketConfiguration
@ServerEndpoint("/wsupdates")
public class WSIoDev extends AbstractWebSocketHandler implements IOutDev{  //implements Runnable
	
	private static WSIoDev myself   = null;
	private final String name                    = "WSIoDev";
	private Set<WebSocketSession> clients        = new HashSet<>();
	private boolean firstConnection              = true;
	private boolean ownerOn                      = true;
	private LifeController gameControl;
	private WebSocketSession owner;
 
	public static WSIoDev getInstance( ) {
		CommUtils.outcyan(  "wslifegui | getInstance  "  );
		if (myself == null) {
			myself = new WSIoDev( );
		}
		return myself;
	}  
	
	public WSIoDev() {
		CommUtils.outmagenta(  "wslifegui | CREATING ... "  );		
	}
	public  void setLifeCotrol( LifeController gameControl ) {
		CommUtils.outblue( name + " | injected gameControl=" + gameControl );
		this.gameControl = gameControl;
	}
 
	
	@Override //from AbstractWebSocketHandler
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);		
		CommUtils.outblue(name + " | afterConnectionEstablished as first:" +  firstConnection + " " + clients.size());
		if( firstConnection ) {
			owner           = session;
			firstConnection = false;
		}
        clients.add(session);
	}
	
	/*
	 * Invio comandi alle pagine HTML connesse
	 */
	public void broadcastToWebSocket(String message) {
		//CommUtils.outyellow(name + " broadcastToWebSocket " + message  );
        for (WebSocketSession client : clients) {
            if (client.isOpen()) {
                try {
                    client.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    CommUtils.outred(name + "ERROR: COULD NOT SEND TEXT THROUGH WEBSOCKET TO " + client.getId() + "!");
                }
            }
        } 
    }	
	
	/*
	 * Gestione comandi provenienti dalla GUI
	 */
	@Override //since AbstractWebSocketHandler
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		CommUtils.outgreen(name + " | receives: " + message + " wirh ownerOn=" + ownerOn );		
		String cmd = message.getPayload();
		if( cmd.contains("owneroff")) ownerOn = false;
		if( cmd.contains("owneron"))  ownerOn = true;
		if( ! session.equals(owner) && ownerOn) {
			CommUtils.outmagenta(name + " | received from a non-owner "   );
			return;
		} 	
		//ConwayGuiControllerLifeLocal.lifeController.elabMsg(cmd);
		elabMsg( cmd );		 
	}

	/*
	 * Mappa messaggi ricevuti in chiamate al controller del gioco
	 */
	public void elabMsg(String message) {
		//CommUtils.outgreen(name + " | elabMsg:" + message + " gameControl=" + gameControl);
		if( gameControl == null ) return;
		if( message.equals("start")) {
			gameControl.startTheGame();
		}else if( message.equals("stop")) {
			gameControl.stopTheGame();
		}else if( message.equals("exit")) {
			gameControl.exitTheGame();
		}else if( message.equals("clear")) {	
			gameControl.clearTheGame();
			display("clearmsglist");   //per eliminare la historuìy dei messaggi su "msgslist
 		}
		else if( message.startsWith("cell")) {
			String[] parts = message.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			gameControl.swithCellState(x-1, y-1);  //La GUI comincia da (1,1)
		}
	}

	@Override
	public void display(String msg) {
		broadcastToWebSocket(msg); 		
	}

	@Override
	public void displayCell(Cell cell) {
		int value = cell.getState() ? 1 : 0;
		int x = cell.getX() + 1;  //mapping to GUI coords
		int y = cell.getY() + 1;
		String msg = "cell(" + y + "," + x + ","+ value + ")";		
		//CommUtils.outcyan(name +  " | displayCell "+ msg);
		display( msg );
	}
	
	
}
