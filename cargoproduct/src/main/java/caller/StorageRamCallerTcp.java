package main.java.caller;

import it.unibo.kactor.sysUtil;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class StorageRamCallerTcp  {

    protected String name = "storageramcaller";
    protected Interaction commChannel;

    public StorageRamCallerTcp(String storageramAddr) {
    	//CommUtils.outgreen(name + " |  StorageRamCallerTcp constructor  "   );
//    	String storageramAddr = System.getenv("STORAGERAM");
//    	CommUtils.outgreen(name + " |  storageramAddr=  " + storageramAddr );
    	if( storageramAddr == null ) {
    		commChannel = ConnectionFactory.createClientSupport23(ProtocolType.tcp, "localhost", "8110");
    	}else {
    		//CommUtils.outgreen(name + " |  commChannel host= storageram " + storageramAddr  );
    	    commChannel = ConnectionFactory.createClientSupport23(ProtocolType.tcp, storageramAddr, "8110");	
    	}
    	 CommUtils.outblue(name + " | connected on " + commChannel);
	}


	public void activate() throws Exception {
		CommUtils.outblue(name + " | put " );	
		//String info = ToProlog.cvtString( "{\"productId\":31,\"name\":\"p31\",\"weight\":311}" );
		String info = sysUtil.toPrologStr( "{\"productId\":31,\"name\":\"p31\",\"weight\":311}", true );
		CommUtils.outblue(name + " | put  " + info);	
		put( 1, info );
        getItem( 1 );
        String v = sysUtil.toPrologStr("pluto and pippo hello", true);
 		CommUtils.outblue(name + " | createAProduct again: " + v );	
		put( 1, v );
        getItem(1);
		put( 1, "a(pippo)" );
        getItem(1);

 
	}
	
	public void put(int k, String p) throws Exception {
	 	IApplMessage putcmd = CommUtils.buildDispatch(name, "createItem", 
	 			"item("+k+","+sysUtil.toPrologStr(p,true)+")", "storagevolatile");
        commChannel.forward(putcmd);
		CommUtils.outcyan(name + " | sends cmd   " + putcmd);
//		 CompletableFuture.supplyAsync( () ->{
	}

	public void delete(int k) throws Exception {
     	IApplMessage deletecmd = CommUtils.buildDispatch(name, "deleteItem", "item("+k+")", "storagevolatile");
        commChannel.forward(deletecmd);
        CommUtils.outcyan(name + " | sends delete cmd   " + deletecmd);
	}
	
	public String getItem(int n) throws Exception {
	 	IApplMessage getreq = CommUtils.buildRequest (name, "getItem","item("+n+")", "storagevolatile");
        //CommUtils.outgreen(name + " | sends request  " + getreq);
        IApplMessage answer = commChannel.request(getreq);  //raise exception
        //CommUtils.outgreen(name + " |  getItem answer=" + answer.msgContent());
        //answer.msgContent() = item('{"productId":33,"name":"p33","weight":333}')
        if( answer.msgContent().contains("item(0)"))  //non esiste
        	return null;
        else return answer.msgContent().replace("item('", "").replace("')", "");
 	}  
	
	public String getAllItems() throws Exception{
        CommUtils.outgreen(name + " | getAllItems    "   );
	 	IApplMessage getreq = CommUtils.buildRequest (name, "getAllItems","dummy(0)", "storagevolatile");
        IApplMessage answer = commChannel.request(getreq);  //raise exception
        CommUtils.outgreen(name + " | getAllItems answer  " + answer);
        return answer.msgContent();
		
	}
	 
	public static void main(String[] args) throws Exception{
		StorageRamCallerTcp caller = new StorageRamCallerTcp( "localhost" );
		caller.activate();
	}

}
