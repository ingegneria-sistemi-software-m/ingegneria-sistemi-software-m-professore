package main.java.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.FindIterable;

import unibo.basicomm23.utils.CommUtils;

public  class  StorageVolatile {
	 
	private static final Logger logger = LoggerFactory.getLogger(StorageVolatile.class);
	private Hashtable<Integer, String> data = new Hashtable<Integer, String>();
   
	private static StorageVolatile storageSingleton = null;
	
	public static StorageVolatile getStorageSingleton() {
		if (storageSingleton == null) {
			storageSingleton = new StorageVolatile();
		}
        logger.info("StorageVolatile getStorageSingleton=" + storageSingleton);
		return storageSingleton;
	}
        
        
	public void put(int id, String jsonRep) {
		data.put(id, jsonRep);
		logger.info("StorageVolatile | put:" + id + " N=" + data.size());
	}

	public String get(int productId) {
		String s = data.get(productId);
		logger.info("StorageVolatile | get:" + productId + ": (JSonString) " + s);
		return data.get(productId);
	}
	
	public List<String> getAllItems(){
		//return Collections.list(data.elements()); 
        List<String> documentList = new ArrayList<>();
        Iterator<String> iter = data.elements().asIterator();
 
        // Aggiungi ogni documento alla lista
		while (iter.hasNext()) {
			documentList.add( CommUtils.toPrologStr(iter.next(), true));
		}
 
        return documentList;	
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
