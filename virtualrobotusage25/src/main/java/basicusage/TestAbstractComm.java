package main.java.basicusage;

import org.json.simple.parser.JSONParser;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class TestAbstractComm {
    private Interaction conn;
    private JSONParser simpleparser = new JSONParser();
    private  String turnrightcmd  = "{\"robotmove\":\"turnRight\"    , \"time\": \"300\"}";
    private  String turnleftcmd   = "{\"robotmove\":\"turnLeft\"     , \"time\": \"300\"}";
    private  String forwardcmd    = "{\"robotmove\":\"moveForward\"  , \"time\": \"1000\"}";  //long ...
    private  String backwardcmd   = "{\"robotmove\":\"moveBackward\" , \"time\": \"1000\"}";
    private  String haltcmd       = "{\"robotmove\":\"alarm\" ,        \"time\": \"10\"}";

    public void doForwardHttp() throws Exception {
    	ProtocolType protocol = ProtocolType.http;
            //conn = ConnectionFactory.createClientSupport(protocol, "localhost", ""); //8090/api/move  PAGINA DI ERRORE
            conn = ConnectionFactory.createClientSupport(protocol, "localhost:8090/api/move"," ");
        //String result = conn.request(forwardcmd);  //Fa una GET che ritorna una pagina HTML
        String result = conn.request(forwardcmd);
        CommUtils.outblue("doForwardHttp result=" + result);
    }
    public void doForwardWs() throws Exception {
    	ProtocolType protocol = ProtocolType.ws;
        conn = ConnectionFactory.createClientSupport(protocol, "localhost:8091", ""); //con observer
        conn.forward(forwardcmd);
    }

    public static void main(String[] args) throws Exception {
        CommUtils.aboutThreads("Before start - ");
        TestAbstractComm appl = new TestAbstractComm();
        appl.doForwardHttp();
        //appl.doForwardWs();
        CommUtils.aboutThreads("At end - ");
    }

}
