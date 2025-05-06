package main.java.caller;



	import org.eclipse.californium.core.CoapClient;
	import org.eclipse.californium.core.CoapResponse;
	import java.io.UnsupportedEncodingException;
	import java.net.URLEncoder;

	public class CoapClientWithDebug {

	    public static void main(String[] args) {
	        try {
	            String M = "Ciao, questo è un test con 'virgole' e 'apostrofi'";
	            
	            // Codifica il messaggio M
	            String encodedMessage = URLEncoder.encode(M, "UTF-8");
	            String coapUri = "coap://californium.eclipseprojects.io:5683/test?q=" + encodedMessage;

	            // Debug: stampa l'URI
	            System.out.println("Sending request to: " + coapUri);

	            // Crea il client CoAP
	            CoapClient client = new CoapClient(coapUri);

	            // Imposta un timeout
	            client.setTimeout((long) 5000);  // Timeout di 5 secondi

	            // Invia la richiesta GET
	            CoapResponse response = client.get();

	            // Verifica la risposta
	            if (response != null) {
	                System.out.println("Response Code: " + response.getCode());
	                System.out.println("Response Text: " + response.getResponseText());
	            } else {
	                System.out.println("No response received.");
	            }

	            // Chiudi il client
	            client.shutdown();
	        } catch (UnsupportedEncodingException e) {
	            System.out.println("Error encoding message: " + e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}


