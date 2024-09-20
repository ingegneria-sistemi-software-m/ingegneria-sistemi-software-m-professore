package main.java.domain;

public interface IStorage {

	public void createItem(int id, String jsonRep);
	public String getItem( int id );
	public boolean deleteItem( int id );
	public int getItemNum();
}
