package test.java.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.domain.Product;
import unibo.basicomm23.utils.CommUtils;
 

public class TestProduct {
 	
	@Before
	public void setUp()  {
		CommUtils.outmagenta("test setup ");
	}
	
	@After
	public void down() { 
		CommUtils.outmagenta("end of test ");
		//System.exit(0);  /AVOID!!!
	}
	
	@Test
	public void testCreate() {
 
 		try {
  			CommUtils.outcyan("testCreate =====");
			Product p  = new Product( 1,"p1",10) ;
 			
			CommUtils.outcyan("testCreate | created " + p);
			
			assertTrue( p.getProductId() == 1 );
			assertTrue( p.getName().equals("p1") );
			assertTrue( p.getWeight() == 10 );
						
		} catch (Exception e) {
			CommUtils.outred("testCreate ERROR " + e.getMessage());
 			fail("testCreate " + e.getMessage());
		}
	} 
	
}
