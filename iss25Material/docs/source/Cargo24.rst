================================
Cargo24
================================

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
   

 

----------------------------------
C:\Didattica2024\qak24\cargo:
----------------------------------

Nuova versione che prova coi colori Latex e con il ruolo 4

#.  Microservizio di memorizzazione prodotti:  
#.  Come actor attorno a un POJO ``ProductServiceLogic``
#.  ``ProductServiceLogic`` usa ``AdapterStorage`` per realizzare persistenza in memoria 
    (``AdapterStorageList``) oppure su MongoDB (``AdapterStorageMongo``)
#.  Che usa logback.xml, elasticseacrh e logstash attivati usando ``docker-compose-EFKOnly.yml`` 
#.  Che si avvale di ``ProductServiceCallerCoap`` come client
#.  Deployed mediante Dockerfile e ``docker-compose.yml``
#.  Che fa parte di un insieme più ampio di microservizi

----------------------------------
Il mcrsrv ProductService 
----------------------------------

- **ProductService** è una applicazione software che viene distribuita mediante una immagine 
  docker memorizzata su DockerHub.
- All'esterno, *ProductService* appare come un servizio RESTful che espone un endpoint 
  (alla porta ``6001``) per 
  memorizzare prodotti e ottenere informazioni su di essi.
- :blue:`ProductService` può essere cosiderato come un :brown:`microservizio` che fa potrebbe fare parte di 
  un sistema più grande di microservizi.
- Per interagire con :blue:`ProductService` è possibile usare un client RESTful che usa il protocollo CoAP.
  Al momento l'uso di HTTP via curl o Postman è escluso, ma vi è però la possibilità di usare un client 
  TCP sempre verso la porta ``6001``.
- l'URL di aceesso alla risorsa a :blue:`ProductService` è  ``http://<HOST>:6001/ctxcargo/productservice``

    .. code::
      
      protocollo://[username[:password]@]host[:porta][</percorso>][?querystring][#fragment]
    
    Si veda: https://it.wikipedia.org/wiki/Uniform_Resource_Locator



- i contenuti dei messaggi da trasmettere via Coap sono 'standardizzati' nella forma 
  :slide2:`msg(MSGID,MSGTYPE,SENDER,RECEIVER,CONTENT,N)`.

Usando le utility di :slide2:`unibo.basicomm23`, possiamo costruire e inviare un messaggio di richiesta in questo modo:

.. code::

  val CoapConn = 
    ConnectionFactory.createClientSupport(ProtocolType.coap, "localhost:6001","ctxcargo/productservice")

  val msgCreate = MsgUtil.buildRequest(name, "createProduct", "product(5,p5,50)", "productservice")
  //msg(createProduct,request,SENDER,productservice,product(5,p5,50),N)
	
  var Answer    = CoapConn.request( msgCreate )  

.. _TestProduct: ../../../../../qak24/cargo/src/main/java/test/TestProduct.java
.. _TestCargo: ../../../../../qak24/cargo/src/main/java/test/TestCargo.javaxx
.. _Cargo logback.xml: ../../../../../qak24/cargo/src/main/resources/logback.xml


        .. image::  ./_static/img/Cargo/ProductServiceLogic.JPG
           :align: center 
           :width: 80%  


.. code::


    MESSAGE                                                             logger_name
    -----------------------------------------------------------------   ----------------------------------
    storagevolatile | STARTS	                                          storageram

    Product | created 3:{"productId":0,"name":"wrong","weight":0}	      main.java.domain.Product

    productservice | STARTS delegating createProduct to createexecutor	productservice_actor

    Product | created json:{"productId":33,"name":"p33","weight":333}	  main.java.domain.Product

    Product | created 3:{"productId":33,"name":"p33","weight":333}	    main.java.domain.Product

    ProductServiceLogic | creatingProduct:{"productId":33,"name":"p33","weight":333}	
                                                                        main.java.domain.ProductServiceLogic

    StorageOnRam | get:33 : null	                                      StorageOnram

    get 0 from 33	                                                       storageram

    createexecutor | 
    handling msg(createProduct,request,cargoservicecaller,productservice,product('{"productId":33,"name":"p33","weight":333}'),0)	
                                                                        createexecutor_actor

    exec_get1 | ANSWR='{"productId":33,"name":"p33","weight":333}'  	  exec_get_actor

    StorageOnRam | put:33 N=1	                                          StorageOnram

    put '{"productId":33,"name":"p33","weight":333}' in 33	            storageram

    exec_get1 | handle_getProduct ID=33	                                exec_get_actor

    StorageOnRam | get:33 : '{"productId":33,"name":"p33","weight":333}'	StorageOnram

    ProductServiceLogic | createProduct:{"productId":33,"name":"p33","weight":333}	
                                                                        main.java.domain.ProductServiceLogic

    get '{"productId":33,"name":"p33","weight":333}' from 33	          storageram

    Product | created 3:{"productId":33,"name":"p33","weight":333}	    main.java.domain.Product

    Product | created json:{"productId":33,"name":"p33","weight":333}	  main.java.domain.Product

    ProductServiceLogic | creatingProduct:{"productId":33,"name":"p33","weight":333}	main.java.domain.ProductServiceLogic

    createexecutor | 
      handling msg(createProduct,request,cargoservicecaller,productservice,product('{"productId":33,"name":"p33","weight":333}'),0)	
                                                                        createexecutor_actor

    StorageOnRam | get:33 : '{"productId":33,"name":"p33","weight":333}'	StorageOnram

    exec_get2 | handle_getProduct ID=33	                                 exec_get_actor



 


    