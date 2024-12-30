.. role:: red
.. role:: blue
.. role:: silde2
.. role:: red 
.. role:: blue 
.. role:: brown 
.. role:: remark
.. role:: worktodo
.. role:: slide
.. role:: slide1
.. role:: slide2
.. role:: slide3
.. role:: slidekp
.. role:: worktodo 

.. _appllevel: file:///C:/Didattica2025/mcrsv24/cargo2025/userDocs/cargoHistory.html#appllevel
.. _cargoenvBase.yml: file:///C:/Didattica2025/mcrsv24/cargo2025/yamls/cargoenvBase.yml
.. _DockerfileM2M: file:///C:/Didattica2025/mcrsv24/cargoserviceM2M/Dockerfile
.. _DockerfileM2MRasp: file:///C:/Didattica2025/mcrsv24/cargoserviceM2M/DockerfileM2mRasp

===================================
cargosystem25
===================================

---------------------------------------
Requisiti
---------------------------------------

Progettare e costruire un :blue:`sistema software` per il carico/scarico di prodotti  
in modo automatizzato mediante robot-DDR su di un cargo navale.
I prodotti da caricare/scaricare devono essere stati precedentemente registrati su database.
 
Versione precedente: :ref:`Cargo24`

-----------------------------------------
Impostazioni preliminari
-----------------------------------------

- Analisi dei requisiti e del problema: individuazione dei :blue:`BoundedContexts` applicativi
- Introduzione alla Clean Architecture e ai principi SOLID
- Comunicazione tra componenti software in termini di :blue:`Business Concepts`
- Introduzione allo sviluppo agile con ``SCRUM``
- Vesro componenti con :brown:`loose-coupling e high-coesion`
- Idea di :blue:`DevOps` e sua evoluzione
- Micro-servizi di supporto attivabili con il file 
  `cargoenvBase.yml`_  (**cargo2025/yamls/cargoenvBase.yml**)



-----------------------------------------
Progetto cargoproduct
-----------------------------------------

Costruzione di un applicativo Java che realizza la logica **CRUD** di gestione di prodotti in uno storage 
di diverse forme: inizialmente una semplice lista in memoria volatile, poi un database MongoDB.

        .. image::  ./_static/img/Cargo/ProductServiceLogic.JPG
           :align: center 
           :width: 60%  

+++++++++++++++++++++++++++++++++++++
Key-points cargoproduct
+++++++++++++++++++++++++++++++++++++

- Impostazione del Workspace Eclipse e di un progetto Gradle con relativo build file.
- Adapter (``AdapterStorage``) per rendere la logica applicativa indipendente dai dispositivi 
  usati per la persistenza.
- Predisposizione di ``AdapterStorage`` per  selezionare la memoria volatile o il database MongoDB
  usando variabili di ambiente. In assenza, uso del singleton ``StorageVolatile``.
- Testing in modo automatizzato con JUnit.
- Logging locale su file.
- Logging su ElasticSearch e Kibana, attivati come micorservizi ELK su Docker.
- Deployment mediante file ``cargoproduct-1.0.jar`` di un componente software che:

   - non è autonomo
   - produce effetti perchè esegue procedure specificate nell'interfaccia ``ICrudOps``
   - nel caso di memoria piena, esegue il metodo ``createProduct`` restituendo una stringa generica di errore 

 

-----------------------------------------
Progetto cargoserviceM2M
-----------------------------------------

Goal: rendere il sistema del :ref:`Progetto cargoproduct` disponibile in rete come (micro)servizio web 
per altri programmi  (interazione **M2M**).

        .. image::  ./_static/img/m2m/cargoserviceM2M.JPG
           :align: center 
           :width: 70%  

Appunti per lo sviluppo del prodotto: :ref:`cargoserviceM2M`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

.. File cargoservice.properties per  selezionare la memoria volatile o il database MongoDB

- Uso di Spring e di un componente @RestController per rendere la logica applicativa accessibile via rete 
  ad altri programmi (interazione **M2M** :brown:`RESTful`).
- @RestController come componente di Spring che riceve richieste HTTP e risponde con oggetti JSON 
  invocando le operazioni dell'interfaccia ``ICrudOps`` realizzate dal singleton ``ProductServiceLogic``.
- Registrazione su Eureka del servizio con nome ``cargoserviceM2M``.
- Interazioni via HTTP (sincrone) e via Web-sockets (asincrone).
- Supporti per interazioni via WS: ``WSServerServiceM2M`` e ``PSLUsageWS``
- Problema degli accessi concorrenti e come evitare la possibile duplicazione di prodotti.
- Distribuzione del prodotto software in forma di micro-servizio su Docker.

File `DockerfileM2M`_ per la generazione della immagine *cargoservice:3.0*
e docker-compsoe file **cargo2025/yamls/cargoServiceNoGui.yml**,
con variabili di ambiente che permettono di usare ``MongoDB``:

  .. code::

    services:
      cargoservice:
        image: cargoservice:3.0 
        #image: natbodocker/cargoservice:3.0
        container_name: cargoservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://mongosrv:27017
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp
        networks:
          - cargo-network

    networks:
      cargo-network:
        external: true


+++++++++++++++++++++++++++++++++++++
cargoserviceM2M su Raspberry
+++++++++++++++++++++++++++++++++++++

File `DockerfileM2MRasp`_ per la generazione della immagine *imgservicerasp:1.0*
e docker-compose file: **cargo2025/yamls/cargoServiceRasp.yml.yml**

.. code::

    services:
          
      cargoservice:
        image: imgservicerasp:1.0 
        #image: natbodocker/imgservicerasp:1.0
        container_name: cargoraspservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://192.168.1.132:27017
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp


+++++++++++++++++++++++++++++++++++++
Uso di cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

- Sperimentazione di callers via HTTP e via Web-sockets (protocollo non limitato ai sistemi Web)
- Definizione di un caller (``PSLCallerHTTP``) che usa il servizio via HTTP e 
  di un caller (``PSLCallerWS``) che usa il servizio via Web-socket.
- L'astrazione :blue:`Interaction`, la libreria ``unibo.basicomm23-1.0``,
  il caller ``PSLCallerWSInteraction`` e
  il caller ``PSLDiscoverCallerHTTPInteraction`` 
  che include la scoperta (*discovery*) del servizio ``cargoserviceM2M`` mediante Eureka.
 

-----------------------------------------
Progetto cargoserviceM2MGui
-----------------------------------------

Goal: dotare il sistema del :ref:`Progetto cargoserviceM2M` di una GUI per la interazione uomo-macchina
(interazione **H2M**).

        .. image::  ./_static/img/m2m/cargoserviceM2MGui.jpg
           :align: center 
           :width: 60%  

Appunti per lo sviluppo del prodotto: :ref:`cargoserviceM2MGui`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2MGui
+++++++++++++++++++++++++++++++++++++

- Uso di Spring e di un componente @Controller per rendere la logica applicativa accessibile via rete ad 
  esseri umani (interazione **H2M**).
- Realizzare una GUI in HTML e Javascript che invia comandi e riceve sia risposte sia aggiornamenti.
- Aggiornamento della pagina mediante Theamleaf
- Uso di form e dell'operatore ``fetch``  per l'invio di comandi come messaggi HTTP.
- Discovery del servizio ``cargoserviceM2M`` mediante Eureka
- Definizione di un caller (``GuiCallerHTTP``) che usa il servizio con Interaction su HTTP, 
  sperimentando diversi tipi  di risposta da part del @Controller


TODO

- Multiple UI, Aggregatori, API Gateway
- User experience
- BFF (Beckends Bor Frontends)

-----------------------------------------
Sistema cargoserviceM2M 
-----------------------------------------

Goal: costruire il sistema facendo interagire due micro-servizi deployed su Docker

        .. image::  ./_static/img/m2m/cargoserviceM2MAndGui.jpg
           :align: center 
           :width: 60%  

- Deploymente mediante File docker-compose: *cargo2025/yamls/cargowareservice.yml* 

  .. code::

    services:
          
      cargoservice:
        #image: cargoservice:3.0  
        image: natbodocker/cargoservice:3.0 
        container_name: cargoservice
        environment:
          - EUREKA_INSTANCE_LEASE_RENEWAL_INTERVAL_IN_SECONDS=60
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - MONGO_URL=mongodb://mongosrv:27017
    #      - RASP_ADDR=192.168.1.248   
        ports:
          - 8111:8111/tcp
          - 8111:8111/udp
        networks:
          - cargo-network


      cargoservicegui:
        #image: cargoservicespringnat:3.0 
        image: natbodocker/cargoservicespringnat:3.0 
        container_name: cargoservicegui
        environment:
          - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka:8761/eureka/
          - SERVICE_URL=http://localhost:8075   ##set to http://CommUtils.getMyPublicip() by PSLControllerWithQak 
          - PRODUCT_SERVICEADDR=cargoservice    
        ports:
          - 8075:8075/tcp
        depends_on:
          - cargoservice
        networks:
          - cargo-network

    networks:
      cargo-network:
        external: true



-----------------------------------------
Sistema cargoserviceM2M con eventi
-----------------------------------------

Goal: estendere il servizio del :ref:`Progetto cargoserviceM2M` in modo che possa emettere informazioni 
in forma di eventi percepibili e visualizzabili nella GUI del :ref:`Progetto cargoserviceM2MGui`


+++++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2M con eventi
+++++++++++++++++++++++++++++++++++++++

- I (micro)servizi non sono solo anemici CRUD-Wrappers
- I (micro)servizi sono concettualmente enti autonomi che possono emettere/percepire eventi e interagire tra loro
  con meccanismi asincroni (es. Web-sockets, publish-subscribe) di comunicazione.
- Il logging distribuito è un meccanismo di monitoraggio e registrazione delle attività e non un meccanismo 
  di comunicazione utile a realizzare in modo generale ed efficiente la comunicazione tra componenti software.
- Introduzione ai tipi logici di messggio `appllevel`_ (*I Protocolli e i dati*)
- Reference: Enterprise Integration Patterns (**EIP**)  


+++++++++++++++++++++++++++++++++++++++
Pub-sub o WebSocket?
+++++++++++++++++++++++++++++++++++++++

.. list-table::
    :widths: 15,35,50
    :width: 100%
    
    * - **Caratteristica**
      - **Broker**    
      - **WebSocket**
    * - Modello
      - Pub-Sub    
      - Bidirezionale, full-duplex
    * - Decoupling
      - Alto    
      - Basso
    * - Scalabilità
      - Alta    
      - Dipende dall'implementazione
    * - Flessibilità
      - Alta    
      - Più limitata
    * - Persistenza
      - Dipende dal broker   
      - Dipende dall'implementazione del server WebSocket
    * - Utilizzi tipici
      - Sistemi distribuiti, streaming, IoT    
      - Applicazioni in tempo reale, chat, notifiche push

===================================
cargosystem25 con attori qak
===================================

-----------------------------------------
Progetto cargoservice
-----------------------------------------

Goal: costruire un micorservizio basato sugli attori.

+++++++++++++++++++++++++++++++++++++
Key-points cargoservice
+++++++++++++++++++++++++++++++++++++

-----------------------------------------
Progetto cargoserviceQakGui
-----------------------------------------

Goal: dotare il sistema del :ref:`Progetto cargoservice` di una GUI per la interazione uomo-macchina.

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceQakGui
+++++++++++++++++++++++++++++++++++++

-----------------------------------------
Progetto cargoserviceM2MQakLocal
-----------------------------------------

+++++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2MQakLocal
+++++++++++++++++++++++++++++++++++++++

Goal: dotare il sistema del :ref:`Progetto cargoservice` di una GUI per la interazione uomo-macchina
evitando la comunicazione via rete tra il RestController della GUI e il serviceqak.


-----------------------------------------
Aggregatori e API Gateway
-----------------------------------------

Sto costruendo un sistema software a microservizi che comprende prodotti, clienti, pagamenti e carico/scarico di mersi su un cargo. La logica di business si può riassumere come segue: un cliente regitsra un prodotto che vuole sia trasportato dal cargo a un certa destinazione, dopo opportuno pagamento. Ho già realizzato i singoli microservizi e vorrei sapere se, per costruire il sistema finale, sia utile, opportuno o necessario introdurre un aggregatore o una API gateway o entrambi.

Introducendo un aggregatore o una API Gateway, puoi migliorare significativamente la progettazione e il funzionamento del tuo sistema a microservizi. Analizziamo la loro utilità in base al tuo contesto:

+++++++++++++++++++++++++++++++++++++++
1. API Gateway
+++++++++++++++++++++++++++++++++++++++

Un'API Gateway è altamente consigliata in un'architettura a microservizi per i seguenti motivi:

- Punto di ingresso unico: Un'API Gateway funge da punto centrale attraverso cui i client (ad esempio, web app o mobile app) interagiscono con i microservizi, eliminando la necessità di conoscere la loro posizione e il loro contratto API.

- Autenticazione e autorizzazione: Può gestire centralmente la sicurezza, come autenticazione JWT, OAuth, o altro.

- Rate Limiting e Throttling: Protegge i tuoi microservizi da accessi eccessivi o attacchi DDoS.

- Orchestrazione semplice: Può instradare richieste multiple ai microservizi appropriati senza che il client debba preoccuparsi di orchestrare le operazioni (ad esempio, chiedere prima i dati di un cliente e poi quelli dei suoi pagamenti).

+++++++++++++++++++++++++++++++++++++++
2. Aggregatore
+++++++++++++++++++++++++++++++++++++++

Un aggregatore, rispetto a una semplice API Gateway, svolge un ruolo più complesso:

- Composizione di servizi: Se ci sono casi in cui i dati di più microservizi devono essere combinati in una risposta unica (es., dati del cliente, del prodotto e dei pagamenti per fornire una vista completa di un ordine), un aggregatore può orchestrare e combinare tali informazioni.
- Riduzione delle chiamate client-microservizi: Piuttosto che il client dover fare più chiamate, l'aggregatore compone i dati e li restituisce in un'unica risposta.

+++++++++++++++++++++++++++++++++++++++
Quando usarli insieme?
+++++++++++++++++++++++++++++++++++++++
In molti casi, è utile usare entrambi, ma con compiti distinti:

#. API Gateway:

  - Gestisce il routing delle richieste e gli aspetti di sicurezza e accesso.
  - Filtra e instrada le richieste ai microservizi o all'aggregatore.

#. Aggregatore:

  - Gestisce la logica di business complessa e le richieste che richiedono dati combinati da più microservizi.

+++++++++++++++++++++++++++++++++++++++
Caso specifico: il tuo sistema
+++++++++++++++++++++++++++++++++++++++

Nel tuo scenario, entrambi potrebbero essere utili per diverse ragioni:

- API Gateway:

  - Utile per centralizzare l'accesso ai microservizi come "Prodotti", "Clienti", "Pagamenti" e "Cargo".
  - Permetterà di gestire la sicurezza e limitare l'esposizione diretta dei microservizi ai client esterni.

- Aggregatore:

  - Può essere usato per costruire una funzionalità di "Gestione Ordine", che compone dati da "Clienti", "Prodotti" e "Pagamenti" per offrire una vista o un'operazione unificata (es. confermare un trasporto).

+++++++++++++++++++++++++++++++++++++++
Esempio di flusso con entrambi:
+++++++++++++++++++++++++++++++++++++++

#. Un cliente effettua una richiesta per registrare un prodotto per il trasporto.
#. La richiesta passa attraverso l'API Gateway, che la instrada al microservizio corretto o all'aggregatore.
#. L'aggregatore raccoglie i dati dai microservizi necessari (ad esempio, verifica il cliente, conferma il pagamento, registra il prodotto e aggiorna il carico del cargo).
#. Il risultato finale è restituito al client tramite l'API Gateway.

+++++++++++++++++++++++++++++++++++++++
AA - Conclusione
+++++++++++++++++++++++++++++++++++++++

- API Gateway: Quasi essenziale in un sistema a microservizi, per gestione di accesso, sicurezza e orchestrazione base.
- Aggregatore: Utile per semplificare la logica complessa e ridurre le chiamate client-microservizi.

Se il sistema è già suddiviso in microservizi, l'introduzione di entrambi i componenti è un passo naturale per migliorarne la scalabilità, la gestione e l'efficienza.

-----------------------------------------
Uso di eventi nel sistema cargo
-----------------------------------------

Con riferimento al problema precedente, vorrei qualche suggerimento e/o considerazione sulle situazioni in cui sia utile che uno dei servizi indicati interagisca con gli altri o gli utenti esterni al sistema, tramire emissione di 'eventi' usando un broker publish-subscribe

L'uso di un broker di messaggistica basato su un modello publish-subscribe può essere estremamente utile in un sistema a microservizi come il tuo. Questo approccio consente ai servizi di comunicare in modo asincrono e decouplato, migliorando la scalabilità e l'estensibilità. Di seguito, alcune considerazioni e suggerimenti relativi al tuo caso:

++++++++++++++++++++++++++++++++++++++++++++++
Quando usare il modello Publish-Subscribe
++++++++++++++++++++++++++++++++++++++++++++++

#. Eventi di dominio importanti:

  - Quando accadono eventi significativi nel sistema (ad esempio, un cliente registra un prodotto per il trasporto, un pagamento è stato completato, o un cargo è stato caricato), è utile notificare altri servizi interessati senza doverli contattare direttamente.
  - Esempio: Quando un pagamento viene completato, il servizio "Pagamenti" potrebbe emettere un evento PaymentConfirmed per notificare altri servizi, come "Cargo" e "Clienti".

#. Riduzione del coupling:

  - Invece di chiamare direttamente un altro servizio (tight coupling), l'emissione di eventi permette ai microservizi di essere indipendenti. I servizi che consumano gli eventi possono cambiare o essere sostituiti senza che il servizio emettitore debba essere modificato.

#. Workflow asincroni:

  - Per processi che non richiedono risposte immediate, l'uso di eventi consente di gestire il flusso senza rallentare il servizio emittente.
  - Esempio: Dopo che un cliente registra un prodotto, un evento ProductRegistered potrebbe avviare l'allocazione dello spazio sul cargo e la generazione di un documento di trasporto.

#. Notifiche agli utenti esterni:

  - Eventi pubblicati dal sistema possono essere usati per notificare gli utenti, ad esempio tramite e-mail, SMS o notifiche push.
  - Esempio: Un evento CargoShipped potrebbe attivare un servizio di notifica che informa il cliente che il prodotto è stato spedito.

++++++++++++++++++++++++++++++++++++++++++++++
Esempi specifici di utilizzo nel tuo sistema
++++++++++++++++++++++++++++++++++++++++++++++

#. 1. Microservizio "Clienti"

  - Evento: CustomerCreated
    - Usato per sincronizzare un nuovo cliente con altri sistemi (es., un CRM esterno o un modulo di reporting).

  - Evento: ProductRequested
    - Emettere un evento quando un cliente registra un prodotto per il trasporto, per avviare la catena di elaborazione.

#. 2. Microservizio "Prodotti"

  - Evento: ProductRegistered
    - Informare i servizi "Pagamenti" e "Cargo" che un nuovo prodotto è stato registrato e può essere elaborato.

  - Evento: ProductDelivered
    - Emettere un evento quando un prodotto viene consegnato, per aggiornare i dati del cliente o inviare notifiche.

#. 3. Microservizio "Pagamenti"

  - Evento: PaymentConfirmed
    - Notificare il servizio "Cargo" che può procedere con l'inserimento del prodotto nella lista di carico.

  - Evento: PaymentFailed
    - Notificare il cliente e fermare l'elaborazione del trasporto.

#. 4. Microservizio "Cargo"

  - Evento: CargoLoaded
    - Informare i sistemi di monitoraggio o generare un avviso ai clienti.

  - Evento: CargoShipped
    - Notificare il servizio di gestione dei clienti e il sistema di reportistica.

++++++++++++++++++++++++++++++++++++++++++++++
Vantaggi dell'approccio Publish-Subscribe
++++++++++++++++++++++++++++++++++++++++++++++

#. Scalabilità:

  - I consumatori possono essere scalati indipendentemente per elaborare grandi volumi di eventi.
  - Ad esempio, se i pagamenti generano molti eventi, il consumatore del servizio "Pagamenti" può scalare senza impattare gli altri.

#. Estensibilità:

  - Nuovi servizi possono essere aggiunti facilmente semplicemente iscrivendosi agli eventi esistenti.
  - Ad esempio, un nuovo servizio di analisi può consumare eventi ProductRegistered o PaymentConfirmed per produrre statistiche.

#. Resilienza:

  - I servizi che pubblicano eventi non si bloccano se un consumatore è temporaneamente offline: il broker (es. RabbitMQ, Kafka, o altri) può bufferizzare i messaggi fino a che i consumatori siano di nuovo disponibili.

++++++++++++++++++++++++++++++++++++++++++++++
Considerazioni sull'implementazione
++++++++++++++++++++++++++++++++++++++++++++++

#. Scelta del broker:

Usa un broker robusto come RabbitMQ, Apache Kafka, o AWS SNS/SQS.
Kafka è ideale per sistemi con eventi a elevato throughput e necessità di persistenza a lungo termine.
RabbitMQ è più semplice da configurare per comunicazioni tradizionali e con flussi di eventi a bassa latenza.

#. Progettazione degli eventi:

Usa un formato standard come JSON o Avro per gli eventi.
Assegna un ID univoco e un timestamp a ogni evento per garantire tracciabilità.
Esempio di payload evento PaymentConfirmed:

.. code::
 
    {
      "eventId": "12345",
      "eventType": "PaymentConfirmed",
      "timestamp": "2023-12-21T12:00:00Z",
      "data": {
        "paymentId": "67890",
        "customerId": "54321",
        "amount": 100.00
      }
    }

#. Gestione degli errori:

- Implementa una dead letter queue (DLQ) per gestire messaggi che non possono essere elaborati.
- Consenti ai consumatori di ripetere l'elaborazione degli eventi in caso di fallimenti temporanei.

#. Monitoraggio:

- Usa strumenti come Prometheus, Grafana, o Elasticsearch per monitorare il traffico degli eventi e rilevare eventuali colli di bottiglia.

++++++++++++++++++++++++++++++++++++++++++++++
Conclusione1
++++++++++++++++++++++++++++++++++++++++++++++
Integrare un sistema di eventi tramite un broker publish-subscribe è estremamente utile per un'architettura a microservizi come la tua. Migliorerà la scalabilità, la resilienza e la flessibilità, consentendo ai servizi di evolversi e interagire senza dipendenze rigide. Progettare eventi chiari e semantici è fondamentale per il successo dell'approccio.


-----------------------------------------
Ma forse ci sono anche svantaggi
-----------------------------------------

Grazie. Credo però che l'uso di eventi con meccanismi publish-subscribe abbia anche qualche svantaggio e ponga diverse problematiche non banali


Hai assolutamente ragione: l'uso di eventi e meccanismi publish-subscribe porta grandi vantaggi, ma introduce anche una serie di sfide e svantaggi che è importante considerare attentamente. Ecco un elenco di problematiche non banali associate a questo approccio, con possibili strategie per mitigarle:

#. 1. Complessità architetturale

  - Problema:

    - L'introduzione di un broker e del paradigma publish-subscribe aumenta la complessità del sistema. Devi gestire la configurazione del broker, garantire la consegna dei messaggi e monitorare l'infrastruttura degli eventi.
  
  - Mitigazione:

    - Usa soluzioni consolidate e ben documentate come Apache Kafka, RabbitMQ o AWS SNS/SQS.
    - Introduci gradualmente gli eventi in base alle necessità, piuttosto che usarli ovunque.
    - Automatizza il deployment e il monitoraggio del broker.

#. 2. Difficoltà di tracciamento (Tracing)
  
    - Problema:
  
      - In un sistema asincrono basato su eventi, è difficile tracciare il flusso di una richiesta end-to-end, specialmente quando molti eventi sono correlati a una singola azione utente.
    
    - Mitigazione:
  
      - Usa tecnologie di distributed tracing come OpenTelemetry o strumenti specifici come Jaeger e Zipkin.
      - Includi correlation ID negli eventi per poterli raggruppare e ricostruire i flussi di elaborazione.

#. 3. Mancanza di controllo diretto

  - Problema:

    - Il servizio che pubblica un evento non ha controllo su come e quando i consumatori elaborano l'evento.
    - I consumatori potrebbero elaborare gli eventi in ritardo o non rispettare le tue aspettative.
  - Mitigazione:

    - Definisci contratti chiari per gli eventi (es., specifiche Avro o JSON Schema).
    - Usa meccanismi di timeout e retry per gestire ritardi e problemi nei consumatori.
    - Implementa una dead letter queue (DLQ) per eventi non elaborabili.

#. 4. Ordine e duplicazione degli eventi
  
    - Problema:
  
      - Garantire l'ordine degli eventi o evitare duplicazioni è complesso.
      - In certi scenari, l'elaborazione fuori ordine può portare a inconsistenze nei dati.
    - Mitigazione:
  
      - Usa un broker che supporti partizionamento e ordinamento nativo (es., Kafka).
      - Disegna i consumatori per essere idempotenti (ossia, capaci di gestire lo stesso evento più volte senza effetti collaterali).
      - Usa sequenze o timestamp negli eventi per rilevare ed eventualmente gestire eventi fuori ordine.

#. 5. Gestione degli errori
  
    - Problema:
  
      - Se un consumatore fallisce durante l'elaborazione, l'evento potrebbe andare perso o bloccarsi in un ciclo di tentativi.
      - Gli errori possono propagarsi silenziosamente senza essere visibili.
    - Mitigazione:
  
      - Implementa una strategia di retry con backoff esponenziale per i consumatori.
      - Configura una DLQ per catturare eventi problematici e analizzarli.
      - Usa metriche e log per identificare rapidamente consumatori che falliscono.

#. 6. Overhead infrastrutturale
    
      - Problema:
    
        - L'introduzione di un broker richiede risorse aggiuntive, sia in termini di hardware che di competenze tecniche.
        - Un broker mal configurato può diventare un single point of failure.
      - Mitigazione:
    
        - Usa servizi gestiti (es., AWS MSK per Kafka o Google Pub/Sub) p
          per ridurre il carico di gestione.
        - Configura il broker per avere ridondanza (es., partizioni replicate in Kafka).
        - Implementa strategie di failover e disaster recovery.

#. 7. Dipendenze implicite
      
        - Problema:
      
          - Sebbene il paradigma publish-subscribe riduca il coupling diretto, introduce dipendenze implicite: i consumatori dipendono dagli eventi pubblicati, ma tali dipendenze non sono sempre visibili o formalmente documentate.
        
        - Mitigazione:
      
          - Usa un Event Schema Registry (ad esempio, Confluent Schema Registry per Kafka) per mantenere una documentazione aggiornata degli eventi.
          - Definisci i contratti per gli eventi in modo esplicito e versiona correttamente gli eventi per gestire modifiche nel tempo.


#. 8. Debugging più complesso
  
    - Problema:
  
      - Individuare la causa di un problema in un sistema distribuito basato su eventi richiede strumenti e competenze specializzate.
    - Mitigazione:
  
      - Implementa logging strutturato per gli eventi.
      - Includi nei log informazioni utili per identificare l'origine e il percorso dell'evento (es., timestamp, producer, consumer).

#. 9. Overhead sui dati
    
      - Problema:
    
        - Gli eventi possono contenere molti dati per supportare casi d'uso generici, aumentando il consumo di memoria e banda.
      - Mitigazione:
    
        - Riduci i dati negli eventi, includendo solo ciò che è strettamente necessario.
        - Usa un approccio event-carried state transfer (l'evento trasporta solo dati rilevanti) o un approccio event sourcing se il sistema richiede un livello maggiore di persistenza.

#. 10. Problemi di consistenza

    - Problema:
      
      - Un modello basato su eventi può introdurre consistenza eventuale, creando situazioni in cui diversi servizi hanno uno stato divergente per un certo periodo di tempo.
    
    - Mitigazione:
    
      - Progetta il sistema per tollerare inconsistenze temporanee.
      - Usa una CQRS (Command Query Responsibility Segregation) se necessario per separare i comandi dagli eventi di lettura.

++++++++++++++++++++++++++++++++++++++++++++++
Conclusione2
++++++++++++++++++++++++++++++++++++++++++++++

Sebbene il modello publish-subscribe porti benefici significativi, la sua implementazione non è priva di sfide. Per mitigare questi problemi, è cruciale:

- Progettare il sistema tenendo conto delle problematiche di tracciabilità, ordine e gestione degli errori.
- Avere una chiara strategia di logging, monitoraggio e testing.
- Introdurre gli eventi in modo incrementale, partendo dai casi d'uso in cui offrono il massimo valore.

Adottando le giuste tecniche e strumenti, i benefici possono superare ampiamente i costi e i rischi associati.







