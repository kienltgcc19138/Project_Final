package vn.kien.event.eventbe.converter;

import org.springframework.stereotype.Component;
import vn.kien.event.eventbe.entity.Event;
import vn.kien.event.eventbe.response.EventResponse;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Le-Hong-Quan
 * Date: 01/03/2023
 * Time: 00:32
 */
@Component
public class EventConverter {
    public static EventResponse convertToEventResponses(Event event) {
        if (event == null) return null;
        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setName(event.getName());
        eventResponse.setTopic(event.getTopic());
        eventResponse.setStatus(event.getStatus());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setLocation(event.getLocation());
        eventResponse.setTimeStart(event.getTimeStart());
        eventResponse.setTimeEnd(event.getTimeEnd());
        eventResponse.setScore(event.getScore());
        eventResponse.setCreatedBy(event.getCreatedBy());
        eventResponse.setCreatedAt(event.getCreatedAt());
        eventResponse.setFileDataResponses(FileDataConverter.convertFileDataResponses(event.getFiles()));
        return eventResponse;
    }

    public static EventResponse convertToEventResponseWithoutFile(Event event) {
        if (event == null) return null;
        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setName(event.getName());
        eventResponse.setTopic(event.getTopic());
        eventResponse.setStatus(event.getStatus());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setLocation(event.getLocation());
        eventResponse.setTimeStart(event.getTimeStart());
        eventResponse.setTimeEnd(event.getTimeEnd());
        eventResponse.setScore(event.getScore());
        eventResponse.setCreatedBy(event.getCreatedBy());
        eventResponse.setCreatedAt(event.getCreatedAt());
        return eventResponse;
    }

    public static Set<EventResponse> convertToEventResponses(Set<Event> events) {
        if (events == null) return new LinkedHashSet<>();
        Set<EventResponse> eventResponses = new LinkedHashSet<>();
        for (Event event : events) {
            EventResponse eventResponse = convertToEventResponses(event);
            eventResponses.add(eventResponse);
        }
        return eventResponses;
    }

    public static EventResponse convertToDetailEventResponses(Event event) {
        if (event == null) return null;
        EventResponse eventResponse = new EventResponse();
        eventResponse.setEventId(event.getEventId());
        eventResponse.setName(event.getName());
        eventResponse.setTopic(event.getTopic());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setLocation(event.getLocation());
        eventResponse.setStatus(event.getStatus());
        eventResponse.setTimeStart(event.getTimeStart());
        eventResponse.setTimeEnd(event.getTimeEnd());
        eventResponse.setScore(event.getScore());
        eventResponse.setCreatedBy(event.getCreatedBy());
        eventResponse.setCreatedAt(event.getCreatedAt());
        eventResponse.setFileDataResponses(FileDataConverter.convertFileDataResponses(event.getFiles()));
        eventResponse.setUserEventResponses(UsersEventConverter.convertToUserEventResponseWithUsers(event.getUsers()));
        return eventResponse;
    }
}
