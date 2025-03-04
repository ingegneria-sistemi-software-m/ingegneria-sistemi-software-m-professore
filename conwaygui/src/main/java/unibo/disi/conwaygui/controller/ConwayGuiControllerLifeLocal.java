package unibo.disi.conwaygui.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import conway.Life;
import conway.LifeController;
import conway.devices.WSIoDev;
import unibo.basicomm23.utils.CommUtils;
 
 
@Controller
public class ConwayGuiControllerLifeLocal {

	@Value("${spring.application.name}")
	String appName;
	
	@Value("${server.port}")
	String serverport;

	public static LifeController lifeController;
	private Life life;
	private boolean started = false;
	
	public ConwayGuiControllerLifeLocal() {
		initLifeApplication();
	}
	
	protected void initLifeApplication() {
		CommUtils.outyellow("ConwayGuiControllerLifeLocal CREATING ... "  );
        life             = new Life( 20,20 );
        lifeController   = new LifeController(life);   	
		CommUtils.outyellow("ConwayGuiControllerLifeLocal injects  lifeController in wsiodev"  );
        WSIoDev.getInstance().setLifeCotrol(lifeController); //injections
	}

	@GetMapping("/")
	public String homePage(Model model) {
		CommUtils.outmagenta("ConwayGuiControllerLifeLocal homePage");
		//ModelAndView modelAndView = new ModelAndView();
	    //modelAndView.setViewName("guipage"); 
		if( ! started ) {
			life.resetGrids();
			model.addAttribute("arg", appName );
			started = true;
		}
	    return "guipage";  
	}
	
	@RequestMapping("/testHTTPCallResponseBody")
	@ResponseBody
	public String testHTTPCallResponseBody( ) {
		CommUtils.outblue("ConwayGuiControllerLifeLocal testHTTPCallResponseBody"  );
		return "{\"message\": \"ConwayGuiControllerLifeLocal Hello from testHTTPCallResponseBody\"}";
	}
	
	@RequestMapping("/getserverip")
	@ResponseBody
	public String getserverip() {
		System.out.println("ConwayGuiControllerLifeLocal doing getserverip"  );
		String p    = CommUtils.getEnvvarValue("HOST_IP"); // in docker-compose
		if( p != null ) {
			String s = "{\"host\":\"P\"}".replace("P",p );
			System.out.println("ConwayGuiControllerLifeLocal con HOST_IP=" + s);		      
			return s;				
		}
		String myip = getServerLocalIp();
		System.out.println("ConwayGuiControllerLifeLocal getserverip: myip=" + myip);
		if( myip == null ) { //non ho la rete ...
			String s = "{\"host\":\"localhost\"}";
			System.out.println("ConwayGuiControllerLifeLocal senza myip=" + s);		      
			return s;				
		}
 		else {
 			String mypubip = getMyPublicip();
 			System.out.println("ConwayGuiControllerLifeLocal getserverip: mypubip=" + mypubip);

 			
 			String s = "{\"host\":\"P\"}".replace("P",myip );
			System.out.println("ConwayGuiControllerLifeLocal con myip=" + s);		      
			return s;				
 		}
	}
 
	protected  String getMyPublicip(){
		try {
			// URL di un servizio che restituisce l'indirizzo IP pubblico
			String serviceUrl = "https://checkip.amazonaws.com";

			// Creazione della connessione HTTP
			URL url = new URL(serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			// Lettura della risposta
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			
			//String localip = getServerLocalIp();
			
			// Stampa dell'indirizzo IP pubblico
			String myip = response.toString().trim();
			//System.out.println("Il tuo indirizzo IP pubblico è: " +  myip);
		    return  myip;
		    //return localip;
		} catch (Exception e) {
			System.out.println("Errore nell'ottenere l'indirizzo IP: " + e.getMessage());
			return null;
		}
	}
	
	protected String getServerLocalIp() {
		
        try {
            Enumeration<NetworkInterface> interfacce = NetworkInterface.getNetworkInterfaces();
            while (interfacce.hasMoreElements()) {
                NetworkInterface interfaccia = interfacce.nextElement();
                Enumeration<InetAddress> indirizzi = interfaccia.getInetAddresses();
                while (indirizzi.hasMoreElements()) {
                    InetAddress indirizzo = indirizzi.nextElement();
                    if (!indirizzo.isLoopbackAddress()) { // Esclude l'indirizzo loopback (127.0.0.1)
                        //System.out.println(interfaccia.getDisplayName() + ": " + indirizzo.getHostAddress());                        
                        if( indirizzo.getHostAddress().startsWith("192.168")) {
                        	System.out.println("ConwayGuiControllerLifeLocal ==== " + indirizzo.getHostAddress());
                        	return indirizzo.getHostAddress();
                        }
                    }
                }
            }
            return null;
        } catch (SocketException e) {
            System.err.println("Errore durante la ricerca degli indirizzi IP: " + e.getMessage());
            return null;
        }			
 	
	}
	 
}
