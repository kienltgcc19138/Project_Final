package vn.kien.event.eventbe.converter;

import vn.kien.event.eventbe.entity.Messages;
import vn.kien.event.eventbe.response.MessagesResponseEvent;

import java.util.LinkedHashSet;

/**
 * Le-Hong-Quan
 * Date: 12/03/2023
 * Time: 18:40
 */
public class MessagesConverter {
    public static MessagesResponseEvent convertToMessagesResponseEvent(Messages messages) {
        if (messages == null) return null;
        MessagesResponseEvent messagesResponseEvent = new MessagesResponseEvent();
        messagesResponseEvent.setUsersId(messages.getUsers().getUsersId());
        messagesResponseEvent.setUsersResponse(UsersConverter.convertToUsersResponseWithoutCourseMajorEvent(messages.getUsers()));
        messagesResponseEvent.setCreatedBy(messages.getCreatedBy());
        messagesResponseEvent.setDescription(messages.getDescription());
        messagesResponseEvent.setCreatedAt(messages.getCreatedAt());
        return messagesResponseEvent;
    }

    public static LinkedHashSet<MessagesResponseEvent> convertToMessagesResponseEvents(LinkedHashSet<Messages> messages) {
        LinkedHashSet<MessagesResponseEvent> messagesResponseEvents = new LinkedHashSet<>();
        for (Messages message : messages) {
            messagesResponseEvents.add(convertToMessagesResponseEvent(message));
        }
        return messagesResponseEvents;
    }
}
