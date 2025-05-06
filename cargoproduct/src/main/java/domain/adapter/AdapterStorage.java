package main.java.domain.adapter;

import main.java.domain.IStorage;
import main.java.storage.StorageVolatile;
import unibo.basicomm23.utils.CommUtils;

public abstract class AdapterStorage implements IStorage {


	
	public static AdapterStorage setup() {
		String mongourl = System.getenv("MONGO_URL");       //in yaml
//		                = CommUtils.getEnvvarValue("MONGO_URL\"); 
		CommUtils.outmagenta("AdapterStorage mongoUrl=" + mongourl );
		String storageramAddr = System.getenv("STORAGERAM_URL");  //in yaml
		CommUtils.outmagenta("AdapterStorage storageramAddr=" + storageramAddr);
//		String storageUsed    = PropUtils.get("STORAGE");         //see cargoservice.properties	   		
// 		CommUtils.outmagenta("AdapterStorage setup storageUsed=" + storageUsed 
// 				+ " mongo?=" + storageUsed.equals("mongo"));		
		if( storageramAddr != null ) return new AdapterStorageramservice( storageramAddr );
		if( mongourl != null ) return new AdapterStorageMongo(mongourl);		
		CommUtils.outred("WARNING: NO URL for external storage - using StorageVolatile"  );
		StorageVolatile storage = StorageVolatile.getStorageSingleton();
		return new AdapterStorageVolatile(storage );
	}


}
