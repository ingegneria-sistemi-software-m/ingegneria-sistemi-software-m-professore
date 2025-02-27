package unibo.disi.conwaygui.ws; 

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import jakarta.websocket.server.ServerEndpoint;
import unibo.basicomm23.utils.CommUtils;
import unibo.disi.conwaygui.controller.ConwayGuiControllerLifeLocal;
 


//@Component //Ci pensa WebSocketConfiguration
@ServerEndpoint("/wsupdates")
public class WSConwayguiLifeLocal extends AbstractWebSocketHandler {  //implements Runnable
	
	private static WSConwayguiLifeLocal myself   = null;
	private final String name                    = "wslifegui";
	private Set<WebSocketSession> clients        = new HashSet<>();
	private WebSocketSession owner;
	private boolean firstConnection              = true;
 
	public static WSConwayguiLifeLocal getInstance() {
		//CommUtils.outblue( name + " | getInstance " + myself );
		if (myself == null) {
			myself = new WSConwayguiLifeLocal();
		}
		return myself;
	}  
	
	public WSConwayguiLifeLocal() {
		CommUtils.outblue(name + " | WSConwayguiLifeLocal CREATED "  );
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
		CommUtils.outgreen(name + " | receives: " + message );		
		String cmd = message.getPayload();
		if( ! session.equals(owner) ) {
			CommUtils.outmagenta(name + " | received from a non-owner "   );
			return;
		} 
	
		ConwayGuiControllerLifeLocal.lifeController.elabMsg(cmd);
		 
	}
	
}
