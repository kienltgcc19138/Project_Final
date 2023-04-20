package vn.kien.event.eventbe.config;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Le-Hong-Quan
 * Date: 16/03/2023
 * Time: 23:33
 */
public class WebSocketDisconnectHandler<S> implements ApplicationListener<SessionDisconnectEvent> {
    Logger log = Logger.getLogger(WebSocketDisconnectHandler.class.getName());

    public WebSocketDisconnectHandler(SimpMessageSendingOperations messagingTemplate) {
        super();
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Optional.ofNullable(event.getUser()).ifPresent(user ->
                log.info("USer " + user.getName() + " disconnected from session id " + event.getSessionId())
        );
    }
}