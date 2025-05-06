package main.java.domain;

import java.util.List;

public interface ICrudOps {
	//CRUD operations
	public boolean isProductPresent(Product p);
	public Product createProduct(Product body);
	public Product getProduct(String productId); 
	public List<String> getAllProducts(); 
	public Product getProduct(int productId);
	public boolean deleteProduct(String productId);  
	public boolean deleteProduct(int productId);
}
 