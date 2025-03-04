package unibo.disi.conwaygui;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConwayguiApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("contextLoads");
	}

}

/*
 

Quando si genera un progetto con *Spring Initializr*, viene creata una classe di test predefinita 
annotata con ``@SpringBootTest``. Questa classe ha il ruolo di avviare il contesto completo 
dell'applicazione per eseguire test di integrazione.

Funzionalità principali
-----------------------

1. **Caricamento del contesto Spring**  
   L'annotazione ``@SpringBootTest`` indica che il test deve avviare un contesto Spring Boot completo.  
   Questo è utile per testare componenti che interagiscono con il contesto di Spring, come 
   servizi, repository e controller.

2. **Supporto per test di integrazione**  
   Questa classe può essere usata per verificare il corretto funzionamento dell'applicazione 
   in un ambiente il più vicino possibile a quello di produzione.

3. **Compatibilità con strumenti di testing**  
   - Si integra con JUnit per eseguire test automatici.  
   - Può essere combinata con altre annotazioni, come ``@MockBean`` per il mocking di dipendenze.

Esempio di Classe di Test
-------------------------

.. code-block:: java

   @SpringBootTest
   class MyApplicationTests {

       @Test
       void contextLoads() {
           // Verifica che il contesto venga caricato correttamente
       }
   }

Conclusione
-----------

La classe di test generata da *Spring Initializr* fornisce un punto di partenza per 
scrivere test di integrazione. Grazie a ``@SpringBootTest``, permette di verificare che 
l'applicazione possa avviarsi correttamente e che le dipendenze siano configurate in modo adeguato.

*/
