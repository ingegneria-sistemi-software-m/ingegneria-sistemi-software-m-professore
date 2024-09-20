package main.java.domain.adapter;

 
import main.java.storage.StorageVolatile;
import unibo.basicomm23.utils.CommUtils;

public class AdapterStorageVolatile extends AdapterStorage  {
  private StorageVolatile storage;
  
  public AdapterStorageVolatile(StorageVolatile storage ) {
       this.storage = storage;
  }
	  
	@Override
	public void createItem(int id, String jsonRep) {
		storage.put(id, jsonRep);		
	}

	@Override
	public String getItem(int id) {
		return storage.get(id);
	}

	@Override
	public boolean deleteItem(int id) {
		return storage.delete(id);
	}

	@Override
	public int getItemNum() {
		return storage.getSize();
	}
 

}
