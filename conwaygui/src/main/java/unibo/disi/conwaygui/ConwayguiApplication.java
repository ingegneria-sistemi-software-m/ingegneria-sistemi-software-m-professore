package unibo.disi.conwaygui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;

/*
 * The @SpringBootApplication annotation is equivalent to using 
 * @Configuration, @EnableAutoConfiguration and @ComponentScan 
 * with their default attributes
 * 
 */
@SpringBootApplication
public class ConwayguiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConwayguiApplication.class, args);
	}


}
