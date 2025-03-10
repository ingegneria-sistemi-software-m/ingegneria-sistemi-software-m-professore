package demows.conway;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import unibo.basicomm23.utils.CommUtils;

public class LifeCallerWs {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        
    	String uri = "ws://localhost:7110/wsupdates"; //wsupdates : path dell'endpoint
        
        WebSocketClient client = new WebSocketClient(new URI(uri)) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                System.out.println("Connessione aperta!");
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Messaggio ricevuto: " + message);
            }

            @Override
            public void onMessage(ByteBuffer message) {
                System.out.println("Messaggio ByteBuffer ricevuto!");
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Connessione chiusa: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("Errore: " + ex.getMessage());
            }

 
        };//client

        client.connect();
        
        new Thread() {
        	
        	public void run() {
        		CommUtils.outblue("START");
        		
        		while( ! client.isOpen() ) {
        			CommUtils.outblue("waiting connections ...");
        			CommUtils.delay(1000);
        		}
//                if ( client.isOpen() ) {
                    CommUtils.outblue("init grid");
                     
                    client.send("cell-1-2");
                    client.send("cell-2-2");
                    client.send("cell-3-2");

                    
                    client.send("start");
                    CommUtils.delay(3000);
                    client.send("stop");
                   
                    client.close();
                    CommUtils.outblue("BYE");

       		
        	}
        }.start();
        
    }
}
/*
        Scanner scanner = new Scanner(System.in);
        while (client.isOpen()) {
            System.out.print("Inserisci messaggio (o 'exit' per uscire): ");
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                client.close();
                break;
            }
            client.send(message);
        }
*/
    