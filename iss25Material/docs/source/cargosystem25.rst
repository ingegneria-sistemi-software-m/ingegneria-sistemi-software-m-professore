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
in modo automatizzato mediante ronot su di un cargo.
I prodotti da caricar/scaricare devono essere stati precedentemente registrati su database.
 
:ref:`Cargo24`
 
-----------------------------------------
Progetto cargoproduct
-----------------------------------------

Costruzione di un pplicativo Java che realizza la logica CRUD di gestione di prodotti in uno storage 
di diverse forme: inizialmente una semplice lista in memoria volatile, poi un database MongoDB.

+++++++++++++++++++++++++++++++++++++
Problematiche cargoproduct
+++++++++++++++++++++++++++++++++++++

- Come rendere la logica applicativa indipendente dai dispositivi usati per la persistenza?
- Come selezionare la memoria volatile o il database MongoDB?
- Come definire ed eseguire test in modo automatizzato?
- Come realizzare un sistema di logging locale su file?
- Come distribuire l'applicativo?

-----------------------------------------
Progetto cargoserviceM2M
-----------------------------------------

Goal: rendere ``cargoproduct`` disponibile in rete come servizio web.

+++++++++++++++++++++++++++++++++++++
Problematiche cargoserviceM2M
+++++++++++++++++++++++++++++++++++++

- Come rendere la logica applicativa accessibile via rete ad altri progrsmmi?
- Come evitare la duplicazione di prodotti in caso di acessi concorrenti?
- Come realizzare un sistema di logging distribuito?
- Come distribuire l'applicativo in forma di micro-servizio su Docker?


-----------------------------------------
Progetto cargoserviceM2MGui
-----------------------------------------

Goal: dotare :ref:`Progetto cargoserviceM2M` di una GUI per la interazione uomo-macchina.

+++++++++++++++++++++++++++++++++++++
Problematiche cargoserviceM2MGui
+++++++++++++++++++++++++++++++++++++

- Come realizzare una GUI in HTML5 e Javascript?
- Come permettere la comunicazione asincrona tra GUI e servizio web?