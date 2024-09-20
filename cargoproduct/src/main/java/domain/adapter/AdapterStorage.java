package main.java.domain.adapter;

import main.java.domain.IStorage;
//import main.java.storage.StorageMongo;
import main.java.storage.StorageVolatile;
import unibo.basicomm23.utils.CommUtils;

public abstract class AdapterStorage implements IStorage {

	
	public static AdapterStorage setup() {
		String mongoUrl = System.getenv("MONGO_URL");  //Set by docker-compose
		 //mongoUrl = "mongodb://localhost:27017";
		if (mongoUrl == null) { 
			CommUtils.outmagenta("USING AdapterStorageVolatile STORAGE");
			return new AdapterStorageVolatile ( new StorageVolatile() );
		}
		else {
			CommUtils.outmagenta("USING AdapterStorageMongo STORAGE: wait a moment ...");
			return null;
		}
	}


}
