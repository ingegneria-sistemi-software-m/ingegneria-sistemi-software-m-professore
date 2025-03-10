package demows.server;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import unibo.basicomm23.utils.CommUtils;

public class WsServerDemo extends WebSocketServer{

	private WebSocket conn;
	
	public WsServerDemo(int port) {
        super(new InetSocketAddress(port));
    }

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		CommUtils.outblue("WsServerDemo | onOpen");
		this.conn = conn;
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		CommUtils.outblue("WsServerDemo | onClose");
		
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
		CommUtils.outblue("WsServerDemo | onMessage: " + message);
		elabMsg(message);
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		CommUtils.outblue("WsServerDemo | onError: " + ex.getMessage());
		
	}

	@Override
	public void onStart() {
		CommUtils.outblue("WsServerDemo | onStart");
		
	}

/*
 * ------------------------------------------------------	
 */
	
protected void elabMsg(String msg) {
	if( msg.startsWith("request")) {
		conn.send("replyTo_" + msg);
		//Dopo avere risposto, il server invia 5 messaggi sulla connessione
		for( int i=1; i<=5; i++ ) {
			CommUtils.delay(1000);
			if( conn.isOpen() ) conn.send("info_"+i  );
			else break;
		}
	}else {
		conn.send("received_" + msg);
	}
}
	
	
	
    public static void main(String[] args) {
        int port = 8887; // Porta su cui il server ascolterà
        WsServerDemo server = new WsServerDemo(port);
        server.start();
    }
}
	