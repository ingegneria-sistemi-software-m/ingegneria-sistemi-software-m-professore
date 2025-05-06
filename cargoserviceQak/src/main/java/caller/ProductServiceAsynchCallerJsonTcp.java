package main.java.caller;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class ProductServiceAsynchCallerJsonTcp  {

	private String p = "product( '{\"productId\":23,\"name\":\"p23\",\"weight\":230}' )";
    protected String name = "cargoservicecaller";
    protected Interaction commChannel;

    public ProductServiceAsynchCallerJsonTcp() {
    	 commChannel = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8111");
    	 CommUtils.outblue(name + " | connected client=" + commChannel);
	}


	public void activate() throws Exception {
 		CommUtils.outblue(name + " | activate: "  );	
        createAProduct( p );
//         deleteProduct(23);
         
         
         getAllProducts();

         //		CommUtils.outblue(name + " | getProduct(33): "  );	
//         getProduct(33);
//		CommUtils.outblue(name + " | createAProduct again: " + p);	
//        createAProduct( p );  //Duplicate product
//        getProduct(31);
	}
	
	protected void createAProduct(String p) throws Exception {
 		IApplMessage req = CommUtils.buildRequest(name, "createProduct", p, "productservice");
		CommUtils.outcyan(name + " | sends request   " + req);
		IApplMessage answer = commChannel.request(req); // raise exception
		CommUtils.outcyan(name + " | answer= " + answer);
		if( answer.msgContent().equals("productid(0)") ) {
			CommUtils.outmagenta(name + " | createAProduct error: " + answer);
		}
		else CommUtils.outblue(name + " | createAProduct answer=" + answer);
//		 CompletableFuture.supplyAsync( () ->{
	}
	protected void getProduct(int n) throws Exception {
        IApplMessage req = CommUtils.buildRequest(name, "getProduct", "product(" + n + ")", "productservice");
        //CommUtils.outblue(name + " | sends request on " + connSupport);
        IApplMessage answer = commChannel.request(req);  //raise exception
        CommUtils.outgreen(name + " | getProduct answer=" + answer);
		
	}
	
	protected void deleteProduct(int n) throws Exception {
        IApplMessage req = CommUtils.buildRequest(name, "deleteProduct", "product(" + n + ")", "productservice");
        CommUtils.outgreen(name + " | sends req " + req);
        IApplMessage answer = commChannel.request(req);  //raise exception
        CommUtils.outgreen(name + " | deleteProduct answer=" + answer);
		
	}
	protected String getAllProducts() throws Exception {
		IApplMessage req = CommUtils.buildRequest(name, "getAllProducts", "dummy(0)", "productservice");
		CommUtils.outcyan(name + " | sends  " + req ); //+ " on " + commChannel
		try {
			IApplMessage answer = commChannel.request( req ); // raise exception
			CommUtils.outcyan(name + " | getAllProducts answer=" + answer.toString() );
			return answer.msgContent();
		} catch (Exception e) {
			CommUtils.outred(name + " | getAllProducts ERROR " + e.getMessage());
			return "";
		}
		
	}	 
	public static void main(String[] args) throws Exception{
		//TCP call requires knowledge at system level!
		ProductServiceAsynchCallerJsonTcp caller = 
			new ProductServiceAsynchCallerJsonTcp( );
		caller.activate();
	}

}
