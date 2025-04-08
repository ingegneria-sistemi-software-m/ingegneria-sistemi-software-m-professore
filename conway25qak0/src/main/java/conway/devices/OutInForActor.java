package main.java.conway.devices;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;
/*
 * Specializza OutInMqttForActor usando la connessione al
 * boker MQTT stabilita dall'actor gameControl.
 * 
 * I messaggi ricevuti sulla connessione sono visualizzati
 * per vedere che arrivano,
 * ma sono gestiti direttamente da gameControl
 * mediante l'evento kernel_rawmsg
 */

public class OutInForActor extends OutInMqttForActor{

  	public OutInForActor( ActorBasic gameControl) {
  		super(gameControl);
  	}
 	
  	@Override
 	protected void connectToBrokerMqtt() {
//  		CommUtils.delay(2009); //La connessione MQTT richiede tempo ....
  		
  		mqttConn = gameControl.getMqtt().getMqttConn();
  		while( mqttConn == null ) {
  			CommUtils.outcyan(name + " | waiting for mqttConn ... "  );
  			CommUtils.delay(1000);
  			mqttConn = gameControl.getMqtt().getMqttConn();
  		}
  		CommUtils.outcyan(name + " | mqtt connection done " + mqttConn);
  	}
  	
  	@Override
  	public void activateReceive() {
  		CommUtils.outred("OutInMqtt |   activateReceiveeeee not appropriate here ");
  	}
  	
	@Override
	public void display(String msg) {
		try {
			//CommUtils.outgreen("OutInMqtt | display  "+ msg + " mqttConn:" + mqttConn);
			if( mqttConn != null ) ((MqttConnection) mqttConn).publish("guiin",msg);  
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}

}
