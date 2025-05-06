package main.java.caller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
/*
 * ---------------------------------------------
 * WARNING:
 *  	ATTIVARE con gradlew runCaller
 *      configurare eureka-client.properties
 * ---------------------------------------------
 */
import com.netflix.discovery.EurekaClient;
import main.java.domain.Product;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class ProductServiceDiscoverAnother  {
	
//	private String p = "product( '{\"productId\":23,\"name\":\"p23\",\"weight\":230}' )";
	private EurekaClient eurekaClient;
//	private String serviceName = "productservice";
	private Interaction connSupport;
	
	protected boolean discoverProductService(String serviceName) {
		try {
			CommUtils.outcyan("connectService discoverProductService:     " );
		   	eurekaClient  = CommUtils.createEurekaClient(); //Usa il file eureka-client.properties 
			CommUtils.outcyan("connectService eurekaClient:     " + eurekaClient);
			String[]  hostPort = CommUtils.discoverService( eurekaClient, serviceName );
			CommUtils.outcyan("connectService hostPort:     " + hostPort);
			 
			if( hostPort != null ) {
				String host = hostPort[0];
				String port = hostPort[1];
				CommUtils.outcyan("connectService Hostname: " + host);
				CommUtils.outcyan("connectService Port:     " + port);
 				connSupport = //new TcpConnection(host, Integer.parseInt(port));
				 ConnectionFactory.createClientSupport23(ProtocolType.tcp, host, port);
 				return true;
			}else {
				CommUtils.outred("" + serviceName + " not found");
			}
		} catch (Exception e) {
			CommUtils.outred("ERROR:" + e.getMessage());
		}
		return false;
	}
 
	protected void useService( ) {
		try {
			Product p1 = new Product(17, "p17", 170);
			String p1Json = CommUtils.toPrologStr(p1.toString(),true);
 			CommUtils.outcyan("doJob p1Json:" + p1Json);
			String pp = "product( JSON )".replace("JSON", p1Json);
			IApplMessage msg = CommUtils.buildRequest("caller", "createProduct", pp, "productservice");
			CommUtils.outblue("SEND:" + msg);
			IApplMessage answer = connSupport.request(msg);
			CommUtils.outblue("ANSWER:" + answer);			
		} catch (Exception e) {
			CommUtils.outred("ERROR:" + e.getMessage());
		}
		
	}
	
    protected void shutDownTheClients() throws Exception {
        // Chiudi il client Eureka
 		CommUtils.delay(1000); 
    	CommUtils.outblue("ServiceUsage shutting down the EurekaClient after 1 sec delay");
		connSupport.close();
        eurekaClient.shutdown();   	
    }
    
    protected void getProduct(int n) throws Exception {
        IApplMessage req = CommUtils.buildRequest("tcpcaller", "getProduct", "product(" + n + ")", "productservice");
        //CommUtils.outblue(name + " | sends request on " + connSupport);
        IApplMessage answer = connSupport.request(req);  //raise exception
        CommUtils.outmagenta("tcpcaller | getProduct answer=" + answer);
		
	}
	
	public void doJob() throws Exception   {
		CommUtils.outcyan("connectService doJob"  );
		if( discoverProductService("productservice")  ) {
            useService();
            getProduct(4);
            shutDownTheClients();
//    		getAllProducts();
		}

        System.exit(0);
	}
	
	
	public void configure() {
	      // Configurazione dell'istanza (informazioni sull'applicazione)
	      MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig() {
	            @Override
	            public String getAppname() {
	               return "my-app";
	            }

	            @Override
	            public String getInstanceId() {
	               return "my-instance-id";
	            }

	            @Override
	            public String getHostName(boolean refresh) {
	               return "localhost";
	            }

	            @Override
	            public int getNonSecurePort() {
	               return 8080;
	            }
	      };

	      // Manager per le informazioni dell'istanza
	      ApplicationInfoManager appInfoManager = new ApplicationInfoManager(instanceConfig);
	               //InstanceInfo.InstanceStatus.UP);

	      // Configurazione del client (URL del server Eureka)
	      DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig() {
	            
	    	    @Override
	            public List<String> getEurekaServerServiceUrls(String myZone) {
	    	    	ArrayList<String> a = new ArrayList<String>();
	    	    	a.add("http://localhost:8761/eureka/");
	               return a;
	            }

	            @Override
	            public boolean shouldRegisterWithEureka() {
	               return true;
	            }

	            @Override
	            public boolean shouldFetchRegistry() {
	               return true;
	            }
	      };

	      // Creazione di un Eureka Client
	      EurekaClient eurekaClient = new DiscoveryClient(appInfoManager, clientConfig);

	      // Registrare il servizio
	      appInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);

	      // Ottenere informazioni su altri servizi registrati
	      System.out.println("Fetching instances from Eureka:");
	      eurekaClient.getInstancesByVipAddress("my-app", false).forEach(instance ->
	            System.out.println("Found instance: " + instance.getHostName() + ":" + instance.getPort())
	      );

	      // Chiudere il client quando non è più necessario
	      Runtime.getRuntime().addShutdownHook(new Thread(eurekaClient::shutdown));
	   }		

    public static boolean checkEureka( ) {
        //CommUtils.outmagenta( "EurekaUniboUtils | checkEureka"  );
    	com.netflix.discovery.EurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
    	List<String> eurekaUrlDefaulZone = clientConfig.getEurekaServerServiceUrls("defaultZone");
    	
    	CommUtils.outmagenta( "EurekaUniboUtils | eurekaUrlDefaulZone num="+ eurekaUrlDefaulZone.size() );
    	if( eurekaUrlDefaulZone.size() == 0 ) return false;
    	
        String url = clientConfig.getEurekaServerServiceUrls("defaultZone").get(0).replace("/eureka/", "");

        String zoneSpec = System.getenv("EUREKA_CLIENT_SERVICEURL_DEFAULTZONE");
        CommUtils.outmagenta( "EurekaUniboUtils | eureka url config="+ url + " zoneSpec=" + zoneSpec);
        
        if( zoneSpec != null )  
        	url = zoneSpec.replace("/eureka/", "");
        CommUtils.outmagenta( "EurekaUniboUtils | eureka url="+ url  );
       
        for( int i=1;i<=10;i++) {
        try {
            // Creiamo una connessione HTTP all'URL
            URL serverUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();

            // Impostiamo il metodo della richiesta (GET in questo caso)
            connection.setRequestMethod("GET");

            // Definiamo un timeout per la connessione e la lettura
            connection.setConnectTimeout(5000);  // Timeout di connessione in millisecondi (5 secondi)
            connection.setReadTimeout(5000);     // Timeout di lettura in millisecondi (5 secondi)

            // Facciamo la richiesta al server e otteniamo il codice di risposta
            int statusCode = connection.getResponseCode();
            //System.out.println("Serverr statusCode: " + statusCode);
            // Verifica se il codice di risposta indica successo (codici 2xx)
            if (statusCode >= 200 && statusCode < 300) {
                //System.out.println("Server EUREKA raggiungibile. Risposta: " + statusCode);
                return true;
            } else {
                CommUtils.outred("Errore nel contattare il server. Codice di risposta: " + statusCode);
                return false;
            }

        } catch (Exception e) {
            // Gestiamo gli errori che possono verificarsi (es. il server non è raggiungibile)
            CommUtils.outred("Errore: Impossibile raggiungere il server. i=" + i);
            CommUtils.delay(2000);
            //if( i == 3) return false;
        }
    }//for
    return false;
    }
    
	public static DiscoveryClient registerTheServiceOnEureka( EurekaInstanceConfig instanceConfig ) {
		if( checkEureka() ) {
	        CommUtils.outcyan("registerTheServiceOnEureka with EurekaInstanceConfig port=" 
	        		+ instanceConfig.getNonSecurePort()
					+ " host=" + instanceConfig.getHostName(false)
					+ " IP=" + instanceConfig.getIpAddress()
	        		+ " appName=" + instanceConfig.getAppname() );
//	        EurekaInstanceConfig instanceConfig           = new MyDataCenterInstanceConfig(); //new EurekaUniboServiceConfig();  
	        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig);
	        //DefaultEurekaClientConfig contiene le informazioni di configurazione per il client Eureka, 
	        //come eureka.serviceUrl.defaultZone, e altri dettagli per connettersi al server Eureka
	        DefaultEurekaClientConfig clientConfig = new  DefaultEurekaClientConfig();
	        CommUtils.outcyan("registerTheServiceOnEureka  | region="+clientConfig.getRegion());
	        DiscoveryClient client = new DiscoveryClient(applicationInfoManager, clientConfig);
	        
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	        CommUtils.outcyan("registerTheServiceOnEureka done"  );
	        
	        return client;
		}else {
			CommUtils.outred("Eureka server not reachable");
			return null;
		}	     
	}	
	
	
	
	

	public static void main(String[] args) throws Exception  {
		//TCP call requires knowledge at system level!
 		ProductServiceDiscoverAnother caller = new ProductServiceDiscoverAnother( );
		caller.doJob();
	}

}
