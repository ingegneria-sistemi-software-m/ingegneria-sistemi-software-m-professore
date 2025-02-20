package conway25JavaTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//By default, JUnit comes with a bundled copy of hamcrest-core

public class conway25JavaTest {

	//@Before
	public void setup() {
		System.out.println("setup");
	}
	
	//@After
	public void down() {
		System.out.println("down");
	}
	
	@Test
	public void xxx() {
		System.out.println("ok xxx");
	}
	
	//@Test
	public void yyy() {
		System.out.println("ok yyy");
	}
}
