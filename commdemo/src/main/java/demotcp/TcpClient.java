package demotcp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpClient {


    // Indirizzo IP del server
    String serverAddress = "127.0.0.1"; // localhost

    // Porta del server
    int port = 8181;

	public void doJob() throws UnknownHostException, IOException {
	    // Crea un Socket per connettersi al server
	    Socket socket = new Socket(serverAddress, port);
	    System.out.println("Connesso al server: " + serverAddress + ":" + port);
	
	    // Crea flussi di input e output per comunicare con il server
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter out   = new PrintWriter(socket.getOutputStream(), true);
	
	    // Invia un messaggio al server
	    String messageToSend = "Ciao dal client!";
	    out.println(messageToSend);
	    System.out.println("Messaggio inviato al server: " + messageToSend);
	
	    // Legge la risposta dal server
	    String response = in.readLine();
	    System.out.println("Risposta dal server: " + response);
	
	    // Chiude la connessione
	    socket.close();
	    
	}
	public static void main(String[] args) throws IOException {
		new TcpClient().doJob();
	}
}
