package demotcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
public class TcpServer  {
private ServerSocket serverSocket;
 
//Porta su cui il server ascolterà
protected int port = 8181;

	public void doJob() throws IOException {
		// Crea un ServerSocket per ascoltare le connessioni in entrata
		serverSocket = new ServerSocket(port);
		System.out.println("Server in ascolto sulla porta " + port);
		
		// Attende una connessione dal client
		Socket clientSocket = serverSocket.accept();
		System.out.println("Client connesso: " + clientSocket.getInetAddress());
		
		// Crea flussi di input e output per comunicare con il client
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		
		// Legge il messaggio dal client
		String message = in.readLine();
		System.out.println("Messaggio ricevuto dal client: " + message);
		
		// Invia una risposta al client
		out.println("Messaggio ricevuto dal server: " + message);
		
		// Chiude le connessioni
		clientSocket.close();
		serverSocket.close();
	} 

	public static void main(String[] args) throws IOException {
		new TcpServer().doJob();
	}
}
