# issLab2025
Repository del corso Ingegneria dei Sistemi Software a.a. 2024/2025 - DISI - University of Bologna
  * [Materiale didattico](iss25Material/docs/_build/html)

<h2 id="Fase1">Fase 1</h2>   <!-- comment: ancora personalizzata](https://github.com/anatali/issLab2025/Fase1) -->

### Dagli oggetti ai microservizi (in Java)
  * [Articolo sui microservizi](iss25Material/docs/_build/html/_static/msoIEEE.pdf)
  * [conway25Java](conway25Java): Classi Java che realizzano la parte core del game Life di Conway
  * [conwaygui](conwaygui): Sistema SpringBoot che ingloba [conway25Java](conway25Java) offrendo una GUI come dispositivo di I/O
  * [conway25JavaMqtt](conway25JavaMqtt): GameLife standalone che interagisce via MQTT con il mondo esterno
  * [conwayguialone](conwayguialone): Servizio SpringBoot che offre la GUI per interagire via MQTT con [conway25JavaMqtt](conway25JavaMqtt)  

### Dagli oggetti agli attori 

 * [qakdemo24](qakdemo24): Esempi di uso dei QakActors
 * [qakms025](qakms025): Un attore qak che realizza un servizio osservabile
 * [conwaycellsqaknaive](conwaycellsqaknaive): un sistema di 4 celle-actor create dinamenicamente
 * [conwaycellsqak](conwaycellsqak): celle per un sistema ConwayLife distribuito con gamemaster (orchestratore)
 * [conwaymasterqak](conwaymasterqak): gamematser per le celle  ConwayLife distribuite
