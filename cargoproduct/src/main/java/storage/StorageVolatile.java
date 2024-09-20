package main.java.storage;

import java.util.Hashtable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unibo.basicomm23.utils.CommUtils;

public   class  StorageVolatile {
	 
	private final Logger logger = LoggerFactory.getLogger(StorageVolatile.class);
	private Hashtable<Integer, String> data = new Hashtable<Integer, String>();

	public void put(int id, String jsonRep) {
		data.put(id, jsonRep);
		logger.info("StorageVolatile | put:" + id + " N=" + data.size());
	}

	public String get(int productId) {
		String s = data.get(productId);
		logger.info("StorageVolatile | get:" + productId + ": (JSonString) " + s);
		return data.get(productId);
	}

	public boolean delete(int id) {
		String s = data.remove(id);
		logger.info("StorageVolatile | delete:" + id + " N=" + data.size());
		return s != null;
	}

	public int getSize() {
		logger.info("StorageVolatile | getSize:" + data.size());
		return data.size();
	}	
 

}
