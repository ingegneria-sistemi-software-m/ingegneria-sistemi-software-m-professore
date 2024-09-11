package main.java.domain;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unibo.basicomm23.utils.CommUtils;

public class Product{
	
	  private static final Logger logger = LoggerFactory.getLogger(Product.class);
	  private  int productId;
	  private  String name;
	  private  int weight;

	/*
	 * Costructor based on a Json String
    */
	public Product(String jsonStr) {
		try {
			CommUtils.outcyan("Product create with:" + jsonStr);
			JSONObject j = (JSONObject) new JSONParser().parse(jsonStr);
			long jpd = (long) j.get("productId");
			productId = Long.valueOf(jpd).intValue();
			name =		(String) j.get("name");
			long jw   = (long) j.get("weight");
			weight=  Long.valueOf(jw).intValue(); 
		} catch (ParseException e) {
			CommUtils.outred("Product | creation error");
			productId = 0;
			weight    = 0;
			name      = "error";
		}
		CommUtils.outblue("Product | created:" +this);
		logger.info("Product  | constructor json:" + this);
	}
	
	/*
	 * Constructor based on an id
	 */	
	public Product( int id ) {
		this( ""+id,"unknown","unknown");
	}

	/*
	 * Constructor based on String values
	 */	
	public Product(String pId, String pname, String pweight) {
		try {
			productId = Integer.parseInt(pId);
			name      = pname;
			weight    = Integer.parseInt(pweight);
		}catch(Exception e) {
			CommUtils.outred("Product creation error");
			productId = 0;
			weight    = 0;
			name      = "error";
		}
		CommUtils.outblue("Product | created:" +this);
		logger.info("Product  | constructor values String:" + this);
	}
	
	/*
	 * Constructor based on int and String values
	 */	
	public Product(int productId, String name, int weight) {
	    this.productId = productId;
	    this.name     = name;
	    this.weight = weight;
		CommUtils.outblue("Product | created:" +this);
		logger.info("Product  | constructor values:" + this);
 	}

	/*
	 * SELETTORI
	 */
	public int getProductId() {
		    return productId;
    }

	public String getName() {
		    return name;
	}

	public int getWeight() {
		    return weight;
	}
	
	@Override
	public String toString() {
		return "{\"productId\":ID,\"name\":NAME,\"weight\":W}"
				.replace("ID", ""+productId).replace("NAME", "\""+name+"\"").replace("W",""+weight);
	}
	
	 /*
	  * Main method to test the class (and check the log using gradlew run)
	  * Better to use a test unit (and check the app_cargoproductTest.log using gradlew test)
	  */

	 public static void main(String[] args) {
		 Product p1 = new Product(1, "p1",  10 );
	 }

 
}
