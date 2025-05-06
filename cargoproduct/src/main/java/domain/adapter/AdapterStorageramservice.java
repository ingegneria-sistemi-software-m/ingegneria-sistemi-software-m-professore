package main.java.domain.adapter;

 

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import main.java.caller.StorageRamCallerTcp;
import unibo.basicomm23.utils.CommUtils;

public class AdapterStorageramservice extends AdapterStorage  {
  private StorageRamCallerTcp tcpClient ;
  
  public AdapterStorageramservice( String hostAddr) {
	  //CommUtils.outgreen( "AdapterStorageramservice | constructor  "   );
	  tcpClient = new StorageRamCallerTcp(hostAddr);
  }
	  
	@Override
	public void createItem(int id, String jsonRep) {
		try {
			tcpClient.put(id, jsonRep);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public String getItem(int id) {
		try {
			return tcpClient.getItem(id);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<String> getAllItems(){
		try {
			String s = tcpClient.getAllItems( );
			//CommUtils.outred("AdapterStorageramservice getAllItems s="+ s); //s=products(['{"productId":33,"name":"p33","weight":333}'])
			// 
			Term listProlog = ((Struct)Term.createTerm(s)).getArg(0) ;   //Una lista Prolog
			 
			String st = listProlog.toString().replace("[", "").replace("]", "");   
			//CommUtils.outred("AdapterStorageramservice getAllItems st="+ st); //st=['{"productId":33,"name":"p33","weight":333}', '..'] 
			return 	new ArrayList<String>(Arrays.asList( st )); //new ArrayList<String>(Arrays.asList(s.split(",")));
		} catch (Exception e) {
			//e.printStackTrace();
			CommUtils.outred("AdapterStorageramservice getAllItems ERROR"+ e.getMessage());
			return null;
		}		
	}

		
	@Override
	public boolean deleteItem(int id) {
		try {
			 tcpClient.delete(id);
			 CommUtils.outblue("AdapterStorageramservice | AFTER deleteItem " + id + " "+ getItem(id));
			 return getItem(id) == null;			 
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public int getItemNum() {
		return 0;  //TODO;
	}
 

}
