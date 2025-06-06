package main.java;
import com.netflix.appinfo.ApplicationInfoManager;
//import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
//import com.netflix.appinfo.PropertiesInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
  

import unibo.basicomm23.utils.CommUtils;

/*
 *  
 */
public class EurekaServiceConfig extends MyDataCenterInstanceConfig{  
	
	private static DiscoveryClient client;
	
	public EurekaServiceConfig() {
		CommUtils.outmagenta("EurekaServiceConfig CREATED " + Thread.currentThread());
	}
	@Override
	public String getAppname( ) {
//		CommUtils.outblack("EurekaServiceConfig  | getAppname " + Thread.currentThread());
		return "ctxunibologger";
	}
	
	@Override
	public String getHostName(boolean refresh) {
//		CommUtils.outblack("EurekaServiceConfig|getHostName"    
//				+ " AT " + TimeUnit.MILLISECONDS.toSeconds( System.currentTimeMillis() )
//				+ " N=" + (client==null ? "?" : (client.getApplications().size()))
//				+ " IN " + Thread.currentThread()); 
		//return super.getHostName(refresh); //restituisce nat480s  
		//return "localhost";
		return "192.168.1.214";
	}

/*
 * I metodi che seguono sono invocati dal 
 * DiscoveryClient ogni 30 sec ma non hanno effetto	
 */
 

	@Override
	public int getNonSecurePort() {
		CommUtils.outblack("EurekaServiceConfig  | getNonSecurePort " + Thread.currentThread());
		return 8260;
	}
	/* 

	@Override
	public int getLeaseExpirationDurationInSeconds(){
		CommUtils.outmagenta("EurekaServiceConfig  | getLeaseExpiration  "
				+ Thread.currentThread() );
		return 60;
	}

	@Override
	public int getLeaseRenewalIntervalInSeconds() {
		CommUtils.outgreen("EurekaServiceConfig  | getLeaseRenewalInterval "
				+ Thread.currentThread() );
		return 60;
	}
*/

	
	public static DiscoveryClient registerService( EurekaServiceConfig instanceConfig ) {
        CommUtils.outmagenta("EurekaServiceConfig  | registerService  " + instanceConfig.getAppname() );
	        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig  );
	        DefaultEurekaClientConfig clientConfig = new  DefaultEurekaClientConfig();
	        client = new DiscoveryClient(applicationInfoManager, clientConfig);	        
	   CommUtils.outmagenta("EurekaServiceConfig  | client:" + client.getApplications().size()
			   + " IN " + Thread.currentThread() );
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	        return client;
	}	
	
 
}
