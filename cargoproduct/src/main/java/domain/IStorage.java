package main.java.domain;

import java.util.List;

public interface IStorage {

	public void createItem(int id, String jsonRep);
	public String getItem( int id );
	public List<String> getAllItems();
	public boolean deleteItem( int id );
	public int getItemNum();
}
