package main.java.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import main.java.domain.adapter.AdapterStorage;
import unibo.basicomm23.utils.CommUtils;

public class ProductServiceLogic implements ICrudOps{
	private  final Logger logger    = LoggerFactory.getLogger(ProductServiceLogic.class);
	  private Product wrongProduct  = new Product(0, "wrong",  0 );
	  private IStorage dataStore    = AdapterStorage.setup();
	  

	  @Override
	  public Product createProduct(Product product)   {
		   int pid = product.getProductId();
		   Product productAnswer;
	      	if ( dataStore.getItem( pid ) == null ) {
				dataStore.createItem(pid,product.toString());
				String outStr = "ProductServiceLogic | createProduct:" + product ;
				CommUtils.outcyan( outStr );
				productAnswer = product;
		   } else {
			   CommUtils.outmagenta("ProductServiceLogic | WARNING - Duplicate key, Product Id: " + product.getProductId());
			   productAnswer = wrongProduct;
		   } 
		   logger.info( "ProductServiceLogic |"+ productAnswer.toString() );
		   return productAnswer;
	  }

	  @Override
	  public boolean isProductPresent(Product p) {
		  int pid = p.getProductId();
		  return dataStore.getItem( pid ) == null;
	  }
	  
	  @Override
	  public Product getProduct(String productId) {	
		  try {
			  return getProduct(Integer.parseInt(productId));
		  }catch(Exception e) {
			  return wrongProduct;
		  }
	  }

	  @Override
	  public Product getProduct(int productId)   {		 
		  String rep = dataStore.getItem(productId);
		  if( rep != null ) return new Product(rep);
		  else return wrongProduct;
	  }  

 	  @Override
	  public boolean deleteProduct(String productId) {	
		  try {
			  return deleteProduct(Integer.parseInt(productId));
		  }catch(Exception e) {
			  return false;
		  }
	  }

	 @Override
	  public boolean deleteProduct(int productId) {
            return dataStore.deleteItem(productId);
	  }

	 /*
	  * Main method to test the class (and check the log using gradlew run)
	  * Better to use a test unit (and check the appTest log using gradlew test)
	  */
	 public static void main(String[] args) {
		 CommUtils.clearlog("./logs/app_cargoproduct.log");
		 int pid                  = 20;
		 Product p1               = new Product(pid, "p20",  20); 
		 ProductServiceLogic appl = new ProductServiceLogic();
		 appl.createProduct(p1);
		 Product pfound = appl.getProduct(pid);
		 CommUtils.outblue("ProductServiceLogic | product 25 found:" + pfound);
		 CommUtils.delay(3000);
		 CommUtils.outblue("ProductServiceLogic | BYE"  );
//		 pfound = appl.getProduct(2);
//		 CommUtils.outblue("ProductServiceLogic | product 2 found:" + pfound);
		 		 
	 }
}
