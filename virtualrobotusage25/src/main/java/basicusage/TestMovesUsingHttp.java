/*
ClientUsingPost.java
===============================================================
Technology-dependent application
TODO. eliminate the communication details from this level
===============================================================
*/
package main.java.basicusage;

//import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import unibo.basicomm23.interfaces.Interaction;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

import java.net.URI;

public class TestMovesUsingHttp {
	private  final String localHostName    = "localhost"; //"localhost"; 192.168.1.7
	private  final int port                = 8090;
	private  final String URL              = "http://"+localHostName+":"+port+"/api/move";
//	private  CloseableHttpClient httpclient ;
	private  JSONParser simpleparser = new JSONParser();	
	 private  String turnrightcmd  = "{\"robotmove\":\"turnRight\"    , \"time\": \"300\"}";
	 private  String turnleftcmd  = "{\"robotmove\":\"turnLeft\"     , \"time\": \"300\"}";
	 private  String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"1500\"}";  //long ...
	 private  String backwardcmd  = "{\"robotmove\":\"moveBackward\" , \"time\": \"2300\"}";
	 private  String haltcmd      = "{\"robotmove\":\"alarm\" ,        \"time\": \"10\"}";
	
	 protected Interaction conn;
	 
	public TestMovesUsingHttp() {
		CommUtils.outyellow("TestMovesUsingHttp  | connecting ... "  );
		conn = ConnectionFactory.createClientSupport(ProtocolType.http,"localhost:8090/api/move","/api/move");
//		httpclient = HttpClients.createDefault();
	} 

  	protected JSONObject callGETHTTP(String crilCmd )  {
  		JSONObject jsonEndmove = null;
		try {
 			StringEntity entity = new StringEntity(crilCmd); 
//			HttpUriRequest httppost = RequestBuilder.post()
//					.setUri(new URI(URL))
//					.setHeader("Content-Type", "application/json")
//					.setHeader("Accept", "application/json")
//					.setEntity(entity)
//					.build();
 			long startTime   = System.currentTimeMillis() ;
 			
			String answer  = conn.request(crilCmd);
			long duration  = System.currentTimeMillis() - startTime;
 			CommUtils.outyellow( "TestMovesUsingHttp" + Thread.currentThread() + " callPOSTHTTP | answer= " + answer + " duration=" + duration );

 			jsonEndmove = (JSONObject) simpleparser.parse(answer);
			CommUtils.outyellow("TestMovesUsingHttp callPOSTHTTP | jsonEndmove=" + jsonEndmove + " duration=" + duration);
		} catch(Exception e){
			CommUtils.outred("TestMovesUsingHttp callPOSTHTTP | " + crilCmd + " ERROR:" + e.getMessage());
		}
		return jsonEndmove;
	}

  	protected String callPOSTHTTP(String crilCmd )  {
  		JSONObject jsonEndmove = null;
  		String answer          = null;
		try {
 			StringEntity entity = new StringEntity(crilCmd); 
 
			long startTime   = System.currentTimeMillis() ;
			
			answer  = conn.request(crilCmd); //Visualizza la risposta come messaggio
			long duration  = System.currentTimeMillis() - startTime;
//			String answer  = EntityUtils.toString(response.getEntity());
//			CommUtils.outyellow( Thread.currentThread() + " callPOSTHTTP | answer= " + answer + " duration=" + duration );

//			jsonEndmove = (JSONObject) simpleparser.parse(answer);
			CommUtils.outyellow("TestMovesUsingHttp callPOSTHTTP | jsonEndmove=" + jsonEndmove + " duration=" + duration);
		} catch(Exception e){
			CommUtils.outred("TestMovesUsingHttp callPOSTHTTP | " + crilCmd + " ERROR:" + e.getMessage());
		}
		return answer;
	}

/*
	BUSINESS LOGIC
*/
	public void doBasicMoves() {
		String result;
		CommUtils.waitTheUser("PUT ROBOT in HOME and hit");
		CommUtils.outblue("STARTING doBasicMoves ... ");
			result = callPOSTHTTP(  turnleftcmd ) ;
			CommUtils.outblue("turnLeft endmove=" + result);
//			result = callPOSTHTTP(  turnrightcmd ) ;
//			CommUtils.outblue("turnRight endmove=" + result);
//		CommUtils.waitTheUser("hit to forward (time 1500)");
//			result = callPOSTHTTP(  forwardcmd  );
//			CommUtils.outblue("moveForward endmove=" + result);
//		CommUtils.waitTheUser("hit to backward (time 2300)");
//		    result = callPOSTHTTP(  backwardcmd );
//			CommUtils.outblue("moveBackward endmove=" + result);
	}
	public void doForward() {
		String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"1000\"}";
		CommUtils.waitTheUser("doForward: PUT ROBOT in HOME  and hit 1CR");
		String result = callPOSTHTTP(  forwardcmd  );
		CommUtils.outblue("moveForward endmove=" + result);
	}
	public void doCollision() {
		String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"3000\"}";
		CommUtils.waitTheUser("doCollision: PUT ROBOT near a wall and hit 1CR");
		String result = callPOSTHTTP(  forwardcmd  );
		CommUtils.outblue("moveForward endmove=" + result);
	}
	
	public void doHalt() {
		String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"3000\"}";
		CommUtils.waitTheUser("doHalt: PUT ROBOT in HOME and hit (forward 3000 and alarm after 1000)");
		sendAlarmAfter(1000);
		String result = callPOSTHTTP(  forwardcmd  );
		CommUtils.outblue("moveForward endmove=" + result);
	}
	
	protected void sendAlarmAfter( int time ){
		new Thread(){
		  	protected  JSONObject mycallHTTP(String crilCmd )  {
		  	     CommUtils.outgreen( Thread.currentThread() + " mycallHTTP starts" );
		  		JSONObject jsonEndmove  = null;
		  		JSONParser mysimpleparser = new JSONParser();
				try {
		 			StringEntity entity = new StringEntity(crilCmd);
					HttpUriRequest httppost = RequestBuilder.post()
							.setUri(new URI(URL))
							.setHeader("Content-Type", "application/json")
							.setHeader("Accept", "application/json")
							.setEntity(entity)
							.build();
					long startTime                 = System.currentTimeMillis() ;
//					CloseableHttpResponse response = httpclient.execute(httppost);
					
					long duration  = System.currentTimeMillis() - startTime;
 					String answer  = conn.request(crilCmd); //EntityUtils.toString(response.getEntity());
					//CommUtils.outgreen( Thread.currentThread() + " mycallHTTP | answer= " + answer + " duration=" + duration );
					jsonEndmove = (JSONObject) mysimpleparser.parse(answer);
					CommUtils.outgreen("TestMovesUsingHttp | "+ Thread.currentThread() + " mycallHTTP | jsonEndmove=" + jsonEndmove + " duration=" + duration);
				} catch(Exception e){
					CommUtils.outred("TestMovesUsingHttp | "+ Thread.currentThread() + " mycallHTTP | " + crilCmd + " ERROR:" + e.getMessage());
				}
				return jsonEndmove;
			}
			public void run(){
				CommUtils.delay(time);
				CommUtils.outgreen("TestMovesUsingHttp | "+ Thread.currentThread() + " send alarm"  );
				JSONObject result = mycallHTTP(  haltcmd  );
				//if( result != null ) 
					CommUtils.outgreen("TestMovesUsingHttp | "+ Thread.currentThread() + " sendAlarmAfter result=" + result);
			}
		}.start();
	}
/*
MAIN
 */
	public static void main(String[] args)   {
		CommUtils.aboutThreads("Before start - "); 
		TestMovesUsingHttp appl = new TestMovesUsingHttp();
//		appl.doBasicMoves();
 		appl.doForward();
//		appl.doCollision();
// 		appl.doHalt();
		CommUtils.aboutThreads("At end - ");
	}
	
 }
