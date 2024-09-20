package test.java.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.domain.Product;
import main.java.domain.ProductServiceLogic;
import unibo.basicomm23.utils.CommUtils;

public class TestProductServiceLogic {

	
	protected ProductServiceLogic appl;
 	
	@Before
	public void setUp()  {
		CommUtils.outmagenta("test setup ");
		appl = new ProductServiceLogic();
	}
	
	@After
	public void down() { 
		CommUtils.outmagenta("end of test ");
		//System.exit(0);  /AVOID!!!
	}

	@Test
	public void testCrudOps() {
 
 		try {
  			CommUtils.outgreen("testCreate ======================================= ");
  			 Product p1 = new Product(1, "p1",  10 );
   			 appl.createProduct(p1);
//  			 if( appl.isProductPresent(p1) ) 
//  	             CommUtils.outblue("ProductServiceLogic | product present");
//  	         else 
//  	        	 CommUtils.outblue("ProductServiceLogic | product NOT present");
  			 Product pfound = appl.getProduct(1);
  			 CommUtils.outblue("ProductServiceLogic | product 1 found:" + pfound);
  			 assertTrue( pfound.getName().equals("p1") );
  			 pfound = appl.getProduct(2);
  			 CommUtils.outblue("ProductServiceLogic | product 2 found:" + pfound);
  			 assertTrue( pfound.getName().equals("error") );
 
 
			
		} catch (Exception e) {
			CommUtils.outred("testProductCreate ERROR " + e.getMessage());
 			fail("testCrudOps " + e.getMessage());
		}
	} 
	
}
