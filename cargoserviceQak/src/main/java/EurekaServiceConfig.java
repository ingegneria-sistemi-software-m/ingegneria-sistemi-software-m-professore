package main.java;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;

import unibo.basicomm23.utils.CommUtils;

/*
 * Usato dal server Eureka per configurare il servizio
 * Si veda https://www.baeldung.com/eureka-self-preservation-renewal
 * https://medium.com/@fahimfarookme/the-mystery-of-eureka-self-preservation-f2db91454f2d
 */

/*
 * In paratica extends EurekaInstanceConfig 
 * 
 * Informazioni di configurazione richieste dall'istanza per registrarsi con il server Eureka. 
 * Una volta registrati, gli utenti possono cercare informazioni in EurekaClient
 */

public class EurekaServiceConfig extends MyDataCenterInstanceConfig{
	private int ncalls = 0;
	//Ottieni il nome dell'applicazione da registrare con Eureka.
	@Override
	public String getAppname( ) {
		return "ctxcargoservice";
	}
	
	//Ottiene il nome host associato a questa istanza.
	@Override
	public String getHostName(boolean refresh) {
		
		String raspAddr = CommUtils.getEnvvarValue("RASP_ADDR");
		if( raspAddr != null ) return raspAddr;
		
		String ip = CommUtils.getMyPublicip(); //localhost()
 
		if (ncalls++ % 10 == 0) {
			CommUtils.outblack("cargoservice EurekaServiceConfig getHostName=" + ip 
					+ " ncalls=" + ncalls
					+ " AT:" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
		}
		return ip;
		//return "localhost";
	}

	/*
	//Ottieni l'IPAdress dell'istanza.
    @Override
    public String getIpAddress() {
        String ipAddress = System.getenv("SERVICE_IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
        	CommUtils.outmagenta("EurekaServiceConfig ipAddress=" + ipAddress);
            return ipAddress;
        } else {
        try {
            // Ritorna l'IP della macchina su cui è in esecuzione il servizio
        	String addr = InetAddress.getLocalHost().getHostAddress();
        	CommUtils.outmagenta("EurekaServiceConfig addr=" + addr);
            return addr;
        } catch (Exception e) {
            // Gestione degli errori in caso di problemi nel recuperare l'indirizzo
            CommUtils.outred("EurekaServiceConfig getIpAddress ERROR " + e.getMessage());
            return "127.0.0.1"; // Default a localhost se non riesce a ottenere l'indirizzo
        }
        }
    }
    */

	//Ottieni la non-secureporta su cui l'istanza deve ricevere traffico.
	@Override
	public int getNonSecurePort() {
		return 8111;
	}

	/*
	 * indica il tempo in secondi che il server Eureka attende da quando ha ricevuto 
	 * l'ultimo heartbeat da un client prima di poter rimuovere tale client dal suo registro
	 */
//	@Override
//	public int getLeaseExpirationDurationInSeconds(){
//		//valore predefinito: 90.
//		return 60*10*6;
//	}
 
	/*
	 * il server calcola i battiti cardiaci previsti al minuto 
	 * da tutti i client registrati: il valore predefinito è 0,85
	 */
 
	@Override
	public int getLeaseRenewalIntervalInSeconds() {
		return 60;
	}
	
	public static DiscoveryClient myRegister( EurekaInstanceConfig instanceConfig ) {
		//if( checkEureka() ) {
	        CommUtils.outcyan("EurekaInstanceConfig | myRegister with  port=" 
	        		+ instanceConfig.getNonSecurePort()
					+ " host=" + instanceConfig.getHostName(false)
					+ " IP=" + instanceConfig.getIpAddress()
					+ " DT=" + instanceConfig.getLeaseExpirationDurationInSeconds()
	        		+ " appName=" + instanceConfig.getAppname() );
//	        EurekaInstanceConfig instanceConfig           = new MyDataCenterInstanceConfig(); //new EurekaUniboServiceConfig();  
	        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig  );
	        //DefaultEurekaClientConfig contiene le informazioni di configurazione per il client Eureka, 
	        //come eureka.serviceUrl.defaultZone, e altri dettagli per connettersi al server Eureka
	        DefaultEurekaClientConfig clientConfig = new  DefaultEurekaClientConfig();
	        CommUtils.outcyan("myRegister  | region="+clientConfig.getRegion());
	        //applicationInfoManager.initComponent(instanceConfig);
	        DiscoveryClient client = new DiscoveryClient(applicationInfoManager, clientConfig);
	        
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	        CommUtils.outcyan("EurekaInstanceConfig | myRegister done"  );
	        
	        return client;

	}	

}
