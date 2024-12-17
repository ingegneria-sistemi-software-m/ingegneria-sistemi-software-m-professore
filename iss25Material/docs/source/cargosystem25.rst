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

===================================
cargosystem25
===================================

---------------------------------------
Requisiti
---------------------------------------

Progettare e costruire un :blue:`sistema software` per il carico/scarico di prodotti  
in modo automatizzato mediante robot-DDR su di un cargo navale.
I prodotti da caricare/scaricare devono essere stati precedentemente registrati su database.
 
:ref:`Cargo24`
 
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
- Adpater (``AdapterStorage``) per rendere la logica applicativa indipendente dai dispositivi 
  usati per la persistenza.
- Predisposizione di ``AdapterStorage`` per  selezionare la memoria volatile o il database MongoDB
  usando variabili di ambiente. In assenza, uso del singleton 
- Testing in modo automatizzato con JUnit.
- Logging locale su file.
- Logging su ElasticSearch e Kibana.
- Deployment mediante jar.

 

-----------------------------------------
Progetto cargoserviceM2M
-----------------------------------------

Goal: rendere il sistema del :ref:`Progetto cargoproduct` disponibile in rete come (micro)servizio web 
per altri programmi.

        .. image::  ./_static/img/m2m/cargoserviceM2M.JPG
           :align: center 
           :width: 60%  

Appunti per lo sviloppo del prodotto: :ref:`cargoserviceM2M`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

.. File cargoservice.properties per  selezionare la memoria volatile o il database MongoDB

- Uso di Spring e di un componente @RestController per rendere la logica applicativa accessibile via rete ad altri progrsmmi
  (interazione **M2M**).
- Registrazione del servizio su Eureka.
- Interazioni via HTTP (sincrone) e via Web-sockets (asincrone)
- Problema degli accessi concorrenti e come evitare la possibile duplicazione di prodotti.
- Sperimentazione di callers via HTTP e via Web-sockets
- Distribuzione del prodotto software in forma di micro-servizio su Docker.
- Definizione di un caller (``PSLCallerHTTP``) che usa il servizio via HTTP e 
  di un caller (``WebSocketClient``) che usa il servizio via Web-socket.


-----------------------------------------
Progetto cargoserviceM2MGui
-----------------------------------------

Goal: dotare il sistema del :ref:`Progetto cargoserviceM2M` di una GUI per la interazione uomo-macchina.

        .. image::  ./_static/img/m2m/cargoserviceM2MGui.jpg
           :align: center 
           :width: 50%  

Appunti per lo sviloppo del prodotto: :ref:`cargoserviceM2MGui`

+++++++++++++++++++++++++++++++++++++
Key-points cargoserviceM2MGui
+++++++++++++++++++++++++++++++++++++

- Uso di Spring e di un componente @Controller per rendere la logica applicativa accessibile via rete ad 
  esseri umani (interazione **H2M**).
- Realizzare una GUI in HTML e Javascript che invia comandi e riceve sia risposte sia aggiornamenti.
- Aggiornamento della pagina mediante Theamleaf
- Uso di form e dell'operatore ``fetch``  per l'invio di comandi
- Discovery del servizio ``cargoserviceM2M`` mediante Eureka
- Definizione di un caller (``GuiCallerHTTP``) che usa il servizio via HTTP sperimentando diversi tipi 
  di risposta da part del @Controller


-----------------------------------------
Sistema cargoserviceM2M 
-----------------------------------------

Goal: costruire il sistema facendo interagire due micro-servizi deployed su Docker

        .. image::  ./_static/img/m2m/cargoserviceM2MAndGui.jpg
           :align: center 
           :width: 60%  

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