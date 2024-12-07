===================================
cargoserviceM2M
===================================



-------------------------------
M2M start
-------------------------------

#. Si veda: file:///C:/Didattica2024/qak24/iss24Material/docs/_build/html/SpringBootIntro.html?highlight=spring
#. Eseguire https://spring.io/

        .. image::  ./_static/img/M2M/springiostart.png
           :align: center 
           :width: 90%  

.. code::

    unzip
    gradlew build

Creare:

.. code::

    src\main\resources\application.properties
    src\main\resources\banner.txt


-------------------------------
M2M first run
-------------------------------

.. code::

    gradlew booturn (INFO messages)
    http://localhost:9111/ (Whitelabel Error Page)

.. eliminare src/test/java/unibo/disi/cargoserviceM2M/CargoserviceM2MApplicationTests.java

-------------------------------
M2M required library
-------------------------------

Inserire (necessaria per l'applicazione):

.. code::

    applibs
        cargoproduct-1.0.jar

-------------------------------
M2M build.gradle
-------------------------------

.. code::

    build.gradle 
        dependencies
        plugins ... 
            id 'org.springframework.boot' version '3.3.4'
            id 'eclipse' 
            id 'application'
            
        version = '1.0'
        repositories {
            mavenCentral()
            flatDir {   dirs '../unibolibs'	 }
            flatDir {	dirs 'applibs'		 }
        }        
        dependencies{
            ...
            implementation 'com.googlecode.json-simple:json-simple:1.1.1'
            implementation group: 'org.json', name: 'json', version: '20180130'

            /* Elasticsearch   */
            implementation("net.logstash.logback:logstash-logback-encoder:7.3")  
            //ATTENZIONE ALLA COMPATIBILITA DELLA VESRSIONE

            implementation name: 'uniboInterfaces'
            implementation name: '2p301'
            implementation name: 'unibo.basicomm23-1.0' 
            implementation name: 'cargoproduct-1.0'
        
        application {
            //Define the main class for the application.
            mainClassName    = 'unibo.disi.cargoserviceM2M.CargoserviceM2MApplication'
        }		
    
    gradlew eclipse
    gradlew build

-------------------------------
M2M application and run
-------------------------------

.. code::

    new package src\main\java\unibo\disi\cargoserviceM2M\controller
    M2MCargoServiceController.java
    gradlew run

-------------------------------
M2M usage
-------------------------------

.. code::

    Eseguire: PSLCallerHTTP.java



-------------------------------
M2M logging
-------------------------------

.. code::

    src\main\resources\logback.xml
       definire i <pettern>
         
         
Definire il logger:

.. code::
    
    public class CargoserviceM2MApplication {
        public  final static Logger logger  = org.slf4j.LoggerFactory.getLogger("m2m");  
    
Osservare i messaggi di log in  ``logs\m2m.log``

-------------------------------
M2M testing  local
-------------------------------

.. code::

    src/main/java/unibo/disi/cargoserviceM2M/config/RestTemplateConfig.java
    src/test/java/unibo/disi/cargoserviceM2M/CargoserviceM2MApplicationTests.java
    gradlew build ESEGUE I TESTS -x test LI ESCLUDE

-------------------------------
M2M testing using ELK
-------------------------------

.. code::

    In logback.xml  - aggiungere ELK:
    In build.gradle - aggiungere dipendenza per Elasticsearch 
    
Il file ``logstash.conf`` nel progetto (``cargo2025\src\main\resources\logstash.conf``) 
che definisce il file ``yml`` 
che attiva ``logstash`` e ``elasticsearch`` definisce i **nomi degli index** 
da selezionare in **Kibana**:

.. code::

    cargo-logs-
    m2m-logs-


Eseguire ``src/test/java/unibo/disi/cargoserviceM2M/CargoserviceM2MApplicationTests.java``
ed esplorare **Kibana in http://localhost:5601/**.

-------------------------------
M2M on Docker
-------------------------------

.. code::

    Dockerfile
    cargoserviceM2M.bat
    cargoserviceM2M.yml

    docker-compose -f cargoservicem2m.yml  -p cargoservicem2m up

-------------------------------
M2M discoverable
-------------------------------

#. Estendiamo le dipenendenze in *build.gradle*:

    .. code::

        // Dipendenza per Eureka Client
        implementation 'com.netflix.eureka:eureka-client:1.10.18'   
            //1.10.18 compatibile con immagine Spring Cloud Netflix 3.x  
        // Dipendenza per Jersey Client (per fare richieste HTTP)
        implementation 'com.sun.jersey:jersey-client:1.19.1'
        implementation 'com.netflix.servo:servo-core:0.13.2'

#. Specifichiamo dove si trova Eureka in una variabile di ambiente di  *cargoserviceM2M.yml*:

    .. code::

        cargoservice: 
            image: cargoservice:3.0 
            #image: natbodocker/cargoservice:3.0 
            container_name: cargoservice
            environment:
            - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/

#. Definiamo nella classe **unibo.disi.cargoserviceM2M.EurekaServiceConfig**
   le proprietà rilevanti (*appaName*, *host* e *port*) del servizio da registare:

    .. code::

            //appaName
            @Override
            public String getAppname( ) {
                return "m2mproductservice";
            }

            //host
        	@Override
            public String getHostName(boolean refresh) {
                return "localhost";
            }

            ...


#. Definiamo i possibili URL del server Eureka nel file ``src/main/resources/eureka-client.properties``

    .. code::

        eureka.serviceUrl.defaultZone=http://eureka:8761/eureka/, http://localhost:8761/eureka/

#. Registriamo il servizio in *CargoserviceM2MApplication.java* usando una utility definita in *CommUtils.java*:

    .. code::

        CommUtils.registerService( main.java.EurekaServiceConfig() )

-------------------------------
M2M discoverable usage
-------------------------------

#. Attiviamo il servizio ed eseguiamo un programma client che ne fa il discovery prima di 
   invocarne le operazioni HTTP-RESTful: 
   ``src/unibo/disi/cargoserviceM2M/callers/PSDiscoverCallerHttp.java``

     .. code::

        EurekaClient eurekaClient  = CommUtils.createEurekaClient(); 
        String[]  hostPort = CommUtils.discoverService(eurekaClient,"m2mproductservice");
		
        BASE_URL = "http://HOST:PORT".replace("HOST", hostPort[0]).replace("PORT", hostPort[1]);

        String endpoint = "/createProduct"
 		URL url = new URL(BASE_URL + endpoint);
		
        //Escuzione di HTTP POST
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
		    content.append(inputLine);
		}
        //content.toString() contiene la risposta del server
		in.close();	
		con.disconnect();

-------------------------------
Verso Interaction
-------------------------------

#. Impostiamo qualche utility per la gestione delle richieste ``HTTP`` in *CommUtils.java* in modo da evitare 
   la verbosità precedente. Ad esempio:
   scrivere:

    .. code::

        private void doHTTPCall( String url, String msg ) throws Exception {
            HttpConnection httpConn = new HttpConnection(url);
            org.json.simple.JSONObject answer = httpConn.callHTTP(msg);
            CommUtils.outmagenta("doHTTPCall:" + answer);          
        }

        private void doHLCall( String url, String msg ) throws Exception {
            Interaction conn = ConnectionFactory.createClientSupport(ProtocolType.http, url, msg);
            String answer = conn.request(msg); //fa httpConn.callHTTP(msg)
            CommUtils.outmagenta("doHLCall answer=" + answer);         
        }


#. Il concetto di HttpConnection e quello di Interaction


