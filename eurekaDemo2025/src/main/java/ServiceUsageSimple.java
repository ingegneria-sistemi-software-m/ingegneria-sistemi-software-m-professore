package main.java;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.utils.CommUtils;


/*
 * Esempio di come usare un servizio registrato su Eureka
 * Il servizio potrebbe essere un attore qak 
 * con cui si comuninca inviando IApplMessages via  Tcp/CoAP
 */
public class ServiceUsageSimple {
	
    private EurekaClient eurekaClient;
    private String serviceName = "ctxeureka";

    
	protected void connectAndUseService(String host, int port) {
		try {
			CommUtils.outcyan("ServiceUsageSimple | found connectService Hostname: " + host);
			CommUtils.outcyan("ServiceUsageSimple | found connectService Port:     " + port);
			TcpConnection conn = new TcpConnection(host, port);
			CommUtils.outcyan("ServiceUsageSimple | conn: " + conn);
			for( int i=1; i<=3; i++) {
				IApplMessage msg = CommUtils.buildDispatch("sender","m1","m"+i,serviceName);
				CommUtils.outblue("SEND: " + msg);
				conn.forward(msg);
				CommUtils.delay(1000);
			}
			conn.close();
//			shutDownEurekaClient();
		} catch (Exception e) {
			CommUtils.outred("ERROR:" + e.getMessage());
		}
		
	}
    public void doJob() {
    	//EUREKA CLIENT
    	eurekaClient = CommUtils.createEurekaClient( new EurekaServiceConfig()  ); 
    	
    	
//	       EurekaInstanceConfig instanceConfig   =  new MyDataCenterInstanceConfig(); //OK Usa il file eureka-client.properties 
//	//         EurekaInstanceConfig instanceConfig           =  new DataCenterInstanceConfig();   //OK
//	        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig); //DEPRECATED MA OK
//	        eurekaClient = new DiscoveryClient(applicationInfoManager, new DefaultEurekaClientConfig() ); // setEurekaZone()
//	        CommUtils.outmagenta(" ....... " + eurekaClient );

    	/*
		Applications applications = eurekaClient.getApplications();
		showApplications(applications);
    	//CommUtils.outyellow("" + eurekaClient );
*/		
		//DISCOVER
		String[]  hostPort = CommUtils.discoverService(eurekaClient, serviceName);
		//String[]  hostPort = CommUtils.discoverService( serviceName );  //USA una sua EurekaServiceConfig 
		if( hostPort != null ) {
            connectAndUseService(hostPort[0], Integer.parseInt(hostPort[1]));
		}else {
			CommUtils.outred("Discoverable " + serviceName + " not found");
		}			
    }
    
    protected void showApplications(Applications applications) {
	  CommUtils.outcyan("ServiceUsageSimple | showApplications eurekaClient=" + eurekaClient + " applications: " + applications.getRegisteredApplications().size());
      for (Application application : applications.getRegisteredApplications()) {
          CommUtils.outcyan("ServiceUsageSimple | showApplications Application Name: " + application.getName() + " N=" + application.getInstances().size());	
      }   	
   	
    }

    protected void shutDownEurekaClient() {
        // Chiudi il client Eureka
 		CommUtils.delay(1000); 
    	CommUtils.outblue("ServiceUsage shutting down the EurekaClient after 1 sec delay");
        eurekaClient.shutdown();   	
    }

    public static void main(String[] args) throws Exception {
//    	String myIp = CommUtils.getMyPublicip();
//    	CommUtils.outmagenta("My public IP="+myIp );
    	new ServiceUsageSimple().doJob();
    }

}
