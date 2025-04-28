package main.java;
 
import com.netflix.discovery.DiscoveryClient;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.tcp.TcpServer;
import unibo.basicomm23.utils.CommUtils;

//docker run -d -p 8761:8761 --name eureka-server netflixoss/eureka:1.10.11  NON SU DOCKERHUB

/*
 * Esempio di come registare un servizio su Eureka
 * Il servizio potrebbe essere un attore qak (che fa da facade per gli attori di un sistema)
 * 
 * Ogni istanza registrata in Eureka deve inviare heartbeat periodici per mantenere 
 * il proprio stato come "UP".
 */
public class ServiceRegistred implements IApplMsgHandler{
 
	private TcpServer myserver;
	private DiscoveryClient eurekaClient;
 
	protected void activateTcpServer() {
		myserver = new TcpServer("servertcp", 8443, this); //this: implements IApplMsgHandler
		myserver.activate();	
		CommUtils.outgreen("TcpServer activated on port 8443 ");
	} 
	
	public void doJob() {
		//REGISTRAZIONE DEL SERVIZIO
  		eurekaClient = CommUtils.registerService( new EurekaServiceConfig() );
 		activateTcpServer();
		CommUtils.delay(600000);
		stop();
	}
	

    public void stop() {
        CommUtils.outgreen("TcpServer stop and eurekaClient shutdown ");
        myserver.deactivate();
        eurekaClient.shutdown(); //deregistra
    }

/*
 * For IApplMsgHandler
 */
	@Override
	public String getName() {
 		return "TcpServiceRegistred";
	}

	@Override
	public void elaborate(IApplMessage message, Interaction conn) {
		CommUtils.outblue(getName() + " received: " + message);		
	}
	
	
    public static void main(String[] args) throws Exception {
        // Avvia il servizio e registra su Eureka
        ServiceRegistred service = new ServiceRegistred();
        service.doJob();
    }
	
}
