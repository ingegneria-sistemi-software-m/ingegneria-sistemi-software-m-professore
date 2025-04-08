package main.java.conway.devices;
import conwayMqtt.Cell;
import conwayMqtt.IOutDev;
import it.unibo.kactor.ActorBasic;
import unibo.basicomm23.mqtt.MqttConnection;
import unibo.basicomm23.utils.CommUtils;

/*

 */

public class OutDevForActor implements IOutDev{

	protected MqttConnection mqttConn;  	
	protected final String name = "outdevforactor";
	protected ActorBasic gameControl;

  	public OutDevForActor( ActorBasic gameControl) {
 		this.gameControl = gameControl;
 		mqttConn = gameControl.getMqtt().getMqttConn();
  	}
  	
  	protected boolean chcekBrokerConnection() {
	  		while( ! mqttConn.isConnected() ) {
		  			CommUtils.outcyan(name + " | waiting for mqttConn ... "  );
		  			CommUtils.delay(200);
		  	}
		  	//CommUtils.outcyan(name + " | mqtt connection done " + mqttConn); 
		  	return true;
  	}
  	@Override
 	public void display(String msg) {
		try {
			//CommUtils.outgreen("outdevforactor | display  "+ msg + " mqttConn:" + mqttConn);
			if( chcekBrokerConnection() ) ((MqttConnection) mqttConn).publish("guiin",msg);  
			else CommUtils.outred("Sorry: connection with Broker MQTT not available");
		} catch (Exception e) {
 			e.printStackTrace();
		}		
	}

	@Override
	public void displayCell(Cell cell) {
		int value = cell.getState() ? 1 : 0;
		int x = cell.getX() + 1;  //mapping to GUI coords
		int y = cell.getY() + 1;
		String msg = "cell(" + y + "," + x + ","+ value + ")";		
		//CommUtils.outcyan("outdevforactor| displayCell "+ msg);
		display( msg );		
	}

}
