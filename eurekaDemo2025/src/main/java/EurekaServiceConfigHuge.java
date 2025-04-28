package main.java;
import java.util.Map;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.EurekaInstanceConfig;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
//import com.netflix.appinfo.PropertiesInstanceConfig;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.DiscoveryClient;
  

import unibo.basicomm23.utils.CommUtils;

/*
 * Usato da ServiceRegistred.java
 */
public class EurekaServiceConfigHuge implements EurekaInstanceConfig{  
	
	private static DiscoveryClient client;
	
	public EurekaServiceConfigHuge() {
		CommUtils.outmagenta("EurekaServiceConfig CREATED " + Thread.currentThread());
	}
	@Override
	public String getAppname( ) {
//		CommUtils.outblack("EurekaServiceConfig  | getAppname " + Thread.currentThread());
		return "ctxeureka";
	}
	
	@Override
	public String getHostName(boolean refresh) {
//		CommUtils.outblack("EurekaServiceConfig|getHostName"    
//				+ " AT " + TimeUnit.MILLISECONDS.toSeconds( System.currentTimeMillis() )
//				+ " N=" + (client==null ? "?" : (client.getApplications().size()))
//				+ " IN " + Thread.currentThread()); 
		//return super.getHostName(refresh); //restituisce nat480s  
		//return "localhost";
		return "localhost";
	}

/*
 * I metodi che seguono sono invocati dal 
 * DiscoveryClient ogni 30 sec ma non hanno effetto	
 */
 

	@Override
	public int getNonSecurePort() {
//		CommUtils.outblack("EurekaServiceConfig  | getNonSecurePort " + Thread.currentThread());
		return 8443;
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

	
	public static DiscoveryClient registerService( EurekaServiceConfigHuge instanceConfig ) {
        CommUtils.outmagenta("EurekaServiceConfig  | registerService  " + instanceConfig.getAppname() );
	        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig  );
	        DefaultEurekaClientConfig clientConfig = new  DefaultEurekaClientConfig();
	        client = new DiscoveryClient(applicationInfoManager, clientConfig);	        
	   CommUtils.outmagenta("EurekaServiceConfig  | client:" + client.getApplications().size()
			   + " IN " + Thread.currentThread() );
	        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
	        return client;
	}
	@Override
	public String getInstanceId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getAppGroupName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isInstanceEnabledOnit() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getSecurePort() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isNonSecurePortEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean getSecurePortEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getLeaseRenewalIntervalInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getLeaseExpirationDurationInSeconds() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getVirtualHostName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSecureVirtualHostName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getASGName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, String> getMetadataMap() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DataCenterInfo getDataCenterInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getIpAddress() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusPageUrlPath() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStatusPageUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getHomePageUrlPath() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getHomePageUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getHealthCheckUrlPath() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getHealthCheckUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSecureHealthCheckUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getDefaultAddressResolutionOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}	
	
 
}
