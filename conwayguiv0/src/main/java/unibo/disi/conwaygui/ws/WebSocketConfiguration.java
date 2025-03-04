package unibo.disi.conwaygui.ws;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import unibo.basicomm23.utils.CommUtils;

 
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	public final String wsPath  = "wsupdates";
	//private boolean workWithMqtt = true;  //check ConwayGuiControllerLifeMqtt
	//private boolean workWithMqtt = false;  //check ConwayGuiControllerLifeLocal
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		CommUtils.outblue( "WebSocketConfiguration | registerWebSocketHandlers" );		
			WSConwayguiLifeLocal wsgui = WSConwayguiLifeLocal.getInstance();
			registry.addHandler(wsgui, wsPath).setAllowedOrigins("*");
 	}
}