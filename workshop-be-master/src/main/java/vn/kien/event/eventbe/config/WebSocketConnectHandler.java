package vn.kien.event.eventbe.config;

import groovy.util.logging.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.security.Principal;
import java.util.logging.Logger;

/**
 * Le-Hong-Quan
 * Date: 16/03/2023
 * Time: 23:31
 */

public class WebSocketConnectHandler<S> implements ApplicationListener<SessionConnectEvent> {
    Logger log = Logger.getLogger(WebSocketConnectHandler.class.getName());

    public WebSocketConnectHandler(SimpMessageSendingOperations messagingTemplate) {
        super();
    }

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        Principal user = readUser(event);
        log(event, user);
    }

    private void log(SessionConnectEvent event, Principal user) {
        String sessionId = readSessionId(event);
//        log.info("User " + user.getName() + " connected to session id " + sessionId);
    }

    String readSessionId(SessionConnectEvent event) {
        return SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders());
    }

    Principal readUser(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        return SimpMessageHeaderAccessor.getUser(headers);
    }

}
