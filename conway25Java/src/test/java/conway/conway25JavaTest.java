package conway;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import conway.devices.ConwayInputMock;

//By default, JUnit comes with a bundled copy of hamcrest-core

public class conway25JavaTest {
private static ConwayInputMock cim;

	@BeforeClass
	public static void setup() {
		System.out.println("setup");
    	//configureTheSystem
        Life life           = new Life( 3,3 );
        LifeController cc   = new LifeController(life);   
        cim = new ConwayInputMock(cc,life);		
	}
	
	@After
	public void down() {
		System.out.println("down");
	}
	
	@Test
	public void test1() {
		System.out.println("ok test1");
		cim.simulateUserControl();
		//assert ??
	}
	
	@Test
	public void yyy() {
		System.out.println("ok yyy");
	}
}

//Con gradlew test, controllare - logs/apptest.log - build/reports/tests/test/index.html

