//package vn.kien.event.eventbe.config;
//
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.messaging.support.MessageHeaderAccessor;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.logging.Logger;
//
//import static java.util.Optional.ofNullable;
//
///**
// * Le-Hong-Quan
// * Date: 16/03/2023
// * Time: 23:53
// */
//@Service
//public class WebSocketChannelInterceptor implements ChannelInterceptor {
//    static final String API_KEY_HEADER = "authKey";
//    static final String SESSION_KEY_HEADER = "simpSessionId";
//    static final String WS_ID_HEADER = "ws-id";
//
//    Logger log = Logger.getLogger(WebSocketChannelInterceptor.class.getName());
//
//    /**
//     * Processes a message before sending it
//     */
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        final StompHeaderAccessor accessor = readHeaderAccessor(message);
//
//        if (accessor.getCommand() == StompCommand.CONNECT) {
//
//            String wsId = readWebSocketIdHeader(accessor);
//            String apiKey = readAuthKeyHeader(accessor);
//            String sessionId = readSessionId(accessor);
//
//            // authenticate the user and if that's successful add their user information to the headers.
//            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(wsId, null);
//            accessor.setUser(user);
//            accessor.setHeader("connection-time", LocalDateTime.now().toString());
//            log.info("User with authKey " + apiKey + ", ws-id " + wsId + " session " + user.toString() + " make a WebSocket connection and generated the user {}");
//
//        }
//
//        return message;
//
//    }
//
//    /**
//     * Instantiate an object for retrieving the STOMP headers
//     */
//    private StompHeaderAccessor readHeaderAccessor(Message<?> message) {
//        final StompHeaderAccessor accessor = getAccessor(message);
//        if (accessor == null) {
//            throw new AuthenticationCredentialsNotFoundException("Fail to read headers.");
//        }
//        return accessor;
//    }
//
//    private String readSessionId(StompHeaderAccessor accessor) {
//        return ofNullable(accessor.getMessageHeaders().get(SESSION_KEY_HEADER))
//                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Session header not found")).toString();
//    }
//
//    private String readAuthKeyHeader(StompHeaderAccessor accessor) {
//        final String authKey = accessor.getFirstNativeHeader(API_KEY_HEADER);
//        if (authKey == null || authKey.trim().isEmpty())
//            throw new AuthenticationCredentialsNotFoundException("Auth Key Not Found");
//        return authKey;
//    }
//
//    private String readWebSocketIdHeader(StompHeaderAccessor accessor) {
//        final String wsId = accessor.getFirstNativeHeader(WS_ID_HEADER);
//        if (wsId == null || wsId.trim().isEmpty())
//            throw new AuthenticationCredentialsNotFoundException("Web Socket ID Header not found");
//        return wsId;
//    }
//
//    StompHeaderAccessor getAccessor(Message<?> message) {
//        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//    }
//
//
//}