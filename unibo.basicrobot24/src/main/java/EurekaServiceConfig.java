package main.java;
import com.netflix.appinfo.MyDataCenterInstanceConfig;

import unibo.basicomm23.utils.CommUtils;

/*
 * Usato da ServiceRegistred.java
 */
public class EurekaServiceConfig extends MyDataCenterInstanceConfig{
	@Override
	public String getAppname( ) {
		return "ctxbasicrobot";
	}
	
	@Override
	public String getHostName(boolean refresh) {
		String ip ="";
		String serviceip = System.getenv("SERVICE_IP") ;
 		if( serviceip != null) {
 			ip = serviceip;
 		}else ip = "192.168.1.18";
 		return ip;
	}

	@Override
	public int getNonSecurePort() {
		return 8020;
	}

    /*
    Indicates the time in seconds that the eureka server waits since it received
    the last heartbeat before it can remove this instance from its view
    and there by disallowing traffic to this instance.
    */
	@Override
	public int getLeaseExpirationDurationInSeconds(){
		return 60*10*6;
	}
    /*
    Indicates how often (in seconds) the eureka client needs to send heartbeats
    to eureka server to indicate that it is still alive.
    */ 
	@Override
	public int getLeaseRenewalIntervalInSeconds() {
		return 60*10*6;
	}
}
