package main.java.caller;

import com.netflix.appinfo.InstanceInfo;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class ProductServiceCallerJsonTcp extends AbstractCaller{

	private String p = "product( '{\"productId\":31,\"name\":\"p31\",\"weight\":311}' )";
	public ProductServiceCallerJsonTcp(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol, hostAddr, entry);
	}
	


	@Override
	protected void body() throws Exception {
        createAProduct( p );
        getProduct(31);
//        createAProduct( p );  //Duplicate product
//        getProduct(31);
       //System.exit(0);
	}
	
//	protected void createAProduct(String p) throws Exception {
//		IApplMessage req = CommUtils.buildRequest(name, "createProduct", p, "productservice");
//		CommUtils.outcyan(name + " | sends request   " + req);
//		IApplMessage answer = commChannel.request(req); // raise exception
//		if( answer.msgContent().equals("productid(0)") ) {
//			CommUtils.outmagenta(name + " | createAProduct error: " + answer);
//		}
//		else CommUtils.outblue(name + " | createAProduct answer=" + answer);
//	}
//	protected void getProduct(int n) throws Exception {
//        IApplMessage req = CommUtils.buildRequest(name, "getProduct", "product(" + n + ")", "productservice");
//        //CommUtils.outblue(name + " | sends request on " + connSupport);
//        IApplMessage answer = commChannel.request(req);  //raise exception
//        CommUtils.outgreen(name + " | getProduct answer=" + answer);
//		
//	}
	 
	public static void main(String[] args) {
		//TCP call requires knowledge at system level!
		ProductServiceCallerJsonTcp caller = 
			new ProductServiceCallerJsonTcp("friendjsontcp", ProtocolType.tcp,"localhost", "8111");
		caller.activate();
	}

}
