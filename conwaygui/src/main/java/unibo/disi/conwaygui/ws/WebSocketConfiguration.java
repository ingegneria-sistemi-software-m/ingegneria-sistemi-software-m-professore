package unibo.disi.conwaygui.ws;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import conway.devices.WSIoDev;
import unibo.basicomm23.utils.CommUtils;

 
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	public final String wsPath  = "wsupdates";

    
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		CommUtils.outblue( "WebSocketConfiguration | registerWebSocketHandlers" );		
			WSIoDev wsgui = WSIoDev.getInstance();
			registry.addHandler(wsgui, wsPath).setAllowedOrigins("*");
 	}
}