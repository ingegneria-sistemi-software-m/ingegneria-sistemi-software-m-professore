package main.java.caller;

import it.unibo.kactor.sysUtil;
import main.java.ToProlog;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class StorageRamCallerTcp  {

    protected String name = "tcpcaller1";
    protected Interaction commChannel;

    public StorageRamCallerTcp() {
    	 commChannel = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8110");
    	 CommUtils.outblue(name + " | connected on " + commChannel);
	}


	public void activate() throws Exception {
		CommUtils.outblue(name + " | put " );	
		//String info = ToProlog.cvtString( "{\"productId\":31,\"name\":\"p31\",\"weight\":311}" );
		String info = sysUtil.toPrologStr( "{\"productId\":31,\"name\":\"p31\",\"weight\":311}", true );
		CommUtils.outblue(name + " | put  " + info);	
		put( 1, info );
        getItem( 1 );
//        String v = sysUtil.toPrologStr("pluto and pippo hello");
// 		CommUtils.outblue(name + " | createAProduct again: " + v );	
//		put( 1, v );
//        getItem(1);
//		put( 1, "a(pippo)" );
//        getItem(1);
 
	}
	
	protected void put(int k, String p) throws Exception {
	 	IApplMessage putcmd = 
	 			CommUtils.buildDispatch(name, "createItem", "item("+k+","+p+")", "storagevolatile");
        commChannel.forward(putcmd);
		CommUtils.outcyan(name + " | sends cmd   " + putcmd);
//		 CompletableFuture.supplyAsync( () ->{
	}
	protected void getItem(int n) throws Exception {
	 	IApplMessage getreq = 
	 			CommUtils.buildRequest (name, "getItem",    "item("+n+")", "storagevolatile");
        //CommUtils.outblue(name + " | sends request on " + connSupport);
        IApplMessage answer = commChannel.request(getreq);  //raise exception
        CommUtils.outgreen(name + " | getProduct getItem=" + answer);
		
	}
	 
	public static void main(String[] args) throws Exception{
		try {
			StorageRamCallerTcp caller = new StorageRamCallerTcp( );
			caller.activate();
		}catch( Exception e) {
			CommUtils.outred("ERROR " + e.getMessage());
		}
	}

}
