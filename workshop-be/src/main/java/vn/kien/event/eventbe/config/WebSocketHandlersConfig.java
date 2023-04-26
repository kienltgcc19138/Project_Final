package vn.kien.event.eventbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import javax.websocket.Session;

/**
 * Le-Hong-Quan
 * Date: 16/03/2023
 * Time: 23:30
 */
@Configuration
public class WebSocketHandlersConfig<S extends Session> {

    @Bean
    public WebSocketConnectHandler<S> webSocketConnectHandler(SimpMessageSendingOperations messagingTemplate) {
        return new WebSocketConnectHandler<>(messagingTemplate);
    }

    @Bean
    public WebSocketDisconnectHandler<S> webSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate) {
        return new WebSocketDisconnectHandler<>(messagingTemplate);
    }

}