package demows.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import unibo.basicomm23.utils.CommUtils;

public class WsServerDemoCaller {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        
    	String uri = "ws://localhost:8887"; //wsupdates : path dell'endpoint
        
        WebSocketClient client = new WebSocketClient(new URI(uri)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("WsServerCaller | Connessione aperta!");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("WsServerCaller | Messaggio ricevuto: " + message);
            }

            @Override
            public void onMessage(ByteBuffer message) {
                System.out.println("WsServerCaller | Messaggio ByteBuffer ricevuto!");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WsServerCaller | Connessione chiusa: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WsServerCaller | Errore: " + ex.getMessage());
            }

 
        };//client

        client.connect();
        
        new Thread() {
        	
        	public void run() {
        		CommUtils.outblue("WsServerCaller | START");
        		
        		while( ! client.isOpen() ) {
        			CommUtils.outblue("waiting connections ...");
        			CommUtils.delay(1000);
        		}
        		
        		client.send("msg1");
        		client.send("request_r1");
        		        		
        	}
        }.start();
        
    }


}
