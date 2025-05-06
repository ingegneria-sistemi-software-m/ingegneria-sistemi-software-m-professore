package main.java.caller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;

public class ProductServiceCallerMqtt extends AbstractCaller{
	private static final Logger logger = LoggerFactory.getLogger("ProductServiceCallerMqtt");
	private String p = "product( '{\"productId\":43,\"name\":\"p43\",\"weight\":433}' )";
	
	public ProductServiceCallerMqtt(String name, ProtocolType protocol, String hostAddr, String entry) {
		super(name, protocol, hostAddr, entry);
		CommUtils.outblue("ProductServiceCallerMqtt avviato correttamente.");
	}

	@Override
	protected void body() throws Exception {
        createAProduct(p);
        getProduct(43);
       System.exit(0);
	}
	
 	protected void loadProduct(String p) throws Exception {
// 		IApplMessage req = CommUtils.buildRequest(name, "loadProduct", "product(1)", "productservice");
 		
 	}
	
//	protected void createAProduct(String p) throws Exception {
//		IApplMessage req = CommUtils.buildRequest(name, "createProduct", p, "productservice");
//		CommUtils.outblue(name + " | sends request  " + req);
//		//IApplMessage answer = connSupport.request(req); // raise exception
//		IApplMessage answer = commChannel.request( req );
//		CommUtils.outblue(name + " | createAProduct answer=" + answer);
//	}
//	protected void getProduct(int n) throws Exception {
//        IApplMessage req = CommUtils.buildRequest(name, "getProduct", "product(" + n + ")", "productservice");
//        CommUtils.outblue(name + " | sends request on " + commChannel);
//        IApplMessage answer = commChannel.request(req);  //raise exception
//        CommUtils.outblue(name + " | getProduct answer=" + answer);
//		
//	}
	 
	public static void main(String[] args) {
		ProductServiceCallerMqtt caller = 
			new ProductServiceCallerMqtt("callermqtt", ProtocolType.mqtt,
					//"tcp://test.mosquitto.org:1883", "unibo/qak/productservice");
					"tcp://localhost:1883", "unibo/qak/productservice");	
					//"tcp://broker.hivemq.com:1883", "unibo/qak/productservice");
		caller.activate();
	}

}

/*
ho attivato mosquitto sulla porta 1883 sul mio PC usando l'immagine docker 
eclipse-mosquitto:2.0.19. Quando cerco di connettermi usando tcp://localhost:1883 
ottengo sempre l'eccezione Connection lost

vorrei sapere quale versione di immagine docker di mosquitto permette a default 
di definire un listener per permettere accessi remoti

A partire dalla versione 2.0 di Eclipse Mosquitto, il comportamento di default del broker 
è stato cambiato per ragioni di sicurezza. Nelle versioni precedenti alla 2.0, 
Mosquitto accettava connessioni da qualsiasi indirizzo di rete senza necessità 
di configurazione aggiuntiva. 

*/