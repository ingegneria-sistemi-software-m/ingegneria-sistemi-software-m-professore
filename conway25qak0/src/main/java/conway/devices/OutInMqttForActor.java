package main.java.conway.devices;
import conwayMqtt.Cell;
import conwayMqtt.IOutDev;
import conwayMqtt.LifeController;
import it.unibo.kactor.ActorBasic;
import main.java.conway.LifeUsageHelper;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.mqtt.MqttInteraction;
import unibo.basicomm23.utils.CommUtils;



public class OutInMqttForActor implements IOutDev{

	private Interaction mqttConn;  	
	//private LifeController lifeontroller;
	private final String name = "outinmqttlife";
	private ActorBasic gameControl;

 	public OutInMqttForActor( ActorBasic gameControl) {
 		this.gameControl = gameControl;
		String broker = System.getenv("MQTTBROKER_URL");
		if( broker == null ) {
			String lcoalAddr = CommUtils.getServerLocalIp();
			//broker = "tcp://localhost:1883";   //in locale outof docker
			broker = "tcp://"+lcoalAddr+":1883"; //se GUI attivata in docker
			CommUtils.outgreen( name + " |  outside docker " + broker + " lcoalAddr=" + lcoalAddr);
		}
		else {
			CommUtils.outgreen( name + " |  in docker to " + broker ); 	
		}					

  		mqttConn = new MqttInteraction( name , broker, "lifein", "guiin" );
  		CommUtils.outcyan(name + " | mqtt connection done " + mqttConn);
  		activateReceive();
	}
 	
	@Override
	public void display(String msg) {
		try {
//			CommUtils.outcyan("OutInMqtt| display forward "+ msg);
			mqttConn.forward(msg);  
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
		//CommUtils.outcyan("OutInMqtt| displayCell "+ msg);
		display( msg );		
	}
	
	
	public void activateReceive() {
		//Tronco encefalico ....
		CommUtils.outcyan(name + " | OutInMqtt activateReceive   "  );
		new Thread() {
			public void run() {
				try {
					while (true) {
						String msg = mqttConn.receiveMsg();
						CommUtils.outcyan(name + " |  receivs: " + msg);
						if( gameControl == null ) continue;  //defensive ....
						//elabMsg(msg);
						cvtToApplMessage( msg );
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();		
	}

	protected void cvtToApplMessage( String msg ) {
		IApplMessage applMsg = null;
		if( msg.startsWith("cell") ) {
			String[] parts = msg.split("-");  //cell-3-2
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			//WARNING: si esclude l'intervento di gameControl!!!!!!!!!!!
			LifeUsageHelper.getInstance().swithCellState(x-1, y-1);  //La GUI comincia da (1,1)
 			//gameControl.swithCellState(x-1, y-1);  //La GUI comincia da (1,1)
			return;
		}
		if( msg.equals("start")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "startGame", "startGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("stop")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "stopGame", "stopGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("clear")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "clearGame", "clearGame(fromgui)", gameControl.getName());
		}
		if( msg.equals("exit")) {
			applMsg = CommUtils.buildDispatch(
				gameControl.getName(), "exitGame", "exitGame(fromgui)", gameControl.getName());
		}
		gameControl.sendMsgToMyself(applMsg);
	}
	/*
	 * Elabora comandi ricevuti via MQTT
	 */
	public void elabMsg(String message) {
		CommUtils.outgreen(name + " | elabMsggg:" + message + " gameControl=" + gameControl);
 		if( gameControl == null ) return;
 		cvtToApplMessage( message );	
 	}


}
