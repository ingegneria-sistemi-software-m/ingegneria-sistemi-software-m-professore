package main.java.storageram;

import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unibo.basicomm23.utils.CommUtils;

public  class  StorageOnRam {
	 
	private static final Logger logger = LoggerFactory.getLogger("StorageOnram");
	private Hashtable<Integer, String> data = new Hashtable<Integer, String>();
   
	private static StorageOnRam storageSingleton = null;
	
	public static StorageOnRam getStorgareSingleton() {
        logger.info("StorageOnRam getStorgareSingleton");
		if (storageSingleton == null) {
			storageSingleton = new StorageOnRam();
		}
		return storageSingleton;
	}
        
        
	public void put(int id, String value) {
		data.put(id, value);
		CommUtils.outcyan("put:" + id + " N=" + data.size());
		logger.info("put:" + id + " N=" + data.size());
	}

	public String get(int productId) {
		String s = data.get(productId);
		CommUtils.outcyan("get:" + productId + " : " + s);
		logger.info("get:" + productId + " : " + s);
		String v = data.get(productId);
		if (v == null) return "0";
		else
			return v;
		//return data.get(productId);
	}

	public boolean delete(int id) {
		String s = data.remove(id);
		logger.info("delete:" + id + " N=" + data.size());
		CommUtils.outcyan("delete" + id + " result= " + s);
		return s != null;
	}

	public int getSize() {
		logger.info("getSize:" + data.size());
		return data.size();
	}	
 
	public List<String> getAllItems(){
		logger.info("getAllItems"  );
		CommUtils.outred("getAllItems" +  " N=" + data.size());
		return Collections.list(data.elements()); 
	}

}
