package unibo.disi.conwaygui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
public class HelloControllerTest {
 
	       @Autowired
	       private TestRestTemplate restTemplate;

	       @Test
	       public void testSayHello() {
	    	   System.out.println("test");
	           ResponseEntity<String> response = 
	               restTemplate.getForEntity("/api/hello", String.class);
	           System.out.println("response=" + response);
	           assertEquals(HttpStatus.OK, response.getStatusCode());
	           assertEquals("Hello, World!", response.getBody());
	       }
 	
}

/*

Di seguito viene mostrato un esempio di test per un semplice controller Spring Boot 
utilizzando ``@SpringBootTest`` e ``TestRestTemplate`` per eseguire richieste HTTP.

Definizione del Controller
--------------------------

Il controller espone un endpoint ``/hello`` che restituisce una semplice stringa.

.. code-block:: java

   @RestController
   @RequestMapping("/api")
   public class HelloController {
       
       @GetMapping("/hello")
       public ResponseEntity<String> sayHello() {
           return ResponseEntity.ok("Hello, World!");
       }
   }

Test del Controller
-------------------

Per testare il controller, utilizziamo ``@SpringBootTest`` insieme a ``TestRestTemplate`` 
per inviare una richiesta HTTP e verificare la risposta.

.. code-block:: java

   @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   @RunWith(SpringRunner.class)
   public class HelloControllerTest {

       @Autowired
       private TestRestTemplate restTemplate;

       @Test
       public void testSayHello() {
           ResponseEntity<String> response = 
               restTemplate.getForEntity("/api/hello", String.class);
           
           assertEquals(HttpStatus.OK, response.getStatusCode());
           assertEquals("Hello, World!", response.getBody());
       }
   }

Spiegazione
-----------

1. **``@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)``**  
   - Avvia un server web su una porta casuale per eseguire test di integrazione.  

2. **``TestRestTemplate``**  
   - Utilizzato per inviare richieste HTTP al server avviato.  

3. **Verifica della risposta**  
   - Controlliamo che lo stato HTTP sia ``200 OK``.  
   - Controlliamo che il corpo della risposta contenga ``"Hello, World!"``.  

Conclusione
-----------

Questo test verifica che il controller risponda correttamente alla richiesta ``GET /api/hello``.  
L'uso di ``TestRestTemplate`` aiuta a simulare una chiamata reale senza dover configurare un client HTTP separato.
*/