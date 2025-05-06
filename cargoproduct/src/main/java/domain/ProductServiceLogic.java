package main.java.domain;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import main.java.domain.adapter.AdapterStorage;
import unibo.basicomm23.utils.CommUtils;

public class ProductServiceLogic implements ICrudOps{
	  private static ProductServiceLogic psl;  //singleton
	  private  final Logger logger  = LoggerFactory.getLogger(ProductServiceLogic.class);
	  private Product wrongProduct  = new Product(0, "wrong",  0 );
	  private IStorage dataStore    = AdapterStorage.setup();  
	  
	  public static ProductServiceLogic getSingleton() {
		if( psl == null )   psl = new ProductServiceLogic();
		return psl;
	  }

	  /*
	   * Constructor private
	   */
		private ProductServiceLogic() {
			CommUtils.outgreen("ProductServiceLogic | constructor  ");
		}

	  @Override
	  public Product createProduct( Product product)   {
		   CommUtils.outblue( "ProductServiceLogic | creatingProduct:"+ product.toString() );
		   logger.info( "ProductServiceLogic | creatingProduct:"+ product.toString() );
		   int pid = product.getProductId();
		   if( pid == 0) return product;  //non creo un product già wrong
		   Product productAnswer;
		   String pidStr = dataStore.getItem( pid );
		   CommUtils.outgreen("ProductServiceLogic | createProduct " + pid + " pidStr: " + pidStr);
		   
	        if ( pidStr == null ) { 
	      		CommUtils.delay(4000);    //For cargoproductDelay-1.0.jar
				dataStore.createItem(pid,product.toString());
				productAnswer = product;
		   } else {  //ESISTE GIA' UN PRODOTTO CON LO STESSO ID
			   if( pid > 0) {
				   CommUtils.outmagenta("ProductServiceLogic | WARNING - Duplicate key, Product Id: " + product.getProductId());
			   }
			   productAnswer = wrongProduct;
		   } 
		   logger.info( "ProductServiceLogic | createProduct:"+ productAnswer.toString() );
		   return productAnswer;
	  }

	  @Override
	  public boolean isProductPresent(Product p) {
		  int pid = p.getProductId();
		  return dataStore.getItem( pid ) != null;
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
	  public List<String> getAllProducts()  {		
		  //CommUtils.outblue( "ProductServiceLogic | getAllProducts .... "  + dataStore);
		  List<String>  answer = dataStore.getAllItems();
		  //CommUtils.outblue( "ProductServiceLogic | getAllProducts answer:"+answer  );
		  return answer;
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
		 
		 //Uso di variabili di ambiente SCONSIGLIATO
		 
		 int pid                  = 20;
		 Product p1               = new Product(pid, "p20",  20); 
		 ProductServiceLogic appl = new ProductServiceLogic();
		 Product pp1              = appl.createProduct(p1);
		 
		 Product pfound           = appl.getProduct(pid);
		 
		 CommUtils.outblue("ProductServiceLogic | product " + pid + " found:" + pfound);
//		 appl.createProduct(p1);   //AGAIN
		 
/*		  
		 Product p2 = appl.createProduct(new Product("due", "p2",  "2" ));
		 CommUtils.outblue("ProductServiceLogic | product p2:" + p2);

		 
		 Product p3 = appl.createProduct(new Product( "{\"productId\":-3,\"name\":\"p20\",\"weight\":20}" ));
		 CommUtils.outblue("ProductServiceLogic | product p3:" + p3);
		 CommUtils.outblue("ProductServiceLogic | BYE"  );
//		 pfound = appl.getProduct(2);
//		 CommUtils.outblue("ProductServiceLogic | product 2 found:" + pfound);
*/		 		 
	 }
}
